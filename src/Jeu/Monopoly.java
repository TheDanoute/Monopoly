package Jeu;

import Ui.GraphUI;
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
                                        ArrayList<Integer> prix = new ArrayList<>(); //Création de la liste de prix de loyer
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
                    TexteUI.message("Premier lancé de dés : " + j.getDesDepart());
                    String c = TexteUI.question("Voules-vous ajouter un nouveau joueur? (oui/non)");
                        if (!c.equals("oui")){
                            nJ = false;
                            
                            joueurs = trieRecursif(joueurs); //Méthode recursive
                            
                            TexteUI.message("Ordre des joueurs");
                            for (Joueur jou : joueurs) {
                                TexteUI.message(""+ jou.getNomJoueur());
                            }
                        }
                }
	}
        
        private ArrayList<Joueur> trieRecursif(ArrayList<Joueur> list) {
            trierListeJoueurs(list);
            int compteur = 0; //Compteur qui bloque la première itération identique
            int i = 0;
            ArrayList<Joueur> listTemp = new ArrayList<>();//Liste des joueurs ayant obtenu le même score
            while(i<list.size()-1) {
                if (list.get(compteur).getDesDepart()!=list.get(i+1).getDesDepart()) {
                    if (i-compteur<1) { //Le compteur augmente si il n'y a pas eu d'itération identique précédemment
                        compteur=i+1;
                    } else {
                        for (int j = compteur;j<=i;j++){ //Boucle des joueurs ayant obtenu le même score
                            int value = lancerDe(); 
                            TexteUI.message("Etant arriver ex aequo, " + list.get(j).getNomJoueur() + " relance le dé et obtient : " + value);
                            list.get(j).setDesDepart(value);
                            listTemp.add(list.get(j));
                        }
                        for (Joueur jou : trieRecursif(listTemp)) { //Tri de la liste temporaire par la même méthode
                            list.set(compteur, jou); //Remplissage pas à pas depuis la première itération (gelé par le compteur)
                            compteur++;         
                        }
                        listTemp.clear();
                    }
                } else {
                }
                i++;
            }
            if (i-compteur>=1) { //Test si la fin de la liste contient des valeurs identiques
                for (int j = compteur;j<=i;j++){
                    int value = lancerDe();
                    TexteUI.message("Etant arriver ex aequo, " + list.get(j).getNomJoueur() + " relance le dé et obtient : " + value);
                    list.get(j).setDesDepart(value);
                    listTemp.add(list.get(j));
                }
                for (Joueur jou : trieRecursif(listTemp)) {
                    list.set(compteur, jou);
                    compteur++;
                }
            }
            return list; //retourne le nouvel ordre de joueurs
        }
                     
        public void trierListeJoueurs(ArrayList<Joueur> listeJoueurs) { //Méthode permetant de trier une liste de joueur par ordre décroissant de leur résultat du lancé de dé
            Collections.sort(listeJoueurs, new Comparator<Joueur>() {
                @Override
                public int compare(Joueur j1, Joueur j2) {
                return Integer.compare(j2.getDesDepart(),j1.getDesDepart());
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
            int i = 0;
            while (joueurs.size()>1) {
                Joueur j = joueurs.get(i);
                    TexteUI.message("***************************************");
                    TexteUI.message("Au tour de " + j.getNomJoueur());
                    
                    this.jouerUnCoup(j,0);
                    i++;
                if (i>=joueurs.size()) {
                    i=0;
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
            TexteUI.message("Vos propriété(s) :");
            for (Compagnie c : aJ.getCompagnies()){
                TexteUI.message("Propriété : "+c.getNom());

            }
            for (Gare g : aJ.getGares()){
                TexteUI.message("Propriété : "+g.getNom());

            }
            for (ProprieteAConstruire p : aJ.getProprietesAConstruire()){
                TexteUI.message("Propriété : "+p.getNom());
                TexteUI.message("Groupe : "+p.getGroupe().getCouleur().toString());
                TexteUI.message("Avec "+p.getImmobilier()+" construction(s)");
            }       
            String rep = TexteUI.question("Que voulez-vous faire ? (avancer/construire/echanger/detruire/hypotheque)");
            while (!rep.equals("avancer")) {
                switch(rep) {
                    case "construire":
                    {
                        this.construire(aJ);
                        break;
                    }
                    case "echanger":
                    {
                        this.echanger(aJ);
                        break;
                    }
                    case "detruire":
                    {
                        this.detruire(aJ);
                        break;
                    }
                    case "hypotheque":
                    {
                        this.hypotheque(aJ);
                        break;
                    }
                    default:
                    {
                        TexteUI.message("Erreur");
                        break;
                    }
                }
                rep = TexteUI.question("Que voulez-vous faire ? (avancer/construire/echanger/detruire/hypotheque)");
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
                        twice=false;
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
            HashMap<CouleurPropriete,ArrayList<ProprieteAConstruire>> list = new HashMap<>(); //Liste par groupe de couleur de toutes les proprietes du joueur
            for (ProprieteAConstruire p : j.getProprietesAConstruire()) {
                if (!list.containsKey(p.getGroupe().getCouleur())) { //Création de la nouvel liste (dans la hashmap) pour un nouveau groupe
                    ArrayList<ProprieteAConstruire> sousList = new ArrayList<>();
                    list.put(p.getGroupe().getCouleur(),sousList); //Nouvelle valeur de hashMap
                }
                list.get(p.getGroupe().getCouleur()).add(p); //Nouvelle valeur de l'arrayList de la hashMap
            }
            for (CouleurPropriete c : list.keySet()) { //Boucle de vérification pour les groupes complets disponibles à la construction
                if (list.get(c).size()!=list.get(c).get(0).getNbPropriete()) {
                    list.remove(c);
                }
            }
            
            if (list.isEmpty()) {
                TexteUI.message("Vous ne possédez aucun groupe de proprietes complet..."); //fin
            } else {
                TexteUI.message("Vous possédez les groupes :");
                for (CouleurPropriete c : list.keySet()) {
                    TexteUI.message(c.toString());
                }
                boolean ok = false; //Boolean anti-faute de frappe
                CouleurPropriete coul = CouleurPropriete.bleuCiel; //Initialisation de variable
                do {
                    try {
                        coul = CouleurPropriete.valueOf(TexteUI.question("Sur quel groupe voulez vous construire ?"));
                        if (list.containsKey(coul)) {
                            ok = true;
                        } else {
                            TexteUI.message("Cette couleur ne fais pas partie de la liste");
                        }
                    } catch(java.lang.IllegalArgumentException e) {
                        TexteUI.message("Erreur, recommencez. (Sensible à la casse)");
                    }
                } while (!ok);
                        ArrayList<ProprieteAConstruire> listP = list.get(coul); //Récupération des proprietes du groupe selectionné
                        boolean stop = false; //Boolean de construction rapide dans sur les mêmes propriétés
                        for (ProprieteAConstruire p : listP) {
                            if (p.isHypotheque()) {
                                TexteUI.message("Au moins une des propriétés de ce groupe est hypothéquée, impossible de construire");
                                stop = false;
                            }
                        }
                        while (!stop) {
                            int max = listP.get(0).getImmobilier(); //Max sera la valeur immobilère max des terrains (0 à 5)
                            boolean onAChanger = false; //Restera false si les terrains ont le même nombre d'immobilier
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
                                TexteUI.message("Tous ces terrains on déjà des hotels, impossible de construire dessus"); //fin
                            } else {
                                HashMap<Integer,ProprieteAConstruire> listPPotentiel = new HashMap<>(); //Liste contenant les proprietés constructibles
                                if (!onAChanger) {
                                    max++; //Tous les terrains ont la même valeur immobilière mais pas encore d'hotel
                                }
                                TexteUI.message("Vous pouvez construire sur :");
                                for (ProprieteAConstruire p : listP) {
                                        if (p.getImmobilier()<max) {
                                        listPPotentiel.put(p.getNum(), p); //Remplissage de la liste
                                        TexteUI.message(p.getNom() + " ; Construction existante : "+ p.getImmobilierString() + " ; n°" + p.getNum());
                                        }
                                }
                                if (max>4 && this.getNbHotels()<1) { //Vérification de maisons et hotels restant à la banque
                                    TexteUI.message("La banque n'a malheuresement plus d'hotel en stock, vous ne pouvez pas construire sur ces terrains...");
                                    stop = true;
                                } else if (max<5 && this.getNbMaisons()<1) {
                                    TexteUI.message("La banque n'a malheuresement plus de maison en stock, vous ne pouvez pas construire sur ces terrains...");
                                    stop = true;
                                } else { //Choix du terrain
                                    String num = TexteUI.question("Sur quel numéro voulez construire ? (numéro)");
                                    try {
                                        listPPotentiel.get(Integer.valueOf(num)).construire();
                                    } catch(NullPointerException e) {
                                        TexteUI.message("Ce terrain n'est pas constructible pour le moment");
                                    }
                                    if (TexteUI.question("Voulez-vous construire une autre maison/hotel ? (oui/non)").equals("non")) { //Boucle de reconstruction rapide
                                        stop = true;
                                    }
                                }
                            }
                        }
                    }
        }
         
         public void detruire(Joueur j) {
             this.getCarreau(2).action(j);
             this.getCarreau(4).action(j);
             HashMap<CouleurPropriete,ArrayList<ProprieteAConstruire>> listP = new HashMap<>();
             TexteUI.message("Liste des groupe avec des construction(s) :");
             ArrayList<ProprieteAConstruire> pros;
             for (CouleurPropriete c : CouleurPropriete.values()) {
                 pros = j.getProprietesAConstruire(c);
                 if (!pros.isEmpty() && pros.get(0).getNbPropriete()==pros.size()) {
                    int immo = 0;
                     for(ProprieteAConstruire p : pros) {
                        immo+=p.getImmobilier();
                    }
                    if (immo>0) {
                        TexteUI.message(pros.get(0).getCouleur().toString());
                        listP.put(pros.get(0).getCouleur(), pros);
                    } 
                 }
             }
             if (listP.isEmpty()) {
                 TexteUI.message("Aucun de vos groupes de propriétés ne contiennent des constructions");
             } else {
                boolean ok = false; //Boolean anti-faute de frappe
                   CouleurPropriete coul = CouleurPropriete.bleuCiel; //Initialisation de variable
                   do {
                       try {
                           coul = CouleurPropriete.valueOf(TexteUI.question("Sur quel groupe voulez vous détruire ?"));
                           if (listP.containsKey(coul)) {
                               ok = true;
                           } else {
                               TexteUI.message("Cette couleur ne fais pas partie de la liste");
                           }
                       } catch(java.lang.IllegalArgumentException e) {
                           TexteUI.message("Erreur, recommencez. (Sensible à la casse)");
                       }
                   } while (!ok);
               pros = listP.get(coul);
               boolean stop = false;
               while (!stop) {
                   int min = pros.get(0).getImmobilier(); 
                   boolean onAChanger = false; 
                   for (ProprieteAConstruire p : pros) {
                        if (p.getImmobilier()!=min) {
                            onAChanger = true;
                            if (p.getImmobilier()<min) {
                                min = p.getImmobilier();
                            }
                       }
                   }
                   if (!onAChanger) {
                       min--;
                   }
                   HashMap<Integer,ProprieteAConstruire> listPPotentiel = new HashMap<>();
                   TexteUI.message("Vous pouvez détruire sur les proprietes suivantes : (attention les hotels ne rendront pas des maisons)");
                   for (ProprieteAConstruire p : pros) {
                       if (p.getImmobilier()>min) {
                           listPPotentiel.put(p.getNum(), p);
                           TexteUI.message(p.getDescription());
                       }
                   }
                   if (min<0) {
                       TexteUI.message("Les terrains de ce groupe sont tous vides, vous ne pouvez plus rien détruire");
                       stop=true;
                   } else {
                       try {
                            listPPotentiel.get(Integer.valueOf(TexteUI.question("Sur quelle propriete voulez vous détruire ? (numéro)"))).detruire();
                            TexteUI.message("Vous avez maintenant : " + j.getCash() + "€");
                       } catch(NullPointerException e){
                           TexteUI.message("Ce terrain n'est pas destructible pour le moment");
                       }
                       if (TexteUI.question("Voulez-vous detruire une autre maison/hotel ? (oui/non)").equals("non")) {
                           stop = true;
                       }
                   }
               }
             }
         }
        
        public void hypotheque(Joueur j){
            String rep = TexteUI.question("Voulez-vous lever une hypotheque ou hypothequer ? (lever/hypotheque)");
            switch(rep) {
                case "lever":
                {
                    HashMap<Integer,CarreauPropriete> list = new HashMap<>();
                    TexteUI.message("Liste des proprietes hypothéquées :");
                    for (ProprieteAConstruire p : j.getProprietesAConstruire()) {
                        if (p.isHypotheque()) {
                            list.put(p.getNum(), p);
                            TexteUI.message(p.getDescription());
                        }
                    }
                    for (Gare g : j.getGares()) {
                        if (g.isHypotheque()) {
                            list.put(g.getNum(), g);
                            TexteUI.message(g.getDescription());
                        }
                    }
                    for (Compagnie c : j.getCompagnies()) {
                        if (c.isHypotheque()) {
                            list.put(c.getNum(), c);
                            TexteUI.message(c.getDescription());
                        }
                    }
                    if (list.isEmpty()){
                        TexteUI.message("Vous n'avez aucune propriété hypothequée");
                    } else {
                        CarreauPropriete c = list.get(Integer.valueOf(TexteUI.question("Sur quelle propriété voulez-vous lever l'hypotheque ? (numéro)")));
                        TexteUI.message("Lever l'hypothèque de cette propriété coute : " + c.getPrixHypotheque() + "€");
                        if (j.getCash()<c.getPrixHypotheque()) {
                            TexteUI.message("Vous n'avez pas assez d'argent pour lever l'hypothèque");
                        } else {
                            if (TexteUI.question("Voulez-vous continuer ? (oui/non)").equals("oui")) {
                                c.leverHypotheque();
                            }
                        }
                    }
                    break;
                }
                case "hypotheque":
                {
                    HashMap<Integer,CarreauPropriete> list = new HashMap<>();
                    TexteUI.message("Liste des proprietes disponible à l'hypotheque :");
                    for (CouleurPropriete c : CouleurPropriete.values()) {
                        ArrayList<ProprieteAConstruire> pros = j.getProprietesAConstruire(c);
                        boolean ok = true;
                        if(!pros.isEmpty()) {
                            if (pros.size()==pros.get(0).getNbPropriete()) {
                                int nb = 0;
                                for (ProprieteAConstruire p : pros) {
                                    nb+=p.getImmobilier();
                                }
                                if (nb>0) {
                                    ok = false;
                                }
                            }
                            if (ok) {
                                for (ProprieteAConstruire p : pros) {
                                    if (!p.isHypotheque()){
                                        list.put(p.getNum(), p);
                                    }
                                }
                            }
                        }    
                    }
                    for (Gare g : j.getGares()) {
                        if (!g.isHypotheque()) {
                            list.put(g.getNum(), g);
                            TexteUI.message(g.getDescription());
                        }
                    }
                    for (Compagnie c : j.getCompagnies()) {
                        if (!c.isHypotheque()) {
                            list.put(c.getNum(), c);
                            TexteUI.message(c.getDescription());
                        }
                    }
                    if (list.isEmpty()){
                        TexteUI.message("Vous n'avez aucune propriété disponible à l'hypotheque");
                    } else {
                        CarreauPropriete c = list.get(Integer.valueOf(TexteUI.question("Quelle propriété voulez-vous hypothequer ? (numéro)")));
                        TexteUI.message("L'hypotheque vous rapporte" + c.getPrix()/2 + "€");
                            if (TexteUI.question("Voulez-vous continuer ? (oui/non)").equals("oui")) {
                                c.hypotheqer();
                            }
                        }
                    }
                }
            }
         
       

         public void echanger(Joueur j1) {
             boolean toutVendu = true;
             int j = 0;
             for (CarreauPropriete cp : this.getCarreauxPropriete()){
                 if (cp.getProprietaire()==null){
                     toutVendu=false;
                 }
             }
            if (toutVendu){
                TexteUI.message("Echange :"); 
                Joueur j2;
                {
                   ArrayList<Joueur> listJoueur = new ArrayList<>();                  // Création d'une liste de joueur ayant des biens à échanger
                   int i = 1;
                   for (Joueur jTemp : joueurs) {
                       if (j1!=jTemp) {
                               // Affichage des propriétés / carte sortie de prison de chaque joueur excepté celui demandant l'échange
                           TexteUI.message("Joueur n°" + i + " : " + jTemp.getNomJoueur());
                           TexteUI.message("Ses propriété(s) :");
                           for (Compagnie c : jTemp.getCompagnies()){
                               TexteUI.message(c.getNom());
                           }
                           for (Gare g : jTemp.getGares()){
                               TexteUI.message(g.getNom());
                           }
                           for (ProprieteAConstruire p : jTemp.getProprietesAConstruire()){
                               TexteUI.message(p.getNom());
                               TexteUI.message("Groupe : "+p.getGroupe());
                               TexteUI.message("Avec "+p.getImmobilier()+" construction(s)");
                           }
                           TexteUI.message("Nombre de carte(s) sortie de prison : "+jTemp.getNBCartePrison());
                           listJoueur.add(jTemp);
                       }
                   }
                   j2 = listJoueur.get(Integer.valueOf(TexteUI.question("Avec quel joueur voulez-vous échanger ? (numéro)"))-1);
                }
                String rep = TexteUI.question("Que voulez-vous de " + j2.getNomJoueur() + " ? (propriete / carte)");  // On récupère le type de l'échange
                switch (rep) {
                                       // Si c'est une propriété
                        case "propriete":
                        {
                                   // La classe échange permet de sauvegarder ce que chaque joueur met dans l'échange (propriétés/argant)
                           Echange echangeJ2 = new Echange(j2);
                           boolean stop = false;
                           boolean stop2 = false;
                           boolean noTrade = false;
                           HashMap<Integer,CarreauPropriete> listP2= new HashMap<>();    // HashMap reliant chaque propriétés disponibles à l'échange à un indice
                           while (!stop){                                                // Tant que l'on souhaite ajouter une propriété à l'échange on reste dans la boucle
                               TexteUI.message("Ses propriété(s) :");
                               int i = 1;
                                       // Affichages des propriétés + intégration dans listP2 avec un indice
                               for (Compagnie c : j2.getCompagnies()){
                                   if (!echangeJ2.getListP().contains(c)){
                                       TexteUI.message("Propriété n°"+i+" : "+c.getNom());
                                       listP2.put(i, c);
                                       i++;
                                   }
                               }
                               for (Gare g : j2.getGares()){
                                   if (!echangeJ2.getListP().contains(g)){
                                       TexteUI.message("Propriété n°"+i+" : "+g.getNom());
                                       listP2.put(i, g);
                                       i++;
                                   }
                               } 
                               for (CouleurPropriete c : CouleurPropriete.values()) {
                                   if (!j2.getProprietesAConstruire(c).isEmpty()) {
                                       int immo = 0;
                                       for (ProprieteAConstruire p : j2.getProprietesAConstruire(c)) {
                                           immo+=p.getImmobilier();
                                       }
                                       if (immo==0) {
                                           for (ProprieteAConstruire p : j2.getProprietesAConstruire(c)) {
                                               if (!echangeJ2.getListP().contains(p)){
                                                   TexteUI.message("Propriété n°"+i+" : "+p.getNom());
                                                   TexteUI.message("Groupe : "+p.getGroupe());
                                                   listP2.put(i,p);
                                                   i++; 
                                               } 
                                           }
                                       }
                                   }
                               }
                               if (!listP2.isEmpty()){
                                   int num = Integer.valueOf(TexteUI.question("Quelle propriete voulez-vous ? (numéro)"));     // Grâce à l'indice, on récupère la propriété souhaitée
                                   echangeJ2.addP(listP2.get(num));                                                            // On l'ajoute à la classe échange
                                   listP2.remove(num);                                                                         // Puis on l'a supprime des propriétés disponibles à l'échange    
                                   String rep2 = TexteUI.question("Voulez-vous une autre propriété de ce joueur ? (oui/non)");
                                   if (!rep2.equals("oui")){
                                       rep2=TexteUI.question("Voulez-vous qu'il rajoute de l'argent ? (oui/non)");
                                       if(rep2.equals("oui")){                         // Possibilité d'ajouter de l'argent à l'échange
                                           String arg =TexteUI.question("Somme :");
                                           int somme= Integer.parseInt(arg);
                                           echangeJ2.setSomme(somme);
                                       }
                                       stop=true;   
                                   }
                               }else{
                                           // Si le joueur choisis n'a pas de propriété échangeable, un message ets affiché et aucun échange ne se fait
                                   TexteUI.message(j2.getNomJoueur()+" n'a pas de propriété échangeable");
                                   noTrade=true;
                                   stop=true;
                               }

                           }

                                   // Après avoir renseigné ce qu'il veut, le joueur indique ce qu'il propose en échange, avec le même procédé

                           if (!noTrade){
                           Echange echangeJ1 = new Echange(j1);
                           HashMap<Integer,CarreauPropriete> listP1= new HashMap<>();
                           while (!stop2){
                               TexteUI.message("Vos propriété(s) :");
                               int i = 1;
                               for (Compagnie c : j1.getCompagnies()){
                                   if (!echangeJ1.getListP().contains(c)){
                                       TexteUI.message("Propriété n°"+i+" : "+c.getNom());
                                       listP1.put(i, c);
                                       i++;
                                   }
                               }
                               for (Gare g : j1.getGares()){
                                   if (!echangeJ1.getListP().contains(g)){
                                       TexteUI.message("Propriété n°"+i+" : "+g.getNom());
                                       listP1.put(i, g);
                                       i++;
                                   }
                               }
                               for (CouleurPropriete c : CouleurPropriete.values()) {
                                   if (!j1.getProprietesAConstruire(c).isEmpty()) {
                                       int immo = 0;
                                       for (ProprieteAConstruire p : j1.getProprietesAConstruire(c)) {
                                           immo+=p.getImmobilier();
                                       }
                                       if (immo==0) {
                                           for (ProprieteAConstruire p : j1.getProprietesAConstruire(c)) {
                                               if (!echangeJ1.getListP().contains(p)){
                                                   TexteUI.message("Propriété n°"+i+" : "+p.getNom());
                                                   TexteUI.message("Groupe : "+p.getGroupe());
                                                   listP1.put(i,p);
                                                   i++;
                                               }
                                           }
                                       }
                                   }
                               }
                               if (!listP1.isEmpty()){
                                   int num = Integer.valueOf(TexteUI.question("Quelle propriete proposez-vous ? (numéro)"));
                                   echangeJ1.addP(listP1.get(num));
                                   listP1.remove(num);
                                   String rep2 = TexteUI.question("Voulez-vous proposer une autre propriété ? (oui/non)");
                                   if (!rep2.equals("oui")){

                                       stop2=true;

                                   }
                               }else{
                                   TexteUI.message(j1.getNomJoueur()+" n'a pas de propriété échangeable");
                                   noTrade=true;
                                   stop2=true;
                               }
                           }
                                   String rep2=TexteUI.question("Voulez-vous ajouter de l'argent ? (oui/non)");
                                   if(rep2.equals("oui")){
                                           String arg =TexteUI.question("Somme :");
                                           int somme= Integer.parseInt(arg);
                                           echangeJ1.setSomme(somme);
                                       }

                               if (noTrade && echangeJ1.getSomme()==0){                                            // Dans ce cas (pas de carté ni d'argent, échange refusé
                                   TexteUI.message("Pas d'échange possible, vous n'avez rien proposé.");
                               }else{
                                          // Sinon, on affiche tout ce que le joueur demande et propose
                                   TexteUI.message(j2.getNomJoueur()+", "+j1.getNomJoueur()+" vous a proposé un échange.\nIl vous demande :");
                                   for (CarreauPropriete cp : echangeJ2.getListP()){
                                       TexteUI.message(cp.getNom());
                                   }
                                   TexteUI.message(String.valueOf(echangeJ2.getSomme())+"€");
                                   TexteUI.message("Et il vous propose :");
                                   for (CarreauPropriete cp : echangeJ1.getListP()){
                                       TexteUI.message(cp.getNom());
                                   }
                                   TexteUI.message(String.valueOf(echangeJ1.getSomme())+"€");
                                   String rep3 = TexteUI.question("Acceptez-vous l'échange ? (oui/non)");  // Ensuite, l'autre joueur peut donner son accord
                                   if (rep3.equals("oui")){
                                               // Si oui, le transfert de propriété et d'argent ce fait
                                       for (CarreauPropriete cp : echangeJ1.getListP()){
                                           cp.setProprietaire(j2);
                                       }
                                       for (CarreauPropriete cp : echangeJ2.getListP()){
                                           cp.setProprietaire(j1);
                                       }
                                       j1.addCash(echangeJ2.getSomme());
                                       j2.removeCash(echangeJ2.getSomme());

                                       j2.addCash(echangeJ1.getSomme());
                                       j1.removeCash(echangeJ1.getSomme());
                                   }
                               }
                           }
                           break;
                        }
                       case "carte":
                        {
                           if (j2.getNBCartePrison()==0){
                               TexteUI.message(j2.getNomJoueur()+" n'a pas de cartes sortie de prison, échange impossible.");
                           }else{
                               String rep2 = TexteUI.question(j2.getNomJoueur()+" a une carte sortie de prison, combien lui en proposez-vous ?");
                               String rep3 = TexteUI.question(j2.getNomJoueur()+", "+j1.getNomJoueur()+" vous propose "+rep2+"€ contre une carte sortie de prison, êtes-vous d'accord? (oui/non)");
                               if (rep3.equals("oui")){
                                   j2.removeCartePrison();
                                   j1.addCartePrison();
                                   j2.addCash(Integer.parseInt(rep2));
                                   j1.removeCash(Integer.parseInt(rep2));
                               }else{
                                   TexteUI.message("Echange annulé, "+j2.getNomJoueur()+" l'a refusé.");
                               }
                           }
                           break;
                        }
                          
            }
            }else{
                TexteUI.message("Vous ne pouvez aps faire d'échange, toutes les propriétés n'ont pas encore été vendu");
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
    
    public boolean removeHotel() {
        if (nbHotels>0) {    
            nbHotels--;
            return true;
        } else {
            return false;
        }
    }
    
    public void addHotel() {
        nbHotels++;
    }
    
    public boolean removeMaison() {
        if (nbMaisons>0) {    
            nbMaisons--;
            return true;
        } else {
            return false;
        }
    }
    
    public void addMaison() {
        nbMaisons++;
    }
    
    public void addMaison(int i) {
        nbMaisons+=i;
    }
    
    public ArrayList<CarreauPropriete> getCarreauxPropriete(){
        ArrayList<CarreauPropriete> listeCp = new ArrayList<>();
        for (Carreau c : carreaux.values()){
            if(c.getClass().toString().equals("class Jeu.ProprieteAConstruire") || c.getClass().toString().equals("class Jeu.Gare") || c.getClass().toString().equals("class Jeu.Compagnie")) {
                listeCp.add((CarreauPropriete) c);
            }
        }
        return listeCp;            
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
