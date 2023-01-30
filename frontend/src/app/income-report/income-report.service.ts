import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class IncomeReportService {

  constructor(private http: HttpClient) { }

  getAvgUserRideCountRequest(date1: Date, date2: Date) {

  }

  getAvgUserDistanceRequest(date1: Date, date2: Date) {

  }

  getAvgDriverIncomeRequest(date1: Date, date2: Date) {
    // DRIVER
  }

  getAvgClientExpenseRequest(date1: Date, date2: Date) {
    // CLIENT
  }

  getTotalUserRideCountRequest(date1: Date, date2: Date) {

  }

  getTotalUserDistanceRequest(date1: Date, date2: Date) {

  }

  getTotalDriverIncomeRequest(date1: Date, date2: Date) {
    // DRIVER
  }

  getTotalClientExpenseRequest(date1: Date, date2: Date) {
    // CLIENT
  }

}
