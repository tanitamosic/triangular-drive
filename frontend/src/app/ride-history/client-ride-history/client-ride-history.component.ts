import { Component, OnInit } from '@angular/core';
import {UserService} from "../../user.service";
import {RideHistoryService} from "../ride-history.service";
import {User} from "../../model/user.class";
import {Router} from "@angular/router";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'app-ride-history',
  templateUrl: './client-ride-history.component.html',
  styleUrls: ['./client-ride-history.component.scss']
})
export class ClientRideHistoryComponent implements OnInit {
  rides: any;
  user: User;
  showReviewModal: boolean = false;
  rideIdForReview: any;
  deadlineDate: any = new Date();
  constructor(private userService: UserService,
              private rideHistoryService: RideHistoryService,
              private router: Router,
              private http:HttpClient) {
    this.http = http;
    this.user = this.userService.getUser();
    const request = this.rideHistoryService.getClientRideHistory(this.user.id);
    this.deadlineDate.setDate(this.deadlineDate.getDate() - 3);
    request.subscribe((response) => {
      this.rides = response;
      this.rides.forEach((r: any) => {
        r.price = r.price / r.passengers.length;
        r.price = Math.round((r.price + Number.EPSILON) * 100) / 100;

        r.isReviewable = true;
        let departTime = new Date(r.departureTime);
        if (this.deadlineDate > departTime) {
          r.isReviewable = false;
        }
        r.reviews.forEach((v: any) => {
          if (v.passenger.id === this.user.id) {
            r.isReviewable = false;
          }
        })

      });
    });
  }

  ngOnInit(): void {
  }

  orderRide(route: any) {
    let url = 'client/home/query/' + route.start.city + '/' + route.start.street
        + '/' + route.start.number + '/'+route.destination.street+'/'+route.destination.number;
    url = url.replace(/ /g, '%20');
    this.router.navigateByUrl(url);
  }

  favoriteRide(route:any){
    const request = this.http.post("/api/client/favorite-route/"+this.user.id+"/"+route.id,{});
    request.subscribe();
  }

  displayReviewModal(ride: any) {
    if (ride.isReviewable) {
      alert(' NO NO NO UWU')
      return;
    }

    this.rideIdForReview = ride.id;
    this.showReviewModal = true;
  }
}
