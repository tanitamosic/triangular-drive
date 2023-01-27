import { Component, OnInit } from '@angular/core';
import {MenuItem} from "primeng/api";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../user.service";
import {User} from "../../model/user.class";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-logged-navbar',
  templateUrl: './logged-navbar.component.html',
  styleUrls: ['./logged-navbar.component.scss']
})
export class LoggedNavbarComponent implements OnInit {
  items: MenuItem[] = [];
  user: User;

  pollingInterval: NodeJS.Timer | undefined;

  constructor(private router: Router, private route: ActivatedRoute, private userService: UserService,
              private http: HttpClient) {
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

    this.addDriverItems();
  }

  addDriverItems() {
    let check = 'pi pi-fw pi-check-circle';
    let available = {
      label: 'Available',
      icon: check,
      command: () => {
        available.icon = check;
        busy.icon = '';
        offline.icon = '';
        this.stopPolling();
        this.driverStatusPolling('AVAILABLE')
      }
    }
    let busy = {
      label: 'Busy',
      icon: '',
      command: () => {
        busy.icon = check;
        available.icon = '';
        offline.icon = '';
        this.stopPolling();
        this.driverStatusPolling('BUSY');
      }
    }
    let offline = {
      label: 'Offline',
      icon: '',
      command: () => {
        offline.icon = check;
        available.icon = '';
        busy.icon = '';
        this.stopPolling();
        this.driverStatusPolling('OFFLINE');
      }
    }

    let status = {
      label: 'Status',
      icon: 'pi pi-fw pi-prime',
      items: [
        available,
        busy,
        offline
      ]
    }
    if (this.user.role === 'ROLE_DRIVER') {
      this.items.push(status);
    }
  }

  driverStatusPolling(status: String) {
    this.pollingInterval = setInterval(() => {
      this.http.get('/api/driver/set-status/' + this.user.id + '/' + status).subscribe(data => {
        console.log(status);
      });
    }, 5000);  // 5000ms = 5s
  }
  stopPolling() {
    clearInterval(this.pollingInterval);
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


  logout() {
    this.userService.removeUserFromStorage();
    this.router.navigateByUrl("").then(r=>{});
    if (this.user.role === 'ROLE_DRIVER') {
      this.stopPolling()
      const request = this.userService.updateDriverStatusRequest(this.user.id, "OFFLINE");
      request.subscribe();
    }
    window.location.reload();

  }
}
