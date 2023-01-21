import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ClientComponent} from "./client/client.component";
import {DriverComponent} from "./driver/driver.component";
import {AdminComponent} from "./admin/admin.component";

const routes: Routes = [
  { path: 'client/home', component: ClientComponent },
  { path: 'driver/home', component: DriverComponent },
  { path: 'admin/home', component: AdminComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
