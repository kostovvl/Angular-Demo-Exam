import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import Comment from 'src/app/core/model/comment.model';
import { AuthService } from 'src/app/core/service/auth.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  form;
  toEdit;
  @Input('comment') comment: Comment
  @Output('delete') delete: EventEmitter<number> = new EventEmitter;
  @Output('edit') edit: EventEmitter<string[]> = new EventEmitter;
  
  constructor(
    private fb: FormBuilder,
    private authService: AuthService
    ) { }

  ngOnInit(): void {
  }

  canUpdate(creatorId: number) {

    if (this.authService.isAdmin() || Number(this.authService.getUserId()) === creatorId) {
      return true;
    }

  }

  editComment() {
    this.toEdit = true;
    this.form = this.fb.group({
      content: [''],
      id: [this.comment.id]
    })
  }

  submit() {
    this.edit.emit(this.form.value);
    this.toEdit = false;
  }

  deleteComment(id: number) {
   return this.delete.emit(id);
  }

  

}
