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

@Component({
    selector: "gebo-ai-audio-component",
    templateUrl: "audio-control.component.html",
    standalone: false
})
export class GeboAIAudioControlComponent implements OnInit, OnChanges, OnDestroy {

    /** Indicates whether audio recording is in progress */
    public recording: boolean = false;
    /** Indicates if there was an error during recording */
    public errorState: boolean = false;
    /** MediaRecorder instance used to capture audio */
    private mediaRecorder?: MediaRecorder;
    /** Array to store audio data chunks during recording */
    private audioChunks: BlobPart[] = [];
    /** Indicates if audio is currently playing */
    public playMode: boolean = false;
    /** Indicates if audio playback is paused */
    public playPaused: boolean = false;
    /** Input to disable the audio control component */
    @Input() public disabled: boolean = false;
    /** Input for audio blob to be played */
    @Input() public playAudioTrack?: Blob;
    /** Event emitter that fires when audio recording is completed */
    @Output() public onAudioTrack: EventEmitter<Blob> = new EventEmitter();
    
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
     * Lifecycle hook that is called when the component is destroyed
     */
    ngOnDestroy(): void {

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
            audio.play();

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
            this.audio.play();
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
        navigator.mediaDevices.getUserMedia({ audio: true })
            .then(stream => {
                // Try a MIME type that's commonly supported on mobile
                const options = {
                    mimeType: 'audio/webm; codecs=opus'
                };

                // Check if this MIME type is supported
                if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                    console.warn(`${options.mimeType} is not supported on this browser. Using default settings.`);
                    // Use the default
                    this.mediaRecorder = new MediaRecorder(stream);
                } else {
                    this.mediaRecorder = new MediaRecorder(stream, options);
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
                this.errorState = true;
                this.recording = false;
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
                this.mediaRecorder = undefined;
                // Do something with 'audioBlob', e.g. upload or play.

            };
        }
    }
}