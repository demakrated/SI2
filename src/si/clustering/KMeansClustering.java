/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.clustering;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import si.io.ClusterFile;
import si.io.SIFTFile;
import java.lang.Math;
import java.util.Date;
import si.io.Grafico;
import si.io.Prueba2D;
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
    
    //centroides iniciales aleatorios
    private byte[][] centroidesIniciales;

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

    //getter de los centroides
    public byte[][] getCentroides() {
        return centroides;
    }

    //setter de los centroides
    public void setCentroides(byte[][] centroides) {
        this.centroides = centroides;
    }

    //getter de las pertenencias
    public int[] getPertenencias() {
        return pertenencias;
    }

    //setter de las pertenencias
    public void setPertenencias(int[] pertenencias) {
        this.pertenencias = pertenencias;
    }
    
    //getter de centroides iniciales
    public byte [][] getCentroidesIniciales(){
        
        return centroidesIniciales;
              
    }
    
    //funcion que calcula distancias euclideas con los vectores (o puntos)
    public double distanciaEuclidea(byte [] vector, int centroide){

        double res1=0,res2=0, resultado=0;

            for(int j=0; j<vector.length; j++){
                res1 = Math.pow(((double)vector[j] - (double)this.getCentroides()[centroide][j]),2);
                res2 += res1;
                res1 = 0;
            }
            res2 = Math.sqrt(res2); //media de un punto con un cluster
            resultado += res2;
            res2 = 0;
   
        return resultado;
    }
    
    //funcion que me devuelve el minimo de un vector
    public double minimo(ArrayList<Double> vector){
        
        double minimo = vector.get(0);
        
        for(int i=1; i<vector.size();i++){
            if(vector.get(i) < minimo){
                minimo = vector.get(i);
            }
        }
        return minimo;
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
        this.centroidesIniciales = new byte [numClusters][tamVector];
        Random random = new Random();
        int iteracion = 0;
        ArrayList<Double> cluster = new ArrayList<Double>();    //variable que contendrá los valores a cada centroide de un punto
        
        //INICIALIZACIÓN DE CLUSTERS
        for(int i=0; i<numClusters; i++){      //voy asignando clusters a los valores de la semilla random
            this.getCentroides()[i] = vectores.get(random.nextInt(numVectores)); 
            this.centroidesIniciales[i] = this.getCentroides()[i];  //me guardo los iniciales
        }
        
        //variable para salir antes del bucle si no hay cambios en loas pertenencias
        boolean cambios = true;
        
        //bucle principal de K-Medias
        while(cambios && iteracion < maxIter){
            
            cambios = false;
            //ASIGNAR VECTORES A CLUSTERS
            //mirar distancias de cada punto con cada cluster y asignar pertenencias
            for(int j=0;j<numVectores;j++){ //cojo punto a punto
                for(int i=0; i< numClusters; i++){  //calculo distancias, selecciono la menor y se lo asigno
                    //calculo cada distancia con cada punto a un cluster
                    cluster.add(distanciaEuclidea(vectores.get(j),i));  //calculo las distancias a cada cluster
                }
                double minimo = minimo(cluster);   //guardo el valor minimo
                for(int i=0;i<numClusters; i++){    //busco el indice minimo en el cluster y lo asigno
                    if(cluster.get(i) == minimo){
                        if(pertenencias[j] != i){
                            cambios = true;
                        }
                        pertenencias[j] = i;    //para cada vector asigno su pertenencia a un cluster
                    }
                }
                cluster.clear();
            }
            
            //array que contendrá los puntos de cada cluster
            ArrayList<byte[]> puntos = new ArrayList<byte[]>();
            
            //bucle que recalcula clusters
            for(int p=0;p<numClusters;p++){ //para cada cluster genero vector con los k lo tienen asignado
                for(int i=0;i<numVectores;i++){ //cojo punto a punto
                    if(pertenencias[i] == p){    //si dicho vector pertenece al cluster
                       puntos.add(vectores.get(i));    //guardo los vectores del cluster en el array
                    }
                }
                byte [] centroideNuevo = new byte[tamVector];
                int media = 0;
                //calculo las medias con los vectores de cada cluster
                for(int i=0;i<tamVector;i++){
                    for(int j=0;j<puntos.size();j++){
                        media += puntos.get(j)[i];
                    }   //voy asignando las componentes del nuevo centroide
                    if(!puntos.isEmpty()){
                        centroideNuevo[i] = (byte) (media/puntos.size()); 
                        this.getCentroides()[p] = centroideNuevo;      //reasigno centroides
                    }
                    media = 0;
                }
                
                puntos.clear();
                        
            }
            iteracion++;
            System.out.println(iteracion);
        }
    }


    public static void main(String[] args) throws IOException {
        
        if (args.length < 2) {
            System.err.println("Número de parámetros incorrecto");
            System.out.println("Uso: java -cp si.clustering.KMeansClustering -jar practica2.jar  k max_iteraciones dir_bd_imag fich_result");
            System.err.println("\t k: nº de clusters a generar (tamaño del 'vocabulario visual')");
            System.err.println("\t max_iteraciones: máximo de iteraciones");
            System.err.println("\t dir_bd_imag: directorio con las imágenes, con un subdirectorio por cada clase");
            System.err.println("\t fich_result: fichero donde se guardarán los centroides de los clusters encontrados");
        } else {

            //List<byte[]> vectores = new Prueba2D().readFile(args[4]); //prueba2D
            //Grafico grafico = new Grafico(); //prueba2D
            //KMeansClustering clusterer = new KMeansClustering(vectores);
            
             
            //grafico.run(vectores, clusterer.getCentroides(), clusterer.getCentroidesIniciales()); //prueba2D
            List<byte[]> vectores = new SIFTFile().readFileset(args[2]);         
            //KMeansClustering clusterer = new KMeansClustering(vectores);
            //clusterer.doClustering(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            //new ClusterFile().writeClusters(clusterer.getCentroides(), args[3]);
            for(int k=60;k<=60;k+=10) {
                KMeansClustering clusterer = new KMeansClustering(vectores);
                System.out.println("Entra doClustering con k = " + k);
                clusterer.doClustering(k, Integer.parseInt(args[1]));
                System.out.println("Fin doClustering");
                new ClusterFile().writeClusters(clusterer.getCentroides(), k+"clusters"+Integer.parseInt(args[1])+"iteraciones.txt");
            }
        }
    }
}
