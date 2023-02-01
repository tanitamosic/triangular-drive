import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user.class";
import {UserService} from "../../user.service";
import {RideHistoryService} from "../ride-history.service";
import {Table} from "primeng/table";

@Component({
  selector: 'app-admin-ride-history',
  templateUrl: './admin-ride-history.component.html',
  styleUrls: ['./admin-ride-history.component.scss']
})
export class AdminRideHistoryComponent implements OnInit {

  rides: any;
  filteredRides: any;
  user: User;
  input: string = '';
  constructor(private userService: UserService, private rideHistoryService: RideHistoryService) {
    this.user = this.userService.getUser();
    const request = this.rideHistoryService.getAllRideHistory();
    request.subscribe((response) => {
      this.rides = response;
      this.rides.forEach((r: any) => {
        r.passengers.fullNames = this.passengerNames(r.passengers);
        r.price = Math.round((r.price + Number.EPSILON) * 100) / 100
      });
      this.filteredRides = this.rides;
    });
  }

  ngOnInit(): void {
  }

  passengerNames(passengerList: any) {
    let retval = '';
    passengerList.forEach((p: any) => {
      retval += p.name + ' ' + p.lastName + ', '
    });
    retval = retval.trim();
    retval = retval.slice(0, retval.length-1);
    return retval;
  }


  clear(dt1: Table) {
    dt1.clear()
  }

  applyFilterGlobal(dt1: Table, $event: any, stringVal: string) {
    dt1.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }
}
