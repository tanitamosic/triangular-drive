import { Component, OnInit } from '@angular/core';
import {MenuItem} from "primeng/api";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../user.service";
import {User} from "../../user.class";

@Component({
  selector: 'app-logged-navbar',
  templateUrl: './logged-navbar.component.html',
  styleUrls: ['./logged-navbar.component.scss']
})
export class LoggedNavbarComponent implements OnInit {
  items: MenuItem[] = [];
  user: User;

  constructor(private router: Router, private route: ActivatedRoute, private userService: UserService) {
    this.user = userService.getUser();
  }

  ngOnInit(): void {
    let driver_reg_item = {
      label: 'Register new driver',
      icon: 'pi pi-fw pi-user-plus',
      command: () => this.registerDriver()
    }

    this.items = [
      {
        label: 'My profile',
        icon: 'pi pi-fw pi-user',
        command: () => this.myProfile()
      }
    ];

    if (this.user.role === 'ROLE_ADMIN'){
      this.items.push(driver_reg_item);
    }
  }

  myProfile(): void {
    this.router.navigate(['user/profile/', this.user.id]).then(r => {})
  }

  private registerDriver() {

  }
}
