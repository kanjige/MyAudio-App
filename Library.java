//Name: Eric Wong
//Student ID: 501174088

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{
		if (content.getType() == Song.TYPENAME){
			Song song = (Song) content;						//Makes song object using content parameters 

			for (int i = 0; i < songs.size(); i++){
				if (song.equals(songs.get(i))){				//Compares made song with songs already downloaded to check if there's a duplicate, throws exception if there is
					//errorMsg = "Song already downloaded";	
					throw new ContentAlreadyDownloaded(errorMsg);
				}
			}
			songs.add(song);	// If song not yet in songs list, adds
		}

		if (content.getType() == AudioBook.TYPENAME){
			AudioBook audbook = (AudioBook) content;		//Makes audiobook object using content parameters 

			for (int i = 0; i < audiobooks.size(); i++){
				if (audbook.equals(audiobooks.get(i))){		//Compares made audiobook with audiobooks already downloaded to check if there's a duplicate, throws exception if there is
					//errorMsg = "AudioBook already downloaded";
					throw new ContentAlreadyDownloaded(errorMsg);
				}
			}
			audiobooks.add(audbook);
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++){		//Iterates through list of songs, prints them 
			int index = i + 1;
			System.out.print("" + index + ". ");	
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++){	//Iterates through list of audiobooks, prints them 
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();

		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++){		//Iterates through list of playlists, prints them 
			int index = i + 1;
			System.out.println(index + ". " + playlists.get(i).getTitle());
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> artists = new ArrayList<String>();	//List of artists

		for (int i = 0; i < songs.size(); i++){
			String artist = songs.get(i).getArtist();	//Makes a new string, being the artist of the song list
			if (!artists.contains(artist)){				//Checks if the artist is already in the list of artists, if not, adds them
				artists.add(artist);	
			}
		}

		for (int i = 0; i < artists.size(); i++){		//Iterates through list of artists, prints them
			System.out.println(i+1 + ". " + artists.get(i));
		}
		
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		if (index < 1 || index > songs.size()){		//Checks if inputted index is within permitted range, if not exception is thrown
			//errorMsg = "Song Not Found";
			throw new ContentNotFound(errorMsg);
		}
		index -= 1;							//Subtracts 1 to the index to translate from how it's listed to the user to how it's actually indexed 
		Song song = songs.get(index);		//Makes song object based on the song in the index of the song list
		songs.remove(song);					//Removes song from song list

		for (int i = 0; i < playlists.size(); i++){				//Iterates through every playlist
			int songind = playlists.get(i).getIndexOf(song);	//Gets the index of the target song in a playlist; if not in the playlist, -1 is returned
			if (songind != -1){									//Checks if song is in the playlist, if it is, deleted song from playlist
				playlists.get(i).deleteContent(songind+1);		
			}
		} 
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());	//Sorts song list based on age, using the SongYearComparitor
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator <Song>
	{
		public int compare(Song s1, Song s2){				//Compares song based on the year it was released
			if (s1.getYear() < s2.getYear()) return -1;
			if (s1.getYear() > s2.getYear()) return 1;
			return 0;
			}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());	//Sorts song list based on length, using SongLengthComparitor
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator <Song>
	{
		public int compare(Song s1, Song s2){			//Compares song based on legnth
			if (s1.getLength() < s2.getLength()) return -1;
			if (s1.getLength() > s2.getLength()) return 1;
			return 0;
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);		//Sorts song list based on name, using compareTo
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())		//Checks if inputted index is within permitted range, if not exception is thrown
		{
			//errorMsg = "Song Not Found";
			throw new ContentNotFound(errorMsg);
		}
		songs.get(index-1).play();		//Plays the song if index is in range
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		if (index < 1 || index > audiobooks.size()){		//Checks if inputted index is within permitted range, if not exception is thrown
			//errorMsg = "AudioBook Not Found";
			throw new ContentNotFound(errorMsg);
		}
		
		else if (chapter < 1 || chapter > audiobooks.get(index-1).getNumberOfChapters()){		//Checks if inputted chapter is within permitted range, if not exception is thrown
			throw new ChapterNotFound(errorMsg);
		}
		
		audiobooks.get(index-1).selectChapter(chapter);		//Sets chapter to play, plays it
		audiobooks.get(index-1).play();
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		if (index < 1 || index > audiobooks.size()){		//Checks if inputted index is within permitted range, if not exception is thrown
			//errorMsg = "AudioBook Not Found";
			throw new ContentNotFound(errorMsg);
		}
		audiobooks.get(index-1).printTOC();		//Prints table of contents
	}
	
  /*
   * Playlist Related Methods
   */
	
    
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		for (int  i = 0; i < playlists.size(); i++){			//Makes a string of each playlist title
			String pltitle = playlists.get(i).getTitle();

			if (pltitle.equals(title)){		//Checks if inputted title is a title that already exists, if so, exception is thrown
				//NOTE: This method is case sensitive (ex. if there's a playlist called 'Workout' and 'WORKOUT', the method will make a new playlist)
				//errorMsg = "Playlist " + title + " Already Exists";
				throw new ContentAlreadyDownloaded(errorMsg);
			}
		}
		Playlist newplaylist = new Playlist(title);	//Makes a new playlist to add to the 'playlists' arraylist
		playlists.add(newplaylist);
	}
	


	//CUSTOM ADDED METHOD: Returns the index of a playlist in the 'playlists' arraylist, used to check if a playlist exists and if it does where
	public int checkPlaylistTitle(String title){

		for (int i = 0; i < playlists.size(); i++){
			String pltitle = playlists.get(i).getTitle();
			if (pltitle.equals(title)){
				return i;
			}
		}
		return -1;	//If the playlist is not in the 'playlists' arraylist, returns -1
	} 




	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{														
		int index = checkPlaylistTitle(title);			//Checks if playlist exists, if not exception is thrown
		if (index == -1){
			//errorMsg = "Playlist " + title + " Not Found";
			throw new ContentNotFound(errorMsg);
		}
		playlists.get(index).printContents();			//Prints all contents in desired playlist
	}
	

	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		int index = checkPlaylistTitle(playlistTitle);	//Checks if playlist exists, if not exception is thrown
		if (index == -1){
			//errorMsg = "Playlist " + playlistTitle + " Not Found";
			throw new PlaylistNotFound(errorMsg);
		}
		playlists.get(index).playAll();			//Plays all the contents in desired playlist
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		 int index = checkPlaylistTitle(playlistTitle);		//Checks if playlist exists, if not exception is thrown
		 if (index == -1){
			//errorMsg = "Playlist " + playlistTitle + " Not Found";
			throw new PlaylistNotFound(errorMsg);
		 }

		if (indexInPL < 1 || indexInPL > playlists.get(index).getSize()){	//Checks if inputted index is within permitted range, if not exception is thrown
			//errorMsg = "Title Not Found";
			throw new ContentNotFound(errorMsg);
		}
		playlists.get(index).play(indexInPL);		//Plays selected content in playlist

	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		//System.out.println(songs.get(index-1));
		int PLindex = checkPlaylistTitle(playlistTitle);		//Checks if playlist exists, if not exception is thrown
		if (PLindex == -1){
			//errorMsg = "Playlist " + playlistTitle + " Not Found";
			throw new PlaylistNotFound(errorMsg);
		}
		if (type.equalsIgnoreCase(Song.TYPENAME)){				//Checks if song type is chosen
			if (index < 1 || index > songs.size()){				//Checks if inputted index is within permitted range, if not exception is thrown
				//errorMsg = "Song Not Found";
				throw new ContentNotFound(errorMsg);
			}
			playlists.get(PLindex).addContent(songs.get(index-1));		//Adds desired song to playlist

		}
		else if (type.equalsIgnoreCase(AudioBook.TYPENAME)){	//Checks if audiobook type is chosen
			if (index < 1 || index > audiobooks.size()){		//Checks if inputted index is within permitted range, if not exception is thrown
				//errorMsg = "AudioBook Not Found";
				throw new ContentNotFound(errorMsg);
			}
			playlists.get(PLindex).addContent(audiobooks.get(index-1));	//Adds desired audiobook to playlist
		}
		else{				//Checks if audiofile type is invalid, error message is set, returned false
			//errorMsg = "Invalid Typename";	
			throw new ContentTypeNotFound(errorMsg);
		}
				
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		int PLindex = checkPlaylistTitle(title);				//Checks if playlist exists, if not exception is thrown
		if (PLindex == -1){
			//errorMsg = "Playlist " + title + " Not Found";
			throw new PlaylistNotFound(errorMsg);
		}
		
		if (index < 1 || index > playlists.get(PLindex).getSize()){		//Checks if inputted index is within permitted range, if not exception is thrown
			//errorMsg = "Title Not Found";
			throw new ContentNotFound(errorMsg);
		}
		
		playlists.get(PLindex).deleteContent(index);	//Deletes desired audiofile type from playlist
	}
	
}




class ContentNotFound extends RuntimeException{
    public ContentNotFound(String errorMessage) {
        super(errorMessage);
    }
}

class ContentAlreadyDownloaded extends RuntimeException{
    public ContentAlreadyDownloaded(String errorMessage) {
        super(errorMessage);
    }
}


class PlaylistAlreadyExists extends RuntimeException{
    public PlaylistAlreadyExists(String errorMessage) {
        super(errorMessage);
    }
}

class PlaylistNotFound extends RuntimeException{
    public PlaylistNotFound(String errorMessage) {
        super(errorMessage);
    }
}

class ContentTypeNotFound extends RuntimeException{
    public ContentTypeNotFound(String errorMessage) {
        super(errorMessage);
    }
}

class ChapterNotFound extends RuntimeException{
    public ChapterNotFound(String errorMessage) {
        super(errorMessage);
    }
}
