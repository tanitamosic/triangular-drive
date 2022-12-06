import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  private jwtToken: string = '';

  constructor() {}

  setToken(token: String) {
    this.jwtToken = token.toString();
  }
  getToken() {
    return this.jwtToken;
  }
}
