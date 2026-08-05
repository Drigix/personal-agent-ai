import { ErrorResponseTypeEnum } from "../enums/error-response-type.enum";

export class ErrorResponseModel {
    constructor(
        public type: ErrorResponseTypeEnum,
        public status: number,
        public message: string,
        public timestamp: Date
    ) { }

    isErrorCorrect(): boolean {
        return this.status !== undefined && this.message !== undefined && this.timestamp !== undefined;
    }
}