import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainRowComponent } from './train-row.component';

describe('TrainRowComponent', () => {
  let component: TrainRowComponent;
  let fixture: ComponentFixture<TrainRowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TrainRowComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrainRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
