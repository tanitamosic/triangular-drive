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


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoggedNavbarComponent,
    UnloggedNavbarComponent
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
    BrowserAnimationsModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
