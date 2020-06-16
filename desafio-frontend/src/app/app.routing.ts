import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { ErrorPageComponent } from "@error-page/error-page.component";
import { SharedModule } from "@shared/shared.module";

const routes: Routes = [
  {
    path: "home",
    loadChildren: "@home/home.module#HomeModule"
  },
  {
    path: "form",
    loadChildren: "@form/form.module#FormModule"
  },
  {
    path: "error-page",
    component: ErrorPageComponent
  },
  {
    path: "",
    redirectTo: "/home",
    pathMatch: "full"
  },
  {
    path: "**",
    redirectTo: "/error-page"
  }
];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
