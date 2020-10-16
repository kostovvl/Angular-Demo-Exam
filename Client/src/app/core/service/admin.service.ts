import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import User from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly create_category_url = 'http://localhost:8080/admin/category/create'

  private readonly posts_for_approval_url = 'http://localhost:8080/admin/post/all'
  private readonly post_approve_url = 'http://localhost:8080/admin/approve/post/'
  private readonly post_delete_url = 'http://localhost:8080/admin/delete/post/'

  private readonly comments_for_approval_url = 'http://localhost:8080/admin/comment/all'
  private readonly approve_comment_url = 'http://localhost:8080/admin/approve/comment/'
  private readonly delete_comment_url = 'http://localhost:8080/admin/delete/comment/'

  private readonly get_all_users_url = 'http://localhost:8080/admin/users/all'
  private readonly upgrade_to_admin_url = 'http://localhost:8080/admin/upgrade/'
  private readonly downgrade_to_user_url = 'http://localhost:8080/admin/downgrade/'

  constructor(private http: HttpClient) { }

  createCategory(form: Object) {
   return this.http.post(this.create_category_url, form);
  }

  getAllPostsForApproval() {
    return this.http.get<Object[]>(this.posts_for_approval_url);
  }

  approvePost(id: number) {
  return this.http.put(this.post_approve_url + id, Object);
  }

  deletePost(id:number) {
    return this.http.delete(this.post_delete_url + id);
  }

  
  getAllCommentsForApproval() {
    return this.http.get<Object[]>(this.comments_for_approval_url);
  }

  approveComment(id: number) {
    return this.http.put(this.approve_comment_url + id, Object);
  }

  deleteComment(id: number) {
    return this.http.delete(this.delete_comment_url + id);
  }

  getAllUsers() {
    return this.http.get<User[]>(this.get_all_users_url);
  }

  upgadeToAdmin(id: number) {
    console.log(id)
    return this.http.put(this.upgrade_to_admin_url + id, Object);
  }

  downgradeToUser(id: number) {
    return this.http.put(this.downgrade_to_user_url + id, Object);
  }

}
