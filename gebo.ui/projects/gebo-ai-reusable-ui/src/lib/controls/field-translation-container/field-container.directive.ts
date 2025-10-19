import { AfterContentInit, Directive, ElementRef, Inject, Optional, Renderer2 } from '@angular/core';
import { GeboAIFieldContainerComponent } from './field-container.component';
import { NgControl, Validators } from '@angular/forms';
import { merge, startWith } from 'rxjs';

@Directive({
    selector: '[gebo-ai-field-element]',
    standalone: false
})
export class GeboAIFieldContainerDirective implements AfterContentInit {
    constructor(
        private el: ElementRef<HTMLElement>,
        private ngControl: NgControl,
        private renderer: Renderer2,
        @Optional() @Inject(GeboAIFieldContainerComponent) private wrapper: GeboAIFieldContainerComponent | null
    ) { }

    ngAfterContentInit(): void {
        if (!this.wrapper) return;


        const existingId = this.el.nativeElement.getAttribute('id');
        if (!existingId) {
            this.renderer.setAttribute(this.el.nativeElement, 'id', this.wrapper.id);
        }

        // aria-describedby -> collega all'help del wrapper (se presente)
        if (this.wrapper.help) {
            this.renderer.setAttribute(this.el.nativeElement, 'aria-describedby', this.wrapper.helpId);
        }


        if (this.wrapper.placeholder) {
            this.renderer.setAttribute(this.el.nativeElement, "placeholder", this.wrapper.placeholder);
        }
        const ctrl = this.ngControl.control;
        if (ctrl) {
            const hasRequired = typeof (ctrl as any).hasValidator === 'function'
                ? (ctrl as any).hasValidator(Validators.required)
                : false;

            this.wrapper.setRequiredFromControl(hasRequired);
            if (this.wrapper.computedRequired) {
                this.renderer.setAttribute(this.el.nativeElement, 'required', '');
                this.renderer.setAttribute(this.el.nativeElement, 'aria-required', 'true');
            }
            const updateInvalidState = () => {
                const invalid = ctrl.invalid && (ctrl.dirty || ctrl.touched);
                if (invalid) {
                    this.renderer.addClass(this.el.nativeElement, 'gebo-ai-invalid-underline');
                    this.renderer.setAttribute(this.el.nativeElement, 'aria-invalid', 'true');
                } else {
                    this.renderer.removeClass(this.el.nativeElement, 'gebo-ai-invalid-underline');
                    this.renderer.removeAttribute(this.el.nativeElement, 'aria-invalid');
                }
            };

            // iniziale + on changes
            merge(ctrl.statusChanges!, ctrl.valueChanges!).pipe(startWith(null)).subscribe(updateInvalidState);
        }
    }
}
