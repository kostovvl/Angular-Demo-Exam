import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

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

}
