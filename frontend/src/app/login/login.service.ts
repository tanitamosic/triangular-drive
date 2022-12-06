import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import {Observable, tap, throwError} from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  http;
  loginResponse: any;

  constructor(private httpClient: HttpClient) {
    this.http = httpClient;
  }

  post(email: String, password: String) {
    const request = this.http.post('/api/login', {'email': email, 'password': password}, {responseType: 'text'});
    request.pipe(
      tap(
        {
          next: value => { console.log("This is tap success log: " + value); },
          error: err => { console.log("This is tap error log: " + err); }
        }
      )
    )
    return request;
  }
}
