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
public class BayesFile {
    
    public void escribirFichero(String fichero, double [][] matriz) throws FileNotFoundException{
        
        PrintWriter pw = new PrintWriter(fichero);
        
        pw.println(matriz.length + " " + matriz[0].length); //tamaño de matriz y vectores 
        for(int i=0; i<matriz.length;i++){
            for(int j=0;j<matriz[0].length;j++){
                pw.print(matriz[i][j] + " ");
            }
            pw.println();
            
        }
        pw.close();
    }
    
    public double [][] leerFichero(File fichero) throws FileNotFoundException{
        
        Scanner sc = new Scanner(fichero);
        
        double [][] matriz;
       
        matriz = new double[sc.nextInt()][sc.nextInt()];
        sc.nextLine();
        

           for(int i=0; i<matriz.length;i++){
                for(int j=0;j<matriz[0].length;j++){
                    matriz[i][j] = sc.nextDouble();
                }
            }
        
        sc.close();
        
        return matriz;
    }
}
