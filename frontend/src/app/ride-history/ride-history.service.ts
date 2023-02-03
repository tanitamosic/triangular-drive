import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RideHistoryService {

  constructor(private http: HttpClient) { }

  getClientRideHistory(clientId: Number) {
    const request = this.http.get('/api/ride/getRidesByUserId/' + clientId);
    return request;
  }

  getClientFavoriteRoutes(clientId: Number) {
    const request = this.http.get('/api/client/get-favorites/' + clientId);
    return request;
  }

  getDriverRideHistory(driverId: Number) {
    const request = this.http.get('/api/ride/getRidesByUserId/' + driverId);
    return request;
  }

  getAllRideHistory() {
    const request = this.http.get('/api/ride/get-all-finished-rides');
    return request;
  }

}
