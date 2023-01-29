import { Component, OnInit } from '@angular/core';
import {City} from "../../model/city.class";
import {ProfileService} from "../../profile/profile.service";
import {CarService} from "../../car.service";
import {CarMaker, CarType, Color} from "../../model/car.class";
import {RegistrationService} from "../../registration.service";

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
  cities: City[] = [];
  carMakes: CarMaker[] = [];
  carTypes: CarType[] = [];
  carModel: String = '';
  selectedCarMake: any;
  selectedCarType: any;
  babyFriendly: boolean = false;
  petFriendly: boolean = false;
  colors: Color[] = [];
  selectedColor: any;
  seats: Number = 0;

  constructor(private profileService: ProfileService, private carService: CarService, private registrationService: RegistrationService) {

    const citiesRequest = this.profileService.getCitiesRequest();
    const typesRequest  = this.carService.getCarTypesRequest();
    const makersRequest = this.carService.getCarMakersRequest();
    const colorsRequest = this.carService.getCarColorsRequest();

    citiesRequest.subscribe((response) => {
      this.cities = response as City[];
    });
    typesRequest.subscribe((response)=> {
      let types = response as String[];
      types.forEach((t) => {
        this.carTypes.push(new CarType(t));
      })
    });
    makersRequest.subscribe((response)=> {
      let makers = response as String[];
      makers.forEach((m) => {
        this.carMakes.push(new CarMaker(m));
      })
    });
    colorsRequest.subscribe((response)=> {
      let colors = response as String[];
      colors.forEach((c) => {
        this.colors.push(new Color(c));
      })
    });
  }

  ngOnInit(): void {
  }

  register($event: MouseEvent) {
    let newDriver = this.packData();
    const request = this.registrationService.postDriverRegistrationRequest(newDriver);
    request.subscribe((response) => {
      alert(response);
    }, (error) => {
      alert(error.message);
    })
  }

  packData() {
    let newCar = {
      make: this.selectedCarMake.code,
      type: this.selectedCarType.code,
      color: this.selectedColor.code,
      seats: this.seats,
      model: this.carModel,
      petFriendly: this.petFriendly,
      babyFriendly: this.babyFriendly,
      // id: 0
    }
    return {
      'car': newCar,
      'name': this.name,
      'lastName': this.lastName,
      'email': this.driverEmail,
      'password1': this.driverPassword,
      'password2': this.driverPasswordConfirm,
      'phone': this.phone,
      'city': this.selectedCity.code
    }
  }

}
