import { HttpRequest } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { BASE_PATH } from "@Gebo.ai/gebo-ai-rest-api";

@Injectable({
    providedIn:"root"
})
export class GeboBackendListService {
    private static urls:string[]=[];
    public constructor( @Inject(BASE_PATH) private basePath:string) {
        this.addBackendServicesBaseUrl(this.basePath);
    }
    public addBackendServicesBaseUrl(url:string):void  {
        if (!(GeboBackendListService.urls.findIndex(x=>x===url)>=0)) {
            GeboBackendListService.urls.push(url);
        }
    }
    public isGeboBackend(request: HttpRequest<any>):boolean {
        let result:boolean=false;
        const url=request.url;
        result=GeboBackendListService.urls.findIndex(x=>x && url &&  url.startsWith(x))>=0;
        return result;
    }
}