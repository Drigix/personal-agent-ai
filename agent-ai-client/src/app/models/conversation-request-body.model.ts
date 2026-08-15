import { AuthenticatedDataRequestBody } from "./authenticated-data-request-body.model";

export class ConversationRequestBody extends AuthenticatedDataRequestBody {
    conversationId?: string;
}