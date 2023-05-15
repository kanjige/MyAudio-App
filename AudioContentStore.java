//Name: Eric Wong
//Student ID: 501174088

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{

	private ArrayList<AudioContent> contents; 
	private Map<String, Integer> titlemap = new TreeMap<String, Integer>();
	private Map<String, ArrayList<Integer>> artistmap = new TreeMap<String, ArrayList<Integer>>();
	private Map<String, ArrayList<Integer>> genremap = new TreeMap<String, ArrayList<Integer>>();

	
	private ArrayList<AudioContent> AudioContentStoreMaker() throws FileNotFoundException
	{
		
		contents = new ArrayList<AudioContent>();
		Scanner in = new Scanner(new File("store.txt"));

		while (in.hasNextLine())
		{
			String contentType = in.nextLine();

			String id = "";
			String title = "";
			int year = 0;
			int length = 0;


			String artist = "";
			String composer = "";
			Song.Genre genre = null;

			if (contentType.equals("SONG"))
			{
				id = in.nextLine();		//Reads and stores ID
				title = in.nextLine();	//Reads and stores name of title
				year = Integer.parseInt(in.nextLine());		//Reads and stores year of release
				length = Integer.parseInt(in.nextLine());	//Reads and stores length of song
				artist = in.nextLine();			//Reads and stores artist name
				composer = in.nextLine();		//Reads and stores composer name
				genre = Song.Genre.valueOf(in.nextLine());		//Reads and stores genre of song

				int lyriclen = Integer.parseInt(in.nextLine());		
				String file = "";
				for (int i = 0; i < lyriclen; i++)		//For loop to read and store each line of the lyrics, adds it to lyric string
				{
					String line = in.nextLine();
					file += line + "\r\n";
				}
				
				contents.add(new Song(title, year, id, Song.TYPENAME, file, length, artist, composer, genre, file));	//Constructs song
			}
			
			String author = "";
			String narrator = "";

			if (contentType.equals("AUDIOBOOK"))
			{
				id = in.nextLine();			//Reads and stores ID
				title = in.nextLine();		//Reads and stores name of title
				year = Integer.parseInt(in.nextLine());			//Reads and stores year of release
				length = Integer.parseInt(in.nextLine());		//Reads and stores length of song
				author = in.nextLine();		//Reads and stores author name
				narrator = in.nextLine();	//Reads and stores narrator name

				int titlecount = Integer.parseInt(in.nextLine());	
				ArrayList <String> chapterTitles = new ArrayList<String>();
				ArrayList <String> chapters = new ArrayList<String>();

				for (int i = 0; i < titlecount; i++)		//Adds titles to arraylist
				{
					String chapterTitle = in.nextLine();
					chapterTitles.add(chapterTitle);
				}

				for (int i = 0; i < titlecount; i++)		//For loop to read and store each line in a chapter, then appends to chapter arraylist
				{
					int chapterlen = Integer.parseInt(in.nextLine());
					String chapter = "";

					for (int j = 0; j < chapterlen; j++)
					{
						String line = in.nextLine();
						chapter += line + "\r\n";
					}

					chapters.add(chapter);

				}
				
					AudioBook book = new AudioBook(title, year, id, AudioBook.TYPENAME,  "", length,
					author, narrator, chapterTitles, chapters);		//Constructs audiobook
					contents.add(book);
			}
			if (contentType.equals("SONG")){			//Song loaded message
			System.out.println ("Loading SONG");
			}
			if (contentType.equals("AUDIOBOOK")){		//Audiobook loaded message
			System.out.println ("Loading AUDIOBOOK");
			}
				
		}
		in.close();
		return contents;
	}

	public AudioContentStore() 
	{
		//contents = new ArrayList<>();
		try{
			contents = AudioContentStoreMaker();
		}
		catch (IOException e){
			System.out.println(e.getMessage());
			System.exit(1);
		}

	//Map of titles
		for (int i = 0; i < contents.size(); i++){
			titlemap.put(contents.get(i).getTitle(), i);
		}

	//Map of artists
		for (int i = 0; i < contents.size(); i++){			
			ArrayList<Integer> indicies = new ArrayList<>();	
			String artist = "";

			if (contents.get(i).getType() == "SONG"){				//If content is a song, gets the artist
				Song song = (Song) contents.get(i);
				artist = song.getArtist();
			}

			if (contents.get(i).getType() == "AUDIOBOOK"){			//If content is a audiobook, gets the author
				AudioBook audiobook = (AudioBook) contents.get(i);	
				artist = audiobook.getAuthor();
			}

			if (!artistmap.containsKey(artist)){					//Checks if artist/author is already in map 
				for (int j = 0; j < contents.size(); j++){			//If not, iterates through each audio content

					String compareArtist = "";

					if (contents.get(j).getType() == "SONG"){		//If content is song, gets an artist to compare to
						Song song = (Song) contents.get(j);
						compareArtist = song.getArtist();			
					}
		
					if (contents.get(j).getType() == "AUDIOBOOK"){	//If content is audiobook, gets an author to compare to
						AudioBook audiobook = (AudioBook) contents.get(j);
						compareArtist = audiobook.getAuthor();
					}

					if (artist.equals(compareArtist)){				//If artist/author matches the one to compare to, adds index to arraylist
						indicies.add(j);
					}
				}
			}

			if (!artistmap.containsKey(artist)){					//Creates new element with unique artist/author and index list
				artistmap.put(artist, indicies);
			}
		}
	

	
	//Map of genres
		for (int i = 0; i < contents.size(); i++){

			ArrayList <Integer> indicies = new ArrayList<>();
			String genre = "";

			if (contents.get(i).getType() == "SONG"){				//If content is a song, takes the genre
				Song song = (Song) contents.get(i);
				genre = song.getGenre().toString();
			}

			if (!genremap.containsKey(genre)){						//Checks if genre is already in map
				for (int j = 0; j < contents.size(); j++){			//If not, iterates through each audio content

					String compareGenre = "";

					if (contents.get(j).getType() == "SONG"){		//If content is a song, makes a comparitor genre
						Song song = (Song) contents.get(j);
						compareGenre = song.getGenre().toString();	
					}
					if (genre.equals(compareGenre)){				//If genre matches one to compare to, adds index to arraylist
						indicies.add(j);	
					}
				}
			}
			if (!genremap.containsKey(genre)){						//Creates new element with unique genre and index list
				genremap.put(genre, indicies);


			
			}
		}


		//System.out.println(titlemap);

		//int index = 0;
		//index = titlemap.get("Shogun");
		//System.out.println(index + 1);
	}		
	
	
	public Map<String, Integer> getTitleMap(){
		return titlemap;
	}

	public Map<String, ArrayList<Integer>> getArtistMap(){
		return artistmap;
	}

	public Map<String, ArrayList<Integer>> getGenreMap(){
		return genremap;
	}


	

	public void Search(String title){

		int index = 0;
		

		index = titlemap.get(title);
		System.out.print((index + 1) + ". ");
		
		if (contents.get(index) instanceof Song){		//Checks if content searched is a song, prints info a song has
			Song song = (Song) contents.get(index); 
			song.printInfo();
		}

		if (contents.get(index) instanceof AudioBook){	//Checks if content searched is an audiobook, prints info an audiobook has
			AudioBook audiobook = (AudioBook) contents.get(index);
			audiobook.printInfo();
		}
	}

	public void SearchArtist(String artist){		

		ArrayList<Integer> indicies = new ArrayList<Integer>();
		indicies = artistmap.get(artist);				//Returns the indicies of songs/audiobooks with inputted artist/author

		for (int i = 0; i < indicies.size(); i++){		//Iterates through list of indexes, takes the index and gets it from content list, prints info of retirved song/audiobook

			if (contents.get(indicies.get(i)) instanceof Song){
				Song song = (Song) contents.get(indicies.get(i));
				System.out.print((indicies.get(i) + 1) + ". ");
				song.printInfo();						
				System.out.println();
			}
			if (contents.get(indicies.get(i)) instanceof AudioBook){
				AudioBook audiobook = (AudioBook) contents.get(indicies.get(i));
				System.out.print((indicies.get(i) + 1) + ". ");
				audiobook.printInfo();
				System.out.println();
			}
		}
	}


	public void SearchGenre(String genre){

		System.out.println(genre);

		ArrayList<Integer> indicies = new ArrayList<Integer>();
		indicies = genremap.get(genre);			//Returns the indicies of songs with inputted genre

		for (int i = 0; i < indicies.size(); i++){		//Iterates through list of indexes, takes the index and gets it from content list, prints info of retirved song

			Song song = (Song) contents.get(indicies.get(i));
			System.out.print((indicies.get(i) + 1) + ". ");
			song.printInfo();
			System.out.println();
		}

	}


	public ArrayList<Integer> getIndexArtist(String artist) throws NullPointerException{		//Gets the indicies of songs/audiobooks with inputted artist/author (used for DOWNLOADA command in MyAudioUI)
		return (artistmap.get(artist));
	}

	public ArrayList<Integer> getIndexGenre(String genre) throws NullPointerException{			//Gets the indicies of songs with inputted genre (used for DOWNLOADG command in MyAudioUI)
		return (genremap.get(genre));
	}

	
	public AudioContent getContent(int index)
	{
		if (index < 1 || index > contents.size())
		{
			return null;
		}
		return contents.get(index-1);
	}
	
	public void listAll()
	{
		for (int i = 0; i < contents.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

}

