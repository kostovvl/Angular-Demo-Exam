import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AdminService } from 'src/app/core/service/admin.service';
import Comment from 'src/app/core/model/comment.model';

@Component({
  selector: 'app-comment-details-admin',
  templateUrl: './comment-details-admin.component.html',
  styleUrls: ['./comment-details-admin.component.css']
})
export class CommentDetailsAdminComponent implements OnInit {

  @Input('comment') comment: Comment;
  @Output('approve') approveEvent: EventEmitter<string> = new EventEmitter;
  @Output('delete') deleteEvent: EventEmitter<string> = new EventEmitter;

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
  }

  approveComment() {
    this.adminService.approveComment(this.comment.id).subscribe(data => {
     this.approveEvent.emit('approved');
    });
    
 }

 deleteComment() {
   this.adminService.deleteComment(this.comment.id).subscribe(data => 
    {this.deleteEvent.emit('deleted');});
 }

}
