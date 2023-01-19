import { Component, OnInit } from '@angular/core';
import {UserService} from "../user.service";
import {User} from "../user.class";
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {ProfileService} from "./profile.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: User;
  profileId: Number = 0;

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private profileService: ProfileService) {
    this.user = this.userService.getUser();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.profileId = Number(params['profileId']);
      if (this.profileId !== this.user.id) {
        const request = this.profileService.getProfileRequest(this.profileId);
        request.subscribe((response) => {
          this.user = JSON.parse(response);
        },
          (error) => {
            alert("Something went kaboom, teehee. Non existent user id.")
          })
      }
    });
  }

}
