export const DEFAULT_LANGUAGE: string = "en";
/** Where the language the person chose is kept between visits. */
export const GEBO_AI_LOCAL_STORAGE_LANG: string = "gebo.ai.lang";
export interface UIExistingText {
    moduleId?: string | null,
    entityId?: string | null;
    componentId?: string | null;
    key: string;
    fieldId: string;
    text: string;
    translation?: string;
}

export type UILanguageResources = { [key: string]: (UILanguageResources | string) };
function getAttribute(key: string, data: UILanguageResources | string | undefined | null): UILanguageResources | string | undefined | null {
    if (typeof data === "string") return undefined;
    return data ? data[key] : undefined;
}
export function extractUIExistingText(item: UIExistingText, data: UILanguageResources): UIExistingText | undefined {
    if (item.moduleId && item.entityId && item.componentId && item.fieldId) {
        const module = getAttribute(item.moduleId, data);
        const entity = getAttribute(item.entityId, module);
        // ids taken from Java class names (the data source menu items) are stored with their
        // dots turned into underscores, as the recorder writes them
        const component = getAttribute(item.componentId, entity) ?? getAttribute(item.componentId.replace(/\./g, "_"), entity);
        const attribute = getAttribute(item.fieldId, component);
        if (module && entity && component && attribute) {
            if (typeof attribute === "string") {
                const out: UIExistingText = {
                    ...item,
                    translation: attribute
                };
                return out;
            }
        }
        return undefined;
    } else return undefined;
}

export function findMatchingTranslation(example: UIExistingText, data: UILanguageResources): UIExistingText | undefined {
    return extractUIExistingText(example,data);
}

export function findMatchingTranlations(examples: UIExistingText[], data: UILanguageResources): UIExistingText[] | undefined {
    const map: Map<string, string> = new Map();
    return examples?.map(x=>extractUIExistingText(x,data)).filter(y=>y?true:false) as (UIExistingText[] | undefined);
}