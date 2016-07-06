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
public class palabra {
    String palabra;
    int frecuencia;
    int nDocumentos;
    int revisada=0;

    public palabra(String palabra, int frecuencia) {
        this.palabra = palabra;
        this.frecuencia = frecuencia;
        nDocumentos=1;
    }
    
    public palabra clone(){
        palabra temp=new palabra(this.palabra,this.frecuencia);
        temp.nDocumentos=this.nDocumentos;
        temp.revisada=this.revisada;
        return temp;
    }
    
    public palabra() {
    }
    
    
}
