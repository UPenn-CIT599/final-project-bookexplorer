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
     * calls on goodreads API and get author ID and name
     * @param authorRespDoc Document object for response received from API call
     * @return string of response from goodreads API
     * @throws MalformedURLException
     * @throws IOException
     */
    public String getAuthorID(Document authorRespDoc) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        String authorID = authorRespDoc.getElementsByTagName("author").item(0).getAttributes().item(0).getTextContent();
        return authorID;
    }

    public Document authorRespDoc(String authorName) throws IOException, SAXException, ParserConfigurationException {
        String keyParam = String.format("key=%s", this.developerKey);
        String authorReqUrl = authorAPIUrl + authorName + "?" + keyParam;
        Request request = new Request.Builder().url(authorReqUrl).build();
        try (Response response = client.newCall(request).execute()) {
            String respBody = response.body().string();
            Document doc = parseResponse(respBody);
            return doc;
        }
    }

    public boolean isAuthorFound(Document authorDoc) throws IOException, SAXException, ParserConfigurationException {
        return authorDoc.getElementsByTagName("name").getLength() > 0;
    }

    public Author saveAuthorDetails(String authorID) {
        String keyParam = String.format("key=%s", this.developerKey);

    }

    /**
     * private method that parses XML response strings
     * @param respBody
     * @return document that can be further
     */
    private Document parseResponse(String respBody) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource source = new InputSource();
        source.setCharacterStream(new StringReader(respBody));
        Document doc = builder.parse(source);
        return doc;
    }
}
