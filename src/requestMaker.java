import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class requestMaker {
	
	/**
	 * Responsibility: makes http request to goodreads API and interprets responses
	 */
	
	String authorAPIUrl;
	String developerKey;
	
	public requestMaker() {
		this.authorAPIUrl = "https://www.goodreads.com/api/author_url";
		//TODO update below to be the real developer key
		this.developerKey = "key";
	}
	
	public void getAuthor(String authorName) throws MalformedURLException, IOException {
		String query = String.format("key=%s", this.developerKey);
		// https://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests
		URLConnection connection = new URL(authorAPIUrl + "?" + query).openConnection();
		InputStream response = connection.getInputStream();
	}
}
