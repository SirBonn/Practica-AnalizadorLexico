/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import object.TokenBag;

/**
 *
 * @author byron
 */
public class Analizador {

     private final char[] separateInput;
     TokenBag tokenBag;
     private String token = "";
     private String data;
     private int rowCount = 1;
     private int columnCount = 0;

     // private char tmpPreChar = '\0';
     private final int[][] stateAuto = new int[6][4];
     private int actualState = 0;

     /*   S0 -> 0   letra -> 0
           S1-> 1    numero -> 1
          S2 -> 2    . --> 2
          S3 -> 3   error -> -<1
          S4 -> 4
          S5 -> 5
      */
     {
          stateAuto[0][0] = 1;
          stateAuto[0][1] = 3;
          stateAuto[0][2] = -1;
          stateAuto[1][0] = 1;
          stateAuto[1][1] = 2;
          stateAuto[1][2] = -1;
          stateAuto[2][0] = 1;
          stateAuto[2][1] = 2;
          stateAuto[2][2] = -1;
          stateAuto[3][0] = -1;
          stateAuto[3][1] = 3;
          stateAuto[3][2] = 4;
          stateAuto[4][0] = -1;
          stateAuto[4][1] = 5;
          stateAuto[4][2] = -1;
          stateAuto[5][0] = -1;
          stateAuto[5][1] = 5;
          stateAuto[5][2] = -1;

     }

     public Analizador(String inputText, TokenBag tokenBag) {
          separateInput = inputText.toCharArray();
          this.tokenBag = tokenBag;
          readTokens();
     }

     private void readTokens() {
          char tmpChar;

          for (int i = 0; i < this.separateInput.length; i++) {
               tmpChar = separateInput[i];
               columnCount++;
               if (tmpChar != '\n' && tmpChar != ' ') {
                    state0(tmpChar, rowCount, columnCount);
               } else {
                    saveToken(token);
                    actualState = 0;
                    token = "";
                    if (tmpChar == '\n') {
                         rowCount++;
                         columnCount = 0;
                    }
               }
          }
     }

     private int nextState(int previousState, int typeChar) {
          int state = -1;

          if (typeChar >= 0 && typeChar <= 2) {
               state = stateAuto[previousState][typeChar];
          }

          return state;
     }

     private int getTypeChar(char symbol) {
          int type = -1;

          if (Character.isLetter(symbol)) {
               type = 0;
          }
          if (Character.isDigit(symbol)) {
               type = 1;
          }
          if (symbol == '.') {
               type = 2;
          }

          return type;
     }

     private String getToken(char symbol) {
          token += symbol;
          return token;
     }

     private void state0(char symbol, int row, int column) {
          int tmpState = nextState(actualState, getTypeChar(symbol));
          actualState = tmpState;

          if (actualState == -1) {
               System.out.println("hubo un error ");
               data = "existe un error con el symbolo " + symbol + " en la linea " + row + " casila " + column;
               actualState = 0;
          } else {
               System.out.println(getToken(symbol));
          }
     }

     private void saveToken(String tmpToken) {
          if (actualState == 1 || actualState == 2) {
               tokenBag.saveToken(tmpToken, 0);
          } else if (actualState == 3) {
               tokenBag.saveToken(tmpToken, 1);
          } else if (actualState == 5) {
               tokenBag.saveToken(tmpToken, 2);
          }

     }
     
     public String getData() {
          return data + "\n";
     }
     
}
