import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RegistrationService} from "../registration.service";

@Component({
  selector: 'app-account.activation',
  templateUrl: './account.activation.component.html',
  styleUrls: ['./account.activation.component.scss']
})
export class AccountActivationComponent implements OnInit {
  code: String = '';

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private registrationService: RegistrationService) { }

  ngOnInit(): void {
  }

  activateAccount() {
    let urlemail = this.activatedRoute.snapshot.paramMap.get('email');
    let activationRequest = {
      confNum: this.code,
      email: urlemail
    }
    const request = this.registrationService.postActivationCode(activationRequest);
    request.subscribe((response) => {
      alert(response);
      this.router.navigateByUrl("/").then(r => {});
    })
  }
}
