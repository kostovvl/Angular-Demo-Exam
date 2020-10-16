import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/service/auth.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  username: String;

  constructor(
    private authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
  }

  isAuthenticated() {
    return this.authService.isAuthenticated();
  }

  isAdmin() {
    return this.authService.isAdmin();
  }

  logout() {
    localStorage.clear();
    this.router.navigate([ '/home' ]);
  }


}
