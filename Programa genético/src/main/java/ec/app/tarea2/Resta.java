/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.app.tarea2;

import ec.app.tarea2.parametros;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;
import java.util.ArrayList;

/**
 *
 * @author obi
 */
public class Resta extends GPNode{
    public String toString() { return "Resta"; }

    public void checkConstraints(final EvolutionState state,
                                 final int tree,
                                 final GPIndividual typicalIndividual,
                                 final Parameter individualBase)
        {
        super.checkConstraints(state,tree,typicalIndividual,individualBase);
        if (children.length!=2)
            state.output.error("Incorrect number of children for node " + 
                               toStringForError() + " at " +
                               individualBase);
        }
    public void eval(final EvolutionState state,
                     final int thread,
                     final GPData input,
                     final ADFStack stack,
                     final GPIndividual individual,
                     final Problem problem)
        {
        
        CaractSelectData salida = ((CaractSelectData)(input));

        children[0].eval(state,thread,input,stack,individual,problem);
        CaractSelectData hijo1=salida.clone();
        
        children[1].eval(state,thread,input,stack,individual,problem);
        CaractSelectData hijo2=salida.clone();
        
        parametros par=new parametros();
        if(hijo1.featuresSize()>hijo2.featuresSize()){
            //Si el tamaño del hijo 1 es mayor que el hijo 2
            salida=hijo1.clone();
            for(int i=0;i<hijo2.featuresSize();i++){
                int numero = Math.abs((hijo1.features[i]-hijo2.features[i])%par.cantidadTotalFeatures);
                //System.out.println("Resta1: "+numero);
                if(!salida.featuresContains(numero)){
                    salida.features[i]= numero;
                }
            }
        }else{
            //Si el tamaño del hijo 2 es mayor que el del hijo 1
            for(int i=0;i<hijo1.featuresSize();i++){
                int numero = Math.abs((hijo1.features[i]-hijo2.features[i])%par.cantidadTotalFeatures);
                //System.out.println("Resta2: "+numero);
                if(!salida.featuresContains(numero)){
                    salida.features[i]= numero;
                }
            }
        }
//        if(par.printFunciones)System.out.println("Resta: "+hijo1.featuresToString()+" - "+hijo2.featuresToString()+" = "+salida.featuresToString());
        
        
        }
}
