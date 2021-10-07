/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author byron
 */
public class TokenBag {

     private final ArrayList<String> stringTokens;
     private final ArrayList<Integer> integersTokens;
     private final ArrayList<Double> doublesTokens;
     private final ArrayList<String> signArray;

     public TokenBag() {
          stringTokens = new ArrayList<>();
          integersTokens = new ArrayList<>();
          doublesTokens = new ArrayList<>();
          signArray = new ArrayList<>();
     }

     public void saveToken(String tokenIn, int type) {

          switch (type) {
               case 0:
                    stringTokens.add(tokenIn);
                    System.out.println("se guardo el string  " + tokenIn);
                    break;
               case 1:
                    integersTokens.add(Integer.parseInt(tokenIn));
                    System.out.println("se guardo el int " + tokenIn);
                    break;
               case 2:
                    doublesTokens.add(Double.parseDouble(tokenIn));
                    System.out.println("se guardo el double " + tokenIn);
                    break;
               case 3:
                    signArray.add(tokenIn);
                    System.out.println("se guardo el signo" +tokenIn);
               default:
                    break;
          }
     }

     public String showTokens() {

          Iterator<String> itString = stringTokens.iterator();
          Iterator<Integer> itInt = integersTokens.iterator();
          Iterator<Double> itDouble = doublesTokens.iterator();
          Iterator<String> itSign = signArray.iterator();

          String info = "";

          info = "Cadenas de texto validas: \n";
          while (itString.hasNext()) {
               String tmpString = itString.next();
               info += tmpString + "\n";
          }
          
          info += "\nEnteros validos: \n";
          while (itInt.hasNext()) {
               int tmpInt = itInt.next();
               info += tmpInt + "\n";
          }
          
          info += "\nDecimales validos: \n";
           while (itDouble.hasNext()) {
              double tmpDouble = itDouble.next();
               info += tmpDouble + "\n";
          }
           
          info += "\n Los signos registrados son:";
          while (itSign.hasNext()) {
              String tmpString = itSign.next();
               info+= tmpString +"\n";
          }
           
           return info;
     }

     public void clearArrays() {
          stringTokens.clear();
          integersTokens.clear();
          doublesTokens.clear();
          System.out.println("la tokenBag esta vacia :) ");
     }

}
