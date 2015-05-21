/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Jeu;

/**
 *
 * @author devaucod
 */
public abstract class Carte {
    private CarteType type;
    private String description;
    
    public Carte(String t,String d) {
        this.setDescription(d);
        this.setType(t);
    }

    public CarteType getType() {
        return type;
    }

    private void setType(String type) {
        this.type = CarteType.valueOf(type);
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }
    
    public abstract void action();
    
}
