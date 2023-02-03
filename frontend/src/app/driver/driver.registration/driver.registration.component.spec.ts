import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DropdownModule } from 'primeng/dropdown';
import { CardModule } from 'primeng/card';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import {Observable, of} from 'rxjs';
import { RegistrationService } from '../../registration.service';
import { CarService } from "../../car.service";
import { ProfileService } from "../../profile/profile.service";
import { DriverRegistrationComponent } from './driver.registration.component';
import {AccountActivationComponent} from "../../register/account.activation/account.activation.component";
import {ToggleButtonModule} from "primeng/togglebutton";
import {DividerModule} from "primeng/divider";
import {PasswordModule} from "primeng/password";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {InputNumberModule} from "primeng/inputnumber";

describe('DriverRegistrationComponent', () => {
  let component: DriverRegistrationComponent;
  let fixture: ComponentFixture<DriverRegistrationComponent>;
  let profileService: ProfileService;
  let registrationService: RegistrationService;
  let carService: CarService;
  let mockProfileService: any;
  let mockRegService: any;
  let mockCarService: any;

  beforeEach(async () => {
    mockProfileService = jasmine.createSpyObj(['getCitiesRequest']);
    mockProfileService.getCitiesRequest.and.returnValue(of([
      {
        name: 'Novi Sad',
        code: 'NS'
      }
    ]));
    mockRegService = jasmine.createSpyObj(['postDriverRegistrationRequest']);
    mockRegService.postDriverRegistrationRequest.and.returnValue(of('OK'));
    mockCarService = jasmine.createSpyObj(['getCarTypesRequest', 'getCarMakersRequest', 'getCarColorsRequest']);
    mockCarService.getCarTypesRequest.and.returnValue(of([
      {
        name: 'Standard',
        code: 'STANDARD'
      }
    ]));
    mockCarService.getCarMakersRequest.and.returnValue(of([
      {
        name: 'Hyundai',
        code: 'HYUNDAI'
      }
    ]));
    mockCarService.getCarColorsRequest.and.returnValue(of([
      {
        name: 'Red',
        code: 'RED'
      }
    ]));

    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        NgbModule,
        FormsModule,
        DropdownModule,
        ToggleButtonModule,
        DividerModule,
        PasswordModule,
        ButtonModule,
        RippleModule,
        InputNumberModule,
        CardModule,RouterTestingModule.withRoutes([])
      ],
      declarations: [ DriverRegistrationComponent ],
      providers: [
        {provide: ProfileService, useValue: mockProfileService},
        {provide: RegistrationService, useValue: mockRegService},
        {provide: CarService, useValue: mockCarService}
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DriverRegistrationComponent);
    component = fixture.componentInstance;
    profileService = TestBed.inject(ProfileService);
    registrationService = TestBed.inject(RegistrationService);
    carService = TestBed.inject(CarService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getCarTypesRequest on init', () => {
    expect(mockCarService.getCarTypesRequest).toHaveBeenCalled();
  });

  it('should call getCarMakersRequest on init', () => {
    expect(mockCarService.getCarMakersRequest).toHaveBeenCalled();
  });

  it('should call getCarColorsRequest on init', () => {
    expect(mockCarService.getCarColorsRequest).toHaveBeenCalled();
  });

  it('should call getCitiesRequest on init', () => {
    expect(mockProfileService.getCitiesRequest).toHaveBeenCalled();
  });

  it('should should call postDriverRegistrationRequest on submit', () => {
    component.name = 'name';
    component.lastName = 'lastName';
    component.driverEmail = 'email1';
    component.driverPassword = 'password';
    component.driverPasswordConfirm = 'password';
    component.selectedCity = {code: 'NS', name: 'Novi Sad'};
    component.petFriendly = true;
    component.babyFriendly = true;
    component.phone = '1234456';
    component.selectedColor = {
      name: 'Red',
      code: 'RED'
    }
    component.selectedCarMake = {
      name: 'Hyundai',
      code: 'HYUNDAI'
    }
    component.selectedCarType = {
      name: 'Standard',
      code: 'STANDARD'
    }
    component.seats = 4;

    component.register(new MouseEvent('click'));

    expect(mockRegService.postDriverRegistrationRequest).toHaveBeenCalled();
    // expect(mockRegService.postDriverRegistrationRequest).toEqual(of('OK'))
  })

});
