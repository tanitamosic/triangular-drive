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
import { MapComponent } from './map/map.component';
import { ClientComponent } from './client/client.component';
import { DriverComponent } from './driver/driver.component';
import { AdminComponent } from './admin/admin.component';
import { ProfileComponent } from './profile/profile.component';
import { FileUploadModule } from "primeng/fileupload";
import { DropdownModule } from "primeng/dropdown";
import { PasswordModule } from "primeng/password";
import { DividerModule } from "primeng/divider";
import { DriverRegistrationComponent } from './driver/driver.registration/driver.registration.component';
import {ToggleButtonModule} from "primeng/togglebutton";
import {CardModule} from "primeng/card";
import {InputNumberModule} from "primeng/inputnumber";
import {RegisterComponent} from "./register/register.component";
import { AccountActivationComponent } from './register/account.activation/account.activation.component';
import {InputMaskModule} from "primeng/inputmask";
import { ReportsComponent } from 'src/app/reports/reports.component';
import { ReportCardComponent } from 'src/app/reports/report.card/report.card.component';
import { ClientMapComponent } from './client/client.map/client.map.component';
import { DriverMapComponent } from './driver/driver.map/driver.map.component';
import { PasswordResetComponent } from './login/password-reset/password-reset.component';
import { RideRequestComponent } from './client/ride.request/ride.request.component';
import { IncomeReportComponent } from './income-report/income-report.component';
import {CalendarModule} from "primeng/calendar";
import {ChartModule} from "primeng/chart";
import { ReviewComponent } from './review/review.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FundsComponent } from './funds/funds.component';
import { ChatComponent } from './chat/chat.component';
import { UserCardComponent } from './user-list-display/user-card/user-card.component';
import { UserListDisplayComponent } from './user-list-display/user-list-display.component';
import { RatingModule } from 'primeng/rating';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoggedNavbarComponent,
    UnloggedNavbarComponent,
    MapComponent,
    ClientComponent,
    DriverComponent,
    AdminComponent,
    ProfileComponent,
    DriverRegistrationComponent,
    RegisterComponent,
    AccountActivationComponent,
    ReportsComponent,
    ReportCardComponent,
    ClientMapComponent,
    DriverMapComponent,
    PasswordResetComponent,
    RideRequestComponent,
    IncomeReportComponent,
    ReviewComponent,
    FundsComponent,
    ChatComponent,
    UserCardComponent,
    UserListDisplayComponent,
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
    InputMaskModule,
    CalendarModule,
    ChartModule,
    NgbModule,
    RatingModule,
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
