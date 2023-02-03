import {Injectable} from '@angular/core';
import {User} from "./model/user.class";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthorizationService} from "./authorizationService";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  user: User | undefined;

  constructor(private httpClient: HttpClient, private router: Router, private authService: AuthorizationService) {}

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
      this.authService.setToken(this.getUserAccessToken());
      // @ts-ignore
      switch (this.user.role) {
        case 'ROLE_ADMIN': { this.router.navigateByUrl('admin/home').then(r => {}); break; }
        case 'ROLE_CLIENT': { this.router.navigateByUrl('client/home').then(r => {}); break; }
        case 'ROLE_DRIVER': { this.router.navigateByUrl('driver/home').then(r => {}); break; }
      }
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

  reportDriver(rideId:Number,reason:String|null){
    const request = this.httpClient.post("/api/client/report", {"rideId":rideId,"reason":reason});
    return request;
  }
}
