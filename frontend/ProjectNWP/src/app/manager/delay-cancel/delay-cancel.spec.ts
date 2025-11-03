import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DelayCancel } from './delay-cancel';

describe('DelayCancel', () => {
  let component: DelayCancel;
  let fixture: ComponentFixture<DelayCancel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DelayCancel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DelayCancel);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
