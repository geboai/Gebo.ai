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
 * Component for managing user data in the Gebo.ai application.
 * Provides functionality for creating, editing, and deleting users with role management.
 * Handles form control for user properties and displays messages for operation outcomes.
 */
import { Component, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { AuthProviderDto, AuthProvidersControllerService, EditableUser, UsersAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { map, Observable } from "rxjs";
@Component({
    selector: "gebo-ai-user-component",
    templateUrl: "gebo-ai-user.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIUsersGroupModule", multi: false }, 
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIUserComponent),
        multi: false
    }]
})
export class GeboAIUserComponent implements OnInit, OnChanges {
    /** Indicates if an operation is in progress */
    public loading: boolean = false;
    /** Array to store toast messages displayed to the user */
    public userMessages: ToastMessageOptions[] = [];
    /** User data received as input, used for editing existing users */
    @Input() user?: EditableUser;
    /** Mode of operation: "NEW" for creating a new user, "EDIT" for updating an existing one */
    @Input() mode?: "EDIT" | "NEW" = "NEW";
    /** Event emitter to notify parent components of changes to user data */
    @Output() changedData: EventEmitter<EditableUser | undefined> = new EventEmitter();

    /** Form group for managing user data fields */
    formGroup: FormGroup = new FormGroup({
        name: new FormControl(),
        sourname: new FormControl(),
        username: new FormControl(),
        disabled: new FormControl(),
        roles: new FormControl(),
        authProvider: new FormControl()
    });

    /** Form group for managing password input */
    pwdFormGroup: FormGroup = new FormGroup({
        password: new FormControl()
    });

    /** Available roles for user assignment */
    protected rolesList: string[] = ["USER", "ADMIN", "APPLICATION"];
    protected actualAuthProvider?: AuthProviderDto.ProviderEnum;
    protected authProviderObservable: Observable<{ code?: string; description?: string }[]> = this.authProvidersControllerService.listAuthProviders().pipe(map((x: AuthProviderDto[]) => {

        return x?.map(y => {
            const data: { code?: string; description?: string } = {
                code: y.provider,
                description: y.description
            };
            return data;
        });

    }));

    /**
     * Component constructor
     * @param usersAdminControllerService Service for user administration operations
     * @param confirmService Service for displaying confirmation dialogs
     */
    constructor(private usersAdminControllerService: UsersAdminControllerService,
        private authProvidersControllerService: AuthProvidersControllerService,
        private confirmService: ConfirmationService) {

    }

    /**
     * Lifecycle hook that is called after component initialization
     * Sets up a subscription to role changes to ensure USER role is always present
     * when APPLICATION role is not selected
     */
    ngOnInit(): void {
        this.formGroup.controls["authProvider"].valueChanges.subscribe((x?: AuthProviderDto.ProviderEnum) => {
            this.actualAuthProvider = x;
        });
        this.formGroup.controls["roles"].valueChanges.subscribe(x => {
            const array: string[] = x;
            let addUserRole: boolean = false;
            if (array && array.length) {
                if (array.find(x => x === 'APPLICATION')) {
                    addUserRole = false;
                } else if (!array.find(x => x === 'USER')) {
                    addUserRole = true;
                }
            } else {
                addUserRole = true;
            }
            if (addUserRole === true) {
                if (!array) {
                    this.formGroup.controls["roles"].setValue(["USER"]);
                } else {
                    array.push("USER");
                    this.formGroup.controls["roles"].setValue(array);
                }
            }
        });
    }

    /**
     * Lifecycle hook that is called when input properties change
     * Loads user data when in EDIT mode and the user input changes
     * @param changes Object containing changes to input properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["user"] && this.user && this.mode == "EDIT" && this.user?.username) {
            this.loading = true;
            this.usersAdminControllerService.findUserByUsername(this.user.username).subscribe({
                next: (value) => {
                    if (!value.roles) {
                        value.roles = ["USER"];
                    }
                    this.formGroup.patchValue(value);

                },
                complete: () => {
                    this.loading = false;
                }
            });
        }
    }

    /**
     * Handles the insertion of a new user
     * Checks if username already exists before creating the user
     */
    doInsert(): void {
        const user: EditableUser = this.formGroup.value;
        const pwdFormValue = this.pwdFormGroup.value;
        this.loading = true;
        this.usersAdminControllerService.findUserByUsername(user.username).subscribe({
            next: (value) => {
                if (!value) {
                    this.usersAdminControllerService.insertUser({
                        user: user,
                        password: pwdFormValue.password
                    }).subscribe({
                        next: (returnedValue) => {
                            this.mode = "EDIT";
                            this.formGroup.patchValue(returnedValue);
                            this.changedData.emit(returnedValue);
                            this.userMessages = [{
                                summary: "User inserted successfully",
                                detail: "",
                                severity: "success"
                            }];
                        },
                        complete: () => {
                            this.loading = false;
                        }
                    });

                } else {
                    this.loading = false;
                    this.userMessages = [{
                        summary: "User already exists",
                        detail: "search the user with the users page",
                        severity: "error"
                    }];
                }
            }
            , error: (error) => {
                this.loading = false;
            }
            , complete: () => {

            }
        });


    }

    /**
     * Handles saving changes to an existing user
     * Updates the user data and notifies parent components
     */
    doSave(): void {
        const value: EditableUser = this.formGroup.value;

        this.loading = true;
        this.usersAdminControllerService.updateUser(value).subscribe({
            next: (value) => {
                this.mode = "EDIT";
                this.formGroup.patchValue(value);
                this.changedData.emit(value);
                this.userMessages = [{
                    summary: "User updated successfully",
                    detail: "",
                    severity: "success"
                }];
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Performs the actual user deletion after confirmation
     * Calls the API to delete the user and notifies parent components
     */
    private doPhisicalDelete() {
        this.loading = true;
        const value: EditableUser = this.formGroup.value;
        this.usersAdminControllerService.deleteUser(value).subscribe({
            next: (value) => {
                this.user = undefined;
                this.changedData.emit(undefined);
                this.userMessages = [{
                    summary: "User deleted successfully",
                    detail: "",
                    severity: "success"
                }];
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Initiates the user deletion process with confirmation
     * Displays a confirmation dialog before executing the delete operation
     */
    doDelete(): void {
        this.confirmService.confirm({
            header: "Delete operation",
            message: "Are you shure you want to delete this user?",
            accept: () => {
                this.doPhisicalDelete();
            }

        })

    }
}