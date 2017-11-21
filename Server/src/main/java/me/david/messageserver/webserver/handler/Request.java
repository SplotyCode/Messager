package me.david.messageserver.webserver.handler;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.cookie.Cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    private final HttpRequest request;
    private final String path;
    private final List<Cookie> cookies = new ArrayList<Cookie>();
    private final HashMap<String, List<String>> get = new HashMap<String, List<String>>();
    private final HashMap<String, Object> post = new HashMap<String, Object>();

    public Request(HttpRequest request, String path) {
        this.request = request;
        this.path = path.endsWith("/") ? path + "index.html" : path;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public String getPath() {
        return path;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public Map<String, List<String>> getGet() {
        return get;
    }

    public Map<String, Object> getPost() {
        return post;
    }
}
