package app;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class GraphNiveau {

    private int n;
    private Set<Noeud> niveau;

    public GraphNiveau() {
        niveau = new LinkedHashSet<>();
    }

    public GraphNiveau(int n, LinkedHashSet<Noeud> niveau) {
        this.n = n;
        this.niveau = niveau;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public LinkedHashSet<Noeud> getNiveau() {
        return (LinkedHashSet)niveau;
    }

    public void setNiveau(LinkedHashSet<Noeud> niveau) {
        this.niveau = niveau;
    }

}
