import { Component, OnInit } from '@angular/core';
import {UserService} from "../../user.service";
import {RideHistoryService} from "../ride-history.service";
import {User} from "../../model/user.class";

@Component({
  selector: 'app-ride-history',
  templateUrl: './client-ride-history.component.html',
  styleUrls: ['./client-ride-history.component.scss']
})
export class ClientRideHistoryComponent implements OnInit {
  rides: any;
  user: User;
  constructor(private userService: UserService, private rideHistoryService: RideHistoryService) {
    this.user = this.userService.getUser();
    const request = this.rideHistoryService.getClientRideHistory(this.user.id);
    request.subscribe((response) => {
      this.rides = response;
      this.rides.forEach((r: any) => {
        r.price = r.price / r.passengers.length;
        r.price = Math.round((r.price + Number.EPSILON) * 100) / 100
      });
    });
  }

  ngOnInit(): void {
  }

}
