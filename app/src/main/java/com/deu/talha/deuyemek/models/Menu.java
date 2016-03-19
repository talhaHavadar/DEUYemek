package com.deu.talha.deuyemek.models;

import java.util.List;

/**
 * Created by talha on 19.07.2015.
 */
/**
 *
 {
     "dateString": "&#xC7;a, &#x15E;u 10",
     "foods": [
         {
         "cal": "250cal",
         "error": false,
         "name": "PATATES MUSAKKA"
         },
         {
         "cal": "229cal",
         "error": false,
         "name": "SU B&#xD6;RE&#x11E;&#x130;"
         }
     ]
 }
 *
 */

public class Menu {
    public String dateString;
    public List<Food> foods;

    public String getFoodsString() {
        StringBuilder sb = new StringBuilder();
        if (foods != null) {
            for (int i = 0; i < foods.size(); i++) {
                sb.append(String.format("%s (%s)", foods.get(i).name, foods.get(i).cal));
                if (i != foods.size() -1) {
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
        return null;

    }

}
