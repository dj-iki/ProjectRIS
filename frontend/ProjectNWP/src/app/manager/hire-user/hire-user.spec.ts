import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HireUser } from './hire-user';

describe('HireUser', () => {
  let component: HireUser;
  let fixture: ComponentFixture<HireUser>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HireUser]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HireUser);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
