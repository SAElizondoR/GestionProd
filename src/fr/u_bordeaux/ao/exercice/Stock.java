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
import java.util.Iterator;

/**
 *
 * @author sergio
 */
public class Stock {
    private static final int MAX = 10;
    private final String nom;
    private final String addresse;
    private final ArrayList<Product> tabProduct = new ArrayList<>();
    private int size = 0;
    
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
    
    public boolean isFull() {
        return size >= MAX;
    }
    
    public boolean ajouterProduit(Product product) {
        if (isFull()) 
            return false;
        
        tabProduct.add(product);
        size = tabProduct.size();
        return true;
    }
    
    public Product rechercherProduit(String nom) {
        Iterator<Product> iter = tabProduct.iterator();
        for (; iter.hasNext();) {
            Product prodActuel = iter.next();
            if (prodActuel.getNom().equals(nom))
                return prodActuel;
        }
        return null;
    }
    
    public String modifierQuantite(String nom, int diff) {
        Product prod = rechercherProduit(nom);
        if (prod == null)
            return "Produit non trouvé.";
        if (!prod.modifierQuantite(diff))
            return "Il n'y a pas assez de stock du produit.";
        return "Le stock du produit a été modifié avec succès.";
    }
    
}
