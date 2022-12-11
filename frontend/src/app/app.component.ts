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

  constructor(private primengConfig: PrimeNGConfig, private userService: UserService) {}

  ngOnInit() {
    this.primengConfig.ripple = true;
    this.userService.onLoad();

  }
}
