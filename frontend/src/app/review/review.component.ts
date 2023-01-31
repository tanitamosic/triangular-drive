import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent implements OnInit {

  driverRating: number = 0;
  carRating: number = 0;
  comment: string = '';

  constructor(private http:HttpClient) { }

  ngOnInit(): void {
  }


  submitReview(){
    const alert_string = "Driver Rating: " + this.driverRating + "\nCar Rating: " + this.carRating + "\nComment: " + this.comment;
    alert(alert_string);
    let userId = 1;
    let rideId = 1;
    const request = this.http.post('api/client/reviewRide/'+rideId+'/'+userId+'{userId}',{headers:{comment:this.comment,driverRating:this.driverRating,carRating:this.carRating}})
    //request.subscribe((response) => {
    //  console.log(response);
    //});

  }

}
