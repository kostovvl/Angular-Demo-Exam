import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PostCreateComponent } from './post-create/post-create.component';
import { PostDetailsComponent } from './post-details/post-details.component';
import { PostEditComponent } from './post-edit/post-edit.component';
import { PostAllComponent } from '../post/post-all/post-all.component';


const routes: Routes = [
    { path: '', pathMatch: 'full', component: PostAllComponent },
    { path: 'all/:id', component: PostAllComponent },
    { path: 'create', component: PostCreateComponent },
    { path: 'edit/:id', component: PostEditComponent },
    { path: 'details/:id', component: PostDetailsComponent }
]

// configures NgModule imports and exports
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })

  export class PostsRoutingModule{

  }