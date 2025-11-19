import { HttpClient, HttpHeaders } from '@angular/common/http';
import {
    Component,
    Input,
    OnChanges,
    SimpleChanges
} from '@angular/core';
import { DomSanitizer, SafeHtml, SafeResourceUrl } from '@angular/platform-browser';
import { getAuth, getAuthHeader } from '../../infrastructure/gebo-credentials';

@Component({
    selector: 'gebo-ai-web-viewer',
    templateUrl: './web-viewer.component.html',
    styleUrls: ['./web-viewer.component.scss'],
    standalone: false
})
export class WebViewerComponent implements OnChanges {

    /**
     * URL da visualizzare nel componente.
     * Esempio: https://www.gebo.ai/ oppure /assets/demo.html
     */
    @Input() url: string | null = null;
    @Input() contentServedByGebo?: boolean;
    @Input() geboServedFileName?: string;
    /**
     * URL sanificata da passare all'iframe.
     */
    safeUrl: SafeResourceUrl | null = null;
    safeHtml: SafeHtml | null = null;
    protected loading: boolean = false;
    constructor(private sanitizer: DomSanitizer, private httpClient: HttpClient) {

    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['url']) {
            this.updateSafeUrl();
        }
        if ((changes["contentServedByGebo"] || changes['url']) && this.contentServedByGebo === true && this.safeUrl) {
            this.loadBody();
        }
    }
    private loadBody(): void {


        if (this.safeUrl && this.url) {
            const headers: HttpHeaders = new HttpHeaders(getAuthHeader());
            this.loading=true;
            this.httpClient
                .get(this.url, {
                    headers,
                    responseType: 'text'
                })
                .subscribe({
                    next: (html: string) => {
                        this.safeHtml = this.sanitizer.bypassSecurityTrustHtml(html);
                        this.loading = false;
                    },
                    error: (err) => {
                        console.error('Errore caricamento contenuto', err);

                        this.loading = false;
                    },
                    complete: () => {
                        this.loading = false;
                    }
                });
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
