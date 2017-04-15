package app;

import java.util.LinkedHashSet;
import java.util.Set;
import javafx.scene.paint.Color;

public class Noeud {

    private String nom;
    private double x, y, w, h;
    private Set<Noeud> parents;
    private Color c=Color.CORAL;

    public Noeud(String nom, double x, double y, double w, double h, LinkedHashSet<Noeud> parents) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.parents = parents;
    }

    public Noeud() {
        nom = "";
        x = y = w = h = 0;
        parents = new LinkedHashSet<>();
    }

    public double getH() {
        return h;
    }

    public double getW() {
        return w;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setParents(LinkedHashSet<Noeud> parents) {
        this.parents = parents;
    }

    public LinkedHashSet<Noeud> getParents() {
        return (LinkedHashSet) parents;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setC(Color c) {
        this.c = c;
    }

    public Color getC() {
        return c;
    }
    
}
