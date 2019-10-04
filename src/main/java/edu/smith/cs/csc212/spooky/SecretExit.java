package edu.smith.cs.csc212.spooky;
/**
 * 
 * @author Bella
 *
 *Make a new class secretExit that inherits exit class
 *we want these exits to be secret and hidden for now
 */
public class SecretExit extends Exit {
	public boolean isSecret;
	private boolean hidden;
	
	/**
	 * gets 
	 * @param target
	 * @param description
	 * secret exit has same arguments as exit--target and description
	 * secret exit is secret and hidden
	 */
	public SecretExit(String target, String description) {
		super(target, description);
		isSecret=true; 
		hidden=true;
	}
	/**
	 * when user types in search, exit becomes not hidden, aka visible
	 */
	public void search() {
		hidden=false;
	
	}
	
	@Override
	public boolean isSecret() {
		return this.isSecret;
	}
	
	@Override
	public boolean hidden() {
		return this.hidden;
		
	}
}
	

