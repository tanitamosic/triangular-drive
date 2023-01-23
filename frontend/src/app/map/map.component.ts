import { Component,  AfterViewInit } from '@angular/core';
import  * as L from 'leaflet';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterViewInit  {

  private map: any;

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
    this.drawMarker();
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
  

  constructor() { 
  }

  ngAfterViewInit(): void {
    this.initMap();
  }


}


