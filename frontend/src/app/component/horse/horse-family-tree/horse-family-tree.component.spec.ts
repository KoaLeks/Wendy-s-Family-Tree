import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HorseFamilyTreeComponent } from './horse-family-tree.component';

describe('HorseFamilyTreeComponent', () => {
  let component: HorseFamilyTreeComponent;
  let fixture: ComponentFixture<HorseFamilyTreeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HorseFamilyTreeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HorseFamilyTreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
