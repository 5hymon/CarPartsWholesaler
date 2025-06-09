import { Routes, RouterModule } from '@angular/router';
import { MainPageComponent } from './main-page/main-page.component';
import { PartsListComponent } from './parts-list/parts-list.component';
import { LoginComponent } from './login/login.component';
import { CarsComponent } from './cars/cars.component';
import { OrdersAdminComponent } from './orders-admin/orders-admin.component';
import { OrdersUserComponent } from './orders-user/orders-user.component';
import { AccountComponent } from './account/account.component';
import { WarehouseComponent } from './warehouse/warehouse.component';
import { UsersComponent } from './users/users.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { EmployeesComponent } from './employees/employees.component';
import { RegisterComponent } from './register/register.component';
import { GarageComponent } from './garage/garage.component';

export const routes: Routes = [
  { path: '', redirectTo: 'main', pathMatch: 'full' },
  { path: 'main', component: MainPageComponent },
  { path: 'parts', component: PartsListComponent },
  { path: 'login', component: LoginComponent },
  { path: 'cars', component: CarsComponent },
  { path: 'orders-admin', component: OrdersAdminComponent },
  { path: 'orders-user', component: OrdersUserComponent },
  { path: 'account', component: AccountComponent },
  { path: 'warehouse', component: WarehouseComponent },
  { path: 'users', component: UsersComponent },
  { path: 'shopping-cart', component: ShoppingCartComponent },
  { path: 'employees', component: EmployeesComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'garage', component: GarageComponent },
  { path: '**', redirectTo: 'main' }
];
