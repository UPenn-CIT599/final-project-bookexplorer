import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class RequestHandler {

    /**
     * Responsibility: makes http request to goodreads API and parses XML responses
     * dependencies: okhttp (depends on kotlin-stdlib and okio)
     */

    String authorAPIUrl;
    String developerKey;
    OkHttpClient client;

    public RequestHandler() {
        this.authorAPIUrl = "https://www.goodreads.com/api/author_url/";
        //TODO update below to be the real developer key
        this.developerKey = "GFGG3YidZvZxbGosF8DWA";
        this.client = new OkHttpClient();
    }

    // https://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests
    public String getAuthor(String authorName) throws MalformedURLException, IOException {
        String keyParam = String.format("key=%s", this.developerKey);
        String authorReqUrl = authorAPIUrl + authorName + "?" + keyParam;
        Request request = new Request.Builder().url(authorReqUrl).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
//            <?xml version="1.0" encoding="UTF-8"?>
//              <GoodreadsResponse>
//                  <Request>
//                      <authentication>true</authentication>
//                      <key><![CDATA[GFGG3YidZvZxbGosF8DWA]]></key>
//                      <method><![CDATA[api_author_link]]></method>
//                  </Request>
//
//
//		            <author id="1077326">
//			            <name><![CDATA[J.K. Rowling]]></name>
//			            <link>https://www.goodreads.com/author/show/1077326.J_K_Rowling?utm_medium=api&amp;utm_source=author_link</link>
//		            </author>
//
//
//
//          </GoodreadsResponse>
        }
    }

    private boolean isAuthorFound(String respBody) {
        return true;
    }
}
