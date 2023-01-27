import {Injectable} from '@angular/core';
import {User} from "./user.class";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  user: User | undefined;

  constructor() {}

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

  getUser() {
    return <User>this.user;
  }

}
