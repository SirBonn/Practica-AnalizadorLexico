/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Files;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author byron
 */
public class FileDriver {

     public FileDriver() {
     }
     
     public void fileReader(JButton readButton, JTextArea textArea) {
          
          readButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooseFile = new JFileChooser();
                chooseFile.showOpenDialog(null);
                File file = chooseFile.getSelectedFile();
                try {
                    FileReader readData = new FileReader(file);
                    BufferedReader br = new BufferedReader(readData);
                    String text = "";
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        text += line + "\n";
                    }
                    textArea.setText(text);
                } catch (Exception a) {
                }
            }
        });
     }
     
     public void fileWritter(JButton exportButton, JTextArea textArea){
     
          exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser file = new JFileChooser(System.getProperty("user.dir"));
                    JOptionPane emergent = new JOptionPane();
                    file.showSaveDialog(emergent);
                    if (file.getSelectedFile() != null) {
                        try (FileWriter fileSave = new FileWriter(file.getSelectedFile())) {
                            fileSave.write(textArea.getText());
                            JOptionPane.showMessageDialog(emergent, "el archivo se guardo correctamente");
                        }
                    }
                } catch (IOException ex) {
                }
            }

        });
     }
}
