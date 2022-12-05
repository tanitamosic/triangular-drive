import { Component, OnInit } from '@angular/core';
import {LoginService} from "./login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  email: String = '';
  password: String = '';
  loginService: LoginService;

  response: String = '';

  constructor(service: LoginService) {
    this.loginService = service;
  }

  login(event: any): void {
    this.loginService.get().subscribe(
      (res) => {
        this.response = res;
      }
    );
  }

  ngOnInit(): void {
  }

}
