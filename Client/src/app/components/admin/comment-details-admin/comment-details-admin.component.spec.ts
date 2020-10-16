import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentDetailsAdminComponent } from './comment-details-admin.component';

describe('CommentDetailsAdminComponent', () => {
  let component: CommentDetailsAdminComponent;
  let fixture: ComponentFixture<CommentDetailsAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommentDetailsAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentDetailsAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
