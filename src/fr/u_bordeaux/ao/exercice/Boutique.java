/*
 * Copyright (C) 2022 selizondorod
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author selizondorod
 */
public class Boutique {
    private static final int MAX = 10;
    private final BufferedReader buff = new BufferedReader(
                new InputStreamReader(System.in));
    private final Stock[] ensembleStock = new Stock[MAX];
    private int ensembleSize = 0;
    
    private String lireChaine() {
        try  {
            return buff.readLine();
        } catch (IOException ex) {
            Logger.getLogger(MyShop.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return null;
    }
    
    private int lireEntier(String condition) {
        boolean exceptionFlag;
        
        do {
            String input = lireChaine();
            int lim_inf = 0, lim_sup = 0;
            try {
                int entier = Integer.parseInt(input);
                if (condition.equals("non negatif")) {
                    if (entier < 0)
                        throw new NumberRestrictionException("<0");
                } else if(condition.startsWith("range")) {
                    String[] part = condition.split(" ");
                    lim_inf = Integer.parseInt(part[1]);
                    lim_sup = Integer.parseInt(part[2]);
                    if (entier < lim_inf || entier > lim_sup) {
                        String message = "![" + part[1] + "-" + part[2] + "]";
                        throw new NumberRestrictionException(message);
                    }
                }
                return entier;
            } catch (NumberRestrictionException ex) {
                String message = ex.getMessage();
                System.out.print("Le valeur saisie n'est pas valide. ");
                if (message.equals("<0"))
                    System.out.print("Veuillez entrer une valeur entière "
                        + "non négative: ");
                else
                    System.out.printf("Veuillez entrer une valeur entière "
                            + "entre %d et %d: ", lim_inf, lim_sup);
                    
                exceptionFlag = true;
            } catch (NumberFormatException ex) {
                System.out.print("Veuillez entrer une valeur entière: ");
                exceptionFlag = true;
            }
        } while (exceptionFlag);
        return 0;
        
    }
    
    private boolean isFull() {
        return ensembleSize >= MAX;
    }
    
    private boolean existsStock(String nom) {
        for (int i = 0; i < ensembleSize; i++) {
            if (ensembleStock[i].getNom().equals(nom))
                return true;
        }
        return false;
    }
    
    private String lireNomStock() {
        String nom = lireChaine();
        while (existsStock(nom)) {
            System.out.println("Un stock portant ce nom existe déjà. "
                    + "Veuillez en saisir un autre: ");
            nom = lireChaine();
        }
        return nom;
    }
    
    private int choisirStock() {
        System.out.println("Sélectionnez le stock:");
        for (int i = 0; i < ensembleSize; i++)
            System.out.printf("%d: %s\n", i + 1, ensembleStock[i].getNom());
        System.out.print("Saisissez votre choix: ");
        String condition = "range 1 " + ensembleSize;
        int choix = lireEntier(condition) - 1;
        System.out.println();
        return choix;
    }
    
    private int choisirOption(String... options) {
        System.out.println("Qu'est-ce que vous voulez faire ?");
        
        int compteur = 0;
        for (String option : options) {
            compteur++;
            System.out.printf("%d: %s\n", compteur, option);
        }
        System.out.print("Saisissez votre choix: ");
        
        String condition = "range 1 " + compteur;
        int choix = lireEntier(condition);
        System.out.println();
        return choix;
    }
    
    private void creerStock() {
        if (isFull()) {
            System.out.println("Il n'est pas possible de créer un stock car"
                    + "le maximum autorisé dans le tableau a déjà été "
                    + "atteint.\n");
            return;
        }
        
        System.out.println("CRÉER UN STOCK");
        System.out.print("Saisissez le nom du stock: ");
        String nom = lireNomStock();
        System.out.print("Saisissez l'addresse du stock: ");
        String addresse = lireChaine();

        ensembleStock[ensembleSize] = new Stock(nom, addresse);
        ensembleSize++;
        
        System.out.println("\nLe stock a été créee avec succès.\n");
    }
    
    private void ajouterProduits() {
        if (ensembleSize == 0) {
            System.out.println("Aucun stock n'a été créé, il n'est donc pas "
                    + "possible d'ajouter des produits\n");
            return;
        }
        
        System.out.println("AJOUTER DES PRODUITS DANS UN STOCK");
        int stockNo = choisirStock();
        
        if (ensembleStock[stockNo].isFull()) {
            System.out.println("Il n'est pas possible d'ajouter des produits "
                    + "en stock car le maximum autorisé dans le tableau a déjà"
                    + "été atteint.\n");
            return;
        }
        
        System.out.print("Saisissez le nom du produit à ajouter: ");
        String nom = lireChaine();
        System.out.print("Saisissez la quantité disponible de ce produit: ");
        int quantite = lireEntier("non negatif");
        
        Product product = new Product(nom, quantite);
        
        if (ensembleStock[stockNo].ajouterProduit(product))
            System.out.println("\nLe produit saisi a été ajouté au stock "
                    + "avec succès.\n");
        else
            System.out.println("\nIl n'a pas été possible d'ajouter le "
                    + "produit saisi au stock.\n");
    }
    
    private void afficherMenu1() {
        int choix = choisirOption("créer un stock",
                "ajouter des produits à un stock");
        if (choix == 1)
            creerStock();
        else
            ajouterProduits();
    }
    
    private void afficherCaracteristiquesProduit() {
        if (ensembleSize == 0) {
            System.out.println("Aucun stock n'a été créé\n");
            return;
        }
        
        System.out.println("AFFICHER LES CARACTÉRISTIQUES D'UN PRODUIT DU "
                + "STOCK");
        int stockNo = choisirStock();
        System.out.print("Saisissez le nom du produit à afficher: ");
        String nom = lireChaine();
        System.out.println();
        
        ensembleStock[stockNo].afficherProduit(nom);
    }
    
    private void modifierQuantiteProduit() {
        if (ensembleSize == 0) {
            System.out.println("Aucun stock n'a été créé\n");
            return;
        }
        
        int choix = choisirOption("ajouter une quantité d'un produit",
                "retirer une quantité d'un produit");
        
        String action;
        if (choix == 1)
            action = "ajouter";
        else
            action = "retirer";
        
        System.out.println(action.toUpperCase() + " UNE QUANTITÉ D'UN PRODUIT");
        int stockNo = choisirStock();
        System.out.print("Saisissez le nom du produit: ");
        String nom = lireChaine();
        System.out.print("Saisissez le nombre d'unités à " + action + ": ");
        int diff = lireEntier("non negatif");
        System.out.println();
        
        if (action.equals("retirer"))
            diff *= -1;
        
        System.out.println(ensembleStock[stockNo].modifierQuantite(nom, diff));
        System.out.println();
    }
    
    public void afficherMenuPrincipal() {
        System.out.println("\nBonjour.");
        int choix;
        do {
            choix = choisirOption(
                    "créer un stock/ajouter des produits dans un stock",
                    "afficher les caractéristiques d'un produit du stock",
                    "ajouter/retirer une quantité d'un produit donné au stock",
                    "quitter");
            
            switch (choix) {
                case 1:
                    afficherMenu1();
                    break;
                case 2:
                    afficherCaracteristiquesProduit();
                    break;
                case 3:
                    modifierQuantiteProduit();
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        } while (choix != 4);
    }
    
    public void close() {
        try {
            buff.close();
        } catch (IOException ex) {
            Logger.getLogger(Boutique.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}
