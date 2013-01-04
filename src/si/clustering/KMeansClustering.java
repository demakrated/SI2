/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.clustering;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import si.io.ClusterFile;
import si.io.SIFTFile;
import java.lang.Math;
//prueba git desde mac a suse
//commit desde github cliente

/**
 * Clase que debe implementar el algoritmo de k-medias.
 */
public class KMeansClustering {

    /**
     * Vectores a agrupar
     */
    private List<byte[]> vectores;
    /**
     * Número de clusters deseados (la k de k-medias)
     */
    private int numClusters;
    /**
     * Longitud de los vectores
     */
    private int tamVector;
    /**
     * Número de vectores
     */
    private int numVectores;
    /**
     * resultado de k-medias: a qué cluster pertenece cada vector
     */
    private int[] pertenencias;
    /**
     * resultado de k-medias: centroides de cada cluster
     */
    private byte[][] centroides;

    /**
     * Constructor
     *
     * @param vectores Lista de vectores descriptores SIFT a "clusterizar". Como
     * los valores de un descriptor SIFT van a estar en el rango [0,255], nos
     * basta con un byte por cada componente del vector. 
     * Cuidado, ya que en Java los bytes son con signo y el rango de byte es [-127,128]. 
     * Por tanto, un -127 en el array equivale a un 0 en el fichero SIFT,
     * y un 128 en memoria al 255 en el fichero SIFT.
     */
    public KMeansClustering(List<byte[]> vectores) {
        this.vectores = vectores;
        this.tamVector = vectores.get(0).length;
        this.numVectores = vectores.size();
        this.pertenencias = new int[numVectores];
    }

    public byte[][] getCentroides() {
        return centroides;
    }

    public void setCentroides(byte[][] centroides) {
        this.centroides = centroides;
    }

    public int[] getPertenencias() {
        return pertenencias;
    }

    public void setPertenencias(int[] pertenencias) {
        this.pertenencias = pertenencias;
    }
    
    public double distanciaEuclidea(byte[][] vector){
        
        double centroide, vc;
        double res1=0,res2=0;
        
        for(int i=0; i<tamVector; i++){
            for(int j=0; j<tamVector; j++){
                //centroide = (double)this.getCentroides()[i][j];
                //vc = vector[i][j];
                res1 = Math.sqrt(Math.pow(((double)this.getCentroides()[i][j] - (double)vector[i][j]),2));
                res2 += res1;
                res1 = 0;
            }
            res1 = 0;
            res2 = res2/tamVector;
        }
        
        
        
        
        return 0;
    }

    //TODO: implementad en este método el algoritmo de k-medias
    /**
     * Realiza el clustering de vectores usando el algoritmo de las k-medias
     *
     * @param k número de clusters a obtener
     * @param maxIter número máximo de iteraciones a ejecutar. El algoritmo
     * podría terminar antes de este número si no hubiera cambios en la
     * asignación de pertenencia a clusters
     * 
     * IMPORTANTE: 
     *  - el método debe guardar los centroides de los clusters obtenidos en la variable miembro "centroides"
     */
    public void doClustering(int k, int maxIter) {  //k son los clusters a formar
        
        this.numClusters = k;   //genero los centroides segun el numero de clusters que tengo y su tamaño
        this.setCentroides(new byte[numClusters][tamVector]);
        Random random = new Random();
        int iteracion = 0;
        
        for(int i=0; i< numClusters; i++){      //voy asignando clusters a los valores de la semilla random
            this.getCentroides()[i] = vectores.get(random.nextInt());
        }
        
        //mirar distancias de cada vector con cada cluster y asignar pertenencias
        for(int i=0; i< numClusters; i++){
            
        }
        
        //calcular distancias ente puntos de centroides
        //cada "vector" es un punto en 128 dimensiones (como si tuviera 3 dimensiones x, y, z pero 128)
        //por tanto cada centroide apunta a un vector, a un punto

    }


    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            System.err.println("Número de parámetros incorrecto");
            System.out.println("Uso: java -cp si.clustering.KMeansClustering -jar practica2.jar  k max_iteraciones dir_bd_imag fich_result");
            System.err.println("\t k: nº de clusters a generar (tamaño del 'vocabulario visual')");
            System.err.println("\t max_iteraciones: máximo de iteraciones");
            System.err.println("\t dir_bd_imag: directorio con las imágenes, con un subdirectorio por cada clase");
            System.err.println("\t fich_result: fichero donde se guardarán los centroides de los clusters encontrados");
        } else {

            List<byte[]> vectores = new SIFTFile().readFileset(args[2]);
            KMeansClustering clusterer = new KMeansClustering(vectores);
            clusterer.doClustering(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            new ClusterFile().writeClusters(clusterer.getCentroides(), args[3]);
        }
    }
}
