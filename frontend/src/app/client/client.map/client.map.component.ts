import {Component, AfterViewInit} from '@angular/core';
import 'leaflet';
import 'leaflet-routing-machine';
import {GeoSearchControl, OpenStreetMapProvider} from 'leaflet-geosearch';
import "node_modules/leaflet-geosearch/dist/geosearch.css";
import {MapRoute, Stop} from "../../map/MapRoute";
import {SearchResult} from "leaflet-geosearch/lib/providers/provider";
import { MapService } from '../../map/map.service';
import { City } from '../../model/city.class';
import { ProfileService } from '../../profile/profile.service';
import {Car, CarType} from "../../model/car.class";
import {CarService} from "../../car.service";
import {UserService} from "../../user.service";
import {Constants} from "../../constants";
import { HttpClient } from '@angular/common/http';
import { AllCarsSimulation } from 'src/app/model/simulation';
import {ActivatedRoute} from "@angular/router";
import {InputTextModule} from 'primeng/inputtext';

declare var L: any;

@Component({
  selector: 'app-client-map',
  templateUrl: './client.map.component.html',
  styleUrls: ['./client.map.component.scss', './leaflet-routing-machine.css']
})
export class ClientMapComponent implements AfterViewInit {

  private map: any;
  inputCounter: number = 0;
  passengerInputCounter: number = 0;

  start: String = '';
  dest1: String = '';
  dest2: String = '';
  dest3: String = '';
  dest4: String = '';
  final: String = '';
  start_number: String = '';
  dest1_number: String = '';
  dest2_number: String = '';
  dest3_number: String = '';
  dest4_number: String = '';
  final_number: String = '';

  stops_string: string = '';
  stop_strings_array: object|any= {};

  passenger1: String = '';
  passenger2: String = '';
  passenger3: String = '';
  passenger4: String = '';
  passenger5: String = '';
  passenger6: String = '';
  passenger7: String = '';
  passengers_string: string= '';

  price: string = '0';
  distance: string = "0";

  selectedCity: any;
  cities: City[] = [];

  hours_string="";
  minutes_string="";
  hours:number=0;
  minutes:number=0;

  reservationVisible:boolean = false;

  rideStarted:boolean=false;
  rideId:number = 0;
  pollingInterval: NodeJS.Timer | undefined;

  provider: OpenStreetMapProvider;
  searchControl: any;

  mapRoute: MapRoute;

  carTypes: CarType[] = [];
  selectedCarType: any;
  babyFriendly: boolean = false;
  petFriendly: boolean = false;
  minSeats: Number = 1;

  carIcon = L.icon({
    iconUrl: 'assets/map_rescources/car.png',

    iconSize:     [40, 90], // size of the icon
    iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
    popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
  });

  driverMarker = L.marker([45.235866, 19.807387], {icon: this.carIcon});

  constructor(private mapService: MapService,
              private profileService: ProfileService,
              private carService: CarService,
              private userService: UserService,
              private http: HttpClient,
              private route: ActivatedRoute) {

    this.provider = new OpenStreetMapProvider();
    this.mapRoute = new MapRoute();
    this.mapService = mapService;

    const carsRequest = this.carService.getAllCarsRequest();
    carsRequest.subscribe((response)=>{
      let array:Array<Object> = response as Object[];
      array.forEach(e=>{
        let c = new Car(e);
      });
    });

    const citiesRequest = this.profileService.getCitiesRequest();
    citiesRequest.subscribe((response) => {
      this.cities = response as City[];
      this.cities.forEach(c=>{
        if (c.code==="NS"){
          this.selectedCity = c;
        }
      });
    });
    const carTypeRequest = this.carService.getCarTypesRequest();
    carTypeRequest.subscribe((response) => {
      //@ts-ignore
      response.forEach(c => {
        this.carTypes.push(new CarType(c));
        this.carTypes.forEach(c=>{
          if (c.code==="STANDARD"){
            this.selectedCarType = c;
          }
        });
      });
    });

  }

