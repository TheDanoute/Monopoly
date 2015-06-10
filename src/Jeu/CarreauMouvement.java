package Jeu;

import Ui.CarreauUI;

public class CarreauMouvement extends CarreauAction {
    
    public CarreauMouvement(int n,String nom,Monopoly m){
        super(n,nom,m);
    }
    
    @Override
    public void action(Joueur j){
        CarreauUI.allerEnPrison();
        j.enPrison();
    }
}