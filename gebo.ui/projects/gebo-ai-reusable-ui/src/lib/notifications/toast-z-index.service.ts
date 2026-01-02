import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ToastZIndexService {
  private _z = new BehaviorSubject<number>(10001);
  z$ = this._z.asObservable();

  bumpAboveOverlays(offset = 10, floor = 10001) {
    const selectors = [
      '.p-dialog-mask',
      '.p-dynamic-dialog-mask',
      '.p-component-overlay',
      '.p-dialog',
      '.p-dynamic-dialog'
    ];

    let max = 0;
    for (const sel of selectors) {
      document.querySelectorAll(sel).forEach(el => {
        const z = parseInt(getComputedStyle(el as Element).zIndex || '0', 10);
        if (!Number.isNaN(z)) max = Math.max(max, z);
      });
    }

    this._z.next(Math.max(floor, max + offset));
  }
}
