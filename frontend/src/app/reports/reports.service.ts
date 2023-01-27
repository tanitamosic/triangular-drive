import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ReportsService {

  constructor(private httpClient: HttpClient) { }

  getReportsRequest() {
    const request = this.httpClient.get('/api/admin/get-reports');
    return request;
  }

  getBlockRequest(id: Number) {
    const request = this.httpClient.get('/api/admin/block/' + id, {responseType: 'text'});
    return request;
  }

  getWarningRequest(id: Number) {
    const request = this.httpClient.get('/api/admin/block/' + id, {responseType: 'text'});
    return request;
  }

  getSolveRequest(id: Number) {
    const request = this.httpClient.get('/api/admin/block/' + id, {responseType: 'text'});
    return request;
  }

}
