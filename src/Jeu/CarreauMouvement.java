package Jeu;

public class CarreauMouvement extends CarreauAction {
    
    public void action(Joueur j){
        j.enPrison();
    }
}