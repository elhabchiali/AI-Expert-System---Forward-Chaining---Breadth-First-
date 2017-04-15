package app;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import javafx.scene.paint.Color;

public class BFS {

    private Faits BdF;
    private ArrayList<Regle> BdR;

    public BFS(Faits BdF, ArrayList<Regle> BdR) {

        this.BdF = BdF;
        this.BdR = BdR;

    }

    public String[] recherche(String but) {

        String[] result = new String[2];
        boolean sortir = false;
        boolean trouve = BdF.contains(but);
        if (trouve) {
            result[0] = "" + trouve;
            return result;
        }

        result[1] = "BdF0 : " + BdF.getFaits();
        result[1] += "\n\t-----------------------\n";
        while (!BdF.contains(but) && !sortir && !BdR.isEmpty()) {
            sortir = true;
            ArrayList<Regle> reglesASupp = new ArrayList<>();
            Faits faitsAajouter = new Faits();
            for (Regle r : BdR) {

                if (BdF.contains(r.getPremisses())) {
                    sortir = false;
                    if (r.getCnc().equals(but)) {
                        trouve = true;
                    }
                    faitsAajouter.addFait(r.getCnc());
                    reglesASupp.add(r);
                }
            }
            if (!reglesASupp.isEmpty()) {
                result[1] += "Regles Appliquées : \n\t";
                for (Regle r : reglesASupp) {
                    result[1] += "R" + r.getI() + "  ";
                }
                BdF.addFait(faitsAajouter);
                BdR.removeAll(reglesASupp);
                result[1] += "\nBdF : " + BdF.getFaits();
            } else {
                result[1] += "Aucune Regles Appliquée.";
            }
            result[1] += "\n\t-----------------------\n";
        }
        result[0] = "" + trouve;
        return result;
    }

    public LinkedHashSet<GraphNiveau> getGraph(String but) {

        int j = 1;
        //niveau 1
        Set<GraphNiveau> graph = new LinkedHashSet<>();
        GraphNiveau gn = new GraphNiveau();
        gn.setN(j);
        LinkedHashSet<Noeud> l = new LinkedHashSet<>();
        int i = 1;
        for (String s : BdF.getFaits()) {
            double windowWidth = 1024;
            int niveau = 1;
            int nNoeuds = BdF.getFaits().size();
            double nWidth = 50;
            double nx, espace = (windowWidth - nNoeuds * nWidth) / (nNoeuds + 1);
            nx = espace + (espace + nWidth) * (i - 1);
            Noeud n = new Noeud(s, nx, 100, nWidth, nWidth, null);
            if (s.equals(but)) {
                n.setC(Color.BLACK);
            }
            l.add(n);
            i++;
        }
        gn.setNiveau(l);
        graph.add(gn);

        boolean sortir = false;
        boolean trouve = BdF.contains(but);

        while (!BdF.contains(but) && !sortir && !BdR.isEmpty()) {
            GraphNiveau graphNiveau = new GraphNiveau();
            graphNiveau.setN(++j);
            LinkedHashSet<Noeud> niveauI = new LinkedHashSet<>();

            sortir = true;
            ArrayList<Regle> reglesASupp = new ArrayList<>();
            Faits faitsAajouter = new Faits();
            int k = 1;
            for (Regle r : BdR) {

                if (BdF.contains(r.getPremisses())) {
                    sortir = false;
                    boolean cont = false;
                    for (GraphNiveau g : graph) {
                        for (Noeud ntemp : g.getNiveau()) {
                            if (ntemp.getNom().equals(r.getCnc())) {
                                cont = true;
                                break;
                            }
                        }
                        if (cont) {
                            break;
                        }
                    }
                    LinkedHashSet<Noeud> parents = new LinkedHashSet<>();
                    for (String p : r.getPremisses()) {

                        double x = 0, y = 0;
                        for (GraphNiveau g : graph) {
                            for (Noeud nn : g.getNiveau()) {
                                if (nn.getNom().equals(p)) {
                                    x = nn.getX();
                                    y = nn.getY();
                                }
                            }
                        }
                        Noeud n = new Noeud(p, x, y, 50, 50, null);
                        parents.add(n);
                    }

                    double windowWidth = 1024;
                    int niveau = gn.getN();
                    int nNoeuds = gn.getNiveau().size();
                    double nWidth = 50;
                    double espace = (windowWidth - nNoeuds * nWidth) / (nNoeuds + 1);
                    double nx = espace + (espace + nWidth) * (k - 1);
                    Noeud noeudI = new Noeud(r.getCnc(), nx, j * 100, 50, 50, parents);

                    if (r.getCnc().equals(but)) {
                        faitsAajouter.addFait(r.getCnc());
                        trouve = true;
                        noeudI.setC(Color.BLACK);
                    } else {
                        faitsAajouter.addFait(r.getCnc());
                    }
                    reglesASupp.add(r);
                    if (!cont) {
                        niveauI.add(noeudI);
                    }

                }
                k++;
            }
            if (!reglesASupp.isEmpty()) {
                BdF.addFait(faitsAajouter);
                graphNiveau.setNiveau(niveauI);
                graph.add(graphNiveau);
            }
            BdR.removeAll(reglesASupp);

        }

        return (LinkedHashSet<GraphNiveau>) graph;
    }
}
