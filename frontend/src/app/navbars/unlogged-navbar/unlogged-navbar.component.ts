import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { MenuItem } from "primeng/api";
import {Router} from "@angular/router";

@Component({
  selector: 'app-unlogged-navbar',
  templateUrl: './unlogged-navbar.component.html',
  styleUrls: ['./unlogged-navbar.component.scss']
})
export class UnloggedNavbarComponent implements OnInit {
  items: MenuItem[] = [];
  displayLoginModal: boolean = false;
  @Output() loggedIn = new EventEmitter<boolean>();

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  onLogin($event: boolean) {
    this.loggedIn.emit(true);
  }

  toRegistrationPage() {
    this.router.navigateByUrl('registration').then(r => {});
  }
}
