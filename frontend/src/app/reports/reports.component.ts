import { Component, OnInit } from '@angular/core';
import {Report} from "../model/report.class";
import {ReportsService} from "./reports.service";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {
  reports: Report[] = [];

  constructor(private reportsService: ReportsService) {

  }

  ngOnInit(): void {
    const request = this.reportsService.getReportsRequest();
    request.subscribe((response) => {
      //@ts-ignore
      response.forEach(e => {
        this.reports.push(new Report(e));
      })
    })
  }

}
