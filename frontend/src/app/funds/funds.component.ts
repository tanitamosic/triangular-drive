import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-funds',
  templateUrl: './funds.component.html',
  styleUrls: ['./funds.component.scss']
})
export class FundsComponent implements OnInit {

  funds:number = 0;
  amount:number = 0;


  constructor(private userService:UserService,private http:HttpClient) { }

  ngOnInit(): void {
    this.getFunds();
  }

  async getFunds(){
    let userId = 5;
    //let userId = this.userService.getUser().id;
    const request = this.http.get("/api/client/get-funds/"+userId);
    request.subscribe((response:any)=>{
      this.funds = response;
    });
  }

  async addFunds(){
    if(this.amount<=0){
      alert("Please enter a valid amount (>0)");
      return;
    }
    let userId = 5;
    //let userId = this.userService.getUser().id;
    const request = this.http.post("/api/client/add-funds/"+userId+"/"+this.amount,{});
    request.subscribe((response:any)=>{
      this.funds = response;
    });
  }

}
