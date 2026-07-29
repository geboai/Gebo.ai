/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * 
 * This component provides audio recording and playback functionality.
 * It allows users to record audio through their device's microphone and play back 
 * previously recorded or provided audio. The component implements lifecycle hooks
 * to handle initialization, changes to inputs, and cleanup.
 */
import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

/** Why recording is currently unavailable, so callers can show a specific message instead of a generic error. */
export type GeboAudioErrorReason = 'permission-denied' | 'no-device' | 'device-busy' | 'insecure-context' | 'unsupported' | 'unknown';

@Component({
    selector: "gebo-ai-audio-component",
    templateUrl: "audio-control.component.html",
    standalone: false,
     providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIAudioControlModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIAudioControlComponent")}
    ]
})
export class GeboAIAudioControlComponent implements OnInit, OnChanges, OnDestroy {

    /** Indicates whether audio recording is in progress */
    public recording: boolean = false;
    /** Indicates if there was an error during recording */
    public errorState: boolean = false;
    /** Why errorState is set - lets callers show a specific message instead of a generic one */
    public errorReason?: GeboAudioErrorReason;
    /** MediaRecorder instance used to capture audio */
    private mediaRecorder?: MediaRecorder;
    /** Microphone stream backing mediaRecorder - kept so its tracks can be stopped explicitly, otherwise the mic stays active (OS/browser indicator stays lit) after recording ends */
    private mediaStream?: MediaStream;
    /** Array to store audio data chunks during recording */
    private audioChunks: BlobPart[] = [];
    /** Indicates if audio is currently playing */
    public playMode: boolean = false;
    /** Indicates if audio playback is paused */
    public playPaused: boolean = false;
    /** Input to disable the audio control component */
    @Input() public disabled: boolean = false;
    @Input() public viewLabels:boolean=true;
    /** Input for audio blob to be played */
    @Input() public playAudioTrack?: Blob;
    /** Event emitter that fires when audio recording is completed */
    @Output() public onAudioTrack: EventEmitter<Blob> = new EventEmitter();

    /** Ordered by preference - webm/opus (Chrome/Firefox/desktop) then the mp4/aac variants Safari/iOS actually supports */
    private static readonly MIME_CANDIDATES = ['audio/webm;codecs=opus', 'audio/mp4', 'audio/aac', 'audio/mpeg'];

    /** Human-readable message matching errorReason, or undefined when there is no error */
    public get errorMessage(): string | undefined {
        if (!this.errorState || !this.errorReason) {
            return undefined;
        }
        switch (this.errorReason) {
            case 'permission-denied': return 'Microphone access was denied. Check your browser/site permissions and try again.';
            case 'no-device': return 'No microphone was found on this device.';
            case 'device-busy': return 'The microphone is already in use by another application.';
            case 'insecure-context': return 'Audio recording requires a secure (HTTPS) connection.';
            case 'unsupported': return 'Audio recording is not supported on this browser.';
            default: return 'Unable to access the microphone.';
        }
    }

    /**
     * Lifecycle hook that is called after data-bound properties are initialized
     */
    ngOnInit(): void {

    }

    /**
     * Lifecycle hook that is called when any data-bound property changes
     * Starts audio playback when playAudioTrack input changes
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.playAudioTrack && changes["playAudioTrack"]) {
            this.play();
        }
    }

    /**
     * Lifecycle hook that is called when the component is destroyed.
     * Releases the MediaRecorder and microphone stream if a recording was left in progress.
     */
    ngOnDestroy(): void {
        if (this.mediaRecorder && this.mediaRecorder.state !== 'inactive') {
            try { this.mediaRecorder.stop(); } catch (e) { }
        }
        this.mediaRecorder = undefined;
        this.releaseMediaStream();
    }

    /** HTML Audio element for playback control */
    private audio?: HTMLAudioElement;

    /**
     * Starts playing the audio track that was provided as input
     * Creates an audio element and begins playback
     */
    play(): void {
        if (this.playAudioTrack) {
            this.playMode = true;
            const audioUrl = URL.createObjectURL(this.playAudioTrack);
            const audio = new Audio(audioUrl);
            audio.play().then(()=>{

            },()=>{
                // Blocked by the browser's autoplay policy - common on mobile Safari/Chrome
                // since this play() runs from a change-detection callback, not directly
                // inside a user gesture. Fall back to the "paused" state so the existing
                // continuePlay() button (invoked from a real click handler, which autoplay
                // policies allow) can start it instead of leaving a stuck fake-playing UI.
                this.playMode = false;
                this.playPaused = true;
            });
            audio.onended=(ev)=>{
                this.playMode=false;
                this.playPaused=false;
            }

            this.audio = audio;
        }
    }

