import { HttpUrlEncodingCodec } from '@angular/common/http';

/**
* CustomHttpUrlEncodingCodec
* Fix plus sign (+) not encoding, so sent as blank space
* See: https://github.com/angular/angular/issues/11058#issuecomment-247367318
*
* NOTE the 'override' modifiers below. swagger-codegen's built-in template targets
* Angular 10 (ngVersion=10.0.6) and emits these two methods WITHOUT them, which is
* a hard compile error on this project's Angular/TypeScript
* ("error TS4114: This member must have an 'override' modifier"). Every generated
* client hit it, so a regeneration silently reintroduced a broken encoder.ts into
* the tree - the committed copies had simply been hand-patched afterwards. This
* template makes the generator emit correct code in the first place.
*/
export class CustomHttpUrlEncodingCodec extends HttpUrlEncodingCodec {
    override encodeKey(k: string): string {
        k = super.encodeKey(k);
        return k.replace(/\+/gi, '%2B');
    }
    override encodeValue(v: string): string {
        v = super.encodeValue(v);
        return v.replace(/\+/gi, '%2B');
    }
}
