import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LoginService} from "./login.service";
import { AuthorizationService } from '../authorizationService'
import {UserService} from "../user.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  email: String = '';
  password: String = '';
  loginService: LoginService;
  authService: AuthorizationService;
  userService: UserService;

  @Output() loggedIn = new EventEmitter<boolean>();
  @Output() modalClosed = new EventEmitter<boolean>();
  loginSuccessful: boolean = false;

  response: any;

  constructor(service: LoginService, authService: AuthorizationService, userService: UserService,
              private router: Router, private route: ActivatedRoute) {
    this.loginService = service;
    this.authService = authService;
    this.userService = userService;
  }

  login(event: any): void {
    let request = this.loginService.post(this.email, this.password);
    request.subscribe((response) => {
      this.response = response;
      this.userService.setUser(this.response);
      // check validity
      this.authService.setToken(this.userService.getUserAccessToken());
      this.loggedIn.emit(true);
      this.modalClosed.emit(true);
      this.loginSuccessful = true;

      switch(this.userService.getUser().role) {
        case 'ROLE_CLIENT': { this.router.navigate(['client/home'], {relativeTo: this.route}).then(r => {}); break;}
        case 'ROLE_DRIVER': { this.router.navigate(['driver/home'], {relativeTo: this.route}).then(r => {});break;}
        case 'ROLE_ADMIN': { this.router.navigate(['admin/home'], {relativeTo: this.route}).then(r => {});break;}
        default: { alert("Something went oogabooga wwonggg, we awe sowwy fou dee inconvenince uwu >.< :3");break;}

      }


    },
      (error) => {
        console.error(error);
        alert("Invalid login credentials.");
        //throw error;
      });
  }

  ngOnInit(): void {
  }

}
