import { inject, Injectable } from "@angular/core";
import { AGENT_USER_SERVICE_URL } from "../config/server-connection.const";
import { HttpClient } from "@angular/common/http";
import { UserDataModel } from "../models/user-data.model";
import { map, Observable } from "rxjs";
import { UserLoginModel } from "../models/user-login.model";

@Injectable()
export class UserAuthService {

  private RESOURCE_URL = AGENT_USER_SERVICE_URL + '/userAuthService/';
  private httpClient = inject(HttpClient);

  private _userData: UserDataModel | null = null;

  get userData(): UserDataModel | null {
    return this._userData;
  }

  set userData(value: UserDataModel | null) {
    this._userData = value;
  }

  register(userLogin: UserLoginModel): Observable<UserDataModel> {
    const url = this.RESOURCE_URL + "register";
    return this.httpClient.post<UserDataModel>(url, userLogin);
  }

  login(userLogin: UserLoginModel): Observable<string> {
    const url = this.RESOURCE_URL + "login";
    return this.httpClient.post<string>(url, userLogin, { responseType: 'text' as 'json' });
  }

  getUserData(username: string): Observable<UserDataModel> {
    const params = { username: username };
    const url = this.RESOURCE_URL + "getUserData";
    return this.httpClient.get<UserDataModel>(url, { params }).pipe(
      map(userData => {
        this._userData = userData;
        return userData;
      })
    );
  }
}