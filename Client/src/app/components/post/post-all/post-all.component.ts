import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import Post from 'src/app/core/model/post.model';
import { PostService } from 'src/app/core/service/post.service';

@Component({
  selector: 'app-post-all',
  templateUrl: './post-all.component.html',
  styleUrls: ['./post-all.component.css']
})
export class PostAllComponent implements OnInit {

  allPosts$: Observable<Post[]>

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    let categoryId = this.route.snapshot.params['id'];
    this.allPosts$ = this.postService.byCategory(categoryId);
  }

  openPost(id: number) {
    this.router.navigate(['posts/details/', id])
  }

}
