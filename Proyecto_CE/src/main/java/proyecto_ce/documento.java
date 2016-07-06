/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_ce;

/**
 *
 * @author obi
 */
public class documento {
    palabra[] palabras;
    int cantPalabras;
    String nombre;
    int clasificacion;
    
    public documento(String nombre, int clasificacion) {
        this.clasificacion=clasificacion;
        this.nombre=nombre;
        palabras=new palabra[10000];
        cantPalabras=0;
    }
    
    public void insertarPalabra(String palabra){
        int bandera=0;
        if(cantPalabras>0){
            for(int i=0;i<cantPalabras;i++){
                if(palabras[i].palabra.equals(palabra)){
                    palabras[i].frecuencia++;
                    bandera=1;
                    break;
                }
            }
        }
        if(bandera==0){
            for(int i=0;i<palabras.length;i++){
                if(palabras[i]==null){
                    palabras[i]=new palabra(palabra,1);
                    cantPalabras++;
                    break;
                }
            }
        }
    }
    
    public void print(){
        System.out.println("\n-------------------------");
        System.out.println("archivo: "+nombre);
        System.out.println("clasificacion: "+clasificacion);
        System.out.println("cantidad de palabras: "+cantPalabras);
        System.out.println("\n°°°°°°°°°°°°°°°°°");
        for(int i=0;i<cantPalabras;i++){
            System.out.println(" - "+palabras[i].palabra+": "+palabras[i].frecuencia);
        }
        System.out.println("°°°°°°°°°°°°°°°°°°");
        System.out.println("-------------------------");
    }
    
    public int frecuenciaPalabra(String string){
        int frecuencia=0;
        for(int i=0;i<cantPalabras;i++){
            if(palabras[i].palabra.equals(string)){
                frecuencia=palabras[i].frecuencia;
                break;
            }
        }
        return frecuencia;
    }
    
    public documento clone(){
        documento temp=new documento(nombre, clasificacion);
        temp.cantPalabras=this.cantPalabras;
        for(int i=0;i<palabras.length;i++){
            if(this.palabras[i]!=null){
                temp.palabras[i]=this.palabras[i].clone();
            }
        }
        return temp;
    }
}
