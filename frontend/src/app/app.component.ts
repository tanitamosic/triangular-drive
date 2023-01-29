import { Component } from '@angular/core';

import { PrimeNGConfig } from 'primeng/api';
import {UserService} from "./user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  loggedIn: boolean = false;
  userService: UserService;
  constructor(private primengConfig: PrimeNGConfig, userService: UserService) {
    this.userService = userService
  }

  ngOnInit() {
    this.primengConfig.ripple = true;
    this.userService.onLoad();
    if (this.userService.user != undefined) {
      this.loggedIn = true;
    }
  }
}
