import { Component,  AfterViewInit } from '@angular/core';
import 'leaflet';
import 'leaflet-routing-machine';
import {GeoSearchControl, OpenStreetMapProvider} from 'leaflet-geosearch';
import "node_modules/leaflet-geosearch/dist/geosearch.css"
import {MapRoute, Stop} from "../../map/MapRoute";
import {SearchResult} from "leaflet-geosearch/lib/providers/provider";
import { MapService } from '../../map/map.service';
import { ProfileService } from '../../profile/profile.service';
import {City} from "../../model/city.class";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../user.service";
import {User} from "../../model/user.class";
import { DriverRideSimulation } from 'src/app/model/simulation';


declare var L: any;
@Component({
  selector: 'app-driver-map',
  templateUrl: './driver.map.component.html',
  styleUrls: ['./driver.map.component.scss', './leaflet-routing-machine.css']
})
export class DriverMapComponent implements AfterViewInit {

  pollingTime: number = 1500;
  driverPathLength: number = 10;



  private map: any;
  inputCounter: number = 0;
  pollingInterval: NodeJS.Timer | undefined;

  price: String = '0';
  distance: string = "0";
  driver_position:any;
  
  activeRide: any = null;
  pathToRide: any = null;
  currPathStep: number = 0;
  ongoingRide: boolean = false;


  provider: OpenStreetMapProvider;
  searchControl: any;
  mapService:MapService;

  mapRoute: MapRoute;
  user: User;
  rides: any;
  
  carIcon = L.icon({
    iconUrl: 'assets/map_rescources/car.png',

    iconSize:     [25, 30], // size of the icon
    iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
    popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
  });

  driverMarker = L.marker([45.235866, 19.807387], {icon: this.carIcon});

  constructor(mapService:MapService, private profileService: ProfileService,
              private httpClient: HttpClient,
              private userService: UserService) {
    this.provider = new OpenStreetMapProvider();
    this.mapRoute = new MapRoute();
    this.mapService = mapService;

    this.user = this.userService.getUser();

  }

