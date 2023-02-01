import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../model/user.class";
import {UserService} from "../../user.service";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.scss']
})
export class UserCardComponent implements OnInit {

  @Input('listedUser') listedUser: User | undefined;
  user: User;
  constructor(private userService: UserService, private router: Router, private http: HttpClient) {
    this.user = this.userService.getUser();
  }

  ngOnInit(): void {
  }

  block() {
    if (this.user.role !== 'ROLE_ADMIN') {
      return;
    }
    // @ts-ignore
    this.http.get('/api/admin/block/' + this.listedUser.id);
  }

  chat() {
    // @ts-ignore
    this.router.navigateByUrl('support/chat/' + this.listedUser.id).then(r => {})
  }
}
