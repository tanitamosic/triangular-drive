import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DropdownModule } from 'primeng/dropdown';
import { CardModule } from 'primeng/card';
import {ProfileService} from "../profile/profile.service";
import {RegistrationService} from "./registration.service";
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import {AccountActivationComponent} from "./account.activation/account.activation.component";



describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let profileService: ProfileService;
  let registrationService: RegistrationService;
  let mockProfileService: any;
  let mockRegService: any;

  beforeEach(async () => {
    mockProfileService = jasmine.createSpyObj(['getCitiesRequest']);
    mockProfileService.getCitiesRequest.and.returnValue(of([
      {
        name: 'Novi Sad',
        code: 'NS'
      },
      {
        name: 'Beograd',
        code: 'BG'
      },
      {
        name: 'Kragujevac',
        code: 'KG'
      }
    ]));

    mockRegService = jasmine.createSpyObj(['postRegisterRequest']);
    mockRegService.postRegisterRequest.and.returnValue(of('OK'));

    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        NgbModule,
        FormsModule,
        DropdownModule,
        CardModule,
        RouterTestingModule.withRoutes(
          [{path: 'activate/:email', component: AccountActivationComponent}])
      ],
      declarations: [RegisterComponent],
      providers: [
        {provide: ProfileService, useValue: mockProfileService},
        {provide: RegistrationService, useValue: mockRegService}
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    registrationService = TestBed.inject(RegistrationService);
    profileService = TestBed.inject(ProfileService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getCitiesRequest on init', () => {
    expect(mockProfileService.getCitiesRequest).toHaveBeenCalled();
  });

  it('should call postRegisterRequest on submit', () => {
    component.name = 'name';
    component.lastName = 'lastName';
    component.email = 'email1';
    component.password1 = 'password';
    component.password2 = 'password';
    component.selectedCity = {code: 'NS', name: 'Novi Sad'};
    component.phone = '1234456';
    component.register(null);
    expect(mockRegService.postRegisterRequest).toHaveBeenCalled();
  });
});
