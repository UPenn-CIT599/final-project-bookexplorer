import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
     * @param authorSearchDoc Document object for of author search response
     * @return string of response from goodreads API
     * @throws MalformedURLException
     * @throws IOException
     */
    public String getAuthorID(Document authorSearchDoc) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        String authorID = authorSearchDoc.getElementsByTagName("author").item(0).getAttributes().item(0).getTextContent();
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
    public Document authorSearchDoc(String authorName) throws IOException, SAXException, ParserConfigurationException {
        String keyParam = String.format("key=%s", this.developerKey);
        String authorSearchUrl = authorSearchApi + authorName + "?" + keyParam;
        String respBody = sendRequest(authorSearchUrl);
        Document doc = parseResponse(respBody);
        return doc;
    }

    /**
     *
     * @param authorSearchDoc
     * @return true if author is found in GoodReads data
     */
    public boolean isAuthorFound(Document authorSearchDoc) {
        return authorSearchDoc.getElementsByTagName("name").getLength() > 0;
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
        Document doc = getAuthorDetail(authorID);
        // parse details of the author from the xml document
        Node author = doc.getElementsByTagName("author").item(0);
        String authorName = author.getChildNodes().item(1).getNodeValue();
        int worksCount = Integer.parseInt(doc.getElementsByTagName("works_count").item(0).getNodeValue());
        String description = doc.getElementsByTagName("about").item(0).getNodeValue();
        Author newAuthor = new Author(authorName);
        // associate the attributes with the new author object
        newAuthor.goodReadsID = authorID;
        newAuthor.worksCount = worksCount;
        newAuthor.description = description;
        return newAuthor;
    }

    /**
     * Make http request to retrieve author details from GoodReads API
     * @param authorID
     * @return Document object of response from GoodReads API
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public Document getAuthorDetail(String authorID) throws IOException, SAXException, ParserConfigurationException {
        String keyParam = String.format("format=xml&key=%s", this.developerKey);
        String url = authorDetailApi + authorID + "?" + keyParam;
        String respBody = sendRequest(url);
        Document doc = parseResponse(respBody);
        return doc;
    }

    /**
     * Creates book objects and saves books to the author
     * @param authorDetailDoc author details response obtained from GoodReads API
     * @param author author object for which the books will be associated with
     */
    public ArrayList<HashMap<String, String>> getAuthorBooks(Document authorDetailDoc, Author author) throws ParserConfigurationException {
        NodeList books = authorDetailDoc.getElementsByTagName("book");
        ArrayList booksWithAttributes = new ArrayList<HashMap<String, String>>();
        for (Node bookNode : iterable(books)) {
            Document bookDoc = nodeToDoc(bookNode);
            String title = bookDoc.getElementsByTagName("title").item(0).getNodeValue();
            String description = bookDoc.getElementsByTagName("description").item(0).getNodeValue();
            String imageUrl = bookDoc.getElementsByTagName("imageUrl").item(0).getNodeValue();
            String goodReadsID = bookDoc.getElementsByTagName("id").item(0).getAttributes().item(0).getTextContent();
            double rating = Double.parseDouble(bookDoc.getElementsByTagName("averageRating").item(0).getNodeValue());
            booksWithAttributes.add(new HashMap<String, String>() {{
                put("title", title);
                put("description", description);
                put("imageURL", imageUrl);
                put("rating", Double.toString(rating));
                put("goodReadsID", goodReadsID);
            }});
        }
        return booksWithAttributes;
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
        InputSource source = new InputSource(new StringReader(respBody));
        Document doc = builder.parse(source);
        return doc;
    }

    /**
     * @param nodeList
     * @return an iterable instance of nodeList, so forEach can be called on the nodeList
     */
    public static Iterable<Node> iterable(final NodeList nodeList) {
        return () -> new Iterator<Node>() {

            private int index = 0;

            public boolean hasNext() {
                return index < nodeList.getLength();
            }

            public Node next() {
                if (!hasNext()) {
                    throw  new NoSuchElementException();
                } else {
                    return nodeList.item(index++);
                }
            }
        };
    }

    private static Document nodeToDoc(Node node) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document newDoc = builder.newDocument();
        Node importedNode = newDoc.importNode(node, true);
        newDoc.appendChild(importedNode);
        return newDoc;
    }
}
