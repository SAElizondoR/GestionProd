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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ListIterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author selizondorod
 */
public class MyShop extends Vector<Stock> {
    private final BufferedReader buff = new BufferedReader(
                new InputStreamReader(System.in));
    int size = 0;
    
    private String lireChaine() {
        try  {
            return buff.readLine();
        } catch (IOException ex) {
            Logger.getLogger(MyAppli.class.getName()).log(Level.SEVERE,
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
                    System.out.print("Veuillez entrer une valeur enti??re "
                        + "non n??gative: ");
                else
                    System.out.printf("Veuillez entrer une valeur enti??re "
                            + "entre %d et %d: ", lim_inf, lim_sup);
                    
                exceptionFlag = true;
            } catch (NumberFormatException ex) {
                System.out.print("Le valeur saisie n'est pas valide. ");
                System.out.print("Veuillez entrer une valeur enti??re: ");
                exceptionFlag = true;
            }
        } while (exceptionFlag);
        return 0;
        
    }
    
    private ZonedDateTime lireDate() {
        boolean exceptionFlag;
        
        do {
            String input = lireChaine();
            
            DateTimeFormatter formatter
                    = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            try {
                return LocalDate.parse(input, formatter)
                        .atStartOfDay(ZoneOffset.UTC);
            } catch (DateTimeParseException ex) {
                System.out.print("Le valeur saisie n'est pas valide. ");
                System.out.print("Veuillez entrer une date au format "
                        + "jj/mm/aaaa: ");
                exceptionFlag = true;
            }
        } while (exceptionFlag);
        return null;
    }
    
    private boolean existsStock(String nom) {
        ListIterator<Stock> iter = listIterator();
        for (; iter.hasNext();) {
            Stock stockActuel = iter.next();
            if (stockActuel.getNom().equals(nom))
                return true;
        }
        return false;
    }
    
    private String lireNomStock() {
        String nom = lireChaine();
        while (existsStock(nom)) {
            System.out.println("Un stock portant ce nom existe d??j??. "
                    + "Veuillez en saisir un autre: ");
            nom = lireChaine();
        }
        return nom;
    }
    
    private int choisirStock() {
        System.out.println("S??lectionnez le stock:");
        ListIterator<Stock> iter = listIterator();
        for (int i = 0; iter.hasNext(); i++) {
            Stock stockActuel = iter.next();
            System.out.printf("%d: %s\n", i + 1, stockActuel.getNom());
        }
        System.out.print("Saisissez votre choix: ");
        String condition = "range 1 " + size;
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
        /* if (isFull()) {
            System.out.println("Il n'est pas possible de cr??er un stock car"
                    + "le maximum autoris?? dans le tableau a d??j?? ??t?? "
                    + "atteint.\n");
            return;
        } */
        
        System.out.println("CR??ER UN STOCK");
        System.out.print("Saisissez le nom du stock: ");
        String nom = lireNomStock();
        System.out.print("Saisissez l'addresse du stock: ");
        String addresse = lireChaine();

        add(new Stock(nom, addresse));
        size = size();
        
        System.out.println("\nLe stock a ??t?? cr??ee avec succ??s.\n");
    }
    
    private void ajouterProduits() {
        if (size == 0) {
            System.out.println("Aucun stock n'a ??t?? cr????, il n'est donc pas "
                    + "possible d'ajouter des produits\n");
            return;
        }
        
        System.out.println("AJOUTER DES PRODUITS DANS UN STOCK");
        int stockNo = choisirStock();
        
        /* if (ensembleStock.get(stockNo).isFull()) {
            System.out.println("Il n'est pas possible d'ajouter des produits "
                    + "en stock car le maximum autoris?? dans le tableau a d??j??"
                    + "??t?? atteint.\n");
            return;
        } */
        
        int typeProduit = choisirOption("ajouter un produit alimentaire",
                "ajouter un produit sanitaire");
        System.out.print("Saisissez le nom du produit ?? ajouter: ");
        String nom = lireChaine();
        System.out.print("Saisissez la quantit?? disponible de ce produit: ");
        int quantite = lireEntier("non negatif");
        
        Product product;
        if (typeProduit == 1) {
            System.out.print("Saisissez la date limite de consommation (au "
                    + "format jj/mm/aaaa): ");
            ZonedDateTime date = lireDate();
            product = new FoodProduct(nom, quantite, date);
        }
        else
            product = new SanitaryProduct(nom, quantite);
        
        if (get(stockNo).ajouterProduit(product))
            System.out.println("\nLe produit saisi a ??t?? ajout?? au stock "
                    + "avec succ??s.\n");
        else
            System.out.println("\nIl n'a pas ??t?? possible d'ajouter le "
                    + "produit saisi au stock.\n");
    }
    
    private void afficherMenu1() {
        int choix = choisirOption("cr??er un stock",
                "ajouter des produits ?? un stock");
        if (choix == 1)
            creerStock();
        else
            ajouterProduits();
    }
    
    private void afficherCaracteristiquesProduit() {
        if (size == 0) {
            System.out.println("Aucun stock n'a ??t?? cr????\n");
            return;
        }
        
        System.out.println("AFFICHER LES CARACT??RISTIQUES D'UN PRODUIT DU "
                + "STOCK");
        int stockNo = choisirStock();
        System.out.print("Saisissez le nom du produit ?? afficher: ");
        String nom = lireChaine();
        System.out.println();
        
        get(stockNo).afficherProduit(nom);
    }
    
    private void modifierQuantiteProduit() {
        if (size == 0) {
            System.out.println("Aucun stock n'a ??t?? cr????\n");
            return;
        }
        
        int choix = choisirOption("ajouter une quantit?? d'un produit",
                "retirer une quantit?? d'un produit");
        
        String action;
        if (choix == 1)
            action = "ajouter";
        else
            action = "retirer";
        
        System.out.println(action.toUpperCase() + " UNE QUANTIT?? D'UN PRODUIT");
        int stockNo = choisirStock();
        System.out.print("Saisissez le nom du produit: ");
        String nom = lireChaine();
        System.out.print("Saisissez le nombre d'unit??s ?? " + action + ": ");
        int diff = lireEntier("non negatif");
        System.out.println();
        
        if (action.equals("retirer"))
            diff *= -1;
        
        System.out.println(get(stockNo)
                .modifierQuantite(nom, diff));
        System.out.println();
    }
    
    public void afficherMenuPrincipal() {
        System.out.println("\nBonjour.");
        int choix;
        do {
            choix = choisirOption(
                    "cr??er un stock/ajouter des produits dans un stock",
                    "afficher les caract??ristiques d'un produit du stock",
                    "ajouter/retirer une quantit?? d'un produit donn?? au stock",
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
            Logger.getLogger(MyShop.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}
