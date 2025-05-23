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
 * Component for changing user passwords in the Gebo.ai application.
 * Provides a form for users to enter their old password and a new password with confirmation.
 * Handles validation, error messages, and communication with the login service.
 */
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";
import { AuthResponse, ChangePasswordResponse, UserInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { LoginService } from "../login/login.service";

@Component({
    selector: "gebo-ai-change-password-component",
    templateUrl: "change-password.component.html",
    standalone: false
})
export class GeboAIChangePasswordComponent implements OnInit, OnChanges {
  /** The user information object */
  @Input() user?: UserInfo;
  /** The authentication response containing user credentials */
  @Input() auth?: AuthResponse;
  /** Controls the visibility of the change password dialog */
  @Input() visible: boolean = false;
  /** Event emitter that fires when the modal is closed */
  @Output() close: EventEmitter<boolean> = new EventEmitter();
  /** Flag indicating whether a password change operation is in progress */
  loading: boolean = false;
  /** Response from the password change API call */
  changePasswordResult?: ChangePasswordResponse;
  /** Messages to display to the user in the UI */
  userMessages: ToastMessageOptions[] = [{
    severity: "info",
    summary: "Fill these fields to change your password"
  }];
  /** Event emitter that fires when the password is successfully changed */
  @Output() changedPasswordCorrectly: EventEmitter<boolean> = new EventEmitter();
  
  /**
   * Constructor for the change password component
   * @param loginService Service for handling authentication and password operations
   */
  constructor(private loginService: LoginService) {

  }
  
  /**
   * Form group containing the password change form fields
   */
  formGroup: FormGroup = new FormGroup({
    username: new FormControl(),
    oldPassword: new FormControl(),
    newPassword: new FormControl(),
    newPassword1: new FormControl()
  });
  
  /**
   * Handles the closing of the modal dialog
   * @param e Event object from the UI
   */
  closeModal(e: any) {
    this.close.emit(true);
  }
  
  /**
   * Initializes the component and sets up form validators
   * Adds a custom validator to check if the new password entries match
   */
  ngOnInit(): void {
    const equalPasswordsValidator: ValidatorFn = (control: AbstractControl) => {
      const validationError: ValidationErrors = {};
      const formGroup = control as FormGroup;
      const v1: string = formGroup.controls["newPassword"].value;
      const v2: string = formGroup.controls["newPassword1"].value
      this.userMessages = [];
      if (v1 && v2 && v1 === v2) return null;
      if (v1 && v2 && v1 !== v2) {
        const msgs: ToastMessageOptions[] = [];
        if (this.changePasswordResult?.newPasswordNeverMatch == true) {
          msgs.push({
            severity: "error",
            summary: "New password and confirm new password do not match",
            detail: "please retry"
          });
        }
        if (this.changePasswordResult?.wrongPassword == true) {
          msgs.push({
            severity: "error",
            summary: "Old password was wrong",
            detail: "please retry"
          });
        }
        if (!msgs.length) {
          msgs.push({ severity: "error", summary: "Error changing password", detail: "New password and confirm password do not match" });
        }
        this.userMessages = msgs;

      }
      return {
        pwdNotMatching: true
      };
    };
    this.formGroup.addValidators(equalPasswordsValidator);
  }
  
  /**
   * Responds to changes in the component's input properties
   * Updates the form values when user or auth information changes
   * @param changes SimpleChanges object containing the changed properties
   */
  ngOnChanges(changes: SimpleChanges): void {
    if (this.user && this.auth) {
      if (this.user.username === this.auth.userInfo?.username) {
        this.formGroup.patchValue({ username: this.user.username });
      } else {
        this.formGroup.patchValue({});
      }
    }
  }
  
  /**
   * Handles the password change operation
   * Calls the login service's changePassword method and processes the response
   * Updates UI messages based on the result
   */
  doChangePassword(): void {
    this.loading = true;
    this.loginService.changePassword(this.formGroup.value).subscribe(
      {
        next: (value) => {
          this.changePasswordResult = value;
          if (this.changePasswordResult.ok === true) {
            this.userMessages = [{
              severity: "success",
              summary: "Password change correctly",
              detail: "press ok to close this window"
            }];
            this.changedPasswordCorrectly.emit(true);
          } else {
            const msgs: ToastMessageOptions[] = [];
            if (this.changePasswordResult.newPasswordNeverMatch == true) {
              msgs.push({
                severity: "error",
                summary: "New password and confirm new password do not match",
                detail: "please retry"
              });
            }
            if (this.changePasswordResult.wrongPassword == true) {
              msgs.push({
                severity: "error",
                summary: "Old password was wrong",
                detail: "please retry"
              });
            }
            this.userMessages = msgs;
          }
        },
        complete: () => {
          this.loading = false;
        }
      }
    );
  }
  
  /**
   * Allows the user to skip the password change process
   * Emits an event indicating the user chose not to change their password
   */
  doSkip(): void {
    this.changedPasswordCorrectly.emit(false);
  }
}