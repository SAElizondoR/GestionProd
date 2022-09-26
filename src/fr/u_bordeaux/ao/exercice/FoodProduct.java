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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author sergio
 */
public class FoodProduct extends Product {
    private ZonedDateTime dateLimiteConsomation;
    
    public FoodProduct(String nom, int quantite,
            ZonedDateTime dateLimiteConsomation) {
        super(nom, quantite);
        this.dateLimiteConsomation = dateLimiteConsomation;
    }
    
    @Override
    public void afficher() {
        super.afficher();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("Date limite de consommation: %s\n",
                dateLimiteConsomation.format(formatter));
    }
    
    public boolean canBeSold() {
        ZonedDateTime now = ZonedDateTime.now();
        return now.toInstant().isBefore(
                dateLimiteConsomation.minusDays(3).toInstant());
    }
}