  ngAfterViewInit(): void {
    this.initMap();

    console.log(this.route.snapshot.params);
    let params = this.route.snapshot.params;
    if (params.hasOwnProperty('sstreet')) {
      this.start = params['sstreet'];
      this.start_number = params['snumber'];
      this.final = params['dstreet'];
      this.final_number = params['dnumber'];
    }

  }

  private initMap(): void {
    console.log()
    this.map = L.map('map-client', {
      center: Constants[this.userService.getUser().city.toString()],
      zoom: 14
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });
    tiles.addTo(this.map);
  }


  getDistance() {
    let dist = 0;
    for (let i = 0; i<this.mapRoute.stops.length-1; ++i){
      let current=this.mapRoute.stops[i];
      let next = this.mapRoute.stops[i+1];
      let ll_current= L.latLng(current.x,current.y);
      let ll_next = L.latLng(next.x,next.y);
      dist+=ll_current.distanceTo(ll_next);
    }
    dist = dist/1000;
    this.distance = dist.toPrecision(2);
  }

  getPrice() {
    const request = this.mapService.get(this.distance);
    request.subscribe(data => {
      this.price = data;
      console.log(this.price);
    });
  }

  filterByCity(r:any): any{
    let res: any = [];
    for (let i=0; i<r.length;++i){
      if (r[i].label.includes(this.selectedCity.name))
        res.push(r[i]);
    }
    return res;
  }

  async search() {
    this.getStops();
    this.getPassengers();
    this.drawToMap(); 
  }

  showReservation(){
    this.reservationVisible=!this.reservationVisible;
  }

  checkNumInput(event:Event){
    
  }

  makeReservation(){
    this.hours=Number.parseInt(this.hours_string);
    this.minutes = Number.parseInt(this.minutes_string);
    if (this.hours<5 && this.minutes<60){
      let time = new Date();
      time.setHours(time.getHours()+this.hours);
      time.setMinutes(time.getMinutes()+this.minutes);
      let timeString = time.toISOString();
      console.log(timeString);
      const request = this.http.get('api/client/make-reservation/'+this.userService.getUser().id,
      {headers:{'stops':this.stops_string,'passengers':this.passengers_string,'petFriendly':String(this.petFriendly),
      'babyFriendly':String(this.babyFriendly),'carType':this.selectedCarType.code,'timeString':timeString,'price':this.price}});
      this.rideId = 0;
      request.subscribe(data => {
        this.rideId = Number(data);
      });
    } else{
      alert("Your reservation can be a maximum of 5 hours in advance.")
    }
    
  }

  async requestRide() {
    
    await this.delay(5000);
    
    //alert(this.stops_string);
    const request = this.http.get('api/client/requestRide/'+this.userService.getUser().id,
    {headers:{stops:this.stops_string, passengers:this.passengers_string,'petFriendly':String(this.petFriendly),
    'babyFriendly':String(this.babyFriendly),'carType':this.selectedCarType.code,'price':this.price}});
    request.subscribe(data => {
      this.rideId = Number(data);
      if(this.rideId>0)
        this.pendingRidesPolling();
    });

    await this.delay(4000);

    if(this.rideId===-1){
      alert("Driver Could Not Be Found")
    }
    else if(this.rideId===-2){
      alert("Payment Failed")
    }
    else if (this.rideId===-3){
      alert("One Or More Passengers Dont Exist")
    }
    else{
      //alert("Ride Requested Successfully With Id: "+rideId+', stops: '+this.stops_string);
      alert("Ride Requested Successfully");
      }
  }

  async getStops() {
    this.stops_string='';
    this.stop_strings_array = {0:'',1:'',2:'',3:'',4:'',5:''}

    this.getQueryResult(this.start+" "+this.start_number + ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
      //this.stops_string += this.selectedCity.code+','+this.start+','+this.start_number+','+r[0].y+','+r[0].x;
      this.stop_strings_array[0] = this.selectedCity.code+','+this.start+','+this.start_number+','+r[0].y+','+r[0].x;
    });

