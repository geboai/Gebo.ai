import { Component, Inject, Input, OnChanges, OnInit, Optional, SimpleChanges } from "@angular/core";
import { ControlContainer, FormGroupDirective } from "@angular/forms";
import { GEBO_AI_FIELD_HOST, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";
import { Subject } from "rxjs";

@Component({
    templateUrl: "field-container.component.html",
    selector: "gebo-ai-field",
    standalone: false,
    providers: [

        { provide: ControlContainer, useExisting: FormGroupDirective }
    ]
})
export class GeboAIFieldContainerComponent implements OnInit, OnChanges {
    @Input({ required: true }) id!: string;
    @Input({ required: true }) label!: string;
    @Input() placeholder!: string;
    @Input() help: string = '';
    @Input() required: boolean = false;
    internalLabel!: string;
    internalPlaceholder!: string;
    internalHelp!: string;
    computedRequired: boolean = false;
    constructor(@Optional() @Inject(GEBO_AI_FIELD_HOST) private host?: GeboAIFieldHost) {

    }
    localizationSubject:Subject<{label?:string,help?:string,placeholder?:string}>=new Subject(); 
    ngOnChanges(changes: SimpleChanges): void {
        if (!this.internalLabel && this.label && changes["label"]) {
            this.internalLabel = this.label;
        }
        if (!this.internalHelp && this.help && changes["help"]) {
            this.internalHelp = this.help;
        }
        if (!this.internalPlaceholder && this.placeholder && changes["placeholder"]) {
            this.internalPlaceholder = this.placeholder;
        }
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