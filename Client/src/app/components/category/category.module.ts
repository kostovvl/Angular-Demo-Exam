import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AllCategoriesComponent } from './all-categories/all-categories.component';
import { ReactiveFormsModule } from '@angular/forms';




@NgModule({
  declarations: [AllCategoriesComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [
    AllCategoriesComponent
  ]
})
export class CategoryModule { }
