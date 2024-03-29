/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.bayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import si.io.BayesFile;
import si.io.DirNavigator;
import si.io.QuantFile;

/**
 * Implementa el aprendizaje con naive bayes
 *
 * @author DCCIA
 */
public class NaiveBayesLearning {

    //TODO: completad este método con vuestro código para aprendizaje con Naive Bayes. El código que se va moviendo por los subdirectorios
    //y procesando cada fichero ya está hecho, añadid el cálculo de la tabla de frecuencias
    /**
     * Implementa el algoritmo naive bayes de aprendizaje
     *
     * @param nomDirImags nombre del directorio con las imágenes de la base de
     * datos
     * @param tamVoc tamaño del vocabulario visual (número de clusters)
     * @param nomFichResult nombre del fichero resultante donde guardar la tabla
     * de probabilidades
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void learn(String nomDirImags, int tamVoc, String nomFichResult) throws FileNotFoundException, IOException {
 
        //obtenemos los subdirectorios (recordar que cada uno es una clase)
        DirNavigator dn = new DirNavigator(nomDirImags, ".quant");
        File[] dirsClases = dn.getSubdirs();

        QuantFile qf = new QuantFile();
        int [] pertenencias;
        ArrayList<Integer> vector = new ArrayList<Integer>();
        ArrayList<ArrayList <Integer>> vectores = new ArrayList<ArrayList <Integer>>();
        ArrayList<String> clases = new ArrayList<String>();
        
        int contadorClases = 0;
        
        //vamos viajando por los subdirectorios...
       for (File dir : dirsClases) {
            System.out.println("Clase " + dir.getName());
            clases.add(dir.getName());
            for(int i=0; i<tamVoc;i++){
                vector.add(0);
            }
            contadorClases++;     //contador de clases
            //...y procesando todos los ficheros con la extensión especificada (.quant) en del directorio  
            for (File f : dn.getFiles(dir)) {
                System.out.println("\t Procesando fichero " + f.getName());
                pertenencias = qf.leerFichero(f);
                for(int i=0;i<pertenencias.length;i++){ //guardo en el vector, en la posición del índice indicada
                    vector.set(pertenencias[i], vector.get(pertenencias[i])+1);
                }  
            }   //guardo el vector clonándolo para poder borrarlo y reusarl la variable
            vectores.add((ArrayList <Integer>) vector.clone());
            vector.clear(); 
        }
       
        System.out.println("Construyendo tabla de frecuencias...");
        
        //matriz o tabla con los valores a guardar
        double [][] matriz = new double[tamVoc][contadorClases];
        int sumatorio=0;
        
        for(int i=0;i<contadorClases;i++){
            sumatorio=0;
            for(int j=0;j<tamVoc;j++){  //hago el sumatorio
                sumatorio += vectores.get(i).get(j);
            }
            for(int j=0;j<tamVoc;j++){  //aplico la fórmula del aprendizaje y guardo datos
                matriz[j][i] = (1.0 + (double)vectores.get(i).get(j)) / ((double)(tamVoc + sumatorio));
            }
        }
        //escribo y guardo la tabla en un fichero
        BayesFile bf = new BayesFile();
        bf.escribirFichero(nomFichResult, matriz,clases);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        NaiveBayesLearning learner = new NaiveBayesLearning();
        if (args.length != 3) {
            System.err.println("Número de parámetros incorrecto");
            System.err.println("Uso: java -cp si.bayes.NaiveBayesLearning -jar practica2.jar dir_sift_cuant k fich_probs");
            System.err.println("\t dir_bd_imag: directorio con los .sift cuantizados, con un subdirectorio por cada clase");
            System.err.println("\t k: nº de clusters (tamaño del 'vocabulario visual')");
            System.err.println("\t fich_probs: fichero donde se guardará la tabla con las probabilidades");            

        } else {
            try {
                int tamVoc = Integer.parseInt(args[1]);
                learner.learn(args[0], tamVoc, args[2]);
            } catch (NumberFormatException nfe) {
                System.err.println("Tamaño de vocabulario (nº clusters) incorrecto");
            }
        }
    }
}
