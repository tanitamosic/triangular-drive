import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../model/user.class";
import {UserService} from "../../user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.scss']
})
export class UserCardComponent implements OnInit {

  @Input('listedUser') listedUser: User | undefined;
  user: User;
  constructor(private userService: UserService, private router: Router) {
    this.user = this.userService.getUser();
  }

  ngOnInit(): void {
  }

  block() {
    alert('not implemented UWU')
  }

  chat() {
    // @ts-ignore
    this.router.navigateByUrl('support/chat/' + this.listedUser.id).then(r => {})
  }
}
