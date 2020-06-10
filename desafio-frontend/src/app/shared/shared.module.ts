import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";

import { MenuComponent } from "~shared/components/menu/menu.component";

@NgModule({
    imports: [CommonModule, RouterModule.forRoot([])],
    exports: [MenuComponent],
    providers: [],
    declarations: [MenuComponent]
})
export class SharedModule {}