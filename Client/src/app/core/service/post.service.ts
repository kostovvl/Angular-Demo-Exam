import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import Post from 'src/app/core/model/post.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private readonly submit_post_url = 'http://localhost:8080/posts/create'
  private readonly post_details_url = 'http://localhost:8080/posts/details/'
  private readonly post_edit_url = 'http://localhost:8080/posts/update/'
  private readonly post_delete_url = 'http://localhost:8080/posts/delete/'
  private readonly all_posts_for_category = 'http://localhost:8080/posts/by_category/'
  
  constructor(private http: HttpClient) { }

  submit(form: Object) {
    return this.http.post(this.submit_post_url, form);
  }

  postDetails(id: number){
    return this.http.get<Post>(this.post_details_url + id)
  }

  edit(id: number, form: Object) {
    return this.http.put(this.post_edit_url + id, form)
  }

  delete(id: number) {
   return this.http.delete(this.post_delete_url + id)
  }

  byCategory(id: number) {
    return this.http.get<Post[]>(this.all_posts_for_category + id);
  }

}
