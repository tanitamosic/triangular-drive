import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from "@angular/forms";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { InputTextModule } from "primeng/inputtext";
import { ButtonModule } from "primeng/button";
import { httpInterceptorProviders } from "./httpInterceptorProviders";
import { LoggedNavbarComponent } from './navbars/logged-navbar/logged-navbar.component';
import { UnloggedNavbarComponent } from './navbars/unlogged-navbar/unlogged-navbar.component';
import { MenubarModule } from 'primeng/menubar';
import { RippleModule } from "primeng/ripple";
import { DialogModule } from 'primeng/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ClientComponent } from './client/client.component';
import { DriverComponent } from './driver/driver.component';
import { AdminComponent } from './admin/admin.component';
import { ProfileComponent } from './profile/profile.component';
import { FileUploadModule } from "primeng/fileupload";
import { DropdownModule } from "primeng/dropdown";
import { PasswordModule } from "primeng/password";
import { DividerModule } from "primeng/divider";
import { DriverRegistrationComponent } from './driver.registration/driver.registration.component';
import {ToggleButtonModule} from "primeng/togglebutton";
import {CardModule} from "primeng/card";
import {InputNumberModule} from "primeng/inputnumber";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoggedNavbarComponent,
    UnloggedNavbarComponent,
    ClientComponent,
    DriverComponent,
    AdminComponent,
    ProfileComponent,
    DriverRegistrationComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    InputTextModule,
    ButtonModule,
    MenubarModule,
    RippleModule,
    DialogModule,
    BrowserAnimationsModule,
    FileUploadModule,
    DropdownModule,
    PasswordModule,
    DividerModule,
    ToggleButtonModule,
    CardModule,
    InputNumberModule,
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
