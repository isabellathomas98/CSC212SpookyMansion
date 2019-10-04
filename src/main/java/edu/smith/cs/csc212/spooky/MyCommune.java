package edu.smith.cs.csc212.spooky;

import java.util.HashMap;
import java.util.Map;

public class MyCommune implements GameWorld {
		private Map<String, Place> places = new HashMap<>();

		/**
		 * Where should the player start?
		 */
		@Override
		public String getStart() {
			return "communalBath";
		}

	public MyCommune() {
		//1
		Place communalBath=insert(
				Place.create("communalBath","you are in the sacred waters of charles manson"));
		communalBath.addExit(new Exit("sewer", "There is a drain u can flush yourself down..."));
		communalBath.addExit(new Exit("charlie", "You see a kind man smiling at you, arms outstretched. Hug him!"));
		
		//2
		Place sewer=insert(
				Place.create("sewer","you are in the sewer"));
		sewer.addExit(new Exit("communalBath", "There is a drain u can climb back up..."));
		sewer.addExit(new Exit("prisonCell", "There is a large metal door..."));
		sewer.addExit(new Exit("tunnel","there is an entrance to a tunnel in the wall"));
		
		//3
		Place charlie=insert(Place.terminal("charlie", "You have been strangled to death by a maniac."));
		
		//4
		Place prisonCell=insert(
				Place.create("prisonCell","you are in jail..."));
		prisonCell.addExit(new Exit("sewer", "run back to the sewer!!!"));
		
		
		//5
		Place tunnel=insert(
				Place.create("tunnel","u r in the tunnel..."));
		tunnel.addExit(new Exit("lab", "turn left in creepy tunnel!!!"));
		tunnel.addExit(new Exit("well", "turn right in creepy tunnel!!!"));
		tunnel.addExit(new Exit("sewer", "turn around...this is sketchy..."));
		
		//6
		Place lab=insert(
				Place.create("lab","u r in a strange lab...don't eat the candy."));
		lab.addExit(new Exit("tunnel", "go back to tunnel!!!"));
		lab.addExit(new Exit("loft", "There is a steep spiral staircase going up!!!"));
		
		Place well=insert(
				Place.create("well","u r falling down a well!."));
		well.addExit(new Exit("exit", "there is a door with some light shining through it!!!"));
		
		//7
		Place loft=insert(
				Place.create("loft","You are in a loft."));
		loft.addExit(new Exit("ground", "You see a large square window...jump?"));
		loft.addExit(new Exit("trampoline","you see another window, but it is small and round"));
		
		//8
		Place ground=insert(
				Place.terminal("ground","You've fallen to your death."));
		
		Place trampoline=insert(
				Place.create("trampoline","You have fallen onto a trampoline."));
		trampoline.addExit(new Exit("communalBath","Bounce back into....."));
		
		
		//9
		Place exit=insert(
				Place.terminal("exit","You've escaped!!!!!!."));
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		checkAllExitsGoSomewhere();
	}
	private Place insert(Place p) {
		places.put(p.getId(), p);
		return p;
	}
	//////////////////////////////////////////////////////////////////////////////
	private void checkAllExitsGoSomewhere() {
		boolean missing = false;
		// For every place:
		for (Place p : places.values()) {
			// For every exit from that place:
			for (Exit x : p.getVisibleExits()) {
				// That exit goes to somewhere that exists!
				if (!places.containsKey(x.getTarget())) {
					// Don't leave immediately, but check everything all at once.
					missing = true;
					// Print every exit with a missing place:
					System.err.println("Found exit pointing at " + x.getTarget() + " which does not exist as a place.");
				}
			}
		}

		// Now that we've checked every exit for every place, crash if we printed any
		// errors.
		if (missing) {
			throw new RuntimeException("You have some exits to nowhere!");
		}
	}
	////////////////////////////////////////////////////
	public Place getPlace(String id) {
		return this.places.get(id);
	}

}
