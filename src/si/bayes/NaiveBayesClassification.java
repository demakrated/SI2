/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.bayes;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Clasificación con naive bayes
 * @author DCCIA
 */
public class NaiveBayesClassification {

    //TODO: implementad en este método el algoritmo de clasificación con naive bayes
    /**
     * Clasifica un fichero con .sift cuantizados basándose en una tabla de probabilidades calculada por NaiveBayesLearning
     * @param nomFichQuant nombre del fichero con los .sift cuantizados
     * @param nomFichProbs nombde del fichero que contiene la tabla de probabilidades
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void classify(String nomFichQuant, String nomFichProbs) throws FileNotFoundException, IOException, ClassNotFoundException {
    }
    
    public static void main(String[] args ) throws IOException, FileNotFoundException, ClassNotFoundException {
        NaiveBayesClassification nbc = new NaiveBayesClassification();
        if (args.length!=2) {
            System.err.println("Número de parámetros incorrecto");
            System.err.println("Uso: java -cp si.bayes.NaiveBayesClassification -jar practica2.jar fich_imagen_cuantizado fich_probs");
            System.err.println("\t fich_imagen_cuantizado: fichero con los .sift cuantizados calculado para la imagen a clasificar");
            System.err.println("\t fich_probs: fichero donde el aprendizaje almacenó las probabilidades de naive bayes");
        }
        else {
            nbc.classify(args[0], args[1]);  
        }
    }
}
