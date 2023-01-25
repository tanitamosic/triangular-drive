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



  post(email:String,password:String,name:String,lastName:String,phone:String,city:String){
    const request = this.http.post('/api/register', {'email': email, 'password': password, 
    'name':name, 'lastName':lastName, 'phone': phone, 'city':city}, {responseType: 'text'});
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