    if (this.dest1!=='') {
      this.getQueryResult(this.dest1 +' '+this.dest1_number+ ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
        //this.stops_string +=";"+ this.selectedCity.code+','+this.dest1+','+this.dest1_number+','+r[0].y+','+r[0].x;
        this.stop_strings_array[1] =";"+ this.selectedCity.code+','+this.dest1+','+this.dest1_number+','+r[0].y+','+r[0].x;
      });
    }
    if (this.dest2!=='') {
      this.getQueryResult(this.dest2 +' '+this.dest2_number+ ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
        //this.stops_string +=";"+  this.selectedCity.code+','+this.dest2+','+this.dest2_number+','+r[0].y+','+r[0].x;
        this.stop_strings_array[2] =";"+ this.selectedCity.code+','+this.dest2+','+this.dest2_number+','+r[0].y+','+r[0].x;
      });
        setTimeout(()=>{},1000);
    }
    if (this.dest3!=='') {
      this.getQueryResult(this.dest3 +' '+this.dest3_number+ ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
        //this.stops_string +=";"+  this.selectedCity.code+','+this.dest3+','+this.dest3_number+','+r[0].y+','+r[0].x;
        this.stop_strings_array[3] = ";"+this.selectedCity.code+','+this.dest3+','+this.dest3_number+','+r[0].y+','+r[0].x;
      });
    }
    if (this.dest4!=='') {
      this.getQueryResult(this.dest4 +' '+this.dest4_number+ ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
        //this.stops_string += ";"+ this.selectedCity.code+','+this.dest4+','+this.dest4_number+','+r[0].y+','+r[0].x;
        this.stop_strings_array[4] = ";"+this.selectedCity.code+','+this.dest4+','+this.dest4_number+','+r[0].y+','+r[0].x;
      });
    }

    this.getQueryResult(this.final +' '+this.final_number+ ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
      //this.stops_string +=";"+  this.selectedCity.code+','+this.final+','+this.final_number+','+r[0].y+','+r[0].x;
      this.stop_strings_array[5] =";"+ this.selectedCity.code+','+this.final+','+this.final_number+','+r[0].y+','+r[0].x;
    });

    await this.delay(4000);

    this.stops_string='';
    for (let i = 0; i < 6; i++) {

      if(this.stop_strings_array[i]!==''){
        this.stops_string+=this.stop_strings_array[i];
      }
    }

  }

  getPassengers() {
    this.passengers_string='';
    if (this.passenger1!=='') this.passengers_string+=this.passenger1+';';
    if (this.passenger2!=='') this.passengers_string+=this.passenger2+';';
    if (this.passenger3!=='') this.passengers_string+=this.passenger3+';';
    if (this.passenger4!=='') this.passengers_string+=this.passenger4+';';
    if (this.passenger5!=='') this.passengers_string+=this.passenger5+';';
    if (this.passenger6!=='') this.passengers_string+=this.passenger6+';';
    if (this.passenger7!=='') this.passengers_string+=this.passenger7+';';
  }

  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }

  drawToMap() {
    if ((this.start === '' || this.final === '') || this.start.length < 10 || this.final.length < 10) {
      alert('Your route needs beginning and the end, dummy.')
      return;
    }
    let previousStop = {x: 0, y: 0};
    this.getQueryResult(this.start+" "+this.start_number + ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
      let rf:any = this.filterByCity(r);
      previousStop.x = r[0].x;
      previousStop.y = r[0].y;

      this.addStopToRoute(r);

    });

    if (this.dest1 !== '' && this.dest1.length > 10) {
      this.getQueryResult(this.dest1+' '+this.dest1_number + ', ' + this.selectedCity.name+ ', Republika Srbija').then(r => {
        this.drawRoute(previousStop.x, previousStop.y, r[0].x, r[0].y);
        previousStop.x = r[0].x;
        previousStop.y = r[0].y;

        this.addStopToRoute(r);
      });
    }

    if (this.dest2 !== '' && this.dest2.length > 10) {
      this.getQueryResult(this.dest2 +' '+this.dest2_number + ', ' + this.selectedCity.name+ ', Republika Srbija').then(r => {
        this.drawRoute(previousStop.x, previousStop.y, r[0].x, r[0].y);
        previousStop.x = r[0].x;
        previousStop.y = r[0].y;

        this.addStopToRoute(r);
      });
    }

    if (this.dest3 !== '' && this.dest3.length > 10) {
      this.getQueryResult(this.dest3+' '+this.dest3_number + ', ' + this.selectedCity.name+ ', Republika Srbija').then(r => {
        this.drawRoute(previousStop.x, previousStop.y, r[0].x, r[0].y);
        previousStop.x = r[0].x;
        previousStop.y = r[0].y;

        this.addStopToRoute(r);
      });
    }

    if (this.dest4 !== '' && this.dest4.length > 10) {
      this.getQueryResult(this.dest4+' '+this.dest4_number + ', ' + this.selectedCity.name+ ', Republika Srbija').then(r => {
        this.drawRoute(previousStop.x, previousStop.y, r[0].x, r[0].y);
        previousStop.x = r[0].x;
        previousStop.y = r[0].y;

        this.addStopToRoute(r);
      });
    }
    this.getQueryResult(this.final+' '+this.final_number + ', ' + this.selectedCity.name+ ', Republika Srbija').then(r => {
      this.drawRoute(previousStop.x, previousStop.y, r[0].x, r[0].y);
      previousStop.x = r[0].x;
      previousStop.y = r[0].y;

      this.addStopToRoute(r);
      this.getDistance();
      this.getPrice();
    });

  }

  private addStopToRoute(r: SearchResult<Object>[]) {
    let newStop = new Stop();
    newStop.x = r[0].x;
    newStop.y = r[0].y;
    newStop.address = r[0].label;
    this.mapRoute.addStop(newStop);
  }

  async getQueryResult(query: String) {
    const result = await this.provider.search({query: String(query)})
    console.log(result);
    return result;
  }

  drawRoute(x1: number, y1: number, x2: number, y2: number) {
    L.Routing.control({
      waypoints: [
        L.latLng(y1, x1),
        L.latLng(y2, x2)
      ],

      routeWhileDragging: false
    }).addTo(this.map);
  }

  addDest() {
    if (this.inputCounter === 4) { return; }
    this.inputCounter = this.inputCounter + 1;
  }

  removeDest() {
    if (this.inputCounter === 4) {
      this.dest4 = '';
      this.inputCounter -= 1;
    } else if (this.inputCounter === 3) {
      this.dest3 = '';
      this.inputCounter -= 1;
    } else if (this.inputCounter === 2) {
      this.dest2 = '';
      this.inputCounter -= 1;
    } else if (this.inputCounter === 1) {
      this.dest1 = '';
      this.inputCounter -= 1;
    }
  }

  addPassenger() {
    if (this.passengerInputCounter === 7) { return; }
    this.passengerInputCounter += 1;

  }

  removePassenger() {

    if (this.passengerInputCounter === 7) {
      this.passenger7 = '';
      this.passengerInputCounter -= 1;
    } else if (this.passengerInputCounter === 6) {
      this.passenger6 = '';
      this.passengerInputCounter -= 1;
    } else if (this.passengerInputCounter === 5) {
      this.passenger5 = '';
      this.passengerInputCounter -= 1;
    } else if (this.passengerInputCounter === 4) {
      this.passenger4 = '';
      this.passengerInputCounter -= 1;
    } else if (this.passengerInputCounter === 3) {
      this.passenger3 = '';
      this.passengerInputCounter -= 1;
    } else if (this.passengerInputCounter === 2) {
      this.passenger2 = '';
      this.passengerInputCounter -= 1;
    } else if (this.passengerInputCounter === 1) {
      this.passenger1 = '';
      this.passengerInputCounter -= 1;
    }
  }

  reportDriver(){
    let reason = prompt("Why are you reporting the driver?");
    const request = this.userService.reportDriver(this.rideId, reason);
    request.subscribe();
  }

  
  pendingRidesPolling() {
    this.pollingInterval = setInterval(() =>{
      const request = this.http.get('/api/ride/get/'+this.rideId);
      request.subscribe((response) => {
        let ride:any = response;
        console.log(response);
        if(ride.status==="ONGOING"){
          if(!this.rideStarted) this.rideStarted=true;
        } else if(ride.status==="FINISHED"){
          this.stopPolling();
          window.location.reload();
        }
      })
    }, 5000) // 5s
  };

  stopPolling() {
    clearInterval(this.pollingInterval);
  }


}
