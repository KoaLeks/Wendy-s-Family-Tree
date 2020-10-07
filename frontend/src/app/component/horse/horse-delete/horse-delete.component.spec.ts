import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HorseDeleteComponent } from './horse-delete.component';

describe('HorseDeleteComponent', () => {
  let component: HorseDeleteComponent;
  let fixture: ComponentFixture<HorseDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HorseDeleteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HorseDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
