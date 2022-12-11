import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LoginService} from "./login.service";
import { AuthorizationService } from '../authorizationService'
import {UserService} from "../user.service";

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

  constructor(service: LoginService, authService: AuthorizationService, userService: UserService) {
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
