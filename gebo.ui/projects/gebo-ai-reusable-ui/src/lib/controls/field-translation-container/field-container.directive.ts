import { AfterContentInit, Directive, ElementRef, Inject, OnDestroy, Optional, Renderer2, Self } from '@angular/core';
import { GeboAIFieldContainerComponent } from './field-container.component';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, NgControl, Validators } from '@angular/forms';
import { merge, startWith, Subscription } from 'rxjs';
import { safeSet } from './primeng-components-multilanguage-adapters.directive';

/**
 * True when the instance's component or directive declares a placeholder input. Read from the
 * Angular definition, as an uninitialized input field is not an own property here
 * (useDefineForClassFields is false).
 */
function declaresPlaceholderInput(instance: unknown): boolean {
    const type = (instance as { constructor?: any })?.constructor;
    const inputs = type?.ɵcmp?.inputs ?? type?.ɵdir?.inputs;
    return !!(inputs && Object.prototype.hasOwnProperty.call(inputs, "placeholder")) || "placeholder" in (instance as object);
}

@Directive({
    selector: '[gebo-ai-field-element]',
    standalone: false
})
export class GeboAIFieldContainerDirective implements AfterContentInit, OnDestroy {
    private localizationSubscription?: Subscription;
    constructor(
        private el: ElementRef<HTMLElement>,

        private renderer: Renderer2,
        @Optional() @Inject(GeboAIFieldContainerComponent) private wrapper: GeboAIFieldContainerComponent | null,
        @Optional() private ngControl?: NgControl,
        @Optional() @Self() @Inject(NG_VALUE_ACCESSOR) private valueAccessors?: ControlValueAccessor[]
    ) { }

    /**
     * The field's placeholder goes on the element: an input component (p-select, p-multiSelect,
     * p-datepicker, geboai-editable-listbox-component...) declares placeholder as an input and is
     * the element's value accessor, so it is set on the component; a native input or textarea
     * takes the attribute.
     */
    private applyPlaceholder(text: string): void {
        // the accessor of the control (components that assign themselves as ngControl.valueAccessor),
        // else the ones the element provides as NG_VALUE_ACCESSOR
        const candidates: unknown[] = [this.ngControl?.valueAccessor, ...(this.valueAccessors || [])];
        const component = candidates.find(accessor => accessor && declaresPlaceholderInput(accessor));
        if (component) {
            safeSet(component, "placeholder", text);
        } else {
            this.renderer.setAttribute(this.el.nativeElement, "placeholder", text);
        }
    }

    ngOnDestroy(): void {
        this.localizationSubscription?.unsubscribe();
    }

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
            this.applyPlaceholder(this.wrapper.internalPlaceholder || this.wrapper.placeholder);
        }
        // the field publishes its texts in the actual language, the placeholder among them
        this.localizationSubscription = this.wrapper.localizationSubject.subscribe(texts => {
            if (texts?.placeholder) {
                this.applyPlaceholder(texts.placeholder);
            }
        });
        if (this.ngControl) {
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
}
