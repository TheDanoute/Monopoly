package Jeu;

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
	private HashMap<Integer,Carreau> carreaux = new HashMap<>();
	private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
	private Interface interface_2;

	public Monopoly(String dataFilename){
		buildGamePlateau(dataFilename);
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
					System.out.println("Propriété :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
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
					System.out.println("Gare :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        Gare gare = new Gare (Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[3]));
                                        this.addCarreau(gare);
                                }
				else if(caseType.compareTo("C") == 0){
					System.out.println("Compagnie :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        Compagnie comp = new Compagnie(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[3]));
                                        this.addCarreau(comp);
				}
				else if(caseType.compareTo("CT") == 0){
					System.out.println("Case Tirage :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
				}
				else if(caseType.compareTo("CA") == 0){
					System.out.println("Case Argent :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        CarreauArgent ca = new CarreauArgent(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[3]));
                                        this.addCarreau(ca);
				}
				else if(caseType.compareTo("CM") == 0){
					System.out.println("Case Mouvement :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
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
		
		BufferedReader reader  = new BufferedReader(new FileReader(filename));
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
                    Scanner sa = new Scanner(System.in);
                    System.out.println("Nom du joueur : ");
                    String nomJ = sa.nextLine();
                    Joueur j = new Joueur(nomJ, this);
                    getJoueurs().add(j);
                    System.out.println("Voules-vous ajouter un nouveau joueur? (oui/non)");
                    String c = sa.nextLine();
                    boolean r = true;
                    while (r){
                        r=false;
                        if (c=="non"){
                            nJ = false;
                        }else if (c!="oui"){
                            System.out.println("Réponse incorrecte");
                            r=true;
                        }
                    }
                }
                Collections.shuffle(getJoueurs());
	}

	public void action(Joueur aJ) {
		throw new UnsupportedOperationException();
	}

	public void jouerUnCoup(Joueur aJ) {
		throw new UnsupportedOperationException();
	}

	public void achatPropriete(Joueur aJ, CarreauPropriete cP) {
		if (aJ.getCash()<cP.getPrix()){
                    System.out.println(aJ.getNomJoueur()+" n'a pas assez d'argent pour acheter la propriété");
                }else{
                    System.out.println("Le joueur "+aJ.getNomJoueur()+" a achété la propriété '"+cP.getNom()+"'");
                    cP.setProprietaire(aJ);
                    aJ.removeCash(cP.getPrix());
                    System.out.println(aJ.getNomJoueur()+"a désormais "+aJ.getCash()+" €");
                }
	}

	public void lancerDesAvancer(Joueur aJ) {
            System.out.println("Nom : "+aJ.getNomJoueur());
            boolean twice = true;
            int i = 0;
            while (twice) {
                int d1 = lancerDe();
                int d2 = lancerDe();
                int sD = d1+d2;
                if (d1!=d2){
                    twice = false;
                }else{
                    i++;
                }
                if (i==3){
                    aJ.enPrison();
                    System.out.println("Le joueur "+aJ.getNomJoueur()+" est envoyé en prison!");
                }else{
                    System.out.println("Somme des dés : "+sD);
                    System.out.println("Carreau actuel : "+aJ.getPositionCourante().getNom());
                    Avancer(aJ, sD);
                    System.out.println("Nouveau carreau : "+aJ.getPositionCourante().getNom());
                    for (Joueur j : joueurs){
                        System.out.println("Nom : "+j.getNomJoueur());
                        System.out.println("Position : "+j.getPositionCourante());
                        System.out.println("Argent : "+j.getCash());
                        System.out.println("Propriété(s) :");
                        for (Compagnie c : j.getCompagnies()){
                            System.out.println(c.getNom());
                        }
                        for (Gare g : j.getGares()){
                            System.out.println(g.getNom());
                        }
                        for (ProprieteAConstruire p : j.getProprietesAConstruire()){
                            System.out.println(p.getNom());
                            System.out.println("Groupe : "+p.getGroupe());
                            System.out.println("Avec "+p.getImmobilier()+" construction(s)");
                        }
                    }
                }
               
            }
            
	}

	public int lancerDe() {
            Random gene = new Random();               
            return gene.nextInt(6)+1;         
	}

	public void Avancer(Joueur aJ, int aNumCase) {
            int position = aJ.getPositionCourante().getNum()+aNumCase;
            aJ.setPositionCourante(getCarreau(position));
	}

	public int getPosition(Joueur aJ) {
                return aJ.getPositionCourante().getNum();
	}
        
        public Carreau getCarreau (int c){
            return this.carreaux.get(c);
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
}