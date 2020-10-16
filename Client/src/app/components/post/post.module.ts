import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostCreateComponent } from './post-create/post-create.component';
import { PostAllComponent } from './post-all/post-all.component';
import { PostDetailsComponent } from './post-details/post-details.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CommentModule } from 'src/app/components/comment/comment.module';
import { PostEditComponent } from './post-edit/post-edit.component';
import { PostsRoutingModule } from './post.routing.module';

@NgModule({
  declarations: [PostCreateComponent, PostAllComponent, PostDetailsComponent, PostEditComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CommentModule,
    PostsRoutingModule,
  ],
  exports: [
    PostCreateComponent, PostAllComponent, PostDetailsComponent, PostEditComponent
  ]
})
export class PostModule { }
