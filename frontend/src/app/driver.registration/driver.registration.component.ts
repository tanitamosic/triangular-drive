import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-driver.registration',
  templateUrl: './driver.registration.component.html',
  styleUrls: ['./driver.registration.component.scss']
})
export class DriverRegistrationComponent implements OnInit {
  driverEmail: String = '';
  driverPassword: String = '';
  driverPasswordConfirm: String = '';
  name: String = '';
  lastName: String = '';
  phone: String = '';
  selectedCity: any;
  cities: any;

  constructor() { }

  ngOnInit(): void {
  }

  register($event: MouseEvent) {

  }
}
