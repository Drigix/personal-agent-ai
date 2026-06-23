import { inject, Injectable } from "@angular/core";
import { AGENT_USER_SERVICE_URL } from "../config/server-connection.const";
import { HttpClient } from "@angular/common/http";
import { UserDataModel } from "../models/user-data.model";
import { Observable } from "rxjs";
import { UserLoginModel } from "../models/user-login.model";

@Injectable()
export class UserAuthorizationService {

  private RESOURCE_URL = AGENT_USER_SERVICE_URL + '/userAuthorizationService/';
  private httpClient = inject(HttpClient);

  register(userLogin: UserLoginModel): Observable<UserDataModel> {
    const url = this.RESOURCE_URL + "register";
    return this.httpClient.post<UserDataModel>(url, userLogin);
  }
}