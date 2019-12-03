import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

public class RequestHandler {

    /**
     * Responsibility: makes http request to goodreads API and parses XML responses
     */

    String authorAPIUrl;
    String developerKey;
    OkHttpClient client;

    public RequestHandler() {
        this.authorAPIUrl = "https://www.goodreads.com/api/author_url/";
        this.developerKey = "GFGG3YidZvZxbGosF8DWA";
        this.client = new OkHttpClient();
    }

    /**
     *
     * @param authorName
     * @return string of response from goodreads API
     * @throws MalformedURLException
     * @throws IOException
     */
    public String getAuthor(String authorName) throws MalformedURLException, IOException, ParserConfigurationException {
        String keyParam = String.format("key=%s", this.developerKey);
        String authorReqUrl = authorAPIUrl + authorName + "?" + keyParam;
        Request request = new Request.Builder().url(authorReqUrl).build();
        try (Response response = client.newCall(request).execute()) {
            String respBody = response.body().string();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource source = new InputSource();
            source.setCharacterStream(new StringReader(respBody));
            Document doc = builder.parse(source);
            String name = doc.getElementsByTagName("name").item(0).getTextContent();
            String authorID = doc.getElementsByTagName("author").item(0).getAttributes().item(0).getTextContent();
            return authorID + " " + name;
            // expected response:
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
//          </GoodreadsResponse>
        } catch (SAXException e) {
            //TODO add exception handling
            return e.getLocalizedMessage();
        }
    }

    public boolean isAuthorFound(String respBody) {
        return true;
    }
}
