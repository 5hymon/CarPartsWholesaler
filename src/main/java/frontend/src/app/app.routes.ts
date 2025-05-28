import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { MainPageComponent } from './main-page/main-page.component';
import { PartsListComponent } from './parts-list/parts-list.component';
import { LoginComponent } from './login/login.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';

export const routes: Routes = [
  // domyślnie przekieruj na main
  { path: '', redirectTo: 'main', pathMatch: 'full' },
  // Twoja nowa strona główna
  { path: 'main', component: MainPageComponent },
  // inne strony
  { path: 'parts', component: PartsListComponent },
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminPanelComponent },
  // wildcard – nieznane ścieżki
  { path: '**', redirectTo: 'main' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
