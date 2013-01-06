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
    
        double [][] matriz;
        double [][] matriz1;
        BayesFile bf = new BayesFile();
        
        double sumatorio = 0;
        ArrayList<Double> probabilidades = new ArrayList<Double>();
        ArrayList<Double> probClases = new ArrayList<Double>();
        ArrayList<String> clases = new ArrayList<String>();
        ArrayList<Integer> tamanyo = new ArrayList<Integer>();
        double masProbable = 0;
        
        DirNavigator dn = new DirNavigator("bd_test", ".quant");
        File[] dirsClases = dn.getSubdirs();
        File fich = new File(nomFichProbs);
        matriz = bf.leerFichero(fich);
        //int contador = 0;
        
        for (File dir : dirsClases) {
            System.out.println("Clase " + dir.getName());
            clases.add(dir.getName()); //guardo el nombre de cada clase en el array
            for (File f : dn.getFiles(dir)) {
                System.out.println("\t Procesando fichero " + f.getName());
                
                for(int i=0;i<matriz.length;i++){   //recorro filas (clases)
                    for(int j=0;j<matriz[0].length;j++){
                        sumatorio += Math.log(matriz[i][j]);  //guardo resultados del sumatorio de cada clase
                    }
                }
            }
            probabilidades.add(sumatorio);  //guardo la probabilidad de cada clase
            for(int i=0;i<probabilidades.size();i++){
                //System.out.println("La clase más probable: " + probabilidades.get(i));
                System.out.println(probabilidades.get(i));
            }
            probClases.add(maximo(probabilidades)); //guardo la mejor probabilidad de cada clase
            sumatorio = 0;
            probabilidades.clear();
        }
        masProbable = maximo(probClases);   //clase más probable
        System.out.print("La clase más probable es: ");
        System.out.println(clases.get(probClases.indexOf(masProbable)));
    }
    
    public double maximo(ArrayList<Double> array){
        
        double maximo = array.get(0);
        
        for(int i=1;i<array.size();i++){
            if(array.get(i) > maximo){
                maximo = array.get(i);
            }
        }
        
        return maximo;
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
