import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import {HttpClientModule} from "@angular/common/http";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {FormsModule} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";
import {ClientComponent} from "../client/client.component";
import {LoginService} from "./login.service";
import {AuthorizationService} from "../authorizationService";
import {UserService} from "../user.service";
import {of} from "rxjs";
import {User} from "../model/user.class";

describe('LoginComponent', () => {

  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthorizationService;
  let loginService: LoginService;
  let userService: UserService;

  class MockLoginService {
    post(email: string, password: string) {
      return of({
        email: email,
        password: password,
        role: 'ROLE_CLIENT',
        access_token: 'abcdefghijklmnopqrstuvwxyz',
      });
    }
  }
  class MockAuthorizationService {
    token: string = '';
    setToken(t: string) {
      this.token = t;
    };
    getToken() {return this.token;}
  }
  class MockUserService {
    user= {
      email: '',
      password: '',
      role: '',
      access_token: '',
    };
    setUser(u: any) {
      this.user.email = u.email;
      this.user.password = u.password;
      this.user.role = u.role;
      this.user.access_token = u.access_token;
    }
    getUser() {
      return this.user;
    }
    getUserAccessToken() {
      return this.user.access_token;
    }
  }
  let mockLoginService = new MockLoginService();
  let mockAuthService = new MockAuthorizationService();
  let mockUserService = new MockUserService();
  let component: LoginComponent;

  beforeEach(async () => {


    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        NgbModule,
        FormsModule,
        RouterTestingModule.withRoutes(
          [{path: 'client/home', component: ClientComponent}])
      ],
      declarations: [ LoginComponent ],
      providers: [
        {provide: LoginService, useClass: MockLoginService, useValue: mockLoginService },
        {provide: AuthorizationService, useClass: MockAuthorizationService, useValue: mockAuthService },
        {provide: UserService, useClass: MockUserService, useValue: mockUserService }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    loginService = TestBed.inject(LoginService);
    authService = TestBed.inject(AuthorizationService);
    userService = TestBed.inject(UserService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call login service with correct input', () => {
    const email = 'test@example.com';
    const password = 'testpassword';
    const response = {
      email: email,
      password: password,
      role: 'ROLE_CLIENT',
      access_token: 'abcdefghijklmnopqrstuvwxyz',
    };
    spyOn(mockLoginService, 'post').withArgs(email, password).and.returnValue(of(response));
    component.email = email;
    component.password = password;

    component.login(null);

    expect(mockLoginService.post).toHaveBeenCalledWith(email, password);
  });

  it('should set user and token after successful login', () => {
    const email = 'test@example.com';
    const password = 'testpassword';
    const response = {
      email: email,
      password: password,
      role: 'ROLE_CLIENT',
      access_token: 'abcdefghijklmnopqrstuvwxyz',
    };
    spyOn(mockLoginService, 'post').withArgs(email, password).and.returnValue(of(response));
    spyOn(mockUserService, 'setUser').withArgs(response).and.returnValue();
    spyOn(mockAuthService, 'setToken').withArgs(response.access_token).and.returnValue();
    component.email = email;
    component.password = password;

    component.login(null);

    expect(mockUserService.getUser().email).toBe(email);
    expect(mockAuthService.getToken()).toBe(response.access_token);
  });
});
