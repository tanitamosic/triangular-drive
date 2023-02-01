import { Component, OnInit,Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {RatingModule} from 'primeng/rating';
import {Route, Router} from "@angular/router";
import {DialogModule} from 'primeng/dialog';
import { UserService } from '../user.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent implements OnInit {

  @Input() rideId: number=1;
  @Input() displayReviewModal: boolean = false;
  driverRating: number = 3;
  carRating: number = 3;
  comment: string = '';

  constructor(private http:HttpClient, private router:Router,private userService:UserService) { }

  ngOnInit(): void {
  }


  submitReview(){
    const alert_string = "Driver Rating: " + this.driverRating + "\nCar Rating: " + this.carRating + "\nComment: " + this.comment;
    alert(alert_string);
    let userId = this.userService.getUser().id;
    const request = this.http.post('api/client/reviewRide/'+this.rideId+'/'+userId+'{userId}',{headers:{comment:this.comment,driverRating:this.driverRating,carRating:this.carRating}})
    request.subscribe((response) => {
     alert('Rating posted UWU');
     this.displayReviewModal = false;
    });

  }

}
