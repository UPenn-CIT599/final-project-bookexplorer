import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class RequestMaker {
	
	/**
	 * Responsibility: makes http request to goodreads API and parses XML responses
	 */
	
	String authorAPIUrl;
	String developerKey;
	
	public RequestMaker() {
		this.authorAPIUrl = "https://www.goodreads.com/api/author_url/";
		//TODO update below to be the real developer key
		this.developerKey = "key";
	}
	
	// https://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests
	public String getAuthor(String authorName) throws MalformedURLException, IOException {
		String query = String.format("key=%s", this.developerKey);
		URLConnection connection = new URL(authorAPIUrl + authorName + "?" + query).openConnection();
		InputStream response = connection.getInputStream();
		Scanner respScanner = new Scanner(response);
		String respBody = respScanner.next();
		return respBody;
	}

	private boolean isAuthorFound(String respBody) {

	}
}
