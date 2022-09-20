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

package fr.ubordeaux.ao.exercice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sergio
 */
public class MyShop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("\nBonjour.");
        
        int choix;
        Stock[] stock = new Stock[10];
        int size = 0;
        BufferedReader buff = new BufferedReader(
                new InputStreamReader(System.in));
        
        do {
            System.out.println("\nQu'est-ce que vous voulez faire ?");
            System.out.println("1: créer un stock/ajouter des produits dans "
                    + "le stock");
            System.out.println("2: afficher les caractéristiques d'un produit "
                    + "du stock");
            System.out.println("3: ajouter/retirer une quantité d'un produit "
                    + "donné au stock");
            System.out.println("4: quitter\n");
            System.out.print("Saisissez votre choix: ");
            choix = lireEntierCondition(buff, "1-4");
            System.out.println();
            
            int stockNumber;
            switch (choix) {
                case 1:
                    System.out.println("1: creer\n2: ajouter\n");
                    int choix2 = lireEntierCondition(buff, "1-2");
                    if (choix2 == 1) {
                        stock[size] = creerStock(buff);
                        size++;
                    }
                    else {
                        System.out.println("Stock number: ");
                        stockNumber = lireEntierCondition(buff, "1-10");
                        ajouterProduits(stock[stockNumber], buff);
                    }
                    break;
                case 2:
                    System.out.println("Stock number: ");
                    stockNumber = lireEntierCondition(buff, "1-10");
                    afficherCaracteristiquesProduit(stock[stockNumber], buff);
                    break;
                case 3:
                    System.out.println("Stock number: ");
                    stockNumber = lireEntierCondition(buff, "1-10");
                    modifierQuantiteProduit(stock[stockNumber], buff);
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        } while (choix != 4);
    }
    
    private static String lireChaine(BufferedReader buff) {
        try  {
            String str = buff.readLine();
            return str;
        } catch (IOException ex) {
            Logger.getLogger(MyShop.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return null;
    }
    
    private static int lireEntierCondition(BufferedReader buff,
            String condition) {
        boolean exceptionFlag = false;
        
        do {
            String input = lireChaine(buff);
            try {
                int entier = Integer.parseInt(input);
                if (condition.equals("1-2") && entier != 1 && entier != 2) {
                    throw new NumberFormatException();
                }
                if (condition.equals("1-4") && entier != 1 && entier != 2
                        && entier != 3 && entier != 4) {
                    throw new NumberFormatException();
                }
                if (condition.equals("1-10") && (entier < 1 || entier > 10)) {
                    throw new NumberFormatException();
                }
                if (condition.equals("non negatif") && entier < 0) {
                    throw new NumberFormatException();
                }
                return entier;
            } catch (NumberFormatException ex) {
                System.out.print("Le valeur saisie n'est pas valide. ");
                if (condition.equals("1-2"))
                    System.out.println("Veuillez entrer une valeur entière "
                            + "entre 1 et 2: ");
                if (condition.equals("1-4"))
                    System.out.println("Veuillez entrer une valeur entière "
                            + "entre 1 et 4: ");
                else
                    System.out.print("Veuillez entrer une valeur entière "
                        + "non négative: ");
                exceptionFlag = true;
            }
        } while (exceptionFlag);
        return 0;
        
    }
    
    private static Stock creerStock(BufferedReader buff) {;
        System.out.println("CRÉER UN STOCK");
        System.out.print("Saisissez le nom du stock: ");
        String nom = lireChaine(buff);
        System.out.print("Saisissez l'addresse du stock: ");
        String addresse = lireChaine(buff);

        Stock stock = new Stock(nom, addresse);
        
        System.out.println("\nLe stock a été créee avec succès.");
        
        return stock;
    }
    
    private static void ajouterProduits(Stock stock, BufferedReader buff) {
        if (stock.isFull()) {
            System.out.println("Il n'est pas possible d'ajouter des produits "
                    + "en stock car le maximum autorisé dans le tableau a déjà"
                    + "été atteint.");
            return;
        }
        System.out.println("AJOUTER DES PRODUITS DANS LE STOCK");
        System.out.print("Saisissez le nom du produit à ajouter: ");
        String nom = lireChaine(buff);
        System.out.print("Saisissez la quantité disponible de ce produit: ");
        int quantite = lireEntierCondition(buff, "non negatif");
        
        Product product = new Product(nom, quantite);
        
        if (stock.ajouterProduit(product))
            System.out.println("\nLe produit saisi a été ajouté au stock "
                    + "avec succès.");
        else
            System.out.println("\nIl n'a pas été possible d'ajouter le "
                    + "produit saisi au stock.");
    }
    
    private static void afficherCaracteristiquesProduit(Stock stock,
            BufferedReader buff) {
        System.out.println("AFFICHER LES CARACTÉRISTIQUES D'UN PRODUIT DU "
                + "STOCK");
        System.out.print("Saisissez le nom du produit à afficher: ");
        String nom = lireChaine(buff);
        
        Product prod = stock.rechercherProduit(nom);
        
        if (prod == null)
            System.out.println("\nProduit non trouvé.");
        else
            prod.afficher();
    }
    
    private static void modifierQuantiteProduit(Stock stock,
            BufferedReader buff) {
        System.out.println("\nQu'est-ce que vous voulez faire ?");
        System.out.println("1: ajouter une quantité d'un produit");
        System.out.println("2: retirer une quantité d'un produit");
        System.out.print("Saisissez votre choix: ");
        
        int choix = lireEntierCondition(buff, "1-2");
        String action;
        if (choix == 1)
            action = "ajouter";
        else
            action = "retirer";
        
        System.out.println(action.toUpperCase() + " UNE QUANTITÉ D'UN PRODUIT");
        System.out.print("Saisissez le nom du produit: ");
        String nom = lireChaine(buff);
        System.out.print("Saisissez le nombre d'unités à " + action + ": ");
        int diff = lireEntierCondition(buff, "non negatif");
        
        if (action.equals("retirer"))
            diff *= -1;
        
        int result = stock.modifierQuantite(nom, diff);
        
        if (result == -1)
            System.out.println("\nProduit non trouvé.");
        else if (result == -2)
            System.out.println("\nIl n'y a pas assez de stock du produit pour "
                    + "effectuer l'action souhaitée.");
        else
            System.out.println("\nLe stock du produit a été modifié avec "
                    + "succès.");
    }
    
}
