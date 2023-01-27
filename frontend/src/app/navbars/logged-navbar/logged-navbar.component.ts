import { Component, OnInit } from '@angular/core';
import {MenuItem} from "primeng/api";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../user.service";
import {User} from "../../model/user.class";

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
    let reports = {
      label: 'Reports',
      icon: 'pi pi-fw pi-exclamation-triangle',
      command: () => this.seeReports()
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
      this.items.push(reports);
    }
  }

  myProfile(): void {
    this.router.navigate(['user/profile/', this.user.id]).then(r => {});
  }

  private registerDriver() {
    this.router.navigate(['admin/register-driver']).then(r => {});
  }

  private seeReports() {
    this.router.navigateByUrl('admin/reports').then(r => {})
  }
}
