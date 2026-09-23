import { HttpRequest } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { BASE_PATH } from "@Gebo.ai/gebo-ai-rest-api";

@Injectable({
    providedIn: "root"
})
export class GeboBackendListService {
    private static urls: string[] = [];
    private static publicUrls: string[] = [];
    public constructor(@Inject(BASE_PATH) private basePath: string) {
        this.addBackendServicesBaseUrl(this.basePath);
    }
    public addBackendServicesBaseUrl(url: string): void {
        if (!(GeboBackendListService.urls.findIndex(x => x === url) >= 0)) {
            GeboBackendListService.urls.push(url);
        }
    }
    public addBackendServicesPublicUrl(relativeUrl: string): void {

        GeboBackendListService.publicUrls.push(this.basePath + relativeUrl);

    }
    public isGeboPublicUrl(request: HttpRequest<any>): boolean {
        let result: boolean = false;
        const url = request.url;
        result = GeboBackendListService.publicUrls.findIndex(x => x && url && url.startsWith(x)) >= 0;
        return result;
    }

    public isGeboBackend(request: HttpRequest<any>): boolean {
        let result: boolean = false;
        const url = request.url;
        result = GeboBackendListService.urls.findIndex(x => x && url && url.startsWith(x)) >= 0;
        return result;
    }
}