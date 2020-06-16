import { TestBed, async } from "@angular/core/testing";
import { RouterTestingModule } from '@angular/router/testing';

import { AppComponent } from "@app/app.component";
import { SharedModule } from '@shared/shared.module';

describe("AppComponent", () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AppComponent],
      imports: [
        RouterTestingModule,
        SharedModule
      ]
    }).compileComponents();
  }));

  it("should create the app", () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
