import { Routes, RouterModule } from '@angular/router';
import { MainPageComponent } from './main-page/main-page.component';
import { PartsListComponent } from './parts-list/parts-list.component';
import { LoginComponent } from './login/login.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { CarsComponent } from './cars/cars.component';
import { OrdersComponent } from './orders/orders.component';

export const routes: Routes = [
  { path: '', redirectTo: 'main', pathMatch: 'full' },
  { path: 'main', component: MainPageComponent },
  { path: 'parts', component: PartsListComponent },
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminPanelComponent },
  { path: 'cars', component: CarsComponent },
  { path: 'orders', component: OrdersComponent },
  { path: '**', redirectTo: 'main' }
];
