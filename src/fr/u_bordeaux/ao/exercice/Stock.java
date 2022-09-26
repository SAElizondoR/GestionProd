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

import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author sergio
 */
public class Stock {
    private final String nom;
    private final String addresse;
    private final ArrayList<Product> tabProduct = new ArrayList<>();
    
    public Stock(String nom, String addresse) {
        this.nom = nom;
        this.addresse = addresse;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }
    
    public boolean ajouterProduit(Product product) {        
        tabProduct.add(product);
        return true;
    }
    
    public void afficherProduit(String nom) {
        Product prod = rechercherProduit(nom);
        
        if (prod == null)
            System.out.println("Produit non trouvé.");
        else
            prod.afficher();
        System.out.println();
    }
    
    public String modifierQuantite(String nom, int diff) {
        Product prod = rechercherProduit(nom);
        if (prod == null)
            return "Produit non trouvé.";
        if (!prod.modifierQuantite(diff))
            return "Il n'y a pas assez de stock du produit.";
        return "Le stock du produit a été modifié avec succès.";
    }
    
    private Product rechercherProduit(String nom) {
        ListIterator<Product> iter = tabProduct.listIterator();
        for (; iter.hasNext();) {
            Product prodActuel = iter.next();
            if (prodActuel.getNom().equals(nom))
                return prodActuel;
        }
        return null;
    }
}
