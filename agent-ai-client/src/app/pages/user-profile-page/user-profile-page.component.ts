import { Component, inject, OnInit, signal, WritableSignal } from "@angular/core";
import { COMMON_IMPORTS, FORMS_IMPORTS, PASSWORD_IMPORTS, PRIMENG_BUTTONS_COMPONENTS, PRIMENG_LABEL_COMPONENTS, PRIMENG_PANEL_COMPONENTS } from "../../shared/primeng-module-import";
import { UserDataModel } from "../../models/user-data.model";
import { UserAuthService } from "../../services/user-auth.service";
import { MessageService } from "primeng/api";
import { TranslateService } from "@ngx-translate/core";

@Component({
    selector: 'user-profile-page',
    templateUrl: 'user-profile-page.component.html',
    styleUrls: ['user-profile-page.component.scss'],
    standalone: true,
    imports: [...COMMON_IMPORTS, ...FORMS_IMPORTS, ...PRIMENG_PANEL_COMPONENTS, ...PRIMENG_LABEL_COMPONENTS, ...PRIMENG_BUTTONS_COMPONENTS]
})
export class UserProfilePageComponent implements OnInit {

    userData: WritableSignal<UserDataModel> = signal<UserDataModel>(new UserDataModel(0, '', '', false, new Set()));
  
    private userAuthService = inject(UserAuthService);
    private messageService = inject(MessageService);
    private translateService = inject(TranslateService);

    ngOnInit(): void {
        this.userData.set(this.userAuthService.userData!);
    }

    saveChanges(): void {
        this.userAuthService.updateUserData(this.userData()).subscribe({
            next: (updatedUserData) => {
                this.messageService.add({
                    severity: 'success',
                    summary: this.translateService.instant('success.defaultHeader'),
                    detail: this.translateService.instant('profile.userDataSaveChangesSuccess') }
                );
                this.userData.set(updatedUserData);
            }
        });
    }
}