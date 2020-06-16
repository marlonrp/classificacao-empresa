import { NgModule } from "@angular/core";

import { HomeRoutingModule } from "@home/home.routing";
import { HomeComponent } from "@home/home.component";
import { SharedModule } from "@shared/shared.module";

@NgModule({
  declarations: [HomeComponent],
  imports: [SharedModule, HomeRoutingModule]
})
export class HomeModule {}
