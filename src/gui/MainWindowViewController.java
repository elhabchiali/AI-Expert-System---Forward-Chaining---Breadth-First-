package gui;

import app.BFS;
import app.Faits;
import app.GraphNiveau;
import app.Noeud;
import app.Regle;
import com.jfoenix.controls.JFXButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowViewController {

    Faits BdF;
    ArrayList<Regle> BdR;
    BFS bfs;

    @FXML
    private TextField butTextField;

    @FXML
    private TextArea resultatTextArea;

    @FXML
    private TextArea bdfTextArea;

    @FXML
    private TextArea bdrTextArea;

    @FXML
    private TextArea butTextArea;

    @FXML
    private JFXButton bdrLoader;

    @FXML
    private JFXButton bdfLoader;

    @FXML
    private Canvas c;

    private String but, bdf, bdr;
    private Set<GraphNiveau> graph;

    @FXML
    void loadFaits(ActionEvent event) {
        String out = getTextFromFile("Charger les faits", "*.txt").replaceAll("\n", "");
        bdfTextArea.setText(out);
    }

    @FXML
    void loadRegles(ActionEvent event) {
        String out = getTextFromFile("Charger les regles", "*.txt");
        bdrTextArea.setText(out);
    }

    @FXML
    void rechercher(ActionEvent event) {
        butTextArea.setText(butTextField.getText().toUpperCase());
        Regle.setN0();
        BdF = parseBdF();
        BdR = parseBdR();
        bfs = new BFS(BdF, BdR);
        String[] out = new String[2];
        out = bfs.recherche(butTextArea.getText().toUpperCase());

        if (Boolean.valueOf(out[0])) {
            out[1] += "But trouvé";
        } else {
            out[1] += "But non trouvé";
        }
        resultatTextArea.setText(out[1]);
    }

    @FXML
    void reinitialiser(ActionEvent event) {
        bdfTextArea.clear();
        bdrTextArea.clear();
        butTextArea.clear();
        butTextField.clear();
        resultatTextArea.clear();
        BdF = parseBdF();
        BdR = parseBdR();
        bfs = new BFS(BdF, BdR);
        Regle.setN0();
    }

    public String getTextFromFile(String msg, String ext) {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Plain text", ext);
        fc.getExtensionFilters().add(extensionFilter);
        fc.setTitle(msg);
        File f = fc.showOpenDialog(bdrLoader.getScene().getWindow());
        FileReader fr = null;
        String out = "";
        try {
            fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            String line = "";
            while ((line = bf.readLine()) != null) {
                out += line + "\n";
            }
            bf.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            System.err.println("FileNotFound ex : " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("IOException ex : " + ex.getMessage());
        }
        return out;
    }

    @FXML
    public void dessinerArbre(ActionEvent e) {
        butTextArea.setText(butTextField.getText().toUpperCase());
        BdF = parseBdF();
        BdR = parseBdR();
        bfs = new BFS(BdF, BdR);
        graph = bfs.getGraph(butTextArea.getText().toUpperCase());

        c = new Canvas(1024, 567);
        GraphicsContext gc = c.getGraphicsContext2D();

        int i = 1;

        for (GraphNiveau gn : graph) {
            double windowWidth = 1024;
            int niveau = gn.getN();
            int nNoeuds = gn.getNiveau().size();

            int ni = 0;
            for (Noeud n : gn.getNiveau()) {
                double nWidth = n.getW();
                double espace = (windowWidth - nNoeuds * nWidth) / (nNoeuds + 1);
                n.setX(espace + (espace + nWidth) * ni++);
                System.out.print(n.getNom() + "(" + n.getX() + "," + n.getY() + ") : ");
                if (n.getParents() != null) {
                    int ni2 = 0;

                    for (Noeud p : n.getParents()) {
                        boolean breack = false;
                        double px=0,py=0;
                        for(GraphNiveau gn2:graph){
                            for(Noeud n2:gn2.getNiveau()){
                                if(gn2.getN()>=gn.getN()){
                                    breack = true;
                                    break;
                                }
                                if(n2.getNom().equals(p.getNom())){
                                    px=n2.getX();
                                    py=n2.getY();
                                }
                                    
                            }
                            if(breack) break;
                        }
                        
                        System.out.print(p.getNom() + "(" + p.getX() + "," + p.getY() + ") ");
                        gc.setFill(Color.BLACK);
                        gc.strokeLine(px + 25, py + 50, n.getX() + 25, n.getY() + 25);
//                        gc.strokeLine(p.getX() + 25, p.getY() + 50, n.getX() + 25, n.getY() + 25);
//                        gc.setFill(Color.TRANSPARENT);
//                        gc.fillOval(p.getX(), p.getY(), p.getW(), p.getH());

                    }
                }
                gc.setFill(n.getC());
                gc.fillOval(n.getX(), n.getY(), n.getW(), n.getH());
                gc.setFill(Color.WHITE);
                gc.fillText(n.getNom(), n.getX() + 21, n.getY() + 27);
                System.out.println();
            }
        }

//        for (GraphNiveau gn : graph) {
//            double windowWidth = 1024;
//            int niveau = gn.getN();
//            int nNoeuds = gn.getNiveau().size();
//
//            int ni = 0;
//            for (Noeud n : gn.getNiveau()) {
//                double nWidth = n.getW();
//                double espace = (windowWidth - nNoeuds * nWidth) / (nNoeuds + 1);
//                n.setX(espace + (espace + nWidth) * ni++);
//                System.out.print(n.getNom() + "(" + n.getX() + "," + n.getY() + ") : ");
//                if (n.getParents() != null) {
//
//                    for (Noeud p : n.getParents()) {
//                        System.out.print(p.getNom() + "(" + p.getX() + "," + p.getY() + ") ");
//                        //gc.setFill(Color.BLACK);
//                        //gc.strokeLine(p.getX() + 25, p.getY() + 50, n.getX() + 25, n.getY() + 25);
//                        //gc.setFill(Color.TRANSPARENT);
//                        //gc.fillOval(p.getX(), p.getY(), p.getW(), p.getH());
//
//                    }
//                }
//                //gc.setFill(n.getC());
//                //gc.fillOval(n.getX(), n.getY(), n.getW(), n.getH());
//                //gc.setFill(Color.WHITE);
//                //gc.fillText(n.getNom(), n.getX() + 21, n.getY() + 27);
//                System.out.println();
//            }
//        }

        try {
            FXMLLoader loader = (new FXMLLoader(MI_ChainageAvant.class.getResource("arbreWindowView.fxml")));
            AnchorPane root = loader.load();
            root.setUserData(graph);
            root.getChildren().add(c);
            Stage arbreStage = new Stage();
            arbreStage.initModality(Modality.NONE);
            Scene scene = new Scene(root);
            arbreStage.setScene(scene);
            arbreStage.setTitle("arbre dessin");
            arbreStage.show();

        } catch (IOException ex) {
            System.err.print("View 'arbreWindowView' probleme :: ");
            System.err.println(ex.getMessage());
        }

//        for(GraphNiveau gn:graph){
//            System.out.println("Niveau : "+gn.getN());
//            for(Noeud n:gn.getNiveau()){
//                System.out.print(""+n.getNom()+" ");
//                if(n.getParents()!=null)
//                    for(Noeud s:n.getParents())
//                        System.out.print(""+s.getNom()+" ");
//                    System.out.println("");
//            }
//            
//            System.out.println("");
//        }
    }

    public ArrayList<Regle> parseBdR() {
        BdR = new ArrayList<>();
        String strRegles = bdrTextArea.getText();
        strRegles = strRegles.toUpperCase().replaceAll("ET|et", ",").replaceAll("DONNE|ALORS", "=").replaceAll("[^A-Z=,\n]", "");

        for (int i = 0; i < strRegles.split("\n").length; i++) {
            Faits faits = new Faits();
            String line = strRegles.split("\n")[i];
            faits.setFaits(new HashSet<String>(Arrays.asList(line.split("=")[0].split(","))));
            BdR.add(new Regle(line.split("=")[1], faits));
        }
        return BdR;
    }

    public Faits parseBdF() {
        BdF = new Faits();
        String strFaits = bdfTextArea.getText().toUpperCase();
        BdF.setFaits(new HashSet<String>(Arrays.asList(strFaits.split(","))));
        return BdF;
    }

    public LinkedHashSet<GraphNiveau> getGraph() {
        return (LinkedHashSet<GraphNiveau>) graph;
    }
}
