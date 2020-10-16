import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PostModule } from '../post/post.module'
import { PostDetailsAdminComponent } from './post-details-admin/post-details-admin.component';
import { CommentDetailsAdminComponent } from './comment-details-admin/comment-details-admin.component';




@NgModule({
  declarations: [AdminPanelComponent, PostDetailsAdminComponent, CommentDetailsAdminComponent , CommentDetailsAdminComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PostModule
  ],
})
export class AdminModule { }
