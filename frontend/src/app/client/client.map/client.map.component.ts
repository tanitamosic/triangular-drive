import {Component, AfterViewInit} from '@angular/core';
import 'leaflet';
import 'leaflet-routing-machine';
import {GeoSearchControl, OpenStreetMapProvider} from 'leaflet-geosearch';
import "node_modules/leaflet-geosearch/dist/geosearch.css"
import {MapRoute, Stop} from "../../map/MapRoute";
import {SearchResult} from "leaflet-geosearch/lib/providers/provider";
import { MapService } from '../../map/map.service';
import { City } from '../../model/city.class';
import { ProfileService } from '../../profile/profile.service';
import {CarType} from "../../model/car.class";
import {CarService} from "../../car.service";
import {UserService} from "../../user.service";
import {Constants} from "../../constants";

declare var L: any;

@Component({
  selector: 'app-client-map',
  templateUrl: './client.map.component.html',
  styleUrls: ['./client.map.component.scss', './leaflet-routing-machine.css']
})
export class ClientMapComponent implements AfterViewInit {

  private map: any;
  inputCounter: number = 0;

  start: String = '';
  dest1: String = '';
  dest2: String = '';
  dest3: String = '';
  dest4: String = '';
  final: String = '';
  price: String = '0';
  distance: string = "0";
  selectedCity: any;
  cities: City[] = [];

  provider: OpenStreetMapProvider;
  searchControl: any;

  mapRoute: MapRoute;
  carTypes: CarType[] = [];
  selectedCarType: any;
  babyFriendly: boolean = true;
  petFriendly: boolean = true;
  minSeats: Number = 1;

  constructor(private mapService: MapService,
              private profileService: ProfileService,
              private carService: CarService,
              private userService: UserService) {

    this.provider = new OpenStreetMapProvider();
    this.mapRoute = new MapRoute();
    this.mapService = mapService;
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
      });
    });

  }

  ngAfterViewInit(): void {
    this.initMap();

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

  search() {
    if ((this.start === '' || this.final === '') || this.start.length < 10 || this.final.length < 10) {
      alert('Your route needs beginning and the end, dummy.')
      return;
    }
    let previousStop = {x: 0, y: 0};
    this.getQueryResult(this.start).then(r => {
      let rf:any = this.filterByCity(r);
      previousStop.x = r[0].x;
      previousStop.y = r[0].y;

      this.addStopToRoute(r);

    });

    if (this.dest1 !== '' && this.dest1.length > 10) {
      this.getQueryResult(this.dest1).then(r => {
        this.drawRoute(previousStop.x, previousStop.y, r[0].x, r[0].y);
        previousStop.x = r[0].x;
        previousStop.y = r[0].y;

        this.addStopToRoute(r);
      });
    }

    if (this.dest2 !== '' && this.dest2.length > 10) {
      this.getQueryResult(this.dest2).then(r => {
        this.drawRoute(previousStop.x, previousStop.y, r[0].x, r[0].y);
        previousStop.x = r[0].x;
        previousStop.y = r[0].y;

        this.addStopToRoute(r);
      });
    }

    if (this.dest3 !== '' && this.dest3.length > 10) {
      this.getQueryResult(this.dest3).then(r => {
        this.drawRoute(previousStop.x, previousStop.y, r[0].x, r[0].y);
        previousStop.x = r[0].x;
        previousStop.y = r[0].y;

        this.addStopToRoute(r);
      });
    }

    if (this.dest4 !== '' && this.dest4.length > 10) {
      this.getQueryResult(this.dest4).then(r => {
        this.drawRoute(previousStop.x, previousStop.y, r[0].x, r[0].y);
        previousStop.x = r[0].x;
        previousStop.y = r[0].y;

        this.addStopToRoute(r);
      });
    }
    this.getQueryResult(this.final).then(r => {
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
}
