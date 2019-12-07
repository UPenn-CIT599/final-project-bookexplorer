import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RequestHandler {

    /**
     * Responsibility: makes http request and parses XML responses
     */

    private String authorSearchApi;
    private String authorDetailApi;
    private String goodReadsDevKey;
    private String mwDevKey;
    private OkHttpClient client;

    public RequestHandler() {
        this.authorSearchApi = "https://www.goodreads.com/api/author_url/";
        this.authorDetailApi = "https://www.goodreads.com/author/show/";
        this.goodReadsDevKey = "GFGG3YidZvZxbGosF8DWA";
        this.mwDevKey = "e274dbeb-31b8-43be-9ccd-ea7b4a0f6dd2";
        this.client = new OkHttpClient();
    }

    /**
     * calls on goodreads API and get author ID and name
     * @param authorSearchDoc Document object for of author search response
     * @return string of response from goodreads API
     */
    public String getAuthorID(Document authorSearchDoc) {
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
        String keyParam = String.format("key=%s", this.goodReadsDevKey);
        String authorSearchUrl = authorSearchApi + authorName + "?" + keyParam;
        String respBody = sendRequest(authorSearchUrl);
        Document doc = parseResponse(respBody);
        return doc;
    }

    /**
     * @param authorSearchDoc - search response from goodReads API
     * @return true if author is found in GoodReads data
     */
    public boolean isAuthorFound(Document authorSearchDoc) {
        return authorSearchDoc.getElementsByTagName("name").getLength() > 0;
    }

    public boolean isBookFound(Document bookSearchDoc) {
        int resultCount = ((Element) bookSearchDoc.getElementsByTagName("search").item(0)).getElementsByTagName("results").getLength();
        return resultCount > 0;
    }

    /**
     * Associates info from GoodReads to author attributes
     * @param authorID - goodReads author ID
     * @return author object
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Author saveAuthorDetails(String authorID) throws IOException, ParserConfigurationException, SAXException {
        Document doc = getAuthorDetail(authorID);
        // parse details of the author from the xml document
        Element author = (Element) doc.getElementsByTagName("author").item(0);
        String authorName = author.getElementsByTagName("name").item(0).getTextContent();
        int worksCount = Integer.parseInt(author.getElementsByTagName("works_count").item(0).getTextContent());
        String description = author.getElementsByTagName("about").item(0).getTextContent();
        int followersCount = Integer.parseInt(author.getElementsByTagName("author_followers_count").item(0).getTextContent());
        Author newAuthor = new Author(authorName);
        // associate the attributes with the new author object
        newAuthor.goodReadsID = authorID;
        newAuthor.worksCount = worksCount;
        newAuthor.followersCount = followersCount;
        newAuthor.description = description;
        return newAuthor;
    }

    /**
     * makes synonyms API request and parses response
     * @param word - word whose synonyms we want to know
     * @return - an array list of synonym words as strings
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public ArrayList<String> getSynonyms(String word) {
        String requestUrl = String.format("https://www.dictionaryapi.com/api/v1/references/thesaurus/xml/%s?key=%s", word, mwDevKey);
        try {
            String resp = sendRequest(requestUrl);
            String trimmedResp = resp.trim().replaceFirst("^([\\W]+)<", "<");
            if (trimmedResp.contains("Results not found")) {
                return new ArrayList<String>();
            } else {
                Document respDoc = parseResponse(trimmedResp);
                if (respDoc.getElementsByTagName("sens").getLength() > 0) {
                    NodeList synonymsNode = ((Element) respDoc.getElementsByTagName("sens").item(0)).getElementsByTagName("syn");
                    if (synonymsNode.getLength() > 0) {
                        String synonyms = synonymsNode.item(0).getTextContent();
                        return new ArrayList<>(Arrays.asList(synonyms.split(", ")));
                    } else {
                        return new ArrayList<>();
                    }
                } else {
                    return new ArrayList<>();
                }
            }
        }
         catch (IOException e) {
             return new ArrayList<>();
        } catch (ParserConfigurationException e) {
            return new ArrayList<>();
        } catch (SAXException e) {
            return new ArrayList<>();
        }
    }

    /**
     * sends request, parses, and adds book description as an attribute to the book object
     * @param book - book object
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void saveBookDescriptions(Book book) throws IOException, ParserConfigurationException, SAXException {
        String bookDetailUrl = String.format("https://www.goodreads.com/book/show/%s.xml?key=%s", book.goodReadsID, goodReadsDevKey);
        String respBody = sendRequest(bookDetailUrl);
        Document respDoc = parseResponse(respBody);
        Element bookElement = (Element) respDoc.getElementsByTagName("book").item(0);
        String description = bookElement.getElementsByTagName("description").item(0).getTextContent();
        book.description = description.replaceAll("<.{1,6}>", " ");
    }

    /**
     * parses book search result XML from the goodreads book search API
     * @param bookSearchDoc - book search API response, Document object
     * @return book object that's created from searched book title, with book attributes
     */
    public Book getBookFromBookSearch(Document bookSearchDoc) {
        Element bookResults = (Element) bookSearchDoc.getElementsByTagName("results").item(0);
        Element work = (Element) bookResults.getElementsByTagName("work").item(0);
        Element workElement = (Element) work;
        Element book = (Element) workElement.getElementsByTagName("best_book").item(0);
        String bookTitle = book.getElementsByTagName("title").item(0).getTextContent();
        String bookId = book.getElementsByTagName("id").item(0).getTextContent();
        String ratingsCount = workElement.getElementsByTagName("ratings_count").item(0).getTextContent();
        String ratings = workElement.getElementsByTagName("average_rating").item(0).getTextContent();
        String publicationYear = workElement.getElementsByTagName("original_publication_year").item(0).getTextContent();
        String imageUrl = book.getElementsByTagName("small_image_url").item(0).getTextContent();
        Book newBook = new Book(bookTitle);
        newBook.ratingsCount = Integer.parseInt(ratingsCount);
        newBook.goodReadsID = bookId;
        newBook.averageRating = Double.parseDouble(ratings);
        newBook.publicationYear = Integer.parseInt(publicationYear);
        newBook.imageUrl = imageUrl;
        return newBook;
    }

    /**
     * Forms request url, makes book search request and returns api response
     * @param title - book title string from user input
     * @return response Document object
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public Document getBookSearchResp(String title) throws IOException, SAXException, ParserConfigurationException {
        String formattedTitle = title.replaceAll(" ", "+").replaceAll("\'", "%27");
        String requestUrl = String.format("https://www.goodreads.com/search/index.xml?key=%s&q=%s", this.goodReadsDevKey, formattedTitle);
        String respBody = sendRequest(requestUrl);
        Document respDoc = parseResponse(respBody);
        return respDoc;
    }

    /**
     * Forms request url, makes http request and returns api response
     * @param authorID
     * @return Document object of response from GoodReads API
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public Document getAuthorDetail(String authorID) throws IOException, SAXException, ParserConfigurationException {
        String keyParam = String.format("format=xml&key=%s", this.goodReadsDevKey);
        String url = authorDetailApi + authorID + "?" + keyParam;
        String respBody = sendRequest(url);
        Document doc = parseResponse(respBody);
        return doc;
    }

    /**
     * Creates book objects and saves books to the author
     * @param authorDetailDoc author details response obtained from GoodReads API
     */
    public ArrayList<HashMap<String, String>> getAuthorBooks(Document authorDetailDoc) throws ParserConfigurationException {
        NodeList books = authorDetailDoc.getElementsByTagName("book");
        ArrayList booksWithAttributes = new ArrayList<HashMap<String, String>>();
        for (Node bookNode : iterable(books)) {
            Document bookDoc = nodeToDoc(bookNode);
            String title = bookDoc.getElementsByTagName("title").item(0).getTextContent();
            String description = bookDoc.getElementsByTagName("description").item(0).getTextContent();
            String imageUrl = bookDoc.getElementsByTagName("image_url").item(0).getTextContent();
            String goodReadsID = bookDoc.getElementsByTagName("id").item(0).getAttributes().item(0).getTextContent();
            double rating = Double.parseDouble(bookDoc.getElementsByTagName("average_rating").item(0).getTextContent());
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
    private String sendRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String respBody = response.body().string();
        return respBody;
    }

    /**
     * parses XML response strings
     * @param respBody string of xml response
     * @return document that can be further parsed by tag name
     */
    private Document parseResponse(String respBody) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource source = new InputSource();
        source.setCharacterStream(new StringReader(respBody));
        Document doc = builder.parse(source);
        doc.getDocumentElement().normalize();
        return doc;
    }

    /**
     * @param nodeList
     * @return an iterable instance of nodeList, so forEach can be called on the nodeList
     */
    private static Iterable<Node> iterable(final NodeList nodeList) {
        return () -> new Iterator<Node>() {

            private int index = 0;

            public boolean hasNext() {
                return index < nodeList.getLength();
            }

            public Node next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
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
