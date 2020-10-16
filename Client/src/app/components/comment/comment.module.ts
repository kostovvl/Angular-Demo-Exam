import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommentComponent } from './comment/comment.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [CommentComponent],
  imports: [
    CommonModule, 
    ReactiveFormsModule
  ],
  exports: [
    CommentComponent
  ]
})
export class CommentModule { }
