import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {User} from "../user.class";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  http;

  constructor(private httpClient: HttpClient) {
    this.http = httpClient;
  }

  getProfileRequest(id: Number) {
    const request = this.http.get(`/api/unregisteredUser/user-profile/${id}`, {responseType: 'text'});
    return request;
  }

}
