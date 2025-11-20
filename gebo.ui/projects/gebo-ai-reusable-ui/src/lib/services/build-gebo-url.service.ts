import { Inject, Injectable } from "@angular/core";
import { BASE_PATH } from "@Gebo.ai/gebo-ai-rest-api";
@Injectable({
    providedIn: "root"
})
export class GeboAIBuildUrlService {
    constructor(@Inject(BASE_PATH) private baseUrl: string) {

    }
    public buildUrl(...segments: string[]): string {
        let composedUrl = this.baseUrl;
        if (segments) {
            segments.forEach(segment => {
                if (segment.startsWith("/")) {
                    segment = segment.substring(1, segment.length - 1);
                }
                if (composedUrl.endsWith("/")) {
                    composedUrl = composedUrl.substring(0, composedUrl.length - 1);
                }
                composedUrl += "/" + segment;
            });
        }

        return composedUrl;
    }
}