import {
    Component,
    Input,
    OnChanges,
    SimpleChanges
} from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
    selector: 'gebo-ai-web-viewer',
    templateUrl: './web-viewer.component.html',
    styleUrls: ['./web-viewer.component.scss'],
    standalone:false
})
export class WebViewerComponent implements OnChanges {

    /**
     * URL da visualizzare nel componente.
     * Esempio: https://www.gebo.ai/ oppure /assets/demo.html
     */
    @Input() url: string | null = null;

    /**
     * URL sanificata da passare all'iframe.
     */
    safeUrl: SafeResourceUrl | null = null;

    constructor(private sanitizer: DomSanitizer) { }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['url']) {
            this.updateSafeUrl();
        }
    }

    private updateSafeUrl(): void {
        if (!this.url) {
            this.safeUrl = null;
            return;
        }

        // QUI potresti aggiungere un controllo di whitelist/domains
        // se la URL proviene da input utente.

        this.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(this.url);
    }
}
