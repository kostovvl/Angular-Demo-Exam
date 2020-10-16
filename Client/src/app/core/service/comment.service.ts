import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import Comment from 'src/app/core/model/comment.model';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private readonly create_comment_url = 'http://localhost:8080/comments/create'
  private readonly get_by_Id_url = 'http://localhost:8080/comments/getById/'
  private readonly get_for_post_url = 'http://localhost:8080/comments/getForPost/'
  private readonly edit_url = 'http://localhost:8080/comments/update/'
  private readonly delete_url = 'http://localhost:8080/comments/delete/'

  constructor(private http: HttpClient) { }

  submit(form: Object) {
    return this.http.post(this.create_comment_url, form);
  }

  getById(id: number) {
    return this.http.get<Comment>(this.get_by_Id_url + id);
  }

  getForPost(id: number) {
    return this.http.get<Comment[]>(this.get_for_post_url + id)
  }

  edit(data: string[]) {
   return this.http.put(this.edit_url + data['id'], data);
  }

  delete(id: number) {
    return this.http.delete<Object>(this.delete_url + id);
  }

}
