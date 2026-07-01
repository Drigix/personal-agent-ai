export class ErrorResponseModel {
    constructor(
        public status: number,
        public message: string,
        public timestamp: Date
    ) { }
}