    /**
     * Pauses the currently playing audio
     */
    pausePlay(): void {
        if (this.audio) {
            this.audio.pause();
            this.playMode = false;
            this.playPaused = true;
        }
    }

    /**
     * Resumes playback of a paused audio track
     */
    continuePlay(): void {
        if (this.audio) {
            this.audio.play().then(()=>{
                this.playMode=false;
            },()=>{});
            this.playMode = true;
            this.playPaused = false;
        }
    }

    /**
     * Cancels an ongoing recording session and discards any recorded data
     */
    abortRecording() {
        if (this.mediaRecorder) {
            try {
                this.mediaRecorder.stop();
                this.mediaRecorder.onstop = () => {
                    this.audioChunks = [];
                    this.recording = false;
                    this.errorState = false;
                    this.errorReason = undefined;
                    this.releaseMediaStream();
                }
            } catch (e) { }
        }
    }

    /**
     * Begins recording audio from the user's microphone
     * Handles browser permissions and sets up the MediaRecorder
     */
    startRecording() {
        this.recording = true;
        this.errorState = false;
        this.errorReason = undefined;

        if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
            // navigator.mediaDevices is undefined on insecure contexts (getUserMedia is only
            // exposed over HTTPS/localhost) as well as on browsers too old to implement it -
            // calling .getUserMedia straight on undefined would throw synchronously, before
            // any promise/catch exists to route it into errorState.
            this.setError(typeof window !== 'undefined' && window.isSecureContext === false ? 'insecure-context' : 'unsupported');
            return;
        }
        if (typeof MediaRecorder === 'undefined') {
            this.setError('unsupported');
            return;
        }

        navigator.mediaDevices.getUserMedia({ audio: true })
            .then(stream => {
                this.mediaStream = stream;

                // Try MIME types in order of preference - webm/opus works on Chrome/Firefox/desktop
                // but Safari/iOS never support it, so an explicit fallback chain is needed rather
                // than relying on whatever MediaRecorder's undocumented per-version default produces.
                const mimeType = GeboAIAudioControlComponent.MIME_CANDIDATES
                    .find(candidate => MediaRecorder.isTypeSupported(candidate));
                if (!mimeType) {
                    console.warn('No preferred audio MIME type is supported on this browser. Using default settings.');
                }

                try {
                    this.mediaRecorder = mimeType ? new MediaRecorder(stream, { mimeType }) : new MediaRecorder(stream);
                } catch (e) {
                    console.error('Unable to create a MediaRecorder on this browser', e);
                    this.setError('unsupported');
                    this.releaseMediaStream();
                    return;
                }

                this.mediaRecorder.ondataavailable = event => {
                    if (event.data.size > 0) {
                        this.audioChunks.push(event.data);
                    }
                };

                this.mediaRecorder.start();
            })
            .catch(error => {
                console.error('Error accessing microphone', error);
                this.setError(GeboAIAudioControlComponent.reasonFromError(error));
            });
    }

    /**
     * Stops the current recording, processes the audio data,
     * and emits the completed audio blob to parent components
     */
    stopRecording() {
        this.recording = false;
        if (this.mediaRecorder) {
            this.mediaRecorder.stop();

            this.mediaRecorder.onstop = () => {
                const audioBlob = new Blob(this.audioChunks, { type: this.mediaRecorder?.mimeType || 'audio/webm' });
                this.audioChunks = [];
                this.onAudioTrack.emit(audioBlob);
                this.errorState = false;
                this.errorReason = undefined;
                this.mediaRecorder = undefined;
                this.releaseMediaStream();
                // Do something with 'audioBlob', e.g. upload or play.

            };
        }
    }

    private setError(reason: GeboAudioErrorReason): void {
        this.errorState = true;
        this.errorReason = reason;
        this.recording = false;
    }

    private releaseMediaStream(): void {
        this.mediaStream?.getTracks().forEach(track => track.stop());
        this.mediaStream = undefined;
    }

    private static reasonFromError(error: any): GeboAudioErrorReason {
        switch (error?.name) {
            case 'NotAllowedError':
            case 'PermissionDeniedError':
            case 'SecurityError':
                return 'permission-denied';
            case 'NotFoundError':
            case 'DevicesNotFoundError':
                return 'no-device';
            case 'NotReadableError':
            case 'TrackStartError':
                return 'device-busy';
            default:
                return 'unknown';
        }
    }
}