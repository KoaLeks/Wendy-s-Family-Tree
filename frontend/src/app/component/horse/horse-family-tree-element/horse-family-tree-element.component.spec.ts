import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HorseFamilyTreeElementComponent } from './horse-family-tree-element.component';

describe('HorseFamilyTreeElementComponent', () => {
  let component: HorseFamilyTreeElementComponent;
  let fixture: ComponentFixture<HorseFamilyTreeElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HorseFamilyTreeElementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HorseFamilyTreeElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
