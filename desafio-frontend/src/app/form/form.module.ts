import { NgModule } from "@angular/core";

import { FormComponent } from "@form/form.component";
import { FormRoutingModule } from "@form/form.routing";
import { SharedModule } from "@shared/shared.module";

@NgModule({
  declarations: [FormComponent],
  imports: [SharedModule, FormRoutingModule]
})
export class FormModule {}
