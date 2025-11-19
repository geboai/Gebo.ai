// layout.component.ts
import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'gebo-ai-chat-layout',
    templateUrl: 'layout.component.html',
    styleUrls: ['layout.component.css'],
    standalone: false
})
export class LayoutComponent implements OnInit {

    @Input() isCollapsed = false;   // sidebar stretta (desktop)
    @Output() isCollapsedChanged: EventEmitter<boolean> = new EventEmitter();
    isMobile = false;      // breakpoint
    mobileOpen = false;    // sidebar aperta in overlay su mobile

    ngOnInit(): void {
        this.checkIsMobile();
        // opzionale: recupero stato da localStorage
        const saved = localStorage.getItem('sidebarCollapsed');
        if (saved !== null) {
            this.isCollapsed = saved === 'true';
        }
    }

    @HostListener('window:resize')
    onResize() {
        this.checkIsMobile();
    }

    private checkIsMobile() {
        this.isMobile = window.innerWidth < 992; // ~ breakpoint Bootstrap lg
        if (!this.isMobile) {
            this.mobileOpen = false;
        }
    }

    toggleSidebar() {
        if (this.isMobile) {
            this.mobileOpen = !this.mobileOpen;
        } else {
            this.isCollapsed = !this.isCollapsed;
            localStorage.setItem('sidebarCollapsed', String(this.isCollapsed));
        }
    }
}
