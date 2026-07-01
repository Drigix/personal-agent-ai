import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { SessionStorageService } from "../services/session-storage.service";
import { SessionStorageKeys } from "../models/constans/session-storage-keys.const";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    private sessionStorageService = inject(SessionStorageService);

    intercept(req: HttpRequest<any>,
              next: HttpHandler): Observable<HttpEvent<any>> {

        const token = this.sessionStorageService.load(SessionStorageKeys.AUTH_TOKEN)?.value;

        if (token) {
            const cloned = req.clone({
                headers: req.headers.set("Authorization",
                    "Bearer " + token)
            });

            return next.handle(cloned);
        }
        else {
            return next.handle(req);
        }
    }
}