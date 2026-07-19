import { jwtDecode } from "jwt-decode";

export class JwtUtils {
    public static decodeToken(token: string): any {
        return jwtDecode(token);
    }

    public static isTokenExpired(token: string): boolean {
        const decodedToken = this.decodeToken(token);
        const currentTime = Math.floor(Date.now() / 1000);
        return decodedToken.exp < currentTime;
    }
}