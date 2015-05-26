package Jeu;

public class CarreauTirage extends CarreauAction {
    
    private CarteType type;
    private PaquetsCartes paquet;
    
    public CarreauTirage(int n,String nom,Monopoly m,CarteType t,PaquetsCartes p){
        super(n,nom,m);
        this.setType(t);
        this.setPaquet(paquet);
    }

    public CarteType getType() {
        return type;
    }

    private void setType(CarteType type) {
        this.type = type;
    }

    public PaquetsCartes getPaquet() {
        return paquet;
    }

    private void setPaquet(PaquetsCartes paquet) {
        this.paquet = paquet;
    }
    
    @Override
    public void action(Joueur j){
        Carte c;
        if (type==CarteType.chance){
            c = paquet.piochezCarteChance();
        } else {
            c = paquet.piochezCarteCommunautaire();
        }
        c.action(j);
        paquet.retourCarte(c);
    }
}