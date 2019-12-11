package Response;

public class ResponseTemplate {
    public String responseType;
    public String responseBody;

    public ResponseTemplate(){};

    public ResponseTemplate(String responseType, String responseBody) {
        this.responseType = responseType;
        this.responseBody = responseBody;
    }

    public ResponseTemplate setResponseType (String responseType) {
        this.responseType = responseType;
        return this;
    }

    public ResponseTemplate setResponseBody (String responseBody) {
        this.responseBody = responseBody;
        return this;
    }
}
