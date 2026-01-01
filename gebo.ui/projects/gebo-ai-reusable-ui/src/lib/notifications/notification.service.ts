import { Inject, Injectable } from "@angular/core";
import { GeboAIRootNotificationService } from "./root-notification.service";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../controls/field-host-component-iface/field-host-component-iface";

@Injectable()
export class GeboAINotificationService {
    constructor(
        private service: GeboAIRootNotificationService,
        @Inject(GEBO_AI_MODULE) moduleId: string,
        @Inject(GEBO_AI_FIELD_HOST) private host: GeboAIFieldHost) {

    }
}