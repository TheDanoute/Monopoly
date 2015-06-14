package Jeu;

import Ui.TexteUI;

public class Interface {
    
    public static Monopoly monopoly;
    
    public static void main(String[] args) {
        // TODO code application logic here
       monopoly = new Monopoly("data.txt",TexteUI.demo());//Demande de démo
       
    }
    
	
}