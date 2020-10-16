import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PostDetailsAdminComponent } from './post-details-admin.component';

describe('PostDetailsAdminComponent', () => {
  let component: PostDetailsAdminComponent;
  let fixture: ComponentFixture<PostDetailsAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PostDetailsAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostDetailsAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
