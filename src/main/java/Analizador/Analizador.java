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
     private String data = "";
     private int rowCount = 1;
     private int columnCount = 0;
     private final int[][] stateAuto = new int[13][10];
     private int actualState = 0;
     private int previousState;
     
     /*                    error -> -<1
          S0   -> 0    letra -> 0
          S1   -> 1    numero -> 1
          S2   -> 2    . --> 2
          S3   -> 3   (  --> 3
          S4   -> 4   )  --> 4
          S5   -> 5   [  --> 5
          S6   -> 6   ]  --> 6
          S7   -> 7   {  --> 7
          S8   -> 8   }  --> 8
          S9   -> 9   Signos/Demas  --> 9
          S10 -> 10
          S11 -> 11
          S12 -> 12
      */
     {
          //estados alfanumericos y numericos
          stateAuto[0][0] = 1;     stateAuto[0][1] = 3;    stateAuto[0][2] = -1;     
          stateAuto[1][0] = 1;     stateAuto[1][1] = 2;    stateAuto[1][2] = -1;
          stateAuto[2][0] = 1;     stateAuto[2][1] = 2;    stateAuto[2][2] = -1;
          stateAuto[3][0] = -1;    stateAuto[3][1] = 3;    stateAuto[3][2] = 4;
          stateAuto[4][0] = -1;    stateAuto[4][1] = 5;    stateAuto[4][2] = -1;
          stateAuto[5][0] = -1;    stateAuto[5][1] = 5;    stateAuto[5][2] = -1;
          
          //estados de signos de agrupacion
          
          stateAuto[0][3] = 6;     stateAuto[0][5]=8;      stateAuto[0][7] = 10;
          stateAuto[6][4] = 7;     stateAuto[8][6]=9;      stateAuto[10][8] = 11;
          stateAuto[0][9] = 12;
          
          //estados de error
          for (int i = 0; i < stateAuto.length; i++) {
               System.out.println("\n");
               for (int j = 0; j < stateAuto[0].length; j++) {
                    if (stateAuto[i][j] == 0 ) {
                              stateAuto[i][j] = -1;
                    } 
                    
                    System.out.print("["+stateAuto[i][j] +"] ");
               }
          }
          System.out.println("");
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
               
               if (Character.isSpaceChar(tmpChar)) {
                    saveToken(token);
                    actualState = 0;
                    token = "";
                    if (tmpChar == '\n') {
                         rowCount++;
                         columnCount = 0;
                    }
               } else {
                    state0(tmpChar, rowCount, columnCount);
               }
          }
     }

     private int nextState(int previousState, int typeChar) {
          int state = -1;
          if (typeChar >= 0 && typeChar <= 13) {
               state = stateAuto[previousState][typeChar];
          }
          return state;
     }

     private int getTypeChar(char symbol) {
          int type = -1;

          if (Character.isLetter(symbol)) {
               type = 0;
          }
          else if (Character.isDigit(symbol)) {
               type = 1;
          }
          else if (symbol == '.') {
               type = 2;
          } else if(symbol == '('){
               System.out.println("se abrio un parentesis");
               type = 3;
          } else if (symbol == ')') {
               System.out.println("se cerro un parentesis");
               type = 4;
          } else if (symbol == '[') {
               System.out.println("se abrio un corchete");
               type = 5;
          } else if (symbol == ']') {
               System.out.println("se cerro un corchete");
               type = 6;
          } else if (symbol == '{') {
               System.out.println("se abrio una llave");
               type = 7;
          } else if (symbol == '}') {
               System.out.println("se cerro una llave");
               type = 8;
          } else if (symbol == '\n'){
               System.out.println("salto de linea");
          } else {
               System.out.println("venia un signo");
               type = 9;
          }
          return type;
     }

     private String getToken(char symbol) {
          token += symbol;
          return token;
     }

     private void state0(char symbol, int row, int column) {
          int tmpState = nextState(actualState, getTypeChar(symbol));
          previousState = actualState;
          actualState = tmpState;
          if (symbol != '\n') {
               System.out.println("Del estado " +previousState +" pasamos con " +symbol +" al estado " + actualState);               
          }

          if (actualState == -1) {
               System.out.println("hubo un error ");
               if(symbol!= '\n'){
                    data += "existe un error con el symbolo " + symbol + " en la linea " + row + " casila " + column+" donde antes se lee " +token +"\n";   
               }
                wrongToken();
          } else {
               System.out.println(getToken(symbol));
          }
     }

     private void saveToken(String tmpToken) {
          if (actualState == 1 || actualState == 2 || previousState == 1 || previousState == 2) {
               tokenBag.saveToken(tmpToken, 0);
          } if (actualState == 3 || previousState == 3) {
               tokenBag.saveToken(tmpToken, 1);
          } if (actualState == 5 || previousState == 5 ) {
               tokenBag.saveToken(tmpToken, 2);
          } if(actualState == 7 || actualState == 9 || actualState == 11 || actualState == 12){
               tokenBag.saveToken(tmpToken, 3);

          } 
     }
     
     private void wrongToken(){
          saveToken(token);
          token="";
          actualState=0;
     }
     
     public String getData() {
          return data + "\n";
     }   
}