import { Component } from "@angular/core";
import { GeboAITranslationService } from "./gebo-translation.service";
import { Observable, take } from "rxjs";


@Component({
    selector: "gebo-ai-language-resource-download",
    template: "<p-button icon='pi pi-language' (onClick)='download()' *ngIf='enabled' [rounded]='true' [text]='true' [raised]='true' severity='info'></p-button>",
    standalone: false
})
export class GeboAILanguageResourcesDownloadComponent {
    constructor(private translationService: GeboAITranslationService) {

    }
    public get enabled(): boolean {
        return GeboAITranslationService.recordingOn;
    }
    public download(): void {
        const blob=this.translationService.download();
        this.doDownload(blob);
    }
    private doDownload(blob$: Observable<Blob>, filename = 'en.json') {
        return blob$.pipe(take(1)).subscribe(blob => {
            const url = URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = filename;          
            a.style.display = 'none';
            document.body.appendChild(a);
            a.click();                      
            a.remove();
            URL.revokeObjectURL(url);       
        });
    }
    protected get languages(): readonly string[] {
        return this.translationService.getLanguages();
    }
    protected changeLanguage(lang:string) {
        this.translationService.changeActualLanguage(lang);
    }
}