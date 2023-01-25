import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { RegistrationService } from './registration.service';
import {City} from "../city.class";
import {ProfileService} from "../profile/profile.service";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent implements OnInit {
  email: String = '';
  password: String = '';
  name: String = '';
  lastName: String = '';
  phone: String = '';
  selectedCity: String = '';
  cities: City[] = [];
  profileService: ProfileService;
  regService: RegistrationService;

  @Output() registered = new EventEmitter<boolean>();
  @Output() modalClosed = new EventEmitter<boolean>();

  constructor(regService:RegistrationService, profService: ProfileService, private router: Router, private route: ActivatedRoute) {
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

    let request = this.regService.post(this.email,this.password,this.name,this.lastName,this.phone,this.selectedCity);
    request.subscribe();

    
  }
  

}

