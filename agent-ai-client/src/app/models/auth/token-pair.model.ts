export class TokenPairModel {
    constructor(
        public accessToken: string,
        public refreshToken: string
    ) {}
}