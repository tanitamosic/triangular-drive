import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from "../model/user.class";

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

  postImageUploadRequest(img: any) {
    // let config = { headers: new HttpHeaders({'Content-Type': 'multipart/form-data', 'boundary': '----XX----'})};
    let data = new FormData();
    data.append('file', img);
    const request = this.http.post('/api/profile/upload-profile-picture', data);
    return request;
  }

  postProfileUpdateRequest(profile: User) {
    const request = this.http.post('/api/profile/update', profile, {responseType:'text'});
    return request;
  }

  getCitiesRequest() {
    const request = this.http.get('/api/profile/get-cities');
    return request;
  }

  postChangePasswordRequest(oldPassword: String, newPassword1: String, newPassword2: String, userId: Number) {
    let body = {
      oldPassword: oldPassword,
      newPassword1: newPassword1,
      newPassword2: newPassword2,
      userId: userId
    }
    const request = this.http.post('/api/user/change-password', body, {responseType: 'text'});
    return request;
  }

}
