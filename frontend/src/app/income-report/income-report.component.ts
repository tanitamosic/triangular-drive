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

  price: any;
  distance: any;
  count: any;
  data: any;
  rides: any;
  rangeDates: Date[] = [];
  chartOptions: any; // DONT TOUCH

  // broj voznji po danima
  // broj predjenih kilometara
  // kolicina potrosenog/zaradjenog novca
  // kumulativna suma (bar), prosek (line), za sve gore-navedene parametre

  avgRideCount = [0, 0, 0, 0, 0, 0, 0];
  avgDistance = [0, 0, 0, 0, 0, 0, 0];
  avgPrice = [0, 0, 0, 0, 0, 0, 0];

  totalRideCount = [0, 0, 0, 0, 0, 0, 0];
  totalDistance = [0, 0, 0, 0, 0, 0, 0];
  totalPrice = [0, 0, 0, 0, 0, 0, 0];

  dayLabels = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  monthLabels = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October',
  'November', 'December']

  constructor(private userService: UserService, private incomeReportService: IncomeReportService) {
    this.user = this.userService.getUser();
  }

  ngOnInit(): void {
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

  divideNumbers(list1: number[], list2: number[]) {
    const result = [];
    for (let i = 0; i < list1.length; i++) {
      result.push(list1[i] / list2[i]);
    }
    return result;
  }

  getWeeksBetweenDates(startDate: Date, endDate: Date) {
    const startTimestamp = startDate.getTime();
    const endTimestamp = endDate.getTime();
    const differenceInTimestamp = endTimestamp - startTimestamp;
    const differenceInWeeks = differenceInTimestamp / (7 * 24 * 60 * 60 * 1000);
    return Math.ceil(differenceInWeeks);
  }


  drawChart($event: MouseEvent) {
    this.resetCharts();


    let date1 = this.rangeDates[0].toISOString().substring(0,10);
    let date2 = this.rangeDates[1].toISOString().substring(0,10);

    let weeksCount = this.getWeeksBetweenDates(this.rangeDates[0], this.rangeDates[1]);
    console.log(date1);
    console.log(date2);

    switch (this.user.role) {
      case 'ROLE_ADMIN': {
        this.getAllRides(weeksCount, date1, date2);
        break;
      }
      case 'ROLE_DRIVER': {
        this.getDriverRides(weeksCount, date1, date2);
        break;
      }
      case 'ROLE_CLIENT': {
        this.getClientRides(weeksCount, date1, date2);
        break;
      }
      default: { alert('Pick the damn dates, dummy UWU'); return; }
    }

  }

  private chartsData(weeksCount: number) {
    this.rides.forEach((r: any) => {
      let date = new Date(r.departureTime);
      let price = r.price;
      if (this.user.role === 'ROLE_CLIENT') {
        price = price / r.passengers.length;
      }
      this.totalPrice[date.getDay()] += price;
      this.totalDistance[date.getDay()] += r.route.distance;
      this.totalRideCount[date.getDay()] += 1;
    });
    this.avgPrice = this.divideNumbers(this.totalPrice, this.totalRideCount);
    this.avgDistance = this.divideNumbers(this.totalDistance, this.totalRideCount);
    this.avgRideCount = this.totalRideCount.map(num => num / weeksCount);
  }

  initCharts() {
    this.price = {
      labels: this.dayLabels,
      datasets: [
        {
          type: 'line',
          label: 'Weekly average per day',
          borderColor: '#42A5F5',
          borderWidth: 2,
          fill: true,
          data: this.avgPrice
        },
        {
          type: 'bar',
          label: 'Total cash',
          backgroundColor: '#66BB6A',
          data: this.totalPrice
        }

      ]
    }
    this.distance = {
      labels: this.dayLabels,
      datasets: [
        {
          type: 'line',
          label: 'Weekly average per day',
          borderColor: '#42A5F5',
          borderWidth: 2,
          fill: true,
          data: this.avgDistance
        },
        {
          type: 'bar',
          label: 'Total distance',
          backgroundColor: '#66BB6A',
          data: this.totalDistance
        }
      ]
    }
    this.count = {
      labels: this.dayLabels,
      datasets: [
        {
          type: 'line',
          label: 'Weekly average per day',
          borderColor: '#42A5F5',
          borderWidth: 2,
          fill: true,
          data: this.avgRideCount
        },
        {
          type: 'bar',
          label: 'Total ride count',
          backgroundColor: '#66BB6A',
          data: this.totalRideCount
        }
      ]
    }
  }

  getAllRides(weeksCount: number, date1: String, date2: String) {
    const request = this.incomeReportService.getAllRidesRequest(date1, date2);
    request.subscribe((response) => { this.rides = response; this.chartsData(weeksCount); this.initCharts(); });
  }

  getDriverRides(weeksCount: number, date1: String, date2: String) {
    const request = this.incomeReportService.getDriverRidesRequest(this.user.id, date1, date2);
    request.subscribe((response) => { this.rides = response; this.chartsData(weeksCount); this.initCharts(); });
  }

  getClientRides(weeksCount: number, date1: String, date2: String) {
    const request = this.incomeReportService.getClientRidesRequest(this.user.id, date1, date2);
    request.subscribe((response) => { this.rides = response; this.chartsData(weeksCount); this.initCharts(); });
  }

  private resetCharts() {
    this.avgRideCount = [0, 0, 0, 0, 0, 0, 0];
    this.avgDistance = [0, 0, 0, 0, 0, 0, 0];
    this.avgPrice = [0, 0, 0, 0, 0, 0, 0];

    this.totalRideCount = [0, 0, 0, 0, 0, 0, 0];
    this.totalDistance = [0, 0, 0, 0, 0, 0, 0];
    this.totalPrice = [0, 0, 0, 0, 0, 0, 0];

    this.price = {};
    this.distance = {};
    this.count = {};
  }
}
