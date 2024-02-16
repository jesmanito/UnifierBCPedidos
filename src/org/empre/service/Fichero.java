package org.empre.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;

public class Fichero implements Serializable {
 
    private static void guardarArrayList(ArrayList<Object> pacientes) {
 
        String fichero = "c:/tmp/pacientes.txt";
 
        try {
            ObjectOutputStream ficheroSalida = new ObjectOutputStream(new FileOutputStream(fichero));
            ficheroSalida.writeObject(pacientes);
            ficheroSalida.flush();
            ficheroSalida.close();
            System.out.println("Pacientes guardados correctamente...");
 
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error: El fichero no existe. ");
        } catch (IOException ioe) {
            System.out.println("Error: Fallo en la escritura en el fichero. ");
        }
 
    }
 
    public static void main(String[] args) {
  /*      
        ArrayList<Object> a = new ArrayList<>();
        a.add("hola");
        a.add("mundo");
        a.add("java");
        Fichero.guardarArrayList(a);
*/
    
        File file = new File(
                  "c:/tmp/pacientes.txt");
        

              BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
        }


        String st;
        try {
            while ((st = br.readLine()) != null)

                // Print the string
                System.out.println(st);
        } catch (IOException e) {
        }


    }
}