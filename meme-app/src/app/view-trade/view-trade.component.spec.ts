import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewTradeComponent } from './view-trade.component';

describe('ViewTradeComponent', () => {
  let component: ViewTradeComponent;
  let fixture: ComponentFixture<ViewTradeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewTradeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewTradeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
