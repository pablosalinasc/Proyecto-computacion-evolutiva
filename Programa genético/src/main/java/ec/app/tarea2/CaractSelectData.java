/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.app.tarea2;

import ec.util.*;
import ec.*;
import ec.gp.*;
import java.util.ArrayList;
/**
 *
 * @author obi
 */
public class CaractSelectData extends GPData {
    public int[] features;
    
    @Override
    public CaractSelectData clone() {
    	CaractSelectData clon = new CaractSelectData();
        parametros par= new parametros();
	clon.features=new int[par.cantidadTotalFeatures];
        if(features!=null){
            for(int i=0;i<par.cantidadTotalFeatures;i++){
                clon.features[i]=features[i];
            }
        }
        return clon;
    }
    
    public int featuresSize(){
        int size=0;
        parametros par = new parametros();
        for(int i=0;i<par.cantidadTotalFeatures;i++){
            if(features[i]!=-1){
                size++;
            }
        }
        return size;
    }
    
    public boolean featuresContains(int numero){
        for(int i=0;i<features.length;i++){
            if(features[i]==numero){
                return true;
            }
        }
        return false;
    }
    
    public String featuresToString(){
        String cadena="[";
        for(int i=0;i<features.length;i++){
            if(features[i]!=-1){
                if(i==0){
                    cadena=cadena.concat(features[i]+"");
                }
                else{
                    cadena=cadena.concat(", "+features[i]);
                }
            }
        }
        cadena=cadena.concat("]");
        return cadena;
    }
    
}
