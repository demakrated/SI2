/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.si.clustering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.*;
import si.clustering.KMeansClustering;
import si.io.SIFTFile;

/**
 *
 * @author otto
 */
public class KMeansClusteringTest {
    
    public KMeansClusteringTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    
    public static final int NUM_PUNTOS = 1000;
    public static final int DISPERSION = 30;
    
    @Test
    public void testDoClusteringRandom() {
        byte[][] semillas = {
            {-100, -100,-100},
            {0, 0, 0},
            {100, 100, 100}
        };
        Random generador = new Random(System.nanoTime());

        List<byte[]> puntos = new ArrayList<byte[]>();
        //Guarda el nº de cluster del que ha salido cada muestra
        int[] origenes = new int[NUM_PUNTOS];
        //Dimensionalidad del vector (en SIFT sería 128)
        int dimPunto = semillas[0].length;
        //Generamos NUM_PUNTOS al azar, "alrededor" de una semilla
        for(int i=0; i<NUM_PUNTOS; i++) {
            //escogemos la semilla al azar
            int numSemilla = generador.nextInt(semillas.length);
            origenes[i] = numSemilla;
            byte[] punto = new byte[dimPunto];
            //Generamos las coordenadas del punto al azar tomando como base la semilla más un nº aleatorio en [0,DISPERSION]
            for(int d=0; d<dimPunto; d++) {
                do {
                    punto[d] = (byte) (semillas[numSemilla][d] + generador.nextInt(DISPERSION));
                //Por si acaso nos hemos salido del rango al sumar o restar, repetimos la generación en ese caso    
                } while (Math.abs(punto[d]-semillas[numSemilla][d])>DISPERSION);
            }
            puntos.add(punto);
        }
        KMeansClustering kmc = new KMeansClustering(puntos);
        kmc.doClustering(semillas.length, 1000);
        int[] pertenencias = kmc.getPertenencias();
        
        //Hacemos una correspondencia entre las etiquetas de los cluster originales y las detectadas por el algoritmo
        //Filas: etiquetas de los cluster originales (nº semilla de la que se han generado)
        //Columnas: etiquetas de los cluster detectados por el algoritmo
        int[][] tablaCorresp = new int[semillas.length][semillas.length];
        for(int i=0; i<NUM_PUNTOS; i++) {
            tablaCorresp[origenes[i]][pertenencias[i]]++;
        }
        //Contamos el número de valores distintos de cero en cada fila. Debería haber solo 1. 
        //Si no, es que el algoritmo ha separado en dos cluster "ficticios" un solo cluster real
        //También el número de valores distintos de cero en cada columna. Debería haber solo 1. 
        //Si no, es que el algoritmo ha agrupado varios cluster reales en uno solo ficticio
        int[] valoresMayoresCeroEnFilas = new int[semillas.length];
        int[] valoresMayoresCeroEnColumnas = new int[semillas.length];
        for (int f=0; f<tablaCorresp.length; f++) {
            for(int c=0; c<tablaCorresp[0].length; c++) {
                System.out.print(tablaCorresp[f][c] + "\t");
                if (tablaCorresp[f][c]>0) {
                    valoresMayoresCeroEnFilas[f]++;
                    valoresMayoresCeroEnColumnas[c]++;
                }
            }
            System.out.println();
        }
        //Ahora comprobamos que en cada fila o en cada columna solo haya un valor mayor que 0
        for(int i=0; i<semillas.length; i++) {
            if (valoresMayoresCeroEnFilas[i]>1)
                fail("En la fila " + i + " hay más de un valor");
            if (valoresMayoresCeroEnColumnas[i]>1)
                fail("En la columna " + i + " hay más de un valor");
        }
                
   }

   


   
}
