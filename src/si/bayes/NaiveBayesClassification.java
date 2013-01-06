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
        
        DirNavigator dn = new DirNavigator(nomFichQuant, ".quant");
        File[] dirsClases = dn.getSubdirs();
        int contador = 0;
        
        //arreglar moverse entre ficheros y directorios
        for (File dir : dirsClases) {
            matriz = null;
            matriz1 = null;
            for (File f : dn.getFiles(dir)) {
                if(contador == 0){
                    matriz = bf.leerFichero(f);
                    tamanyo.add(matriz.length);
                }
                System.err.println(f.getName());
                
            }
            //arreglar hasta aqui
            //matriz = matriz1;
            //cada fila es una clase en la matriz, y cada posicion la prob de cada foto
            for(int i=0;i<matriz.length && contador < 1;i++){   //recorro filas (clases)
                for(int j=0;j<matriz[0].length;j++){
                    sumatorio += Math.log(matriz[i][j]);  //guardo resultados del sumatorio de cada clase
                }
                probabilidades.add(sumatorio);  //guardo la probabilidad de cada clase
                probClases.add(minimo(probabilidades));
                sumatorio = 0;
                clases.add(dir.getName());  //guardo el nombre de cada clase en el array
                probabilidades.clear();
            }
            contador++;
        }
        masProbable = minimo(probClases);   //clase más probable
        System.out.print("La clase más probable es: ");
        System.out.println(clases.get(probClases.indexOf(masProbable)));
    }
    
    public double minimo(ArrayList<Double> array){
        
        double minimo = array.get(0);
        
        for(int i=1;i<array.size();i++){
            if(array.get(i) < minimo){
                minimo = array.get(i);
            }
        }
        
        return minimo;
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
