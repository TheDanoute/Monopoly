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
        if (type==CarteType.chance){ //Case chance
            CarreauUI.piocherCarte(type);
            c = this.getMonopoly().getCartes().piochezCarteChance();
        } else { //Case Caisse Communautaire
            CarreauUI.piocherCarte(type);
            c = this.getMonopoly().getCartes().piochezCarteCommunautaire();
        }
        c.action(j); //Si carte prison : li seulement la description
        if (c.getClass().getSimpleName().equals("CartePrison")) { //Si carte sortie de prison la carte ne doit pas retourner dans la pile de carte
            j.addCartePrison(c);
        } else { //Mise de la carte dans les arrayList "poubelle"
            this.getMonopoly().getCartes().retourCarte(c);
        }
    }
}