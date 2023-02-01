import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientRideHistoryComponent } from './client-ride-history.component';

describe('RideHistoryComponent', () => {
  let component: ClientRideHistoryComponent;
  let fixture: ComponentFixture<ClientRideHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientRideHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientRideHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
