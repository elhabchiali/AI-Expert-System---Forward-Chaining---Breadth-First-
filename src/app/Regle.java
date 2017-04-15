package app;

import java.util.HashSet;

public class Regle {

	private static int n = 0;
	private String cnc;
	private Faits premisses;
	private int i = 0;
	
	public Regle(String cnc,Faits faits) {
		this.cnc = cnc;
		this.premisses = faits;
		this.i = ++n;
		}
	public Regle() {
		
		this.cnc = "";
		this.premisses = new Faits();
		
	}
	public int getI() {
		return i;
	}
        public static void setN0(){
            Regle.n = 0;
        }
	public String getCnc() {
		return cnc;
	}
	public void setCnc(String cnc) {
		this.cnc = cnc;
	}
	public HashSet<String> getPremisses() {
		return (HashSet)(premisses.getFaits());
	}
	public void setPremisses(Faits faits) {
		this.premisses = faits;
	}
	
}
