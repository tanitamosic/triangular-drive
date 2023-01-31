import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserListDisplayComponent } from './user-list-display.component';

describe('UserListDisplayComponent', () => {
  let component: UserListDisplayComponent;
  let fixture: ComponentFixture<UserListDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserListDisplayComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserListDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
