import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ResponceInterceptorService  implements HttpInterceptor {

  constructor(public toaster: ToastrService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(tap((success) => {
      if (success instanceof HttpResponse) {
        if (success.url.endsWith('login') || success.url.endsWith('register') ||
         success.url.endsWith('/create') || success.url.includes('delete')) {
          this.toaster.success('success')
        }
      }
     
    }), catchError((err) => {
      if (err['url'].endsWith('login')) {
        this.toaster.error('Wrong credentials');
      } else if(err.status === 403){
        this.toaster.error('Session Expired! Please Login again')
      }
      else {
        console.log(err)
        this.toaster.error(err.error)
      }
      throw err;
    })
    )}
}
