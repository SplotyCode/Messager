package me.david.messageserver.webserver;

public enum  WebServerError {

    INVALID_REQUEST(0, "invalid request"),
    UNKNOWN_ACTION(1, "unknown action"),
    PAYLOAD_INCOMPLETE(2, "payload is not complete"),
    ID_NOT_FOUND(3, "id was not found");

    WebServerError(int errorId, String error) {
        this.errorId = errorId;
        this.error = error;
    }

    private final int errorId;
    private final String error;

    public int getErrorId() {
        return errorId;
    }

    public String formatError(){
        return String.format("[\"error\", { \"id\": %d, \"message\": \"%s\" }]", getErrorId(), getError());
    }

    public String getError() {
        return error;
    }

}
