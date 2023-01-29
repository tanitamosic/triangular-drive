import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss']
})
export class PasswordResetComponent implements OnInit {
  email: String = '';
  beforeReset: boolean = true;
  afterReset: boolean = false;

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
  }

  resetPassword($event: MouseEvent) {
    const request = this.httpClient.get('/api/user/reset-password/' + this.email, { headers: {'Content-Type': 'text/plain'}});
    request.subscribe();
    this.beforeReset = false;
    this.afterReset = true;
  }
}
