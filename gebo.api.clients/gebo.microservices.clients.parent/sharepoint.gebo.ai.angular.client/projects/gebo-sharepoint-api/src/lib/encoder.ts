import { HttpUrlEncodingCodec } from '@angular/common/http';

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
