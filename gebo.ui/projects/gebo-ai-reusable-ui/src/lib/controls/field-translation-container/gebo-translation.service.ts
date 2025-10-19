import { Injectable } from "@angular/core";
import { DEFAULT_LANGUAGE, UIExistingText } from "./text-language-resources";
import { Observable, of, Subject,concat } from "rxjs";

/*******************************************************************************
 * The actual UI resources are all written in english, used as a default language
 * resources source, once the language is changed this service will publish all
 * the translations and respond to immediate text resources lookups (translateOnActualLanguage)
 */
@Injectable({
    providedIn: "root"
})
export class GeboAITranslationService {
    private static actualLanguage: string = DEFAULT_LANGUAGE;

    public languageChanges: Subject<UIExistingText[]> = new Subject();
    public translateOnActualLanguage(textResources: UIExistingText[]): Observable<UIExistingText[]> {
        if (textResources) {
            textResources.forEach(x=>{
                console.log(JSON.stringify(x));
            });
        }
        const texts: UIExistingText[] = [];

        return concat(of(texts),this.languageChanges);
    }
    public changeActualLanguage(language: string): void {
        GeboAITranslationService.actualLanguage = language;
    }
}