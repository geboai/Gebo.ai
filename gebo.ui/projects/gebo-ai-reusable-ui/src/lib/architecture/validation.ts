import { AbstractControl, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";
const stdErrorMessage: ValidationErrors = {
    baseUrl: "A valid url is required"
};
const wrongProtocolErrorMessage: ValidationErrors = {
    baseUrl: "A valid http or https url is required"
};
export class GeboAIValidators extends Validators {
    public static baseUrl(required: boolean): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const actualValue = control.value;
            if (!actualValue && required === true) return {
                baseUrl: "This url is required"
            };
            if (!actualValue && required !== true)
                return null;
            try {
                const urlValue: URL = new URL(actualValue);
                if (!(urlValue.protocol && urlValue.protocol.toLowerCase() in ["http","https"])) {
                    return wrongProtocolErrorMessage;
                }
            } catch (e) {
                return stdErrorMessage;
            }
            return null;
        }
    }
}