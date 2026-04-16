import { Component, forwardRef, Input, OnInit } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { GBaseChatModelConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAITranslationService } from "../field-translation-container/gebo-translation.service";
import { findMatchingTranlations, UIExistingText } from "../field-translation-container/text-language-resources";
import { map } from "rxjs";
interface InternalData {[key:string]:boolean};
@Component({
    selector: "gebo-ai-chat-model-use-component",
    templateUrl: "chat-model-use.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIChatModelUseModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIChatModelUseComponent"), multi: false },
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIChatModelUseComponent),
            multi: true
        }
    ]
})
export class GeboAIChatModelUseComponent implements ControlValueAccessor,OnInit {
    protected formGroup!: FormGroup;
    private chatModelUses: GBaseChatModelConfig.ForUsesEnum[] = [];
    protected options: { code: GBaseChatModelConfig.ForUsesEnum, description: string }[] = [
        { code: "CHAT", description: "Chat with users" },
        { code: "INTERNAL_SERVICES", description: "Model used for internal services" }];

    @Input() placeholder:string="";
    constructor(private geboTranslation:GeboAITranslationService) {
        const objects:{[key:string]:FormControl}={};
        this.options.forEach(x=>{
            objects[x.code]=new FormControl();
        })
        this.formGroup=new FormGroup(objects);
        this.formGroup.valueChanges.subscribe({
            next:(data:InternalData)=>{
                const out:GBaseChatModelConfig.ForUsesEnum[] = [];
                if (data) {
                    const keys=Object.keys(data);
                    if (keys) {
                        keys.forEach(k=>{
                            if (data[k]===true) {
                                out.push(k as GBaseChatModelConfig.ForUsesEnum);
                            }
                        });
                    }
                    
                }    
                this.chatModelUses=out;
                if (this.onChange)
                    this.onChange(out);
            }
        });
    }    
    ngOnInit(): void {
        
        const texts=this.options.map(x=>{
            const text:UIExistingText={
                moduleId:"GeboAIChatModelUseModule",
                entityId:"GeboAIChatModelUseComponent",
                componentId:x.code,
                fieldId:"description",
                key:"description",
                text:x.description
            };
            return text;
        });
        this.geboTranslation.translateOnActualLanguage(texts).pipe(map(resource=>{
            return resource? findMatchingTranlations(texts,resource):texts;
        })).subscribe({
            next:(options)=>{
                let translated:{ code: GBaseChatModelConfig.ForUsesEnum, description: string }[]=[];
                if (options && options.length) {
                    translated=options.map(x=>{
                        const value:any={code:x.componentId,description:x.translation?x.translation:x.text};
                        return value;
                    });
                    this.options=translated;
                }
            }
        });
    }
    writeValue(obj: any): void {
        this.chatModelUses = obj;
        const data:InternalData={};
        if (this.chatModelUses && this.chatModelUses.length) {
            this.chatModelUses.forEach(cmu=>{
                data[cmu]=true;
            });
        }
        this.formGroup.patchValue(data);
    }
    private onChange: any;
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    private onTouch: any;
    registerOnTouched(fn: any): void {
        this.onTouch = fn;
    }
    setDisabledState?(isDisabled: boolean): void {
        if (isDisabled === true) {
            this.formGroup.disable();
        } else {
            this.formGroup.enable();
        }
    }
}