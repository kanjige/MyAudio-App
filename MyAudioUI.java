//Name: Eric Wong
//Student ID: 501174088

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content




			//A2 Modified Download Action
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int fromIndex = 0;
				int toIndex = 0;
				
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt())
				{
					fromIndex = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt())
				{
					toIndex = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}


					for (int index = fromIndex; index < toIndex + 1; index++){
						AudioContent content = store.getContent(index);
					try{		//Tries to download content, prints message 
						mylibrary.download(content);
						if (content.getType() == "SONG"){
							System.out.println("Song " +  content.getTitle() + " Added to Library");
						}
						if (content.getType() == "AUDIOBOOK"){
							System.out.println("Audiobook " +  content.getTitle() + " Added to Library");
							}
						}
					
					catch (ContentAlreadyDownloaded e){		//Catches exception if content has already been downloaded, prints message
						if (content.getType() == "SONG"){
							System.out.println("Song " +  content.getTitle() + " already downloaded");
						}
						if (content.getType() == "AUDIOBOOK"){
							System.out.println("Audiobook " +  content.getTitle() + " already downloaded");
							}
					}
					catch (NullPointerException e){
						System.out.println("Content Not Found in Store");
					}
								
				}
			}				
			



			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{	
				int index = 0;

				System.out.print("Song Number: ");		//Scans next input
				if (scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}

				try{		//Tries to play song
					mylibrary.playSong(index);
				}
				catch (ContentNotFound e){		//Catches exception if content doesn't exist, prints message
					System.out.println("Song Not Found");
				}
			}

			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				int index = 0;
				System.out.print("Audio Book Number: ");		//Scans next input
				if (scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}
			// Print error message if the book doesn't exist in the library
				try{		//Tries to print table of contents
					mylibrary.printAudioBookTOC(index);
				}
				catch (ContentNotFound e){		//Catches exception if content doesn't exist, prints message
					System.out.println("Audiobook Not Found");
				}
			}


			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int index = 0;
				int chapter = 0;

				System.out.print("Audio Book Number: ");	//Scans next input
				if (scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}
				System.out.print("Chapter Number: ");		//Scans next input
				if (scanner.hasNextInt()){
					chapter = scanner.nextInt();
					scanner.nextLine();
				}

				try{		//Tries to play audiobook
					mylibrary.playAudioBook(index, chapter);
				}
				catch (ContentNotFound e){		//Catches exception if content doesn't exist, prints message
					System.out.println("Audiobook not found");
				} 
				catch (ChapterNotFound e){		//Catches exception if chapter doesn't exist, prints message
					System.out.println("Chapter not found");
				} 
				

			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				
			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				
			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				String title = "";

				System.out.print("Playlist Title: ");		//Scans next input
				if (scanner.hasNext()){
					title = scanner.next();
					scanner.nextLine();
				}


				try{		//Tries to play whole playlist
					mylibrary.playPlaylist(title);
				}	
				catch (PlaylistNotFound e){		//Catches exception if playlist doesn't exist, prints message
					System.out.println("Playlist" + title + "Not Found");
				}
			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				String title = "";
				int index = 0;

				System.out.print("Playlist Title: ");		//Scans next input
				if (scanner.hasNext()){
					title = scanner.next();
					scanner.nextLine();
				}

				System.out.print("Content Number: ");		//Scans next input
				if (scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}

				try{		//Tries to play single content in playlist
					mylibrary.playPlaylist(title, index);
				}
				catch (PlaylistNotFound e){		//Catches exception if playlist doesn't exist, prints message
					System.out.println("Playlist " + title + " Not Found");
				}
				catch (ContentNotFound e){		//Catches exception if content doesn't exist, prints message
					System.out.println("Title Not Found");
				}
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				int index = 0;

				System.out.print("Library Song #: ");		//Scans next input
				if (scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}

				try{		//Tries to delete song
					mylibrary.deleteSong(index);
				}
				catch (ContentNotFound e){		//Catches exception if content doesn't exist, prints message
					System.out.println ("Song Not Found");
				}
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				String title = "";

				System.out.print("Playlist Title: ");		//Scans next input
				if (scanner.hasNext()){
					title = scanner.next();
					scanner.nextLine();
				}

				try{		//Tries to make playlist
					mylibrary.makePlaylist(title);
				}
				catch (PlaylistAlreadyExists e){		//Catches exception if playlist already exists, prints message
					System.out.println("Playlist " + title + " Already Exists");
				}
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String title = "";
				
				System.out.print("Playlist Title: ");		//Scans next input
				if (scanner.hasNext()){
					title = scanner.next();
					scanner.nextLine();
				}

				try{		//Tries to print playlist content
					mylibrary.printPlaylist(title);
				}
				catch (PlaylistNotFound e){		//Catches exception if playlist doesn't exist, prints message
					System.out.println("Playlist " + title + " Not Found");
				}
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				String title = "";
				String type = "";
				int index = 0;

				System.out.print("Playlist Title: ");		//Scans next input
				if (scanner.hasNext()){
					title = scanner.next();
					scanner.nextLine();
				}

				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");		//Scans next input - postcast input does not work
				if (scanner.hasNext()){
					type = scanner.next();
					scanner.nextLine();
				}

				System.out.print("Library Content #: ");		//Scans next input
				if (scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}


				try{		//Trues to add content to playlist
					mylibrary.addContentToPlaylist(type, index, title);
				}
				catch (PlaylistNotFound e){		//Catches exception if playlist doesn't exist, prints message
					System.out.println("Playlist " + title + " Not Found");
				}
				catch (ContentNotFound e){		//Catches exception if content doesn't exist, prints message
					if (type.equalsIgnoreCase(Song.TYPENAME)){
						System.out.println("Song Note Found");
					}
					if (type.equalsIgnoreCase(AudioBook.TYPENAME)){
						System.out.println("Audiobook Note Found");
					}
				}
				catch (ContentTypeNotFound e){		//Catches exception if content type doesn't exist, prints message
					System.out.println("Invalid Typename");
				}
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				String title = "";
				int index = 0;

				System.out.print("Playlist Title: ");			//Scans next input
				if (scanner.hasNext()){
					title = scanner.next();
					scanner.nextLine();
				}
				System.out.print("Playlist Content #: ");		//Scans next input
				if (scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}

				try{		//Tries to delete content from playlist
					mylibrary.delContentFromPlaylist(index, title);
				}
				catch (PlaylistNotFound e){		//Catches exception if playlist doesn't exist, prints message
					System.out.println("Playlist " + title + " Not Found");
				}
				catch (ContentNotFound e){		//Catches exception if content doesn't exist, prints message
					System.out.println("Title Not Found");
				}
			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}



		//A2 Actions

			//AudioContentStore acstore = new AudioContentStore();

			if (action.equalsIgnoreCase("SEARCH")){
				String title = "";

				System.out.print("Title: ");			//Scans next input
				if (scanner.hasNext()){
					title  = scanner.nextLine();
				
				try{		//Tries to search for an audio content title
				store.Search(title);
				}
				catch (NullPointerException e){		//Catches exception if content doesn't exist, prints message
					System.out.println("No matches for " + title);
				}
				}
			}


			else if (action.equalsIgnoreCase("SEARCHA")){
				String artist = "";

				System.out.print("Artist: ");			//Scans next input
				if (scanner.hasNext()){
					artist = scanner.nextLine();
					//scanner.nextLine();
				
				try{		//Tries to serach for audio contents by artist/ author
					store.SearchArtist(artist);
				}
				catch (NullPointerException e){		//Catches exception if artist/author doesn't exist, prints message
					System.out.println("No matches for " + artist);
				}
				}
			}

			else if (action.equalsIgnoreCase("SEARCHG")){
				String genre = "";

				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");			//Scans next input
				if (scanner.hasNext()){
					genre = scanner.next();
					//scanner.nextLine();
				try{		//Tries to search for audio contents by genre
					store.SearchGenre(genre);
				}
				catch (NullPointerException e){		//Catches exception if genre doesn't exist, prints message
					System.out.println("No matches for " + genre);
				}
				}
			}


			


			else if (action.equalsIgnoreCase("DOWNLOADA"))
			{
				String artist = "";
				System.out.print("Artist/ Author Name: ");			//Scans next input
				if (scanner.hasNext()){
					artist = scanner.nextLine();
				}

				try{		//Tries to get indexes of songs/audiobooks by artist/ author
					ArrayList<Integer> indicies = new ArrayList<Integer>();
					indicies = store.getIndexArtist(artist);	//Uses custom made method in AudioContentStore to get list of indixes of content artist/author made in store
					int index = 0;

					for (int i = 0; i < indicies.size(); i++){		//Iterates through indexes, gets audio content to download
						index = (indicies.get(i)) + 1;
						AudioContent content = store.getContent(index);		

						try{		//Tries to download audio content
						mylibrary.download(content);
						if (content.getType() == "SONG"){
							System.out.println("Song " +  content.getTitle() + " Added to Library");
						}
						if (content.getType() == "AUDIOBOOK"){
							System.out.println("Audiobook " +  content.getTitle() + " Added to Library");
							}
						
						}
						catch (ContentAlreadyDownloaded e){		//Catches exception if content already downloaded, prints message
							if (content.getType() == "SONG"){
								System.out.println("Song " +  content.getTitle() + " already downloaded");
							}
							if (content.getType() == "AUDIOBOOK"){
								System.out.println("Audiobook " +  content.getTitle() + " already downloaded");
								}
						}
					}
				}
				catch (NullPointerException e){		//Catches exception if artist/author doesn't exist, prints message
					System.out.println("No matches for " + artist);
				}
			}






			else if (action.equalsIgnoreCase("DOWNLOADG"))
			{
				String genre = "";
				System.out.print("Genre: ");			//Scans next input
				if (scanner.hasNext()){
					genre = scanner.nextLine();
				}

				try{			//Tries to get indexes of songs under genre
				ArrayList<Integer> indicies = new ArrayList<Integer>();
				
				indicies = store.getIndexGenre(genre);	//Uses custom made method in AudioContentStore to get list of indixes of songs under inputted genre made in store
				int index = 0;

				for (int i = 0; i < indicies.size(); i++){		//Iterates through indexes, gets audio content to download
					index = (indicies.get(i)) + 1;
				
					AudioContent content = store.getContent(index);
						
						try{		//Tries to download audio content
							mylibrary.download(content);
							if (content.getType() == "SONG"){
								System.out.println("Song " +  content.getTitle() + " Added to Library");
							}
							if (content.getType() == "AUDIOBOOK"){
								System.out.println("Audiobook " +  content.getTitle() + " Added to Library");
								}
							
						}
						catch (ContentAlreadyDownloaded e){			//Catches exception if content already downloaded, prints message
							if (content.getType() == "SONG"){
								System.out.println("Song " +  content.getTitle() + " already downloaded");
							}
							if (content.getType() == "AUDIOBOOK"){
								System.out.println("Audiobook " +  content.getTitle() + " already downloaded");
								}
							}
					}
				}
				catch (NullPointerException e){		//Catches exception if genre doesn't exist, prints message
					System.out.println("No matches for " + genre);	
				}	
			}




			System.out.print("\n>");
		}
	}
}
