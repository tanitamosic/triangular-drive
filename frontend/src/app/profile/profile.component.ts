import { Component, OnInit } from '@angular/core';
import {UserService} from "../user.service";
import {User} from "../user.class";
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: User;
  profileId: Number = 0;

  constructor(private userService: UserService,
              private route: ActivatedRoute) {
    this.user = this.userService.getUser();
  }

  ngOnInit(): void {

  }

}
