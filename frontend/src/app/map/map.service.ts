import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  http;
  loginResponse: any;

  constructor(private httpClient: HttpClient) {
    this.http = httpClient;
  }

  get(distance:number) {
    const params = new HttpParams().append('distance', distance);
    let request =  this.http.get('/api/route/price', {params, responseType: 'text'}); 
    return request;
  }

}
