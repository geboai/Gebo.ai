export const DEFAULT_LANGUAGE: string = "en";
export interface UIExistingText {
    entityId?: string|null;
    componentId?: string|null;
    key: string;
    fieldId: string;
    text: string;
    translation?: string;
}

export function findMatchingTranslation(example: UIExistingText, data: UIExistingText[]): UIExistingText | undefined {
    return data?.find(x => x.entityId === example.entityId && x.componentId === example.componentId && x.fieldId === example.fieldId && (x.translation ? true : false));
}

export function findMatchingTranlations(examples: UIExistingText[], data: UIExistingText[]): UIExistingText[] | undefined {
    const map: Map<string, string> = new Map();
    if (examples) {
        examples.forEach(x => {
            if (x.entityId && x.componentId)
                map.set(x.entityId + "||" + x.componentId, "OK");
        });
    }
    const filtered = data.filter(x => (x.entityId && x.componentId) && map.has(x.entityId + "||" + x.componentId));
    return examples.map(x => findMatchingTranslation(x, filtered)).filter(y => y ? true : false) as UIExistingText[];
}