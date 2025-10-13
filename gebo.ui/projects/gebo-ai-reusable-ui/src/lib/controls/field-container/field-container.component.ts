import { Component, Inject, Input, OnInit, Optional } from "@angular/core";
import { ControlContainer, FormGroupDirective } from "@angular/forms";
import { GEBO_AI_FIELD_HOST, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";

@Component({
    templateUrl: "field-container.component.html",
    selector: "gebo-ai-field",
    standalone: false,
    providers: [

        { provide: ControlContainer, useExisting: FormGroupDirective }
    ]
})
export class GeboAIFieldContainerComponent implements OnInit{

    @Input({ required: true }) id!: string;


    @Input({ required: true }) label!: string;

    @Input() placeholder!: string;

    @Input() help: string = '';


    @Input() required: boolean = false;
    computedRequired = false;
    constructor(@Optional() @Inject(GEBO_AI_FIELD_HOST) private host?: GeboAIFieldHost) { 

    }
    ngOnInit(): void {
        
    }
    get helpId(): string {
        return `${this.id}-help`;
    }
    setRequiredFromControl(isRequired: boolean) {
        this.computedRequired = isRequired || this.required;
    }
}