import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ClientComponent} from "./client/client.component";
import {DriverComponent} from "./driver/driver.component";
import {AdminComponent} from "./admin/admin.component";
import { ProfileComponent } from "./profile/profile.component";
import {DriverRegistrationComponent} from "./driver/driver.registration/driver.registration.component";
import {RegisterComponent} from "./register/register.component";
import {AccountActivationComponent} from "./register/account.activation/account.activation.component";
import {ReportsComponent} from "./reports/reports.component";
import {MapComponent} from "./map/map.component";
import {PasswordResetComponent} from "./login/password-reset/password-reset.component";
import {IncomeReportComponent} from "./income-report/income-report.component";

const routes: Routes = [
  { path: '', component: MapComponent },
  { path: 'forgot-password', component: PasswordResetComponent },
  { path: 'client/home', component: ClientComponent },
  { path: 'driver/home', component: DriverComponent },
  { path: 'admin/home', component: AdminComponent },
  { path: 'user/profile/:profileId', component: ProfileComponent },
  { path: 'admin/register-driver', component: DriverRegistrationComponent },
  { path: 'registration', component: RegisterComponent },
  { path: 'activate/:email', component: AccountActivationComponent },
  { path: 'admin/reports', component: ReportsComponent},
  { path: 'user/charts', component: IncomeReportComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
