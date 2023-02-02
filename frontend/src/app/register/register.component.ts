import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { RegistrationService } from './registration.service';

import {ProfileService} from "../profile/profile.service";
import {City} from "../model/city.class";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent implements OnInit {
  email: String = '';
  password1: String = '';
  password2: String = '';
  name: String = '';
  lastName: String = '';
  phone: String = '';
  selectedCity: any;
  cities: City[] = [];
  profileService: ProfileService;
  regService: RegistrationService;

  constructor(regService:RegistrationService, profService: ProfileService, private router: Router) {
    this.regService = regService;
    this.profileService = profService;
    // LOAD CITIES
    const request = this.profileService.getCitiesRequest();
    request.subscribe((response) => {
      this.cities = response as City[];
      console.log("Cities: " + this.cities)
        })
   }



  ngOnInit(): void {

  }

  register(event: any): void{
    if (this.password1 !== this.password2 || this.password1 === '' || this.password1.length < 6 || this.password1.length > 16) {
      alert('Input proper passwords, dummy. uwu');
      return;
    }
    let body = {
      name: this.name,
      lastName: this.lastName,
      email: this.email,
      password1: this.password1,
      password2: this.password2,
      phone: this.phone,
      city: this.selectedCity.code
    }
    let request = this.regService.postRegisterRequest(body);
    request.subscribe((response) => {
      this.router.navigateByUrl('activate/' + response).then(r => {})
    });


  }


}

