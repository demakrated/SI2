/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author danielpedrajasvandevelde
 */
public class BayesFile {
    
    //funcion para escribir en un fichero la matri
    public void escribirFichero(String fichero, double [][] matriz, ArrayList <String> clases) throws FileNotFoundException{
        
        PrintWriter pw = new PrintWriter(fichero);
        
        for(int i=0; i<clases.size(); i++){
            pw.print(clases.get(i) + " ");
        }
        pw.println();
        
        pw.println(matriz.length + " " + matriz[0].length); //tamaño de matriz y vectores 
        for(int i=0; i<matriz.length;i++){
            for(int j=0;j<matriz[0].length;j++){
                pw.print(matriz[i][j] + " ");
            }
            pw.println();
            
        }
        pw.close();
    }
    
    //funcion para leer un fichero o construir la matriz
    public double [][] leerFichero(File fichero) throws FileNotFoundException{
        
        Scanner sc = new Scanner(fichero);
        sc.useLocale(Locale.ENGLISH);
        int filas,columnas;
        
        double [][] matriz;
        
        sc.nextLine();
        
        filas = sc.nextInt();
        columnas = sc.nextInt();
       
        matriz = new double[filas][columnas];
        //sc.nextLine();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = sc.nextDouble();
            }
        }

        sc.close();
        
        return matriz;
    }
    
    public ArrayList <String> leerClases(File fichero) throws FileNotFoundException{
        
        Scanner sc = new Scanner(fichero);
        ArrayList <String> clases = new ArrayList <String>();
        String aux = "";
        aux = sc.nextLine();
        String[] array = aux.split(" ");
        
        for(int i=0; i<array.length;i++){
            clases.add(array[i]);
        }
        
        return clases;
    }
}
