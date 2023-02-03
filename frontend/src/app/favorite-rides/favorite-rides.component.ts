import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user.class';
import { RideHistoryService } from '../ride-history/ride-history.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-favorite-rides',
  templateUrl: './favorite-rides.component.html',
  styleUrls: ['./favorite-rides.component.scss']
})
export class FavoriteRidesComponent implements OnInit {
  routes: any;
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
    const request = this.rideHistoryService.getClientFavoriteRoutes(this.user.id);
    request.subscribe((response)=>{
      this.routes=response;
    })
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
    const r = this.http.post("/api/client/favorite-route/"+this.user.id+"/"+route.id,{});
    r.subscribe();
    const request = this.rideHistoryService.getClientFavoriteRoutes(this.user.id);
    request.subscribe((response)=>{
      this.routes=response;
    })
  }


}
