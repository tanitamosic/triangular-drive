import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LoginService} from "./login.service";
import { AuthorizationService } from '../authorizationService'

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

  @Output() loggedIn = new EventEmitter<boolean>();
  @Output() modalClosed = new EventEmitter<boolean>();
  loginSuccessful: boolean = false;

  response: any;

  constructor(service: LoginService, authService: AuthorizationService) {
    this.loginService = service;
    this.authService = authService;
  }

  login(event: any): void {
    let request = this.loginService.post('adminmdj@siit.com', 'admin');
    request.subscribe((response) => {
      this.response = response;
      // check validity
      this.authService.setToken(this.response);
      this.loggedIn.emit(true);
      this.modalClosed.emit(true);
      this.loginSuccessful = true;
    });
  }

  ngOnInit(): void {
  }

}
