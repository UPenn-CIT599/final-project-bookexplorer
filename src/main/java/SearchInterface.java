import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * SearchInterface Class
 * 
 * MCIT591 - Final Project
 * 
 * The following class designs the search interface the user will use to
 * search for new books. The user will enter a book that they enjoy reading,
 * an author they like, and a genre they would like to explore. The interface
 * will then return a list of books and authors that the user may like.
 * 
 * @author Thomas Flannery
 *
 */

public class SearchInterface implements Runnable {
	
	// Initialize search results for book title, author, genre, book url, and image
	Book recBook;
	Author recAuthor;
	String bookTitleInput = "";
	String authorInput = "";
	String genreInput = "";
	String urlResult = "";
	String imageResult = "";

	// Allow image to be displayed on JPanel
	ImageIcon myImageIcon = new ImageIcon(imageResult);
	
	@Override
	public void run() {
		// Create components
		JFrame frame = new JFrame("MCIT591 - Final Project: Book Explorer");
		
		// Create Panels for interface
		JPanel outerMostPanel = new JPanel(new GridLayout(1, 2));
		JPanel leftPanelSearchEngine = new JPanel(new FlowLayout());
		JPanel rightPanelSearchResults = new JPanel(new FlowLayout());
		
		// Create labels for welcoming user and instructions
		JLabel welcomeLabel = new JLabel("Welcome to our book search engine!");
		welcomeLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		JLabel instructionsLabel1 = new JLabel("Please fill out the three fields below.");
		JLabel instructionsLabel2 = new JLabel("Enter the title of a book you enjoyed, an author you enjoy reading,");
		JLabel instructionsLabel3 = new JLabel("and a genre you would like to explore.");
		
		// Create labels and panels for displaying search results and images
		JLabel searchResultsLabel = new JLabel("Here is your book recommendation!");
		JPanel searchImageResult = new JPanel();
		JLabel imageResult = new JLabel();
		imageResult.setIcon(myImageIcon);
		
		// Create and designate sizes of text fields for book search results

		JTextField authorResultField = new JTextField();
		authorResultField.setPreferredSize(new Dimension(220, 24));
		authorResultField.setEditable(false);
		JTextField urlResultField = new JTextField();
		urlResultField.setPreferredSize(new Dimension(220, 24));
		urlResultField.setEditable(false);
		
		// Creates search options (book title)
		JLabel bookTitleLabel = new JLabel("Book Title");
		JTextField bookTitle = new JTextField("Enter a book title that you like");
		bookTitle.setPreferredSize(new Dimension(220, 24));
		
		// Creates search options (author name)
		JLabel authorLabel = new JLabel("Author name");
		JTextField author = new JTextField("Enter an author that you like");
		author.setPreferredSize(new Dimension(220, 24));
		
		// Create search options (genre)
		String[] genreOptions = {"Art", "Biography", "Business", "Children",
				"Cooking", "Crime", "Fantasy", "Fiction", "Historical fiction", "History",
				"Horror", "Humor", "Music", "Mystery", "Nonfiction", "Philosophy", "Poetry",
				"Religion", "Science", "Science fiction", "Suspense", "Spirituality",
				"Sports", "Travel", "Young adult"};
		
		JLabel genreLabel = new JLabel("Genre");
		JComboBox genre = new JComboBox(genreOptions);
		genre.setPreferredSize(new Dimension(200, 24));
		
		// Saving genre result to String
		genre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent g) {
				genreInput = (String) genre.getSelectedItem();
			}
		});
		
		// Create search button to initiate code
		JButton search = new JButton("Search");
		search.setPreferredSize(new Dimension(200, 150));
		
		// ActionListener: activates when the user presses the search button
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Code to run after search button is pressed
				if (e.getSource() == search) {
					bookTitleInput = bookTitle.getText();
					authorInput = author.getText();

					HashMap<String, Object> recResults = (new UserInteraction()).recommendAuthorAndBook(authorInput, genreInput, bookTitleInput);
					Book recommendedBook = (Book) recResults.get("Book");
					Author recommendedAuthor = (Author) recResults.get("Author");


					JTextField bookTitleResultField = new JTextField(recommendedBook.title);
					bookTitleResultField.setPreferredSize(new Dimension(220, 24));
					bookTitleResultField.setEditable(false);
					rightPanelSearchResults.add(bookTitleResultField);
					
//					 If algorithms can't find a similar enough book
//					if (!bookIsFound) {
//						JOptionPane.showMessageDialog(rightPanelSearchResults, "Sorry, the book, author, and genre you searched for netted no results."
//								+ " Please try again.");
//					}
				}
			}
			
		});
		
		// Add components to the search window
		// Add frames
		frame.add(outerMostPanel);
		outerMostPanel.add(leftPanelSearchEngine);
		outerMostPanel.add(rightPanelSearchResults);
		
		// Add welcome and instructions
		leftPanelSearchEngine.add(welcomeLabel);
		leftPanelSearchEngine.add(instructionsLabel1);
		leftPanelSearchEngine.add(instructionsLabel2);
		leftPanelSearchEngine.add(instructionsLabel3);
		
		// Add search option for book title
		leftPanelSearchEngine.add(bookTitleLabel);
		leftPanelSearchEngine.add(bookTitle);
		
		// Add search option for author
		leftPanelSearchEngine.add(authorLabel);
		leftPanelSearchEngine.add(author);
		
		// Add drop down menu for genre
		leftPanelSearchEngine.add(genreLabel);
		leftPanelSearchEngine.add(genre);
		
		// Add search button to initiate search
		leftPanelSearchEngine.add(search, BorderLayout.SOUTH);
		
		// Add search result labels and images
		rightPanelSearchResults.add(searchResultsLabel, BorderLayout.NORTH);
		rightPanelSearchResults.add(searchImageResult);
		searchImageResult.add(imageResult);
		
		// Add search result text fields
//		rightPanelSearchResults.add(bookTitleResultField);
		rightPanelSearchResults.add(authorResultField);
		rightPanelSearchResults.add(urlResultField);
		
		// Required components
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new SearchInterface());
	}

}
