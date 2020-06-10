import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";

import { HomeComponent } from "~features/home/home.component";
import { ErrorPageComponent } from "~features/error-page/error-page.component";
import { RegisterEntryComponent } from "~features/register-entry/register-entry.component";

export const routes: Routes = [
    {
        path: "home",
        component: HomeComponent
    },
    {
        path: "register-entry",
        component: RegisterEntryComponent
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
]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    providers: [],
    declarations: [],
    exports: [RouterModule]
})
export class RoutingModule {}