/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author DanJeux
 */
public class PaquetsCartes {
    private ArrayList<Carte> cartesChances;
    private ArrayList<Carte> cartesChancesPoubelle;
    private ArrayList<Carte> cartesCommunautaires;
    private ArrayList<Carte> cartesCommunautairesPoubelle;
    
    public PaquetsCartes(String dataFilename) {
        cartesChances = new ArrayList<>();
        cartesChancesPoubelle = new ArrayList<>();
        cartesCommunautaires = new ArrayList<>();
        cartesCommunautairesPoubelle = new ArrayList<>();
        this.initPaquetsCartes(dataFilename);
        // Mélange des listes de cartes
        Collections.shuffle(cartesChances);
        Collections.shuffle(cartesCommunautaires);
    }
    
    private void initPaquetsCartes(String dataFilename) {
        try{
            ArrayList<String[]> data = readDataFile(dataFilename, ",");

            for(int i=0; i<data.size(); ++i){
                String caseType = data.get(i)[1];
                switch (caseType) {
                    case "A":
                        {
                            CarteArgent c = new CarteArgent(data.get(i)[0],data.get(i)[2],Integer.valueOf(data.get(i)[3]));
                            this.addCarte(c);
                            break;
                        }
                    case "M":
                        {
                            CarteMouvement c = new CarteMouvement(data.get(i)[0],data.get(i)[2],Integer.valueOf(data.get(i)[3]),false);
                            this.addCarte(c);
                            break;
                        }
                    case "MA":
                        {
                            CarteMouvement c = new CarteMouvement(data.get(i)[0],data.get(i)[2],Integer.valueOf(data.get(i)[3]),true);
                            this.addCarte(c);
                            break;
                        }
                    case "P":
                        {
                            CartePrison c = new CartePrison(data.get(i)[0],data.get(i)[2]);
                            this.addCarte(c);
                            break;
                        }
                    case "S":
                        {
                            CarteSpecial c = new CarteSpecial(data.get(i)[0],data.get(i)[3],Integer.valueOf(data.get(i)[2]));
                            this.addCarte(c);
                            break;
                        }
                    default:
                        System.err.println("[initPaquetsCartes()] : Invalid Data type");
                        break;
                }
                
	    }
        } catch(FileNotFoundException e){
            System.err.println("[initPaquetsCartes()] : File is not found!");
	} catch(IOException e){
            System.err.println("[initPaquetsCartes()] : Error while reading file!");
	}
    }
    
    private void addCarte(Carte c){
        if(c.getType()==CarteType.communautaire){
            cartesCommunautaires.add(c);
        } else {
            cartesChances.add(c);
        }
    }
    private ArrayList<String[]> readDataFile(String filename, String token) throws FileNotFoundException, IOException
	{
		ArrayList<String[]> data = new ArrayList<>();
		
		BufferedReader reader  = new BufferedReader(new FileReader("src/Data/"+filename));
		String line = null;
		while((line = reader.readLine()) != null){
			data.add(line.split(token));
		}
		reader.close();
		
		return data;
	}
    
    public Carte piochezCarteChance() {
        // Si la liste cartesChances est vide, cette dernière recupère la liste cartesChancesPoubelle puis elle est mélangée. 
        // La liste poubelle est ensuite vidée. 
        if(cartesChances.isEmpty()) {
            cartesChances = cartesChancesPoubelle;
            Collections.shuffle(cartesChances);
            cartesChancesPoubelle.clear();
        }
        
        Random gene = new Random();
        int rang = gene.nextInt(cartesChances.size());
        Carte c = cartesChances.get(rang);
        cartesChances.remove(rang);
        return c;
    }
    
    public Carte piochezCarteCommunautaire() {
        // Si la liste cartesCommunautaires est vide, cette dernière recupère la liste cartesCommunautairesPoubelle puis elle est mélangée. 
        // La liste poubelle est ensuite vidée.
        if(cartesCommunautaires.isEmpty()) {
            cartesCommunautaires = cartesCommunautairesPoubelle;
            Collections.shuffle(cartesCommunautaires);
            cartesCommunautairesPoubelle.clear();
        }
        // Création d'un Random pour générer un entier compris entre 1 et la taille de la liste cartesCommunautaires, on récupère ensuite la carte
        // correspondant au rang.
        Random gene = new Random();
        int rang = gene.nextInt(cartesCommunautaires.size());
        Carte c = cartesCommunautaires.get(rang);
        cartesCommunautaires.remove(rang);
        return c;
    }
    
    public void retourCarte(Carte c){
        // Permet de mettre une carte dans la liste poubelle, si c'est une carte chance, dans la liste carteChancesPoubelle; sinon
        // dans la liste carteCommunautairesPoubelle.
        if (c.getType()==CarteType.chance) {
            cartesChancesPoubelle.add(c);
        } else {
            cartesCommunautairesPoubelle.add(c);
        }
    }


}
