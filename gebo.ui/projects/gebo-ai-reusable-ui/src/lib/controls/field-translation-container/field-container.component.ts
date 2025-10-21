import { Component, Inject, Input, OnChanges, OnInit, Optional, SimpleChanges } from "@angular/core";
import { ControlContainer, FormGroupDirective } from "@angular/forms";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";
import { Subject } from "rxjs";
import { findMatchingTranslation, UIExistingText } from "./text-language-resources";
import { GeboAITranslationService } from "./gebo-translation.service";

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
    constructor(private translationService: GeboAITranslationService, @Optional() @Inject(GEBO_AI_MODULE) private moduleId?: string, @Optional() @Inject(GEBO_AI_FIELD_HOST) private host?: GeboAIFieldHost) {
        if (this.host) {
            if (Array.isArray(this.host)) {
                const array = Array.from(this.host);
                this.host = array.length ? array[array.length - 1] : undefined;
            }
        }
        if (this.moduleId && this.moduleId.length) {
            this.moduleId = this.moduleId[this.moduleId.length - 1];
        }
    }
    localizationSubject: Subject<{ label?: string, help?: string, placeholder?: string }> = new Subject();
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
        const existingTexts: UIExistingText[] = [];
        let label: UIExistingText | undefined = undefined;
        let help: UIExistingText | undefined = undefined;
        let placeholder: UIExistingText | undefined = undefined;
        if (this.internalLabel) {
            existingTexts.push(label = {
                moduleId:this.moduleId,
                entityId: this.host?.getEntityName(),
                componentId: this.id,
                key: "label",
                fieldId: "label",
                text: this.internalLabel
            });
        }
        if (this.internalHelp) {
            existingTexts.push(help = {
                moduleId:this.moduleId,
                entityId: this.host?.getEntityName(),
                componentId: this.id,
                key: "help",
                fieldId: "help",
                text: this.internalHelp
            });
        }
        if (this.internalPlaceholder) {
            existingTexts.push(placeholder = {
                moduleId:this.moduleId,
                entityId: this.host?.getEntityName(),
                componentId: this.id,
                key: "placeholder",
                fieldId: "placeholder",
                text: this.internalHelp
            });
        }
        const substitutions = this.translationService.translateOnActualLanguage(existingTexts);
        if (substitutions) {
            substitutions.subscribe({
                next: (resources) => {
                    if (resources ) {
                        if (label) {
                            const labelTranslation = findMatchingTranslation(label, resources);
                            if (labelTranslation && labelTranslation.translation) {
                                this.internalLabel = labelTranslation.translation;
                            }
                        }
                        if (help) {
                            const helpTranslation = findMatchingTranslation(help, resources);
                            if (helpTranslation && helpTranslation.translation) {
                                this.internalHelp = helpTranslation.translation;
                            }
                        }
                        if (placeholder) {
                            const placeholderTranslation = findMatchingTranslation(placeholder, resources);
                            if (placeholderTranslation && placeholderTranslation.translation) {
                                this.internalPlaceholder = placeholderTranslation.translation;
                            }
                        }
                        this.localizationSubject.next({
                            label: this.internalLabel,
                            help: this.internalHelp,
                            placeholder: this.internalPlaceholder
                        });
                    }
                }
            });
        }
    }
    get helpId(): string {
        return `${this.id}-help`;
    }
    setRequiredFromControl(isRequired: boolean) {
        this.computedRequired = isRequired || this.required;
    }
}