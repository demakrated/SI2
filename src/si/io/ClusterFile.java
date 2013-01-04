/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Permite leer y escribir en fichero los clusters calculados con k-medias
 * @author DCCIA
 */
public class ClusterFile {

    /**
     * Guarda un fichero de texto con los centroides de los clusters
     * @param clusters centroides: 1ª dimensión: número de cluster, 2º dimensión, componentes de cada descriptor
     * @param nomFich nombre del fichero donde queremos guardar los datos
     * @throws IOException Si se produce algún error de E/S al guardar los datos
     * @throws RuntimeException si el parámetro "clusters" tiene 0 filas y/o columnas
     */
    public void writeClusters(byte[][] clusters, String nomFich) throws IOException {
        PrintWriter pw = new PrintWriter(nomFich);
        if (clusters.length == 0 || clusters[0].length == 0) {
            throw new RuntimeException("clusters vacíos o datos incorrectos");
        }
        //Imprime el número de clusters
        int numClusters = clusters.length;
        pw.println(numClusters);
        //Imprime el tamaño de cada descriptor
        int tamDescriptor = clusters[0].length;
        pw.println(tamDescriptor);
        //Imprime los componentes del descriptor, sumándoles 128 
        //para que en la salida aparezcan valores en [0,255] y no en [-128,127]
        for (int c = 0; c < numClusters; c++) {
            for (int i = 0; i < tamDescriptor; i++) {
                pw.print(clusters[c][i] + 128 + " ");
            }
            pw.println();
        }
        pw.close();
    }

    /**
     * Lee los clusters de un fichero
     * @param nomFich nombre del fichero de donde leer los datos
     * @return array con centroides: 1ª dimensión: número de cluster, 2º dimensión, componentes de cada descriptor
     * @throws FileNotFoundException si no se encuentra el fichero. (Fdo:el Capitán Obvio)
     */
    public byte[][] readClusters(String nomFich) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(nomFich));
        int numClusters = sc.nextInt();
        int tamDescriptor = sc.nextInt();
        byte[][] clusters = new byte[numClusters][tamDescriptor];
        for (int c = 0; c < numClusters; c++) {
            for (int i = 0; i < tamDescriptor; i++) {
                //Los datos del archivo están en [0,255], los de byte en [-128,127]
                clusters[c][i] = (byte)(sc.nextShort() - 128);
            }
        }
        return clusters;
    }
}
