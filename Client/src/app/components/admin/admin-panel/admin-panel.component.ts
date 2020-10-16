import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ResolveStart, Router } from '@angular/router';
import { Observable } from 'rxjs';
import Post from 'src/app/core/model/post.model';
import Comment from 'src/app/core/model/comment.model';
import { AdminService } from 'src/app/core/service/admin.service'
import { CommentService } from 'src/app/core/service/comment.service';
import { PostService } from 'src/app/core/service/post.service';
import User from 'src/app/core/model/user.model';
import { UserService } from 'src/app/core/service/user.service';
import { AuthService } from 'src/app/core/service/auth.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  form;
  PostsforApproval$: Observable<Object[]>
  CommentsForApproval$: Observable<Object[]>
  allUsers$: Observable<User[]>
  post: Post
  comment: Comment

  constructor(
    private fb: FormBuilder,
    private adminService: AdminService,
    private postService: PostService,
    private commentService: CommentService,
    private authService: AuthService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', Validators.required]
    })
    this.PostsforApproval$ = this.adminService.getAllPostsForApproval();
    // this.CommentsForApproval$ = this.adminService.getAllCommentsForApproval();
    this.allUsers$ = this.adminService.getAllUsers();
  }

  get f() {
    return this.form.controls;
  }

  

  submit() {
    this.adminService.createCategory(this.form.value)
    .subscribe(data => {
      this.router.navigate([ '/home' ])
    })
  }

  postDetails(id: number) {
    this.postService.postDetails(id).subscribe(data => {
      this.post = data;
    })
  }

 

  commentDetails(id : number) {
    this.commentService.getById(id).subscribe(data => {
      this.comment = data;
    })
  }

  clearPage() {
    this.post = null;
    this.comment = null;
    this.ngOnInit();
  }

  isRootAdmin() {
    return this.authService.isRootAdmin();
  }

  userIsAdmin(roles: Object[]) {
    for (let i = 0; i < roles.length; i++) {
     if (roles[i]['role'] === "ADMIN") 
     return true
    }
    return false;
  }

  toUser(id: number) {
    this.adminService.downgradeToUser(id)
    .subscribe(data => {this.ngOnInit()})
  }

  toAdmin(id: number) {
    this.adminService.upgadeToAdmin(id)
    .subscribe(data => {this.ngOnInit()})
  }

}
