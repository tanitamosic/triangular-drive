import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user.class";

@Component({
  selector: 'app-user-list-display',
  templateUrl: './user-list-display.component.html',
  styleUrls: ['./user-list-display.component.scss']
})
export class UserListDisplayComponent implements OnInit {
  userList: User[] = [];


  constructor(private http: HttpClient) {
    const request = this.http.get('/api/admin/get-users');
    request.subscribe((response) => {
      //@ts-ignore
      this.userList = response;
    })
  }

  ngOnInit(): void {
  }

}
