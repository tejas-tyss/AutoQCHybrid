package moneyControl.SupportLibraries;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpHandler
    {
        public static HttpResponse post(HttpRequest httpRequest) {

        HttpResponse httpResponse = new HttpResponse();
        RequestSpecification request = RestAssured.given();
        request.headers(httpRequest.getHeaders());
        if (null != httpRequest.getParams() && httpRequest.getParams().size() > 0) {

            request.formParams(httpRequest.getParams());
        } else if (null != httpRequest.getStringBody() && !httpRequest.getStringBody().isEmpty()) {
            request.body(httpRequest.getStringBody());
        } else if (null != httpRequest.getServiceBody() && !httpRequest.getServiceBody().isEmpty()) {
            request.body(httpRequest.getServiceBody());
        }
        //request.log().all();
        Response response = request.post(httpRequest.getUrl());
        httpResponse.setStatusCode(response.getStatusCode());
        httpResponse.setContent(response.body().asString());
        //response.body().prettyPrint();
        return httpResponse;
    }

        public HttpResponse get(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        RequestSpecification request = RestAssured.given();
        //request.log().all();
        if (null != httpRequest.getParams() && httpRequest.getParams().size() > 0)
            request.formParams(httpRequest.getParams());
        request.headers(httpRequest.getHeaders());
        Response response = request.get(httpRequest.getUrl());
        //response.body().prettyPrint();
        httpResponse.setStatusCode(response.getStatusCode());
        httpResponse.setContent(response.body().asString());
        httpResponse.setBodyContentsBytes(response.getBody().asByteArray());
        return httpResponse;
    }

        public static HttpResponse patch(HttpRequest httpRequest) {

        HttpResponse httpResponse = new HttpResponse();

        RequestSpecification request = RestAssured.given();

        request.headers(httpRequest.getHeaders());

        Response response = request.patch(httpRequest.getUrl());

        httpResponse.setStatusCode(response.getStatusCode());

        httpResponse.setContent(response.body().asString());

        return httpResponse;
    }

        public static byte[] getDownload(HttpRequest httpRequest) {

        RequestSpecification request = RestAssured.given();

        request.headers(httpRequest.getHeaders());

        Response response = request.get(httpRequest.getUrl());

        byte[] fileBytes = response.getBody().asByteArray();
        return fileBytes;
    }

        public static HttpResponse delete(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        RequestSpecification request = RestAssured.given();
        request.headers(httpRequest.getHeaders());
        Response response = request.delete(httpRequest.getUrl());
        httpResponse.setStatusCode(response.getStatusCode());
        httpResponse.setContent(response.body().asString());
        return httpResponse;
    }

        public static HttpResponse deleteWithPayload(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        RequestSpecification request = RestAssured.given();
        request.headers(httpRequest.getHeaders());
        request.body(httpRequest.getServiceBody());
        //request.log().all();
        Response response = request.delete(httpRequest.getUrl());
        httpResponse.setStatusCode(response.getStatusCode());
        httpResponse.setContent(response.body().asString());
        return httpResponse;
    }


    }

class HttpResponse{

    private int statusCode;
    private String content;

    @Getter
    @Setter
    private byte[] bodyContentsBytes;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JSONObject getJsonResponse() {
        return new JSONObject(this.content);
    }

    public JSONArray getJsonArrayResponse() {

        return new JSONArray(this.content);
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusCode=" + statusCode +
                ", content='" + content + '\'' +
                '}';
    }


}