  ngAfterViewInit() {
    this.initMap();
    this.initDriverPosition();
    this.pollingFunction();
    this.driverPolling();
  }

 


  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }

  updateDriverPosition(){
    if(this.ongoingRide && this.pathToRide !=null){
      this.driver_position.x = this.pathToRide[this.currPathStep].x;
      this.driver_position.y = this.pathToRide[this.currPathStep].y;
      this.currPathStep++;
      if(this.currPathStep == this.pathToRide.length){
        this.currPathStep--;
      }
    }
    else{
      this.driver_position.x += Math.random() * 0.001 - 0.0005;
      this.driver_position.y += Math.random() * 0.001 - 0.0005;
    }
  }

  updateDriverMarker(){
    this.map.removeLayer(this.driverMarker);
    this.driverMarker = L.marker([this.driver_position.x, this.driver_position.y], {icon: this.carIcon});
    this.driverMarker.addTo(this.map);
  }

  private addMarker(lat: number, lng: number): void {
    const marker = L.marker([ lat,lng]).addTo(this.map);
  }

  private initMap(): void {
    // console.log(L.map())
    this.map = L.map('map-driver', {
      center: [45.257795, 19.834442],
      zoom: 14
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });
    tiles.addTo(this.map);
  }

  initDriverPosition(){
    let noviSad = {x:45.25, y:19.834}
    let beograd = {x:44.80567919897563, y:20.462132567172574}
    let jagodina = {x:43.98027021185065, y:21.257504465671992}
    let kragujevac = {x:44.01452576545675, y:20.912502147344274}
    let nis = {x:43.313168397041885, y:21.89867323870089}
    let subotica = {x:46.10433877318924, y:19.6595365451995}
    if(this.user.city == "NS"){
      this.driver_position = noviSad;
    }
    else if(this.user.city == "BG"){
      this.driver_position = beograd;
    }
    else if(this.user.city == "JA"){
      this.driver_position = jagodina;
    }
    else if(this.user.city == "KG"){
      this.driver_position = kragujevac;
    }
    else if(this.user.city == "NI"){
      this.driver_position = nis;
    }
    else if(this.user.city == "SU"){
      this.driver_position = subotica;
    }
  }

  driverPolling() {
    this.pollingInterval = setInterval(() =>{
      this.pollingFunction();
    }, this.pollingTime)
  };

  pollingFunction(){
    this.getAssignedRides();
    this.updateDriverPosition();
    this.updateDriverMarker();
    this.sendPosition();
  }

  getAssignedRides(){
    const request = this.httpClient.get('/api/ride/' + this.user.id + '/assigned-ride');
    request.subscribe((response) => {
      this.rides = response;
    });

  }
  setActiveRide(ride:any) {
    this.activeRide = ride;
    this.drawRoute(ride.route);
    this.calculatePathToRide(ride.route);
    this.ongoingRide = true;
    this.setRideStatus(ride.id, "ONGOING");
  }

  drawRoute(route:any){
    let lastx = route.start.latitude;
    let lasty = route.start.longitude;
    for(let i=0; i<route.stops.length; ++i){
      let stop = route.stops[i];
      this.drawPath(lastx,lasty,stop.latitude,stop.longitude);
      lastx = stop.latitude;
      lasty = stop.longitude;
    }
    this.drawPath(lastx,lasty,route.destination.latitude,route.destination.longitude);
  }

  calculatePathToRide(route:any){
    this.pathToRide = this.divideToSteps(this.driver_position.x,this.driver_position.y,route.start.latitude,route.start.longitude,this.driverPathLength);
    let lastx = route.start.latitude;
    let lasty = route.start.longitude;
    for(let i=0; i<route.stops.length; ++i){
      let stop = route.stops[i];
      let path = this.divideToSteps(lastx,lasty,stop.latitude,stop.longitude,this.driverPathLength);
      this.pathToRide = this.pathToRide.concat(path);
      lastx = stop.latitude;
      lasty = stop.longitude;
    }
    let path = this.divideToSteps(lastx,lasty,route.destination.latitude,route.destination.longitude,this.driverPathLength);
    this.pathToRide = this.pathToRide.concat(path);
  }

  divideToSteps(startx:number, starty:number, endx:number, endy:number, steps:number){
    let stepx = (endx-startx)/steps;
    let stepy = (endy-starty)/steps;
    let path = [];
    let pathString = "";
    for(let i=0; i<steps; ++i){
      path.push({x:startx+stepx*i, y:starty+stepy*i});
    }
    path.push({x:endx, y:endy});
    return path;
  }

  async  sendPosition() {
    const request = this.httpClient.post('api/driver/updatePosition/'+this.userService.getUser().id+'/'+this.driver_position.x+'/'+this.driver_position.y, null);
    request.subscribe();
  }

  stopPolling() {
    clearInterval(this.pollingInterval);
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
    // let res: any = [];
    // for (let i=0; i<r.length;++i){
    //   if (r[i].label.includes(this.selectedCity.name))
    //     res.push(r[i]);
    // }
    // return res;
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

  drawPath(x1: number, y1: number, x2: number, y2: number) {
    L.Routing.control({
      waypoints: [
        L.latLng(x1, y1),
        L.latLng(x2, y2)
      ],

      routeWhileDragging: false
    }).addTo(this.map);
  }


  setRideStatus(rideId:number,status: string) {
    const request = this.httpClient.post('api/driver/setRideStatus/'+rideId+'/'+status, null);
    request.subscribe();
  }
 


  // TODO: IMPLEMENT THESE BUTTONS
  acceptRide($event: MouseEvent, id: Number) {
    //alert('TODO: ACCEPT RIDE')
    //foreach of rides
    for (let i=0; i<this.rides.length; ++i){
      if (this.rides[i].id == id){
        this.setActiveRide(this.rides[i]);
        break;
      }
    }
  }

  rejectRide($event: MouseEvent, id: Number) {
    for (let i=0; i<this.rides.length; ++i){
      if (this.rides[i].id == id){
        let reason = prompt("Why are you rejecting the ride? ")
        let rideId = this.rides[i].id;
        const request = this.userService.reportDriver(rideId, reason);
        request.subscribe();
        const rejRequest = this.httpClient.post("/api/driver/reject-ride",{"rideId":rideId, "reason":reason});
        rejRequest.subscribe();
        break;
      }
    }
  }


  endRide(){
    this.ongoingRide = false;
    this.setRideStatus(this.activeRide.id,"FINISHED");
    this.activeRide = null;
    window.location.reload();
  }

  emergencyStop(){}
}
