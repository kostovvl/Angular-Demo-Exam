import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  setLoggedUserInfo(userData: Object) {
    localStorage.setItem('username', userData['user']);
    localStorage.setItem('id', userData['id']);
    localStorage.setItem('roles', userData['roles'])
    localStorage.setItem('token', userData['token'])
  } 

  getUsername() {
    return localStorage.getItem('username');
  }

  getUserId() {
    return localStorage.getItem('id')
  }

  getRoles() {
    return localStorage.getItem('roles');
  }

  getToken() {
    return localStorage.getItem('token');
  }

  isAuthenticated() {
    return localStorage.getItem('token') !== null;
  }

  isAdmin() {
    return localStorage.getItem('roles').includes('ADMIN');
  }
  
  isNotAuthenticated() {
    return localStorage.getItem('token') === null;
  }

}
