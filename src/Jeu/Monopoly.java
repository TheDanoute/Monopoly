package Jeu;

import Ui.TexteUI;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Monopoly {
	private int nbMaisons = 32;
	private int nbHotels = 12;
        private PaquetsCartes cartes;
	private HashMap<Integer,Carreau> carreaux = new HashMap<>();
	private ArrayList<Joueur> joueurs = new ArrayList<>();
        private ArrayList<Joueur> joueursTemp = new ArrayList<>();
	private Interface interface_2;

	public Monopoly(String dataFilename){
            this.jouerUnePartie(dataFilename);
	}

	private void buildGamePlateau(String dataFilename)
	{
		try{
			ArrayList<String[]> data = readDataFile(dataFilename, ",");
			Groupe groupe = new Groupe();
                        String couleur = "";
                        for(int i=0; i<data.size(); ++i){
				String caseType = data.get(i)[0];
				if(caseType.compareTo("P") == 0){
                                        if (!couleur.equals(data.get(i)[3])) {
                                             couleur = data.get(i)[3];
                                             groupe = new Groupe(Integer.valueOf(data.get(i)[11]),couleur);
                                        }
                                        TexteUI.message("Propriété :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1] + "\tCouleur : " + couleur);
                                        ArrayList<Integer> prix = new ArrayList<>();
                                        for (int j=5;j<11;j++) {
                                            prix.add(Integer.valueOf(data.get(i)[j]));
                                        }
                                        ProprieteAConstruire pAC = new ProprieteAConstruire(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[4]),prix,groupe,Integer.valueOf(data.get(i)[11]));
                                        this.addCarreau(pAC);
                                }
                                
				else if(caseType.compareTo("G") == 0){
					TexteUI.message("Gare :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        Gare gare = new Gare (Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[3]));
                                        this.addCarreau(gare);
                                }
				else if(caseType.compareTo("C") == 0){
					TexteUI.message("Compagnie :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        Compagnie comp = new Compagnie(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[3]));
                                        this.addCarreau(comp);
				}
                                else if(caseType.compareTo("CP") == 0){
					TexteUI.message("Case Prison :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        CarreauPrison cp = new CarreauPrison(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this);
                                        this.addCarreau(cp);
				}
				else if(caseType.compareTo("CT") == 0){
					TexteUI.message("Case Tirage :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        CarteType t;
                                        if (data.get(i)[2].equals("Chance")) {
                                            t = CarteType.chance;
                                        } else {
                                            t = CarteType.communautaire;
                                        }
                                        CarreauTirage cT = new CarreauTirage(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,t);
                                        this.addCarreau(cT);
				}
				else if(caseType.compareTo("CA") == 0){
					TexteUI.message("Case Argent :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        CarreauArgent ca = new CarreauArgent(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[3]));
                                        this.addCarreau(ca);
				}
				else if(caseType.compareTo("CM") == 0){
					TexteUI.message("Case Mouvement :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        CarreauMouvement cM = new CarreauMouvement(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this);
                                        this.addCarreau(cM);
				}
				else
					System.err.println("[buildGamePleateau()] : Invalid Data type");
			}
			
		} 
		catch(FileNotFoundException e){
			System.err.println("[buildGamePlateau()] : File is not found!");
		}
		catch(IOException e){
			System.err.println("[buildGamePlateau()] : Error while reading file!");
		}
	}
        
        private void addCarreau(Carreau c) {
            carreaux.put(c.getNum(), c);
        }
	
	private ArrayList<String[]> readDataFile(String filename, String token) throws FileNotFoundException, IOException
	{
		ArrayList<String[]> data = new ArrayList<String[]>();
		
		BufferedReader reader  = new BufferedReader(new FileReader("src/Data/"+filename));
		String line = null;
		while((line = reader.readLine()) != null){
			data.add(line.split(token));
		}
		reader.close();
		
		return data;
	}

        
        
        public void initialiserPartie() {
		boolean nJ = true;
                while (nJ) {
                    String nomJ = TexteUI.question("Nom du joueur : ");
                    Joueur j = new Joueur(nomJ, this);
                    joueursTemp.add(j);
                    TexteUI.message("Somme du premier lancé de dés : " + j.getSommeDesDepart());
                    String c = TexteUI.question("Voules-vous ajouter un nouveau joueur? (oui/non)");
                        if (!c.equals("oui")){
                            nJ = false;
                            trierListeJoueurs(joueursTemp);
                            /*for (int i = 0; i<joueursTemp.size(); i++) {
                                for (int x = 0; i<joueursTemp.size();x++) {
                                    if(x!=i) {
                                        
                                    }
                            }
                            }*/
                            TexteUI.message("Ordre des joueurs");
                            for (Joueur jou : joueursTemp) {
                                TexteUI.message(""+ jou.getNomJoueur());
                            }
                        }
                }
            joueurs = joueursTemp;
	}
        
        public void trierListeJoueurs(ArrayList<Joueur> listeJoueurs) {
            Collections.sort(listeJoueurs, new Comparator<Joueur>() {
                @Override
                public int compare(Joueur j1, Joueur j2) {
                return Integer.compare(j1.getSommeDesDepart(),j2.getSommeDesDepart());
                }   
            });
        }
        

	public void action(Joueur aJ) {
                aJ.getPositionCourante().action(aJ);
	}

        public void jouerUnePartie(String dataFilename) {
            cartes = new PaquetsCartes("cartes_"+dataFilename);
            this.buildGamePlateau(dataFilename);
            this.initialiserPartie();
            while (joueurs.size()>1) {
                for (Joueur j : joueurs) {
                    TexteUI.message("***************************************");
                    TexteUI.message("Au tour de " + j.getNomJoueur());
                    
                    this.jouerUnCoup(j,0);
                }
            }
        }

	/*public void achatPropriete(Joueur aJ, CarreauPropriete cP) {
		if (aJ.getCash()<cP.getPrix()){
                    TexteUI.message(aJ.getNomJoueur()+" n'a pas assez d'argent pour acheter la propriété");
                }else{
                    TexteUI.message("Le joueur "+aJ.getNomJoueur()+" a achété la propriété '"+cP.getNom()+"'");
                    cP.setProprietaire(aJ);
                    aJ.removeCash(cP.getPrix());
                    TexteUI.message(aJ.getNomJoueur()+"a désormais "+aJ.getCash()+" €");
                }
	}*/
        
        public void jouerUnCoup(Joueur aJ,int s) {
            TexteUI.message("Vous avez "+aJ.getCash()+"€");
            carreaux.get(2).action(aJ);
            carreaux.get(4).action(aJ);
            String rep = TexteUI.question("Que voulez-vous faire ? (avancer/construire/echanger)");
            while (!rep.equals("avancer")) {
                switch(rep) {
                    case "construire":
                    {
                        this.construire(aJ);
                    }
                    case "echanger":
                    {
                        //this.echanger(aJ);
                    }
                }
                rep = TexteUI.question("Que voulez-vous faire ? (avancer/construire/echanger)");
            }
            TexteUI.message("Vous avancer");
            boolean twice = true;
            int comp=0;
            while (twice) {
                if (aJ.getPrison()) {
                    this.action(aJ);
                    twice = false;
                } else {
                    twice = lancerDesAvancer(aJ,s);
                    if (twice) {
                      comp++;  
                    }
                    if(comp==3){
                        // Si le joueur fait 3 doubles d'affilé, il va en prison.
                        aJ.enPrison();
                        TexteUI.message("Le joueur "+aJ.getNomJoueur()+" a fait trois double de suite, il est envoyé en prison!");
                        twice = false;
                    }else{
                        this.action(aJ);
                    }
                    s = 0;
                }
            }
            
	}
              
	public boolean lancerDesAvancer(Joueur aJ,int s) {
            //s = 0 si le joueur sort de prison en faisant un double
            //TexteUI.message("Nom : "+aJ.getNomJoueur());
            int d1,d2,sD;
            if (s==0) {
            d1 = lancerDe();
            d2 = lancerDe();
            sD = d1+d2;
            TexteUI.message("D1 = " + d1 + " ; D2 = " + d2 + " ; Somme des dés = " + sD);
            } else {
                //d1 et d2 sont égaux pour return true
                d1 = 0;
                d2 = 0;
                sD = s;
            }
            // Donne des informations sur la somme des dés, la position actuelle du joueur et la position qu'il occupe après avoir avancé.
            TexteUI.message("Ancien Carreau : "+aJ.getPositionCourante().getNom());
            Avancer(aJ, sD);
            TexteUI.message("Carreau Actuel : "+aJ.getPositionCourante().getNom());
            // Donne les noms, positions, argent, propriétés de tous les joueurs de la partie.
            /*for (Joueur j : joueurs){
                TexteUI.message("Nom : "+j.getNomJoueur());
                TexteUI.message("Position : "+j.getPositionCourante());
                TexteUI.message("Argent : "+j.getCash());
                TexteUI.message("Propriété(s) :");
                for (Compagnie c : j.getCompagnies()){
                    TexteUI.message(c.getNom());
                }
                for (Gare g : j.getGares()){
                    TexteUI.message(g.getNom());
                }
                for (ProprieteAConstruire p : j.getProprietesAConstruire()){
                    TexteUI.message(p.getNom());
                    TexteUI.message("Groupe : "+p.getGroupe());
                    TexteUI.message("Avec "+p.getImmobilier()+" construction(s)");
                }
            }*/
            if (d1==d2){
                // Si le joueur effectue un double, retourne vrai, faux sinon.
                return true;
            }
            else{
                return false;
            }
	}

	public int lancerDe() {
            Random gene = new Random();               
            return gene.nextInt(6)+1;         
	}

	public void Avancer(Joueur aJ, int aNumCase) {
            aJ.setPositionCourante(aJ.getPositionCourante().getNum()+aNumCase);
	}
        
         public void construire(Joueur j) {
            TexteUI.message("Construction :");
            HashMap<CouleurPropriete,ArrayList<ProprieteAConstruire>> list = new HashMap<>();
            for (ProprieteAConstruire p : j.getProprietesAConstruire()) {
                if (!list.containsKey(p.getGroupe().getCouleur())) {
                    ArrayList<ProprieteAConstruire> sousList = new ArrayList<>();
                    list.put(p.getGroupe().getCouleur(),sousList);
                }
                list.get(p.getGroupe().getCouleur()).add(p);
            }
            for (CouleurPropriete c : list.keySet()) {
                if (list.get(c).size()!=list.get(c).get(0).getNbPropriete()) {
                    list.remove(c);
                }
            }
            
            if (list.isEmpty()) {
                TexteUI.message("Vous ne possédez aucun groupe de proprietes complet...");
            } else {
                TexteUI.message("Vous possédez les groupes :");
                for (CouleurPropriete c : list.keySet()) {
                    TexteUI.message(c.toString());
                }
                boolean ok = false;
                CouleurPropriete coul = CouleurPropriete.bleuCiel;
                do {
                    try {
                        coul = CouleurPropriete.valueOf(TexteUI.question("Sur quel groupe voulez vous construire ?"));
                        if (list.containsKey(coul)) {
                            ok = true;
                        } else {
                            TexteUI.message("Cette couleur ne fais pas partie de la liste");
                        }
                    } catch(java.lang.IllegalArgumentException e) {
                        TexteUI.message("Erreur, recommencez. (Sensible à la case)");
                    }
                } while (!ok);
                        ArrayList<ProprieteAConstruire> listP = list.get(coul);
                        boolean stop = false;
                        while (!stop) {
                            int max = listP.get(0).getImmobilier();
                            boolean onAChanger = false;
                            for (ProprieteAConstruire p : listP) {
                                if (p.getImmobilier()!=max) {
                                    onAChanger = true;
                                    if (p.getImmobilier()>max) {
                                        max = p.getImmobilier();
                                    }
                                }
                            }
                            if (!onAChanger && max==5) {
                                stop=true;
                                TexteUI.message("Tous ces terrains on déjà des hotels, impossible de construire dessus");
                            } else {
                                HashMap<Integer,ProprieteAConstruire> listPPotentiel = new HashMap<>();
                                if (!onAChanger) {
                                    max++;
                                }
                                TexteUI.message("Vous pouvez construire sur :");
                                for (ProprieteAConstruire p : listP) {
                                        if (p.getImmobilier()<max) {
                                        listPPotentiel.put(p.getNum(), p);
                                        TexteUI.message(p.getNom() + " ; Construction existante : "+ p.getImmobilierString() + " ; n°" + p.getNum());
                                        }
                                }
                                String num = TexteUI.question("Sur quel numéro voulez construire ?");
                                listPPotentiel.get(Integer.valueOf(num)).construire();
                                if (TexteUI.question("Voulez-vous construire une autre maison/hotel ? (oui/non)").equals("non")) {
                                    stop = true;
                                }
                            }
                        }
                    }
        }
        
        public PaquetsCartes getPaquetsCartes() {
            return cartes;
        }
        
	public int getPosition(Joueur aJ) {
                return aJ.getPositionCourante().getNum();
	}
        
        public Carreau getCarreau (int c){
            return this.carreaux.get(c);
        }
        
        public void retourCarte(Carte c){
            cartes.retourCarte(c);
        }
    /**
     * @return the nbMaisons
     */
    public int getNbMaisons() {
        return nbMaisons;
    }

    /**
     * @param nbMaisons the nbMaisons to set
     */
    public void setNbMaisons(int nbMaisons) {
        this.nbMaisons = nbMaisons;
    }

    /**
     * @return the nbHotels
     */
    public int getNbHotels() {
        return nbHotels;
    }

    /**
     * @param nbHotels the nbHotels to set
     */
    public void setNbHotels(int nbHotels) {
        this.nbHotels = nbHotels;
    }

    /**
     * @return the carreaux
     */
    public HashMap<Integer,Carreau> getCarreaux() {
        return carreaux;
    }

    /**
     * @param carreaux the carreaux to set
     */
    public void setCarreaux(HashMap<Integer,Carreau> carreaux) {
        this.carreaux = carreaux;
    }

    /**
     * @return the joueurs
     */
    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    /**
     * @param joueurs the joueurs to set
     */
    public void setJoueurs(ArrayList<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    /**
     * @return the interface_2
     */
    public Interface getInterface_2() {
        return interface_2;
    }

    /**
     * @param interface_2 the interface_2 to set
     */
    public void setInterface_2(Interface interface_2) {
        this.interface_2 = interface_2;
    }

    public PaquetsCartes getCartes() {
        return cartes;
    }
    
}
