package model;

import java.util.ArrayList;

public class Request {

    private Integer token;
    private String name;
    private ArrayList<String> parameters;
    private String body;
    private boolean result;


    public Request(Integer token, String name, ArrayList<String> parameters, String body) {
        this.token = token;
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    public Request(){

    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
