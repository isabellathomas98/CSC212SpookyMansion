package edu.smith.cs.csc212.spooky;

import java.util.ArrayList;
import java.util.List;

/**
 * This is our main class for SpookyMansion.
 * It interacts with a GameWorld and handles user-input.
 * It can play any game, really.
 *
 * @author jfoley
 *
 */
public class InteractiveFiction {
	

	/**
	 * This method actually plays the game.
	 * @param input - a helper object to ask the user questions.
	 * @param game - the places and exits that make up the game we're playing.
	 * @return where - the place the player finished.
	 */
	static String runGame(TextInput input, GameWorld game) {
		// This is the current location of the player (initialize as start).
		// Maybe we'll expand this to a Player object.
		String place = game.getStart();
		//List<String> words = input.getUserWords("?");
		
		/**
		 * This is where we have the items that player has collected in a list.
		 */
		List<String> playerItems= new ArrayList<String>();

		// Play the game until quitting.
		// This is too hard to express here, so we just use an infinite loop with breaks.
		while (true) {
			// Print the description of where you are.
			Place here = game.getPlace(place);
			
			//System.out.println();
			System.out.println("... --- ...");
			//can comment out later
			//System.out.println("place="+place);
			//System.out.println("here="+here);
			//System.out.println(here.getDescription());
			here.printDescription();
			
			/**
			 * If user has visited a place already, they will be told. 
			 */
			if (here.hasVisited()) {
				System.out.println("feels familiar...");
			}
			
			here.visit();

			// Game over after print!
			if (here.isTerminalState()) {
				break;
			}

			// Show a user the ways out of this place.
			
			List<Exit> exits = here.getVisibleExits();

			/**
			 * If the exits are not hidden, then print them to user.
			 */
			for (int i=0; i<exits.size(); i++) {
				Exit e = exits.get(i);
				if (e.hidden()==false) {
					System.out.println(" "+i+". " + e.getDescription());
				}
			}

			// Figure out what the user wants to do, for now, only "quit" is special.
			List<String> words = input.getUserWords("?");
			if (words.size() > 1) {
				System.out.println("Only give the system 1 word at a time!");
				continue;
			}

			// Get the word they typed as lowercase, and no spaces.
			// Do not uppercase action -- I have lowercased it.
			String action = words.get(0).toLowerCase().trim();
			

			/**
			 * User can type in either "q" or "escape" to quit game.
			 */
			if (action.equals("q")||action.equals("escape")) {
				if (input.confirm("Are you sure you want to quit?")) {
					return place;
				}
				
				else continue;
			}
			
			/**
			 * If user needs help, they can type help to receive help message.
			 */
			if (action.equals("help")) {
				System.out.println("Help message: You must type in a number. Type q or escape to quit game.");
				continue;
			}
			
			/** 
			 * If user types search in a room, hidden exit will become visible.
			 */
			if (action.equals("search")) {
				for (int i=0; i<exits.size(); i++) {
					Exit e = exits.get(i);
					//System.out.println(" "+i+". " + e.getDescription());
					e.search();
					
				}
				continue;
			}
			/**
			 * If the user types "take", then the item taken will be added to their list of items.
			 */
			if(action.equals("take")) {
				playerItems.addAll(here.take());
				continue;
			}
			/**
			 * if user asks for what items they have, it will print
			 */
			if(action.equals("stuff")) {
				if (playerItems.size()==0){
					System.out.println("You have nothing.");}
				else System.out.println("You have" + playerItems);
					continue;
				
			}
			

			// From here on out, what they typed better be a number!
			Integer exitNum = null;
			try {
				exitNum = Integer.parseInt(action);
			} catch (NumberFormatException nfe) {
				System.out.println("That's not something I understand! Try a number!");
				continue;
			}

			if (exitNum < 0 || exitNum >= exits.size()) {
				System.out.println("I don't know what to do with that number!");
				continue;
			}

			// Move to the room they indicated.
			Exit destination = exits.get(exitNum);
			place = destination.getTarget();
		}

		return place;
	}

	/**
	 * This is where we play the game.
	 * @param args
	 */
	public static void main(String[] args) {
		// This is a text input source (provides getUserWords() and confirm()).
		TextInput input = TextInput.fromArgs(args);
		System.out.print("WELCOME. WHICH GAME WOULD YOU LIKE TO PLAY?\n Spooky mansion or My Commune \n");
		List<String> words = input.getUserWords("type spooky or commune");
		String action = words.get(0).toLowerCase().trim();
		

		// This is the game we're playing.
		//also if user picks neither uhhh.. tell them u cant do that. prints something.
		
		/**
		 * There are two games: spookyMansion and myCommune. User can pick one. 
		 * Note: User must click twice to do everything...trying to figure out why.
		 */
		if (action.equals("spooky")){
			GameWorld game = new SpookyMansion();

		//if game chosen is spooky mansion
			runGame(input, game);}
		
		//if the game chosen is commune::::::::
		if (action.equals("commune")){
			GameWorld game = new MyCommune();

			// Actually play the game.
			runGame(input, game);}

		// You get here by typing "quit" or by reaching a Terminal Place.
		System.out.println("\n\n>>> GAME OVER <<<");
	}

}
