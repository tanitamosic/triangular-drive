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

  get(): Observable<String>{
    return this.http.get('/api/temp', {responseType: 'text'});
  }

  post() {
    let response;
    const request = this.http.post('/api/temp', {});
    request.subscribe((res) => {
      console.log(res);
      response = res;
    });
    return response;
  }
}
