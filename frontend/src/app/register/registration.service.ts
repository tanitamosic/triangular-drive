import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  http;
  loginResponse: any;

  constructor(private httpClient: HttpClient) {
    this.http = httpClient;
  }



  postRegisterRequest(body: Object){
    const request = this.http.post('/api/register', body, {responseType: 'text'});
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

  postActivationCode(body: Object) {
    const request = this.http.post('/api/register/confirm', body, {responseType: 'text'});
    return request;
  }
}
