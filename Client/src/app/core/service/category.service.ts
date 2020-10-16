import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Category from '../model/category.model'

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private readonly all_categories_url = 'http://localhost:8080/categories/all';
  private readonly delete_url = 'http://localhost:8080/categories/delete/';
  private readonly update_url = 'http://localhost:8080/categories/update/';

  constructor(private http: HttpClient) { }

  all() {
    return this.http.get<Category[]>(this.all_categories_url);
  }

  update(id: number, form: Object) {
    return this.http.put(this.update_url + id, form);
  }

  delete(id: number) {
    return this.http.delete(this.delete_url + id);
  }

}
