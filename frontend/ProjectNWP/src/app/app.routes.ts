import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Register } from './auth/register/register';
import { Flight } from './search/flight/flight';
import { Search } from './search/search/search';
import { Booking } from './booking/booking/booking';
import { Countries } from './search/countries/countries';
import { Cities } from './search/cities/cities';
import { Airports } from './search/airports/airports';
import { Account } from './account/account/account';
import { AddFlight } from './manager/add-flight/add-flight';
import { AddPlane } from './manager/add-plane/add-plane';
import { HireUser } from './manager/hire-user/hire-user';
import { DelayCancel } from './manager/delay-cancel/delay-cancel';
import { Employee } from './employee/employee/employee';
import { Admin } from './admin/admin/admin';
import { authGuard } from './guards/auth-guard';
import { roleGuard } from './guards/role-guard';

export const routes: Routes = [
    { path: "login", component: Login },
    { path: "register", component: Register },
    { path: "search", component: Search },
    { path: "flights", component: Flight },
    { path: "booking", component: Booking, canActivate: [authGuard] },
    { path: "countries", component: Countries},
    { path: "cities", component: Cities },
    { path: "airports", component: Airports },
    { path: "account", component: Account, canActivate: [authGuard] },
    { path: "add-flight", component: AddFlight, canActivate: [authGuard, roleGuard], data: { roles: ["MANAGER"] } },
    { path: "add-plane", component: AddPlane, canActivate: [authGuard, roleGuard], data: { roles: ["MANAGER"] } },
    { path: "hire-user", component: HireUser, canActivate: [authGuard, roleGuard], data: { roles: ["MANAGER"] } },
    { path: "delay-cancel-flight", component: DelayCancel, canActivate: [authGuard, roleGuard], data: { roles: ["MANAGER"] } },
    { path: "employee", component: Employee, canActivate: [authGuard, roleGuard], data: { roles: ["EMPLOYEE"] } },
    { path: "admin", component: Admin, canActivate: [authGuard, roleGuard], data: { roles: ["ADMIN"] } },
    { path: "", redirectTo: "search", pathMatch: "full" }
];
