import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private httpClient: HttpClient) { }

  postDriverRegistrationRequest(newDriver: Object) {
    const request = this.httpClient.post("/api/admin/register-driver", newDriver, {responseType: 'text'});
    return request;
  }
}
