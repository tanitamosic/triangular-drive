import { Component,  AfterViewInit } from '@angular/core';
import 'leaflet';
import 'leaflet-routing-machine';
import {GeoSearchControl, OpenStreetMapProvider} from 'leaflet-geosearch';

declare var L: any;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss', './leaflet-routing-machine.css']
})
export class MapComponent implements AfterViewInit  {

  private map: any;

  provider: OpenStreetMapProvider;
  searchControl: any;
  constructor() {
    this.provider = new OpenStreetMapProvider();
    // @ts-ignore
    this.searchControl = new GeoSearchControl({
      provider: this.provider,
    });
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

    L.Routing.control({
      waypoints: [
        L.latLng(45.257795, 19.834442),
        L.latLng(45.2361365, 19.838948636610578)
      ],
      routeWhileDragging: true
    }).addTo(this.map);
    tiles.addTo(this.map);
    this.drawMarker();

    this.search();

  }

   search() {
    this.provider.search({ query: "Bulevar Despota Stefana 7, 21000 Novi Sad, Republika Srbija" })
      .then((result) => {
        console.log(result);
      });
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







}


