/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author otto sta como una moto
 */
public class SIFTFile {
    private static final String SIFT_EXT = "sift";

    public List<byte[]> readSIFTFile(File fich) throws FileNotFoundException {
        List<byte[]> vectores = new ArrayList<byte[]>();
        Scanner sc = new Scanner(fich);
        //Hay que usar este locale porque si no Scanner usa el español y espera los reales con coma decimal
        sc.useLocale(Locale.ENGLISH);
        //Leer cabecera: número de features y longitud del vector descriptor.
        int numFeatures = sc.nextInt();
        int tamDescriptor = sc.nextInt();
        //ir leyendo las features
        for (int i = 0; i < numFeatures; i++) {
            //Las coordenadas, escala y orientación las descartamos, no nos interesan
            sc.nextDouble(); //fila
            sc.nextDouble(); //columna
            sc.nextDouble(); //escala
            sc.nextDouble(); //orientación
            byte[] vector = new byte[tamDescriptor];
            for (int j = 0; j < tamDescriptor; j++) {
                vector[j] = (byte)(sc.nextShort()-128);
            }
            vectores.add(vector);
        }
        return vectores;
    }
    
     public List<byte[]> readFileset(String nomDir) {
        
        DirNavigator navigator = new DirNavigator(nomDir, SIFT_EXT);
        File[] dirsClases = navigator.getSubdirs();
        
        List<byte[]> vectores = new ArrayList<byte[]>();
        SIFTFile sfr = new SIFTFile();
        for (File dirClase : dirsClases) {
            System.out.println(dirClase.getName());
            File[] fichsImag = navigator.getFiles(dirClase);
            for (File fich : fichsImag) {
                System.out.println("\t"+fich.getName());
                try {
                    List<byte[]> vectoresLeidos = sfr.readSIFTFile(fich);
                    vectores.addAll(vectoresLeidos);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SIFTFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("TOTAL: " + vectores.size() + " descriptores");
        
        return vectores;
    }
   
    
}
