import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/authentication/login/login.component';
import { RegisterComponent } from './components/authentication/register/register.component';
import { LandingPageComponent } from './components/shared/landing-page/landing-page.component';
import { AdminPanelComponent } from './components/admin/admin-panel/admin-panel.component';
import { UnAuthGuard } from 'src/app/core/guard/unAuth.guard';
import { AdminGuard } from 'src/app/core/guard/admin.guard';
import { AuthGuard } from 'src/app/core/guard/auth.guard';
import { CanLoadAuthGuard } from 'src/app/core/guard/can.load.auth.guard';


const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'home'},
  {path: 'home', component: LandingPageComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent, canActivate: [UnAuthGuard]},
  {path: 'admin', component: AdminPanelComponent, canActivate: [AuthGuard, AdminGuard]},
  {path: 'posts', loadChildren: './components/post/post.module#PostModule', canLoad:[CanLoadAuthGuard]},
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
