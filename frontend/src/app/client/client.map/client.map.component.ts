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
  passenger1: String = '';
  passenger2: String = '';
  passenger3: String = '';
  passenger4: String = '';
  passenger5: String = '';
  passenger6: String = '';
  passenger7: String = '';
  price: String = '0';
  distance: string = "0";
  selectedCity: any;
  cities: City[] = [];
  simulation:AllCarsSimulation;
  stops_string: string = '';
  passengers_string: string= '';

  provider: OpenStreetMapProvider;
  searchControl: any;

  mapRoute: MapRoute;
  carTypes: CarType[] = [];
  selectedCarType: any;
  babyFriendly: boolean = false;
  petFriendly: boolean = false;
  minSeats: Number = 1;

  constructor(private mapService: MapService,
              private profileService: ProfileService,
              private carService: CarService,
              private userService: UserService,
              private http: HttpClient,
              private route: ActivatedRoute) {

    this.provider = new OpenStreetMapProvider();
    this.mapRoute = new MapRoute();
    this.mapService = mapService;

    this.simulation = new AllCarsSimulation();

    const carsRequest = this.carService.getAllCarsRequest();
    carsRequest.subscribe((response)=>{
      let array:Array<Object> = response as Object[];
      array.forEach(e=>{
        let c = new Car(e);
        this.simulation.allCars.push(c);
      });
      this.simulation.updatePositions();
      this.drawCarMarkers();
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
    // console.log("Distance: "+this.distance);
    // console.log(this.mapRoute);
  }

  getPrice() {
    const request = this.mapService.get(this.distance);
    request.subscribe(data => {
      this.price = data;
      console.log(this.price);
    });

    //this.price = result;

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
    
    //stops = this.selectedCity.code+','+this.start+','+this.start_number+','+start_latitude+','+start_longitude;
   
    
    this.requestRide();
    
  }

  async requestRide() {
    
    await this.delay(2000);
    const request = this.http.get('api/client/requestRide/'+this.userService.getUser().id,{headers:{stops:this.stops_string, passengers:this.passengers_string,'petFriendly':String(this.petFriendly),'babyFriendly':String(this.babyFriendly),'carType':this.selectedCarType.code}});
    let rideId: number = 0;
    request.subscribe(data => {
      rideId = Number(data);
    });

    await this.delay(2000);

    if(rideId===-1){
      alert("Driver Could Not Be Found")
    }
    else if(rideId===-2){
      alert("Payment Failed")
    }
    else if (rideId===-3){
      alert("One Or More Passengers Dont Exist")
    }
    else{
      alert("Ride Requested Successfully With Id: "+rideId+', stops: '+this.stops_string);
      let request_ride_input:any = document.getElementById('request_ride_input');
      //request_ride_input.style.display = 'none';
      request_ride_input.remove();
    }

  
  }

  async getStops() {
    this.stops_string='';

    this.getQueryResult(this.start+" "+this.start_number + ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
      this.stops_string += this.selectedCity.code+','+this.start+','+this.start_number+','+r[0].y+','+r[0].x;
    });

    if (this.dest1!=='') {
      this.getQueryResult(this.dest1 +' '+this.dest1_number+ ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
        this.stops_string +=";"+ this.selectedCity.code+','+this.dest1+','+this.dest1_number+','+r[0].y+','+r[0].x;
      });
    }
    if (this.dest2!=='') {
      this.getQueryResult(this.dest2 +' '+this.dest2_number+ ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
        this.stops_string +=";"+  this.selectedCity.code+','+this.dest2+','+this.dest2_number+','+r[0].y+','+r[0].x;
      });
        setTimeout(()=>{},1000);
    }
    if (this.dest3!=='') {
      this.getQueryResult(this.dest3 +' '+this.dest3_number+ ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
        this.stops_string +=";"+  this.selectedCity.code+','+this.dest3+','+this.dest3_number+','+r[0].y+','+r[0].x;
      });
    }
    if (this.dest4!=='') {
      this.getQueryResult(this.dest4 +' '+this.dest4_number+ ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
        this.stops_string += ";"+ this.selectedCity.code+','+this.dest4+','+this.dest4_number+','+r[0].y+','+r[0].x;
      });
    }

    this.getQueryResult(this.final +' '+this.final_number+ ', ' + this.selectedCity.name + ', Republika Srbija').then(r => {
      this.stops_string +=";"+  this.selectedCity.code+','+this.final+','+this.final_number+','+r[0].y+','+r[0].x;
    });

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
    });
    this.getDistance();
    this.getPrice();
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


  async drawMarker() {
    var carIcon = L.icon({
      iconUrl: 'assets/map_rescources/wheel.png',

      iconSize:     [40, 90], // size of the icon
      iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
      popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
    });
    var lat:number =0;
    var long:number = 0;
    navigator.geolocation.getCurrentPosition((position) => {lat=position.coords.latitude;long=position.coords.longitude;});
    await new Promise(r => setTimeout(r, 1));
    const marker = L.marker([ lat,long],{icon:carIcon}).addTo(this.map);
  }

  drawCarMarkers() {
    var carIcon = L.icon({
      iconUrl: 'assets/map_rescources/car.png',

      iconSize:     [40, 90], // size of the icon
      iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
      popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
    });
    this.simulation.allCars.forEach(c=>{
      console.log("drawcars");
        const marker = L.marker([ c.position[0],c.position[1]],{icon:carIcon}).addTo(this.map);
    })
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

  applyFilter($event: any) {
    console.log(this.selectedCity);
    console.log(this.selectedCarType);
    console.log(this.babyFriendly);
    console.log(this.petFriendly);
    // TODO: FILTER AVAILABLE DRIVERS BY PARAMETERS (CHILD/PET FRIENDLY, CAR TYPE, CITY)
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

}
