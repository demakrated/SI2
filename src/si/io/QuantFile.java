/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author danielpedrajasvandevelde
 */
public class QuantFile {
    
    //funcion para escribir en un fichero la spertenencias
    public void escribirFichero(String fichero, int [] pertenencias) throws FileNotFoundException{
        
        PrintWriter pw = new PrintWriter(fichero);
        for(int i=0; i<pertenencias.length;i++){
            pw.println(pertenencias[i]);
        }
        pw.close();
    }
    
    //funcion para leer las pertenencias y devolverlas de un fichero
    public int [] leerFichero(File fichero) throws FileNotFoundException{
        
        Scanner sc = new Scanner(fichero);
        
        int [] pertenencias;
        int contador = 0;
        
        while(sc.hasNextInt()){
            sc.nextInt();
            contador++;
        }
        sc.close();
        sc = new Scanner(fichero);
        pertenencias = new int[contador];
        contador = 0;
        
        while(sc.hasNextInt()){
            pertenencias[contador] = sc.nextInt();
            contador++;
        }
        sc.close();
        
        return pertenencias;
    }
}
