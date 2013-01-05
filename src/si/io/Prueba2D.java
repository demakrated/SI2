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

/**
 *
 * @author mireia
 */
public class Prueba2D {
    public List<byte[]> readFile(String nomFich) throws FileNotFoundException {
        List<byte[]> vectores = new ArrayList<byte[]>();
        File fich = new File(nomFich);
        Scanner sc = new Scanner(fich);
        //Hay que usar este locale porque si no Scanner usa el español y espera los reales con coma decimal
        sc.useLocale(Locale.ENGLISH);
        //Leer cabecera: número de features y longitud del vector descriptor.
        int numFeatures = sc.nextInt();
        int tamDescriptor = sc.nextInt();
        //ir leyendo las features
        for (int i = 0; i < numFeatures; i++) {
            byte[] vector = new byte[tamDescriptor];
            for (int j = 0; j < tamDescriptor; j++) {
                vector[j] = (byte)(sc.nextShort());
            }
            vectores.add(vector);
        }
        return vectores;
    }
}
