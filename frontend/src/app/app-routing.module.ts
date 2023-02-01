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
import {ReviewComponent} from "./review/review.component";
import { FundsComponent } from './funds/funds.component';
import {ChatComponent} from "./chat/chat.component";
import {UserListDisplayComponent} from "./user-list-display/user-list-display.component";
import {ClientRideHistoryComponent} from "./ride-history/client-ride-history/client-ride-history.component";
import {AdminRideHistoryComponent} from "./ride-history/admin-ride-history/admin-ride-history.component";
import {DriverRideHistoryComponent} from "./ride-history/driver-ride-history/driver-ride-history.component";

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
  { path: 'user/charts', component: IncomeReportComponent},
  { path: 'user/review', component: ReviewComponent},
  { path: 'user/funds', component: FundsComponent},
  { path: 'support/chat/:userid', component: ChatComponent},
  { path: 'admin/see-users', component: UserListDisplayComponent},
  { path: 'client/ride-history', component: ClientRideHistoryComponent},
  { path: 'driver/ride-history', component: DriverRideHistoryComponent},
  { path: 'admin/ride-history', component: AdminRideHistoryComponent},
  { path: 'client/home/query/:selectedCity/:sstreet/:snumber/:dstreet/:dnumber', component: ClientComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
