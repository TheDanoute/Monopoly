package Jeu;

import Ui.CarreauUI;

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
            CarreauUI.piocherCarte(type);
            c = this.getMonopoly().getCartes().piochezCarteChance();
        } else {
            CarreauUI.piocherCarte(type);
            c = this.getMonopoly().getCartes().piochezCarteCommunautaire();
        }
        c.action(j);
        if (c.getClass().getSimpleName().equals("CartePrison")) {
            j.addCartePrison(c);
        } else {
            this.getMonopoly().getCartes().retourCarte(c);
        }
    }
}