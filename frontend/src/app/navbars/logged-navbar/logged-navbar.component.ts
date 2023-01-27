import { Component, OnInit } from '@angular/core';
import {MenuItem} from "primeng/api";
import {User} from "../../user.class";
import {UserService} from "../../user.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-logged-navbar',
  templateUrl: './logged-navbar.component.html',
  styleUrls: ['./logged-navbar.component.scss']
})
export class LoggedNavbarComponent implements OnInit {
  items: MenuItem[] = [];

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    let reports = {
      label: 'Reports',
      icon: 'pi pi-fw pi-exclamation-triangle',
      command: () => this.seeReports()
    }
    if (this.userService.getUser().role === 'ROLE_ADMIN') {
      this.items.push(reports);
    }
  }

  private seeReports() {
    this.router.navigateByUrl('admin/reports').then(r => {})
  }
}
