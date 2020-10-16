import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CategoryService } from 'src/app/core/service/category.service';
import { PostService } from 'src/app/core/service/post.service';
import Category from 'src/app/core/model/category.model';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/service/auth.service';

@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.css']
})
export class PostCreateComponent implements OnInit {

  categories$ : Observable<Category[]>
  form;

  constructor(
    private categoryService: CategoryService,
    private PostService: PostService,
    private authService : AuthService,
    private fb : FormBuilder,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.categories$ = this.categoryService.all();
    this.form = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required],
      creatorName: [this.authService.getUsername()],
      creatorId: [this.authService.getUserId()],
      categoryId: ['', Validators.required]
    })
  }

  get f () {
    return this.form.controls;
  }

  submit() {
    this.PostService.submit(this.form.value)
    .subscribe(data => {
      this.router.navigate([ '/home' ]);
    })
  }

}
