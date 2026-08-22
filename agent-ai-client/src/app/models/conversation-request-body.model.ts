import { AuthenticatedDataRequestBody } from "./authenticated-data-request-body.model";

export class ConversationRequestBody extends AuthenticatedDataRequestBody {
    conversationId?: string;
    constructor(conversationId?: string, userDataId?: number) {
        super();
        this.conversationId = conversationId;
        this.userDataId = userDataId;
    }
}