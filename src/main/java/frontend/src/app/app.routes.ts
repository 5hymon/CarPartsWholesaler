import { Routes, RouterModule } from '@angular/router';
import { MainPageComponent } from './main-page/main-page.component';
import { PartsListComponent } from './parts-list/parts-list.component';
import { LoginComponent } from './login/login.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { CarsComponent } from './cars/cars.component';
import { OrdersAdminComponent } from './orders-admin/orders-admin.component';
import { OrdersUserComponent } from './orders-user/orders-user.component';
import { AccountComponent } from './account/account.component';
import { WarehouseComponent } from './warehouse/warehouse.component';

export const routes: Routes = [
  { path: '', redirectTo: 'parts', pathMatch: 'full' },
  { path: 'main', component: MainPageComponent },
  { path: 'parts', component: PartsListComponent },
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminPanelComponent },
  { path: 'cars', component: CarsComponent },
  { path: 'orders-admin', component: OrdersAdminComponent },
  { path: 'orders-user', component: OrdersUserComponent },
  { path: 'account', component: AccountComponent },
  { path: 'warehouse', component: WarehouseComponent },
  { path: '**', redirectTo: 'main' }
];
