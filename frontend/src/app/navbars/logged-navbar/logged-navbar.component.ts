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
    let funds = {label:"Funds", icon:"pi pi-fw pi-money-bill", command: () => this.router.navigateByUrl('user/funds').then(r=>{})};

    this.items=[];

    this.addHomeItem();

    this.items.push({
      label: 'My profile',
      icon: 'pi pi-fw pi-user',
      command: () => this.myProfile()
    });
    this.items.push({
      label: 'Statistics',
      icon: 'pi pi-fw pi-chart-bar',
      command: () => {this.router.navigateByUrl('user/charts').then(r=>{});}
    });


    
    if(this.user.role==="ROLE_CLIENT"){
      this.items.push(funds);
      
    }
    this.addClientDriverItems();
    this.addAdminItems();
    this.addDriverItems();
  }

  addHomeItem() {
    let homenav = "";
    if (this.user.role === 'ROLE_CLIENT') {
      homenav = "client/home";
    } else if (this.user.role === 'ROLE_DRIVER') {
      homenav = "driver/home";
    } else if (this.user.role === 'ROLE_ADMIN') {
      homenav = "admin/home";
    }
    let map_obj = {label:"Home", icon:"pi pi-fw pi-map", command: () => this.router.navigateByUrl(homenav).then(r=>{})};
    this.items.push(map_obj);
  }
  private addAdminItems() {
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
    let users = {
      label: 'List users',
      icon: 'pi pi-fw pi-database',
      command: () => this.listUsers()
    }
    if (this.user.role === 'ROLE_ADMIN') {
      this.items.push(driver_reg_item);
      this.items.push(reports);
      this.items.push(users);
    }
  }

  addClientDriverItems() {
    let support = {
      label: 'Support',
      icon: 'pi pi-fw pi-info-circle',
      command: () => { this.router.navigateByUrl('support/chat/' + this.user.id ).then(r=>{}); }
    }
    if (this.user.role === 'ROLE_CLIENT' || this.user.role === 'ROLE_DRIVER') {
      this.items.push(support);
    }
  }

  addDriverItems() {
    let check = 'pi pi-fw pi-check-circle';
    let circle = 'pi pi-fw pi-circle';
    let available = {
      label: 'Available',
      icon: check,
      command: () => {
        available.icon = check;
        busy.icon = circle;
        offline.icon = circle;
        this.stopPolling();
        this.updateDriverStatus('AVAILABLE');
        this.getDriverStatusPolling();
      }
    }
    let busy = {
      label: 'Busy',
      icon: circle,
      command: () => {
        busy.icon = check;
        available.icon = circle;
        offline.icon = circle;
        this.stopPolling();
        this.updateDriverStatus('BUSY');
        this.getDriverStatusPolling();
      }
    }
    let offline = {
      label: 'Offline',
      icon: circle,
      command: () => {
        offline.icon = check;
        available.icon = circle;
        busy.icon = circle;
        this.stopPolling();
        this.updateDriverStatus('OFFLINE');
        this.getDriverStatusPolling();
      }
    }

    let status = {
      label: 'Status',
      icon: 'pi pi-fw pi-power-off',
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

  updateDriverStatus(status: String) {
    this.http.get('/api/driver/set-status/' + this.user.id + '/' + status).subscribe(data => {
      console.log(status);
    });
  }

  getDriverStatusPolling() {
    this.pollingInterval = setInterval(() => {
      this.http.get('/api/driver/get-status' + this.user.id).subscribe(data => {
        console.log(data);
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
    delete this.userService.user;
    this.router.navigate([""]).then(r=>{});
    if (this.user.role === 'ROLE_DRIVER') {
      this.stopPolling()
      const request = this.userService.updateDriverStatusRequest(this.user.id, "OFFLINE");
      request.subscribe();
    }

  }

  private listUsers() {
    this.router.navigateByUrl('admin/see-users').then(r => {})
  }
}
