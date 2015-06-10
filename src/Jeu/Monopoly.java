package Jeu;

import Ui.*;
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
                                        ArrayList<Integer> prix = new ArrayList<>(); //Création de la liste de prix de loyer
                                        for (int j=5;j<11;j++) {
                                            prix.add(Integer.valueOf(data.get(i)[j]));
                                        }
                                        ProprieteAConstruire pAC = new ProprieteAConstruire(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[4]),prix,groupe,Integer.valueOf(data.get(i)[11]));
                                        this.addCarreau(pAC);
                                }
                                
				else if(caseType.compareTo("G") == 0){
                                        Gare gare = new Gare (Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[3]));
                                        this.addCarreau(gare);
                                }
				else if(caseType.compareTo("C") == 0){
                                        Compagnie comp = new Compagnie(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[3]));
                                        this.addCarreau(comp);
				}
                                else if(caseType.compareTo("CP") == 0){
                                        CarreauPrison cp = new CarreauPrison(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this);
                                        this.addCarreau(cp);
				}
				else if(caseType.compareTo("CT") == 0){
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
                                        CarreauArgent ca = new CarreauArgent(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this,Integer.valueOf(data.get(i)[3]));
                                        this.addCarreau(ca);
				}
				else if(caseType.compareTo("CM") == 0){
                                        CarreauMouvement cM = new CarreauMouvement(Integer.valueOf(data.get(i)[1]),data.get(i)[2],this);
                                        this.addCarreau(cM);
				}
				else
					TexteUI.errorFile(0);
			}
			
		} 
		catch(FileNotFoundException e){
                    TexteUI.errorFile(1);
		}
		catch(IOException e){
                    TexteUI.errorFile(2);
		}
	}
        
        private void addCarreau(Carreau c) {
            carreaux.put(c.getNum(), c);
            TexteUI.printInfo(c);
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
                    ajouterJoueur(TexteUI.nouveauJoueur());
                        if (!TexteUI.ajouterJoueur()){
                            nJ = false;
                            joueurs = trieRecursif(joueurs); //Méthode recursive
                            TexteUI.afficherJoueurs(joueurs);
                        }
                }
	}

        
        public void ajouterJoueur (String nomJ) {
            Joueur j = new Joueur(nomJ, this);
            joueurs.add(j);
            TexteUI.ajoutJoueur(j);
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
                            TexteUI.trieJoueur(list.get(j), value);
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
                    TexteUI.trieJoueur(list.get(j), value);
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
            TexteUI.afficherJoueurs(joueurs);
            int i = 0;
            while (joueurs.size()>1) {
                Joueur j = joueurs.get(i);
                    TexteUI.nouveauTour(j);
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
            JoueurUI.printCashVous(aJ);
            JoueurUI.printVosProprietes(aJ);
            String rep = TexteUI.choixTour();
            boolean sort = false;
            while (!rep.equals("avancer")&&!sort) {
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
                        try {
                            this.detruire(aJ);
                        } catch (Exception e) {
                            TexteUI.message(e.getMessage());
                        }
                        break;
                    }
                    case "hypotheque":
                    {
                        try {
                            this.hypotheque(aJ,true);
                        } catch (Exception e) {
                            TexteUI.message(e.getMessage());
                        }
                        break;
                    }
                    case "sortir":
                    {
                        sort = aJ.sortDuJeu();
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                rep = TexteUI.choixTour();
            }
            if (rep.equals("avancer")) {
            TexteUI.avancer();
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
                        TexteUI.troisDouble(aJ);
                        twice = false;
                    }else{
                        this.action(aJ);
                        twice=false;
                    }
                    s = 0;
                }
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
            TexteUI.dés(d1, d2);
            } else {
                //d1 et d2 sont égaux pour return true
                d1 = 0;
                d2 = 0;
                sD = s;
            }
            // Donne des informations sur la somme des dés, la position actuelle du joueur et la position qu'il occupe après avoir avancé.
            TexteUI.printAncienCarreau(aJ.getPositionCourante());
            Avancer(aJ, sD);
            TexteUI.printNouveauCarreau(aJ.getPositionCourante());
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
            ProprieteUI.construction();
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
                ProprieteUI.aucunGroupeComplet(); //fin
            } else {
                CouleurPropriete coul = ProprieteUI.chooseGroupe(list);
                    ArrayList<ProprieteAConstruire> listP = list.get(coul); //Récupération des proprietes du groupe selectionné
                        boolean stop = false; //Boolean de construction rapide dans sur les mêmes propriétés
                        for (ProprieteAConstruire p : listP) {
                            if (p.isHypotheque()) {
                                ProprieteUI.erreurHypo();
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
                                ProprieteUI.erreurHotel(); //fin
                            } else {
                                HashMap<Integer,ProprieteAConstruire> listPPotentiel = new HashMap<>(); //Liste contenant les proprietés constructibles
                                if (!onAChanger) {
                                    max++; //Tous les terrains ont la même valeur immobilière mais pas encore d'hotel
                                }
                                ProprieteUI.construireSur();
                                for (ProprieteAConstruire p : listP) {
                                        if (p.getImmobilier()<max) {
                                        listPPotentiel.put(p.getNum(), p); //Remplissage de la liste
                                        ProprieteUI.printPropriete(p);
                                        }
                                }
                                if (max>4 && this.getNbHotels()<1) { //Vérification de maisons et hotels restant à la banque
                                    stop = ProprieteUI.errorBanque(true);
                                } else if (max<5 && this.getNbMaisons()<1) {
                                    stop = ProprieteUI.errorBanque(false);
                                } else { //Choix du terrain
                                    int num = ProprieteUI.chooseNum();
                                    try {
                                        listPPotentiel.get(num).construire();
                                    } catch(NullPointerException e) {
                                        ProprieteUI.errorConstruction();
                                    }
                                    //Boucle de reconstruction rapide
                                        stop = !ProprieteUI.encoreConstruire();
                                }
                            }
                        }
                    }
        }
         
         public void detruire(Joueur j) throws Exception {
             HashMap<CouleurPropriete,ArrayList<ProprieteAConstruire>> listP = new HashMap<>();
             ArrayList<ProprieteAConstruire> pros;
             for (CouleurPropriete c : CouleurPropriete.values()) {
                 pros = j.getProprietesAConstruire(c);
                 if (!pros.isEmpty() && pros.get(0).getNbPropriete()==pros.size()) {
                    int immo = 0;
                     for(ProprieteAConstruire p : pros) {
                        immo+=p.getImmobilier();
                    }
                    if (immo>0) {
                        listP.put(pros.get(0).getCouleur(), pros);
                    } 
                 }
             }
             if (listP.isEmpty()) {
                 throw new Exception("Aucun de vos groupes de propriétés ne contiennent des constructions");
             } else { 
               pros = listP.get(ProprieteUI.chooseGroupe(listP));
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
                   ProprieteUI.printDetruire();
                   for (ProprieteAConstruire p : pros) {
                       if (p.getImmobilier()>min) {
                           listPPotentiel.put(p.getNum(), p);
                           ProprieteUI.printPropriete(p);
                       }
                   }
                   if (min<0) {
                       stop = ProprieteUI.errorVide();
                   } else {
                       try {
                            listPPotentiel.get(ProprieteUI.chooseNum()).detruire();
                            JoueurUI.printCashVous(j);
                       } catch(NullPointerException e){
                           ProprieteUI.errorDestruction();
                       }
                        stop = !ProprieteUI.encoreDetruire();
                   }
               }
             }
         }
        
        public void hypotheque(Joueur j,boolean display) throws Exception{
            String rep = ProprieteUI.menuHypo(display);
            switch(rep) {
                case "lever":
                {
                    HashMap<Integer,CarreauPropriete> list = new HashMap<>();
                    ProprieteUI.printListHypo();
                    for (ProprieteAConstruire p : j.getProprietesAConstruire()) {
                        if (p.isHypotheque()) {
                            list.put(p.getNum(), p);
                            ProprieteUI.printPropriete(p);
                        }
                    }
                    for (Gare g : j.getGares()) {
                        if (g.isHypotheque()) {
                            list.put(g.getNum(), g);
                            ProprieteUI.printGare(g);
                        }
                    }
                    for (Compagnie c : j.getCompagnies()) {
                        if (c.isHypotheque()) {
                            list.put(c.getNum(), c);
                            ProprieteUI.printCompagnie(c);
                        }
                    }
                    if (list.isEmpty()){
                        ProprieteUI.errorHypo();
                    } else {
                        boolean ok = false;
                        CarreauPropriete c = (CarreauPropriete)carreaux.get(2); //Initialisation de la variable
                        while (!ok) {
                            try {
                                c = list.get(ProprieteUI.chooseHypo());
                                ok = true;
                            } catch (java.lang.NullPointerException e) {
                                ProprieteUI.errorNonHypo();
                            }
                        }
                        ProprieteUI.leverHypo(c);
                        if (j.getCash()<c.getPrixHypotheque()) {
                            JoueurUI.errorArgent(j);
                        } else {
                            if (ProprieteUI.continuerHypo()) {
                                c.leverHypotheque();
                            }
                        }
                    }
                    break;
                }
                case "hypotheque":
                {
                    HashMap<Integer,CarreauPropriete> list = new HashMap<>();
                    ProprieteUI.hypoDispo();
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
                                        ProprieteUI.printPropriete(p);
                                    }
                                }
                            }
                        }    
                    }
                    for (Gare g : j.getGares()) {
                        if (!g.isHypotheque()) {
                            list.put(g.getNum(), g);
                            ProprieteUI.printGare(g);
                        }
                    }
                    for (Compagnie c : j.getCompagnies()) {
                        if (!c.isHypotheque()) {
                            list.put(c.getNum(), c);
                            ProprieteUI.printCompagnie(c);
                        }
                    }
                    if (list.isEmpty()){
                        throw new Exception("Vous n'avez aucune propriété disponible à l'hypotheque");
                    } else {
                       boolean ok = false;
                        CarreauPropriete c = (CarreauPropriete)carreaux.get(2); //Initialisation de la variable
                        while (!ok) {
                            try {
                                c = list.get(ProprieteUI.chooseHypo());
                                ok = true;
                            } catch (java.lang.NullPointerException e) {
                                ProprieteUI.errorHypoNonProposee();
                            }
                        }
                        ProprieteUI.printHypo(c);
                            if (ProprieteUI.continuerHypo()) {
                                c.hypotheqer();
                            }
                        }
                    }
                }
            }
         
       

         public void echanger(Joueur j1) {
                    // On teste si toutes le propriétés ont été vendu (condition pour faire un échange)
             boolean toutVendu = true;
             int j = 0;
             for (CarreauPropriete cp : this.getCarreauxPropriete()){
                 if (cp.getProprietaire()==null){
                     toutVendu=false;
                 }
             }
            if (toutVendu){
                TexteUI.echange();
                Joueur j2;
                {
                   ArrayList<Joueur> listJoueur = new ArrayList<>();                  // Création d'une liste de joueur ayant des biens à échanger
                   int i = 1;
                   for (Joueur jTemp : joueurs) {
                       if (j1!=jTemp) {
                               // Affichage des propriétés / carte sortie de prison de chaque joueur excepté celui demandant l'échange
                           JoueurUI.afficheProprietes(jTemp, i);
                           listJoueur.add(jTemp);
                       }
                   }
                   j2 = TexteUI.joueurChoisisEchange(listJoueur);
                }
                String rep = TexteUI.typeEchange();  // On récupère le type de l'échange
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
                           TexteUI.sesProps();
                           while (!stop){                                                // Tant que l'on souhaite ajouter une propriété à l'échange on reste dans la boucle
                               listP2 = JoueurUI.afficheProprietesEchangeables(j2, echangeJ2, listP2);                             
                               if (!listP2.isEmpty()){
                                   int num = TexteUI.choixPropEch();     // Grâce à l'indice, on récupère la propriété souhaitée
                                   echangeJ2.addP(listP2.get(num));                                                            // On l'ajoute à la classe échange
                                   listP2.remove(num);                                                                         // Puis on l'a supprime des propriétés disponibles à l'échange    
                                   String rep2 = TexteUI.autrePropEch();
                                   if (!rep2.equals("oui")){
                                       String rep3 = TexteUI.rajouteArgent();
                                       if(rep3.equals("oui")){                         // Possibilité d'ajouter de l'argent à l'échange
                                           int somme =TexteUI.combienArgent();
                                           echangeJ2.setSomme(somme);
                                       }
                                       stop=true;   
                                   }
                               }else{
                                           // Si le joueur choisis n'a pas de propriété échangeable, un message est affiché et aucun échange ne se fait
                                   TexteUI.pasPropEch(j2);
                                   noTrade=true;
                                   stop=true;
                               }
                           }
                                   // Après avoir renseigné ce qu'il veut, le joueur indique ce qu'il propose en échange, avec le même procédé
                           if (!noTrade){
                           Echange echangeJ1 = new Echange(j1);
                           HashMap<Integer,CarreauPropriete> listP1= new HashMap<>();
                           while (!stop2){
                               TexteUI.vosProps();
                               JoueurUI.afficheProprietesEchangeables(j1, echangeJ1, listP1);
                               if (!listP1.isEmpty()){
                                   int num = TexteUI.choixPropEch2();
                                   echangeJ1.addP(listP1.get(num));
                                   listP1.remove(num);
                                   String rep2 = TexteUI.autrePropEch2();
                                   if (!rep2.equals("oui")){
                                       stop2=true;
                                   }
                               }else{
                                   TexteUI.pasPropEch(j1);
                                   noTrade=true;
                                   stop2=true;
                               }
                           }
                                   String rep2=TexteUI.rajouteArgent();
                                   if(rep2.equals("oui")){
                                           int somme =TexteUI.combienArgent();
                                           echangeJ1.setSomme(somme);
                                       }

                               if (noTrade && echangeJ1.getSomme()==0){              // Dans ce cas (pas de carté ni d'argent, échange refusé
                                   TexteUI.pasEchRienProp();
                               }else{
                                          // Sinon, on affiche tout ce que le joueur demande et propose
                                   String rep3 = TexteUI.propositionEchange(echangeJ1, echangeJ2, j1, j2);
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
                               TexteUI.pasCartePrison(j2);
                           }else{
                               int rep2 = TexteUI.offreCartePrison(j2);
                               String rep3 = TexteUI.propositionEchange(j2, j1, rep2);
                               if (rep3.equals("oui")){
                                   j2.removeCartePrison();
                                   j1.addCartePrison();
                                   j2.addCash(rep2);
                                   j1.removeCash(rep2);
                               }else{
                                   TexteUI.echAnnul(j2);
                               }
                           }
                           break;
                        }
                          
            }
            }else{
                TexteUI.pasEncoreEch();
            } 
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
