package Jeu;

public class CarreauTirage extends CarreauAction {
    
    private CarteType type;
    
    public CarreauTirage(int n,String nom,Monopoly m,CarteType t){
        super(n,nom,m);
        this.setType(t);
    }

    public CarteType getType() {
        return type;
    }

    private void setType(CarteType type) {
        this.type = type;
    }
    
    @Override
    public void action(Joueur j){
        Carte c;
        if (type==CarteType.chance){
            c = this.getMonopoly().getPaquetsCartes().piochezCarteChance();
        } else {
            c = this.getMonopoly().getPaquetsCartes().piochezCarteCommunautaire();
        }
        c.action(j);
        this.getMonopoly().getPaquetsCartes().retourCarte(c);
    }
}