import { BASE_PATH, DeepSearchRequest } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAIBaseStreamingService, IGeboChatMessage } from "../../services/base-streaming.service";
import { Inject, Injectable } from "@angular/core";
const deepSearchStreamRelativeURL:string="api/users/GeboDeepSearchController/streamDeepSearch";
@Injectable({
    providedIn:"root"
})
export class GeboAIStreamDeepSearchService extends GeboAIBaseStreamingService {
    constructor(@Inject(BASE_PATH) private basePath: string) {
        super();

    }
    public streamDeepSearch(request:DeepSearchRequest, onMessage: (msg: IGeboChatMessage | string) => void, onError?: (err: any) => void): void {
        const url:string=this.basePath+deepSearchStreamRelativeURL;
        this.internalStreamChat(url,request,onMessage,onError);
    }
}