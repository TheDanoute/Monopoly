package Jeu;

public class CarreauMouvement extends CarreauAction {
    
    public CarreauMouvement(int n,String nom,Monopoly m){
        super(n,nom,m);
    }
    
    @Override
    public void action(Joueur j){
        j.enPrison();
    }
}