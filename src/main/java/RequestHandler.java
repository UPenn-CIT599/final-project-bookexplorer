import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RequestHandler {

    /**
     * Responsibility: makes http request and parses XML responses
     */

    String authorSearchApi;
    String authorDetailApi;
    String developerKey;
    OkHttpClient client;

    public RequestHandler() {
        this.authorSearchApi = "https://www.goodreads.com/api/author_url/";
        this.authorDetailApi = "https://www.goodreads.com/author/show/";
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

    /**
     * calls on author search API and returns the API response
     * @param authorName, author name from user input
     * @return Document object of the API response
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public Document authorRespDoc(String authorName) throws IOException, SAXException, ParserConfigurationException {
        String keyParam = String.format("key=%s", this.developerKey);
        String authorSearchUrl = authorSearchApi + authorName + "?" + keyParam;
        String respBody = sendRequest(authorSearchUrl);
        Document doc = parseResponse(respBody);
        return doc;
    }

    /**
     *
     * @param authorDoc
     * @return true if author is found in GoodReads data
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public boolean isAuthorFound(Document authorDoc) throws IOException, SAXException, ParserConfigurationException {
        return authorDoc.getElementsByTagName("name").getLength() > 0;
    }

    /**
     * Associates info from GoodReads to author attributes
     * @param authorID
     * @return author object
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Author saveAuthorDetails(String authorID) throws IOException, ParserConfigurationException, SAXException {
        String keyParam = String.format("format=xml&key=%s", this.developerKey);
        String url = authorSearchApi + authorID + "?" + keyParam;
        String respBody = sendRequest(url);
        Document doc = parseResponse(respBody);
        Node author = doc.getElementsByTagName("author").item(0);
        String authorName = author.getChildNodes().item(1).getNodeValue();
        int worksCount = Integer.parseInt(doc.getElementsByTagName("works_count").item(0).getNodeValue());
        String description = doc.getElementsByTagName("about").item(0).getNodeValue();
        Author newAuthor = new Author(authorName);
        newAuthor.goodReadsID = authorID;
        newAuthor.worksCount = worksCount;
        newAuthor.description = description;
        return newAuthor;
    }

    /**
     * send request and receive xml response in string
     * @param url request url
     * @return string of xml response
     * @throws IOException
     */
    public String sendRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String respBody = response.body().string();
        return respBody;
    }

    /**
     * private method that parses XML response strings
     * @param respBody
     * @return document that can be further
     */
    public Document parseResponse(String respBody) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource source = new InputSource();
        source.setCharacterStream(new StringReader(respBody));
        Document doc = builder.parse(source);
        return doc;
    }
}
