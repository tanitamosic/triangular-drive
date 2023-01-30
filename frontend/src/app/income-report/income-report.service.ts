import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class IncomeReportService {

  constructor(private http: HttpClient) { }

  getAllRidesRequest(date1: String, date2: String) {
    const request = this.http.get('/api/admin/get-rides/' + date1 + '/' + date2);
    return request
  }

  getDriverRidesRequest(driverId: Number, date1: String, date2: String) {
    const request = this.http.get('/api/driver/get-driver-rides/'+ driverId + '/' + date1 + '/' + date2);
    return request
  }

  getClientRidesRequest(clientId: Number, date1: String, date2: String) {
    const request = this.http.get('/api/client/get-client-rides/' + clientId + '/' + date1 + '/' + date2);
    return request
  }

}
