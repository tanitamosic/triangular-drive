import { Component, OnInit } from '@angular/core';
import {UserService} from "../user.service";
import {User} from "../model/user.class";
import {IncomeReportService} from "./income-report.service";

@Component({
  selector: 'app-income-report',
  templateUrl: './income-report.component.html',
  styleUrls: ['./income-report.component.scss']
})
export class IncomeReportComponent implements OnInit {

  user: User;

  data: any;
  rangeDates: Date[] = [];
  chartOptions: any; // DONT TOUCH

  // broj voznji po danima
  // broj predjenih kilometara
  // kolicina potrosenog/zaradjenog novca
  // kumulativna suma (bar), prosek (line), za sve gore-navedene parametre

  avgRideCount: any;
  avgDistance: any;
  avgIncome: any;
  avgExpense: any;

  totalRideCount: any;
  totalDistance: any;
  totalIncome: any;
  totalExpense: any;

  dayLabels = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
  monthLabels = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October',
  'November', 'December']

  constructor(private userService: UserService, private incomeReportService: IncomeReportService) {
    this.user = this.userService.getUser();
  }

  ngOnInit(): void {
    this.data = {
      labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
      datasets: [
        {
        type: 'line',
        label: 'Dataset 1',
        borderColor: '#42A5F5',
        borderWidth: 2,
        fill: true,
        data: [
          50,
          25,
          12,
          48,
          56,
          76,
          42
        ]
      },
        {
        type: 'bar',
        label: 'Dataset 2',
        backgroundColor: '#66BB6A',
        data: [
          21,
          84,
          24,
          75,
          37,
          65,
          34
        ],
        borderColor: 'white',
        borderWidth: 2
      },
        {
        type: 'bar',
        label: 'Dataset 3',
        backgroundColor: '#FFA726',
        data: [
          41,
          52,
          24,
          74,
          23,
          21,
          32
        ]
      }]
    };

    this.chartOptions =  {
      plugins: {
        legend: {
          labels: {
            color: '#495057'
          }
        }
      },
      scales: {
        x: {
          ticks: {
            color: '#495057'
          },
          grid: {
            color: '#ebedef'
          }
        },
        y: {
          ticks: {
            color: '#495057'
          },
          grid: {
            color: '#ebedef'
          }
        }
      }
    };
  }

  drawChart($event: MouseEvent) {
    console.log(this.rangeDates[0].toISOString().substring(0,10))
    console.log(this.rangeDates[1].toISOString().substring(0,10))
  }

  getAvgUserRideCount() {

  }

  getAvgUserDistance() {

  }

  getAvgDriverIncome() {
    // DRIVER
  }

  getAvgClientExpense() {
    // CLIENT
  }

  getTotalUserRideCount() {

  }

  getTotalUserDistance() {

  }

  getTotalDriverIncome() {
    // DRIVER
  }

  getTotalClientExpense() {
    // CLIENT
  }


}
