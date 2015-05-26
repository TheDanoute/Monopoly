package Jeu;

import Ui.TexteUI;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
		this.buildGamePlateau(dataFilename);
                cartes = new PaquetsCartes("carte_"+dataFilename);
	}

	private void buildGamePlateau(String dataFilename)
	{
		try{
			ArrayList<String[]> data = readDataFile(dataFilename, ",");
			
			//TODO: create cases instead of displaying
			Groupe groupe = new Groupe();
                        String couleur = null;
                        for(int i=0; i<data.size(); ++i){
				String caseType = data.get(i)[0];
				if(caseType.compareTo("P") == 0){
					TexteUI.message("Propriété :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        if (!couleur.equals(data.get(i)[3])) {
                                             couleur = data.get(i)[3];
                                             groupe = new Groupe(Integer.valueOf(data.get(i)[11]),couleur);
                                        }
                                        ArrayList<Integer> prix = new ArrayList<>();
                                        for (int j=5;j<11;j++) {
                                            prix.add(Integer.valueOf(data.get(i)[j]));
                                        }
                                        ProprieteAConstruire pAC = new ProprieteAConstruire(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[4]),prix,groupe);
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
				else if(caseType.compareTo("CT") == 0){
					TexteUI.message("Case Tirage :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
				}
				else if(caseType.compareTo("CA") == 0){
					TexteUI.message("Case Argent :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        CarreauArgent ca = new CarreauArgent(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[3]));
                                        this.addCarreau(ca);
				}
				else if(caseType.compareTo("CM") == 0){
					TexteUI.message("Case Mouvement :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
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
		
		BufferedReader reader  = new BufferedReader(new FileReader("../../Data/"+filename));
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
                    getJoueurs().add(j);
                    String c = TexteUI.question("Voules-vous ajouter un nouveau joueur? (oui/non)");
                    boolean r = true;
                    while (r){
                        r=false;
                        if (c.equals("non")){
                            nJ = false;
                        }else if (c.equals("oui")){
                            r=true;
                        }else {
                            TexteUI.message("Réponse incorrecte");
                        }
                    }
                }
                Collections.shuffle(getJoueurs());
	}

	public void action(Joueur aJ) {
                aJ.getPositionCourante().action(aJ);
	}

	

	public void achatPropriete(Joueur aJ, CarreauPropriete cP) {
		if (aJ.getCash()<cP.getPrix()){
                    TexteUI.message(aJ.getNomJoueur()+" n'a pas assez d'argent pour acheter la propriété");
                }else{
                    TexteUI.message("Le joueur "+aJ.getNomJoueur()+" a achété la propriété '"+cP.getNom()+"'");
                    cP.setProprietaire(aJ);
                    aJ.removeCash(cP.getPrix());
                    TexteUI.message(aJ.getNomJoueur()+"a désormais "+aJ.getCash()+" €");
                }
	}
        
        public void jouerUnCoup(Joueur aJ) {
            boolean twice = true;
            int i = 0;
            while (twice) {
                twice = lancerDesAvancer(aJ);
                i++;
                if(i==4){
                    aJ.enPrison();
                    TexteUI.message("Le joueur "+aJ.getNomJoueur()+" est envoyé en prison!");
                }else{
                    this.action(aJ);
                }
            }
	}
              
	public boolean lancerDesAvancer(Joueur aJ) {
            TexteUI.message("Nom : "+aJ.getNomJoueur());
            int d1 = lancerDe();
            int d2 = lancerDe();
            int sD = d1+d2;
            TexteUI.message("Somme des dés : "+sD);
            TexteUI.message("Carreau actuel : "+aJ.getPositionCourante().getNom());
            Avancer(aJ, sD);
            TexteUI.message("Nouveau carreau : "+aJ.getPositionCourante().getNom());
            for (Joueur j : joueurs){
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
            }
            if (d1==d2){
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
            aJ.setPositionCourante(getCarreau(aJ.getPositionCourante().getNum()+aNumCase));
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
