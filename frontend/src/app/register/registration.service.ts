import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private httpClient: HttpClient) {
  }



  postRegisterRequest(body: Object){
    const request = this.httpClient.post('/api/register', body, {responseType: 'text'});
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
    const request = this.httpClient.post('/api/register/confirm', body, {responseType: 'text'});
    return request;
  }
}
