/**
* Created by Jérôme on 12/16/2014.
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class http_wtm {
  private final String USER_AGENT = "Mozilla/5.0";

  private void get_users() throws Exception {
    String url = "http://www.jerome-rpi.noip.me/api/users";

    HttpClient client = new DefaultHttpClient();
    HttpGet request = new HttpGet(url);

    request.addHeader("User-Agent", USER_AGENT);
    HttpResponse response = client.execute(request);
    System.out.println("sending GET request to " + url);
    System.out.println("response = " + response.getStatusLine().getStatusCode());
    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    StringBuffer result = new StringBuffer();
    String line = "";
    while ((line = rd.readLine()) != null) {
      result.append(line);
    }
    System.out.println(result.toString());
  }

  public static void main(String[] args) {
    http_wtm http = new http_wtm();

    System.out.println("testing 1 - send get request");
    try {
      http.get_users();
    } catch (Exception e) {
      System.out.println("error lol");
    }
    System.out.println("testing 1 - send put request");
    System.out.println("testing 1 - send delete request");
  }
}
