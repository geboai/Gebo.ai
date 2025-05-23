/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * A component that handles license agreement functionality. It implements ControlValueAccessor
 * to work with Angular's form system, allowing it to be used in reactive forms.
 * The component tracks whether a user has accepted a license agreement and emits the
 * license content when accepted.
 */
import { Component, ElementRef, forwardRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";

@Component({
    selector: "gebo-ai-licence-component",
    templateUrl: "licence.component.html",
    styleUrl: "licence.component.css",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAILicenceComponent),
            multi: true
        }
    ],
    standalone: false
})
export class GeboAILicenceComponent implements ControlValueAccessor,OnInit,OnChanges {
    
    /**
     * Reference to the license text content element in the template
     */
    @ViewChild("licenceBody") licenceBody?:ElementRef<Element>;
    
    /**
     * Internal form group to manage the acceptance checkbox state
     */
    public internalFormGroup:FormGroup=new FormGroup({
        acceptMark:new FormControl()
    });
    
    /**
     * Current date used for displaying when the license is being viewed/accepted
     */
    public data:Date=new Date();
    
    /**
     * Name of the person or entity accepting the license
     */
    @Input() public licencee:string="";
    
    /**
     * Initializes the component and sets up a subscription to the acceptMark control.
     * When the checkbox value changes, it either notifies acceptance or non-acceptance.
     */
    ngOnInit(): void {
        this.internalFormGroup.controls["acceptMark"].valueChanges.subscribe(
            (acceptMark:any)=>{
                if (acceptMark===true) {
                    this.notifyAccepted();
                }else {
                    this.notifyNotAccepted();
                }
            }
        );   
    }
    
    /**
     * Handles changes to component inputs. If the licensee name changes and the
     * license is already accepted, it re-triggers the acceptance notification.
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.licencee && changes["licencee"]) {
            const acceptMark=this.internalFormGroup.controls["acceptMark"].value
            if (acceptMark===true) {
                this.notifyAccepted();
            }
        }
    }
    
    /**
     * Notifies the parent form that the license has not been accepted
     * by passing undefined to the onChange callback
     */
    notifyNotAccepted():void {
        this.onChange(undefined);
    }
    
    /**
     * Notifies the parent form that the license has been accepted
     * by passing the license HTML content through the onChange callback
     */
    notifyAccepted():void {
        if (this.licenceBody?.nativeElement?.innerHTML) {
            this.onChange(this.licenceBody?.nativeElement?.innerHTML);
        }
    }
    
    /**
     * ControlValueAccessor method to write a value to the component.
     * Not implemented as this component only outputs values.
     */
    writeValue(obj: any): void {
        
    }
    
    /**
     * Default onChange handler - replaced when registerOnChange is called
     */
    private onChange:(a:any)=>void=(a:any)=>{};
    
    /**
     * ControlValueAccessor method to register a function to call when the control's value changes
     */
    registerOnChange(fn: any): void {
        this.onChange=fn;
    }
    
    /**
     * Default onTouched handler - replaced when registerOnTouched is called
     */
    private onTouched:(a:any)=>void=(a:any)=>{};
    
    /**
     * ControlValueAccessor method to register a function to call when the control receives a touch event
     */
    registerOnTouched(fn: any): void {
        this.onTouched=fn;
    }
    
    /**
     * ControlValueAccessor method to enable/disable the component
     * Not implemented in this component
     */
    setDisabledState?(isDisabled: boolean): void {
        
    }

   
}