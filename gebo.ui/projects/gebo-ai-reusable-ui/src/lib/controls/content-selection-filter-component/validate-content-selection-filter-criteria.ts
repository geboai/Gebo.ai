import { ValidationErrors } from "@angular/forms";
import { GContentSelectionFilterCriteria } from "@Gebo.ai/gebo-ai-rest-api";

export function validateCriteria(value: GContentSelectionFilterCriteria): ValidationErrors | null {
    let validationValue: ValidationErrors | null = null;
    let atLeastACondition: boolean = false;
    if (value.maxFileSize) atLeastACondition = true;
    if (value.maxModificationAgeInDays) atLeastACondition = true;
    if (value.nameFilter && value.nameFilterCriteria !== undefined) atLeastACondition = true;
    if ((value.nameFilter && value.nameFilterCriteria === undefined) || (!value.nameFilter && value.nameFilterCriteria !== undefined)) {
        validationValue={"nameFilterConditionInvalid":true};
    }
    if (value.extensions && value.extensions?.length) atLeastACondition = true;
    if (atLeastACondition && !validationValue)
        return null;
    if (validationValue) return validationValue;

    validationValue = { "noConditionExpressed": true };
    return validationValue;

}