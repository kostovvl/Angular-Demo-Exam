import { Injectable } from '@angular/core';
import { 
   CanActivate,
   ActivatedRouteSnapshot,
   RouterStateSnapshot, 
   Router, CanLoad, Route, UrlSegment
} from '@angular/router';
import { AuthService } from '../service/auth.service';


@Injectable({
  providedIn: 'root'
})
export class CanLoadAuthGuard implements CanLoad {

  constructor(
    private authService : AuthService,
    private router : Router
  ) { }

  canLoad(route: Route, segments: UrlSegment[]):  boolean {
    if (this.authService.isAuthenticated()) {
        return true;
      }  
  
      this.router.navigate(['/login']);
      
      return false;
  }
}
