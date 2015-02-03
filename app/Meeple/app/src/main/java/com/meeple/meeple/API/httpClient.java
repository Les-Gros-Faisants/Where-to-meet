package com.meeple.meeple.API;
import com.loopj.android.http.*;

/**
 * Created by arkeopix on 2/3/15.
 */
public class httpClient {
    private static final String BASE_URL = "http://arkeopix-rpi.noip.me:3000/api/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void del(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String url) {
        return BASE_URL + url;
    }
}
