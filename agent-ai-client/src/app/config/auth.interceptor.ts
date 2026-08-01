import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { BehaviorSubject, catchError, filter, Observable, switchMap, take, throwError } from "rxjs";
import { SessionStorageService } from "../services/session-storage.service";
import { SessionStorageKeys } from "../models/constans/session-storage-keys.const";
import { ErrorResponseModel } from "../models/errors/error-response.model";
import { UserAuthService } from "../services/user-auth.service";
import { TokenPairModel } from "../models/auth/token-pair.model";
import { StorageModel } from "../models/storage.model";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    private sessionStorageService = inject(SessionStorageService);
    private userAuthService = inject(UserAuthService);
    private isRefreshing = false;
    private refreshTokenSubject = new BehaviorSubject<string | null>(null);

    intercept(req: HttpRequest<any>,
              next: HttpHandler): Observable<HttpEvent<any>> {
        if (req.url.includes('/userAuthService/refresh')) {
            return next.handle(req);
        }

        const token = this.sessionStorageService.load(SessionStorageKeys.AUTH_TOKEN)?.value;
        const authReq = token ? this.addToken(req, token) : req;

        return next.handle(authReq).pipe(
            catchError((error: ErrorResponseModel) => {
                if (error.status === 401) {
                return this.handle401(authReq, next);
                }
                return throwError(() => error);
            })
        );
    }

    private addToken(req: HttpRequest<any>, token: string): HttpRequest<any> {
        return req.clone({ headers: req.headers.set('Authorization', `Bearer ${token}`) });
    }

     private handle401(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      return this.userAuthService.refresh(this.sessionStorageService.load(SessionStorageKeys.REFRESH_TOKEN)?.value).pipe(
        switchMap((tokenPair: TokenPairModel) => {
          this.isRefreshing = false;
          this.sessionStorageService.save(new StorageModel(SessionStorageKeys.AUTH_TOKEN, tokenPair.accessToken));
          this.sessionStorageService.save(new StorageModel(SessionStorageKeys.REFRESH_TOKEN, tokenPair.refreshToken));
          this.refreshTokenSubject.next(tokenPair.accessToken);
          return next.handle(this.addToken(req, tokenPair.accessToken));
        }),
        catchError((err) => {
          this.isRefreshing = false;
          this.userAuthService.logout();
          return throwError(() => err);
        })
      );
    }

    return this.refreshTokenSubject.pipe(
      filter((token): token is string => token !== null),
      take(1),
      switchMap((newAccessToken) => next.handle(this.addToken(req, newAccessToken)))
    );
  }
}