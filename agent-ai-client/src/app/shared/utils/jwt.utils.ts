import { jwtDecode } from "jwt-decode";

export class JwtUtils {
    public static decodeToken(token: string): any {
        return jwtDecode(token);
    }
}