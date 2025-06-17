import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { RouterModule, Routes } from "@angular/router";
import { BlockUIModule } from "primeng/blockui";
import { GeboAIOauth2LandingComponent } from "./oauth2-landing.component";
const routes:Routes=[
    {
        path:"",
        component: GeboAIOauth2LandingComponent
    }
];
@NgModule({
    imports:[CommonModule,ReactiveFormsModule,BlockUIModule,RouterModule.forChild(routes)],
    declarations:[GeboAIOauth2LandingComponent]
})
export class GeboAIOauth2LandingModule {

}