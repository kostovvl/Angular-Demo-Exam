import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly register_url = 'http://localhost:8080/auth/register'
  private readonly login_url = 'http://localhost:8080/auth/login'

  constructor(private http: HttpClient) { }

  register(form) {
    return this.http.post(this.register_url, form);
  }

  login(form) {
    return this.http.post(this.login_url, form);
  }

}
