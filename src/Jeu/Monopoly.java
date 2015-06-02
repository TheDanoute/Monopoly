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
	private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
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
                    joueurs.add(j);
                    TexteUI.message("Somme du premier lancé de dés : " + j.getSommeDesDepart());
                    String c = TexteUI.question("Voules-vous ajouter un nouveau joueur? (oui/non)");
                        if (!c.equals("oui")){
                            nJ = false;
                            Collections.sort(joueurs, new Comparator<Joueur>() {
                                @Override
                                public int compare(Joueur j1, Joueur j2) {
                                return Integer.compare(j1.getSommeDesDepart(),j2.getSommeDesDepart());
                            }
                            
                        });
                            for (int i =0; i<joueurs.size();i++) {
                                TexteUI.message("Somme dés" + joueurs.get(i).getSommeDesDepart());
                            }
                            /*for (int i =0; i<joueurs.size();i++){
                                if(joueurs.get(i).getSommeDesDepart()==joueurs.get(i+1).getSommeDesDepart()){
                                    joueurs.get(i).getMonopoly().lancerDe();
                                    joueurs.get(i+1).getMonopoly().lancerDe();}
                            }*/
                        }
                }
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
            TexteUI.question("Appuyer sur enter pour continuer");
            TexteUI.message("Début du tour :");
            
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
