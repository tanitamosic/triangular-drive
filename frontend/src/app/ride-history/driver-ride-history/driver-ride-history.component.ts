import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user.class";
import {UserService} from "../../user.service";
import {RideHistoryService} from "../ride-history.service";

@Component({
  selector: 'app-driver-ride-history',
  templateUrl: './driver-ride-history.component.html',
  styleUrls: ['./driver-ride-history.component.scss']
})
export class DriverRideHistoryComponent implements OnInit {

  rides: any;
  user: User;
  constructor(private userService: UserService, private rideHistoryService: RideHistoryService) {
    this.user = this.userService.getUser();
    const request = this.rideHistoryService.getDriverRideHistory(this.user.id);
    request.subscribe((response) => {
      this.rides = response;
      this.rides.forEach((r: any) => {
        r.passengers.fullNames = this.passengerNames(r.passengers);
        r.price = Math.round((r.price + Number.EPSILON) * 100) / 100
      });
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

}
