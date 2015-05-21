package Jeu;

public abstract class CarreauAction extends Carreau {
    
    public CarreauAction (int n,String nom,Monopoly m){
        super(n,nom,m);
    }
    public abstract void action(Joueur j);
}