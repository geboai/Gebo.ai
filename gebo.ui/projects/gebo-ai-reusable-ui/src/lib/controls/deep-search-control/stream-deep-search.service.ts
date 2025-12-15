import { BASE_PATH, DeepSearchRequest, GeboChatRequest } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAIBaseStreamingService, IGeboChatMessage } from "../../services/base-streaming.service";
import { Inject, Injectable } from "@angular/core";
const deepSearchStreamRelativeURL:string="/api/users/GeboDeepSearchController/streamDeepSearch";
const deepSearchStreamInChatRelativeURL:string="/api/users/GeboDeepSearchController/streamDeepSearchWithChatContext";
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
    public streamDeepSearchInChat(request:GeboChatRequest, onMessage: (msg: IGeboChatMessage | string) => void, onError?: (err: any) => void): void {
        const url:string=this.basePath+deepSearchStreamInChatRelativeURL;
        this.internalStreamChat(url,request,onMessage,onError);
    }
}