import { Component,  AfterViewInit } from '@angular/core';
import 'leaflet';
import 'leaflet-routing-machine';
import {GeoSearchControl, OpenStreetMapProvider} from 'leaflet-geosearch';
import "node_modules/leaflet-geosearch/dist/geosearch.css"
import {MapRoute, Stop} from "./MapRoute";
import {SearchResult} from "leaflet-geosearch/lib/providers/provider";

declare var L: any;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss', './leaflet-routing-machine.css']
})
export class MapComponent implements AfterViewInit  {

  private map: any;
  inputCounter: number = 0;

  start: String = '';
  dest1: String = '';
  dest2: String = '';
  dest3: String = '';
  dest4: String = '';
  final: String = '';

  provider: OpenStreetMapProvider;
  searchControl: any;

  mapRoute: MapRoute;

  constructor() {
    this.provider = new OpenStreetMapProvider();
    this.mapRoute = new MapRoute();
  }

  ngAfterViewInit(): void {
    this.initMap();

  }

  private initMap(): void {
    console.log()
    this.map = L.map('map', {
      center: [ 45.257795, 19.834442],
      zoom: 14
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });
    tiles.addTo(this.map);
  }

   search() {
      if ((this.start === '' || this.final === '') || this.start.length < 10 || this.final.length < 10) {
        alert('Your route needs beginning and the end, dummy.')
        return;
      }
      let previousStop = {x: 0, y: 0};
      this.getQueryResult(this.start).then(r => {
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
}


