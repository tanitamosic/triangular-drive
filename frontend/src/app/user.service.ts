import {Injectable} from '@angular/core';
import {User} from "./model/user.class";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  user: User | undefined;

  constructor(private httpClient: HttpClient) {}

  setUser(u: String) {
    this.user = new User(u);
    localStorage.setItem("user", JSON.stringify(this.user));
  }

  getUserAccessToken() {
    if (this.user === undefined) {
      return "NONE";
    }
    return this.user.accessToken;
  }

  onLoad(): void {
    if (localStorage.getItem("user") !== null) {
      // @ts-ignore
      this.user = JSON.parse(localStorage.getItem("user"));
      console.log(this.user);
    }
  }

  getUser(): User {
    return <User>this.user;
  }

  removeUserFromStorage() {
    localStorage.removeItem("user");
  }

  updateDriverStatusRequest(id: Number, status: String) {
    const request = this.httpClient.get("/api/driver/set-status/" + id + "/" + status);
    return request;
  }
}
