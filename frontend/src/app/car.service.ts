import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private httpClient: HttpClient) { }

  getCarTypesRequest() {
    const request = this.httpClient.get('/api/cars/get-all-car-types');
    return request;
  }

  getCarMakersRequest() {
    const request = this.httpClient.get('/api/cars/get-all-car-makers');
    return request;
  }

  getCarColorsRequest() {
    const request = this.httpClient.get('/api/cars/get-all-car-colors');
    return request;
  }
  getAllCarsRequest(){
    const request = this.httpClient.get('/api/cars/get-all');
    return request;
  }
}
