import { Injectable } from '@angular/core';
import { 
   CanActivate,
   ActivatedRouteSnapshot,
   RouterStateSnapshot, 
   Router
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class UnAuthGuard implements CanActivate {

  constructor(
    private authService : AuthService,
    private router : Router
  ) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    
    if (this.authService.isNotAuthenticated()) {
      return true;
    }  

    this.router.navigate(['/home']);
    
    return false;
  }
}
