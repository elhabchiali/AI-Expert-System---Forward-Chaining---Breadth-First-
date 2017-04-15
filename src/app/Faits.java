package app;

import java.util.HashSet;
import java.util.Set;

public class Faits {

	private Set<String> faits;
	
	public Faits(HashSet<String> faits) {
		this.faits = faits;
	}
	
	public Faits() {
		this.faits = new HashSet<String>();
	}

	public void setFaits(HashSet<String> faits) {
		this.faits = faits;
	}
	
	public HashSet<String> getFaits() {
		return (HashSet)faits;
	}
	
	public boolean contains(HashSet<String> fait){
			
		if(faits.containsAll(fait))
			return true;
		return false;
	
	}
	
	public boolean contains(String fait){
		
		if(faits.contains(fait))
			return true;
		return false;
	
	}
	
	public void addFait(String fait) {
		faits.add(fait);
	}

	public void addFait(Faits faits) {
		this.faits.addAll(faits.getFaits());
	}
	
}
