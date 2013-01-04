/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.io;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Permite moverse por todos los subdirectorios de un directorio especificado, buscando determinado tipo de archivo
 */
public class DirNavigator {

    private FilenameFilter fnf;
    private File dir;

    /**
     * Constructor
     * @param nomDir nombre del directorio donde están todos los subdirectorios a visitar
     * @param extension extensión de los archivos que nos interesa procesar
     */
    public DirNavigator(String nomDir, final String extension) {
        this.dir = new File(nomDir);

        if (!dir.isDirectory()) {
            throw new RuntimeException(nomDir + " debería ser un directorio");
        }

        this.fnf = new FilenameFilter() {
            @Override
            public boolean accept(File file, String string) {
                return string.matches(".*" + extension + "$");
            }
        };

    }

    /**
     * Obtiene todos los subdirectorios del directorio "principal"
     * @return subdirectorios
     */
    public File[] getSubdirs() {
        FilenameFilter dirFilter = new FilenameFilter() {
            @Override
            //current es el dir donde está el archivo a chequear, name es el nombre de dicho archivo
            public boolean accept(File current, String name) {
                return new File(current,name).isDirectory();
            }
        };

        return this.dir.listFiles(dirFilter);
    }

    /**
     * Obtiene todos los ficheros (restringiéndonos al tipo que nos interesa) de un subdirectorio
     * @param dir subdirectorio donde buscar los ficheros
     * @return ficheros encontrados
     */
    public File[] getFiles(File dir) {
        if (!dir.isDirectory()) {
            throw new RuntimeException(dir.getName() + " debería ser un directorio");
        }
        return dir.listFiles(this.fnf);
    }
}
