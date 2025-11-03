import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPlane } from './add-plane';

describe('AddPlane', () => {
  let component: AddPlane;
  let fixture: ComponentFixture<AddPlane>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddPlane]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddPlane);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
