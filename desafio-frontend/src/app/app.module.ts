import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NoPreloading, RouterModule } from "@angular/router";

import { AppComponent } from "@app/app.component";
import { AppRoutingModule } from "@app/app.routing";
import { SharedModule } from "@shared/shared.module";
import { HomeModule } from "@home/home.module";
import { ErrorPageComponent } from "@error-page/error-page.component";
import { FormModule } from "@form/form.module";

@NgModule({
  declarations: [AppComponent, ErrorPageComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot([], {
      paramsInheritanceStrategy: "always",
      preloadingStrategy: NoPreloading,
      useHash: true
    }),
    AppRoutingModule,
    SharedModule,
    HomeModule,
    FormModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
