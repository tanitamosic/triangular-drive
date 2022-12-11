import { TestBed } from '@angular/core/testing';

import { UnloggedServiceService } from './unlogged-service.service';

describe('UnloggedServiceService', () => {
  let service: UnloggedServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UnloggedServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
