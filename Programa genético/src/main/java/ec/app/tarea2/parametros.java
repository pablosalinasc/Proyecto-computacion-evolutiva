/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.app.tarea2;

/**
 *
 * @author obi
 */
public class parametros {
    int cantidadTotalFeatures;
    int largoDataset;
    String rutaDataset;
    boolean printFunciones;
    int offsetFeatures;
    int indiceClasificaciones;
    float lambda;
    int columnaExtra;
    int offsetClases;
    public parametros(){
        //Si es true imprime el resultado de las funciones
        printFunciones=true;
        //AUS
//        lambda=0.1f;
//        rutaDataset="AUS.data";
//        largoDataset=250;
//        cantidadTotalFeatures=14;
//        offsetFeatures=0;
//        indiceClasificaciones=14;
//        columnaExtra=1;
//        offsetClases=0;
        //WBC
//        lambda=0.1f;
//        rutaDataset="WBC.data";
//        largoDataset=351;
//        cantidadTotalFeatures=30;
//        offsetFeatures=2;
//        indiceClasificaciones=1;
//        columnaExtra=0;
//        offsetClases=0;
        //PIMA
//        lambda=0.1f;
//        rutaDataset="PIMA.data";
//        largoDataset=351;
//        cantidadTotalFeatures=8;
//        offsetFeatures=0;
//        indiceClasificaciones=8;
//        columnaExtra=1;
//        offsetClases=0;
        //GC
//        lambda=0.1f;
//        rutaDataset="GC.data";
//        largoDataset=351;
//        cantidadTotalFeatures=24;
//        offsetFeatures=0;
//        indiceClasificaciones=24;
//        columnaExtra=1;
//        offsetClases=1;
        //IONO
//        lambda=0.1f;
//        rutaDataset="IONO.data";
//        largoDataset=351;
//        cantidadTotalFeatures=34;
//        offsetFeatures=0;
//        indiceClasificaciones=34;
//        columnaExtra=1;
//        offsetClases=0;
        //ENRON1
        lambda=0.1f;
        rutaDataset="dataset.out";
        largoDataset=351;
        cantidadTotalFeatures=100;
        offsetFeatures=0;
        indiceClasificaciones=100;
        columnaExtra=1;
        offsetClases=0;
        
    }
}
