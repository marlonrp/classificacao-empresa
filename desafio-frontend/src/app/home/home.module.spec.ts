import { HomeModule } from "@home/home.module";

describe('HomeModule', () => {
  beforeEach(() => {
    return new HomeModule();
  });

  it('should create an instance', () => {
    expect(HomeModule).toBeTruthy();
  });
});
