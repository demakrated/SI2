/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.clustering;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import si.io.ClusterFile;
import si.io.DirNavigator;
import si.io.SIFTFile;

/**
 * Implementa la cuantización de descriptores SIFT. De tener muchos descriptores
 * de dimensión 128 pasamos a quedarnos para cada uno solo con el número del
 * cluster más cercano
 *
 * @author DCCIA
 */
public class Quantizer {

    /**
     * Extensión por defecto de los ficheros cuantizados
     */
    public static final String EXT_QUANT = ".quant";
    /**
     * Clusters que encontró el algoritmo de k-medias
     */
    private byte[][] clusters;

    /**
     * Constructor
     *
     * @param nomFichClusters nombre del fichero con los clusters
     * @throws FileNotFoundException
     */
    public Quantizer(String nomFichClusters) throws FileNotFoundException {
        ClusterFile cf = new ClusterFile();
        clusters = cf.readClusters(nomFichClusters);
    }

    /**
     * Método ya implementado, va por los subdirectorios del directorio
     * especificado buscando archivos .sift y procesándolos
     *
     * @param nomDir nombre del directorio con la base de datos de imágenes,
     * donde se supone que están también los .sift
     * @param extension extensión a usar para el fichero cuantizado. El fichero
     * resultante tendrá el nombre del fichero .sift original, seguido de esta
     * extensión.
     * @throws IOException
     */
    public void quantizeFileset(String nomDir, String extension) throws IOException {
        DirNavigator dn = new DirNavigator(nomDir, extension);
        File[] dirsClases = dn.getSubdirs();
        for (File dir : dirsClases) {
            System.out.println("Cuantizando clase " + dir.getName());
            File[] sifts = dn.getFiles(dir);
            for (File sift : sifts) {
                System.out.println("\t" + sift.getName());
                quantizeFile(sift, sift.getCanonicalPath() + EXT_QUANT);
            }
        }
    }

    //TODO: completad este método con la cuantización de los descriptores SIFT
    /**
     * Debe cuantizar un fichero .sift. 
     * La lectura de datos del fichero .sift y el guardado del fichero cuantizado
     * ya están implementadas.
     *
     * @param fichSIFT fichero con los descriptores .sift
     * @param nomFichResult fichero resultante de la cuantización
     * @throws FileNotFoundException
     */
    void quantizeFile(File fichSIFT, String nomFichResult) throws FileNotFoundException {
        
        //Ya implementado: lectura de los descriptores SIFT de un archivo
        SIFTFile sf = new SIFTFile();
        List<byte[]> descriptores = sf.readSIFTFile(fichSIFT);
        

        //1. Implementar la cuantización en si (asignación de cada descriptor al cluster más cercano)
        // Los centroides de los clusters están en la variable miembro "clusters"
             
        //2. Implementar el guardado de datos en el fichero de nombre "nommFichResult"
        
    }

   

    public static void main(String[] args) throws FileNotFoundException, IOException {
        if (args.length != 2) {
            System.err.println("Número de parámetros incorrecto");
            System.err.println("uso: java -cp si.clustering.Quantizer -jar practica2.jar dir_fichs_sift fich_clusters");
            System.err.println("\t dir_fichs_sift: directorio donde están los .sift, con un subdirectorio por clase");
            System.err.println("\t fich_clusters: fichero con los clusters creados por k-medias");
            
        } else {
            Quantizer q = new Quantizer(args[1]);
            q.quantizeFileset(args[0], ".sift");
        }
    }
}
