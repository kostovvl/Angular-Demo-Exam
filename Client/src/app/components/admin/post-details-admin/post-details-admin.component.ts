import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import Post from 'src/app/core/model/post.model';
import { AdminService } from 'src/app/core/service/admin.service';
import { AuthService } from 'src/app/core/service/auth.service';
import { PostService } from 'src/app/core/service/post.service';

@Component({
  selector: 'app-post-details-admin',
  templateUrl: './post-details-admin.component.html',
  styleUrls: ['./post-details-admin.component.css']
})
export class PostDetailsAdminComponent implements OnInit {

  @Input('post') post: Post;
  @Output('approve') approveEvent: EventEmitter<string> = new EventEmitter;
  @Output('delete') deleteEvent: EventEmitter<String> = new EventEmitter;

  constructor(
    private authService: AuthService,
    private adminService: AdminService,
    private postService: PostService,
  ) { }

  ngOnInit(): void {
    
  }

  isAdmin() {
    return this.authService.isAdmin();
  }

  approvePost() {
    this.adminService.approvePost(this.post.id).subscribe(data => {
     this.approveEvent.emit('approved');
    });
    
 }

 deletePost() {
   this.adminService.deletePost(this.post.id).subscribe(data => 
    {this.deleteEvent.emit('deleted');});
 }


}
