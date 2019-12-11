package ResponseTemplate;

public class Response {
    public String responseType;
    public String responseBody;

    public Response(){};

    public Response(String responseType, String responseBody) {
        this.responseType = responseType;
        this.responseBody = responseBody;
    }

    public Response setResponseType (String responseType) {
        this.responseType = responseType;
        return this;
    }

    public Response setResponseBody (String responseBody) {
        this.responseBody = responseBody;
        return this;
    }
}
