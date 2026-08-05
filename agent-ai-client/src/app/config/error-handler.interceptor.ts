import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { catchError, throwError, Observable } from "rxjs";
import { ErrorResponseModel } from "../models/errors/error-response.model";
import { MessageService } from "primeng/api";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {

    private messageService = inject(MessageService);
    private translateService = inject(TranslateService);

    private urlsNotToCatch = [
        '/assets/i18n/',
        '/userAuthService/refresh'
    ];

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.urlsNotToCatch.some(url => req.url.includes(url))) {
            return next.handle(req);
        }
        return next.handle(req).pipe(
            catchError((error: HttpErrorResponse) => {
                const backendError = this.extractBackendError(error);
                this.messageService.add({
                    severity: 'error',
                    summary: this.translateService.instant('error.defaultHeader'),
                    detail: backendError?.message ?? this.translateService.instant('error.unknown')
                });

                return throwError(() => backendError ?? error);
            })
        );
    }

    private extractBackendError(error: HttpErrorResponse): ErrorResponseModel | null {
        const errorResponse = new ErrorResponseModel(error.error?.type, error.error?.status, error.error?.message, error.error?.timestamp);
        if (errorResponse instanceof ErrorResponseModel && errorResponse.isErrorCorrect()) {
            return error.error;
        }
        if (error.error && typeof error.error === 'string') {
            return JSON.parse(error.error) as ErrorResponseModel;
        }
        return null;
    }
}