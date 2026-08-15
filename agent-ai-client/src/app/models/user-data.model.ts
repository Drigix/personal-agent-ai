import { RoleModel } from "./role.model";

export class UserDataModel {
    constructor(
        public userDataId: number,
        public username: string,
        public enabled: boolean,
        public roles: Set<RoleModel>
    ) {}
}