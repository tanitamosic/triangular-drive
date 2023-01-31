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

  private map: any;
  inputCounter: number = 0;
  pollingInterval: NodeJS.Timer | undefined;

  price: String = '0';
  distance: string = "0";

  provider: OpenStreetMapProvider;
  searchControl: any;
  mapService:MapService;

  mapRoute: MapRoute;
  user: User;
  rides: any;

  simulation:DriverRideSimulation;

  constructor(mapService:MapService, private profileService: ProfileService,
              private httpClient: HttpClient,
              private userService: UserService) {
    this.provider = new OpenStreetMapProvider();
    this.mapRoute = new MapRoute();
    this.mapService = mapService;
    this.simulation = new DriverRideSimulation();

    this.user = this.userService.getUser();

  }

  ngAfterViewInit(): void {
    this.initMap();
    this.sendPosition();
  }

  async  sendPosition() {
    //get coordinates using navigator.geolocation
    let latitude: number = 0;
    let longitude: number = 0;
    navigator.geolocation.getCurrentPosition((position) => {latitude=position.coords.latitude;longitude=position.coords.longitude;});

    await new Promise(r => setTimeout(r, 1000));

    const request = this.httpClient.post('api/driver/updatePosition/'+this.userService.getUser().id+'/'+latitude+'/'+longitude, null);
    request.subscribe();
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


  pendingRidesPolling() {
    this.pollingInterval = setInterval(() =>{
      const request = this.httpClient.get('/api/ride/' + this.user.id + '/assigned-ride');
      request.subscribe((response) => {
        this.rides = response;
      })
    }, 5000) // 5s
  };

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


  // TODO: IMPLEMENT THESE BUTTONS
  acceptRide($event: MouseEvent, id: Number) {
    alert('TODO: ACCEPT RIDE')
  }

  rejectRide($event: MouseEvent, id: Number) {
    let reason = prompt("Thou must state thy reasoning for rejection (uwu): ")
    alert(reason)
  }
}
