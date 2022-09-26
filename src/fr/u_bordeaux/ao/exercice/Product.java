/*
 * Copyright (C) 2022 sergio
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.u_bordeaux.ao.exercice;

/**
 *
 * @author sergio
 */
public class Product {
    private final int ref;
    private int quantite;
    private static int comptour = 0;
    private final String nom;
    
    public Product(String nom, int quantite) {
        ref = comptour;
        this.nom = nom;
        this.quantite = quantite;
        comptour++;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }
    
    public void afficher() {
        System.out.printf("Référence: %d\nNom: %s\nQuantité: %d\n", ref,
                nom, quantite);
        
    }
    
    public boolean modifierQuantite(int diff) {
        int nouveauQuantite = quantite + diff;
        
        if (nouveauQuantite < 0)
            return false;
        
        quantite = nouveauQuantite;
        return true;
    }
}
