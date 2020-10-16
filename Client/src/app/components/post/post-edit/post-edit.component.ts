import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import Post from 'src/app/core/model/post.model';
import Category from 'src/app/core/model/category.model';
import { PostService } from 'src/app/core/service/post.service';
import { CategoryService } from 'src/app/core/service/category.service';

@Component({
  selector: 'app-post-edit',
  templateUrl: './post-edit.component.html',
  styleUrls: ['./post-edit.component.css']
})
export class PostEditComponent implements OnInit {
  
  categories$: Observable<Category[]>
  post: Post
  form;

  constructor(
    private postService: PostService,
    private categoryService: CategoryService,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    let postId = this.route.snapshot.params['id'];
    this.postService.postDetails(postId)
    .subscribe(data => {
      this.post = data
      console.log(this.post)
      this.categories$ = this.categoryService.all();
      this.form = this.fb.group({
        title: ['', Validators.required],
        content: ['', Validators.required],
        creatorName: [this.post.creatorName],
        creatorId: [this.post.creatorId],
        categoryId: ['', Validators.required]
      })
    })
  }

  get f() {
    return this.form.controls;
  }

  submit() {
    this.postService.edit(this.post.id, this.form.value)
    .subscribe(data => {
      let postId = this.post.id;
      this.router.navigate([ 'post/details/', postId])
    })
  }

}
