import { ComponentFixture, TestBed } from '@angular/core/testing';

import { XeMessageComponent } from './xe-message.component';

describe('XeMessageComponent', () => {
  let component: XeMessageComponent;
  let fixture: ComponentFixture<XeMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ XeMessageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(XeMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
