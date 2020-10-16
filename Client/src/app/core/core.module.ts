import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { UserService } from './service/user.service';
import { AuthService } from './service/auth.service';
import { JwtInterceptorService } from 'src/app/core/interceptor/jwt-interceptor.service';
import { ResponceInterceptorService } from 'src/app/core/interceptor/responce-interceptor.service';  



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    UserService, 
    AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptorService, multi: true},
    { provide: HTTP_INTERCEPTORS, useClass: ResponceInterceptorService, multi: true}
  ], 
 
})
export class CoreModule { }