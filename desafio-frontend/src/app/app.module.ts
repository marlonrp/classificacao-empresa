import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

import { AppComponent } from "~app/app.component";
import { RoutingModule } from "~app/app-routing.module";
import { SharedModule } from "~shared/shared.module";
import { ErrorPageComponent } from "~features/error-page/error-page.component";
import { HomeComponent } from "~features/home/home.component";
import { RegisterEntryComponent } from "~features/register-entry/register-entry.component";

@NgModule({
  declarations: [AppComponent, ErrorPageComponent, HomeComponent, RegisterEntryComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RoutingModule,
    SharedModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
