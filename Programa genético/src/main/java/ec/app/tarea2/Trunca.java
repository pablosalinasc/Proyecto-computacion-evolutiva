/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.app.tarea2;

import ec.*;
import ec.gp.*;
import ec.util.*;
import java.util.ArrayList;

/**
 *
 * @author obi
 */
public class Trunca extends GPNode{
    public String toString() { return "Truncar"; }

    public void checkConstraints(final EvolutionState state,
                                 final int tree,
                                 final GPIndividual typicalIndividual,
                                 final Parameter individualBase)
        {
        super.checkConstraints(state,tree,typicalIndividual,individualBase);
        if (children.length!=1)
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
//        System.out.print("Concatenar");
        CaractSelectData salida = ((CaractSelectData)(input));
        
        children[0].eval(state,thread,input,stack,individual,problem);
//        System.out.print("    Hijo 1: "+salida.features.toString());
        CaractSelectData hijo1=salida.clone();
        int size=salida.featuresSize();
        if(size>1){
            for(int i=0;i<size;i++){
                if(i>=(size/2)){
                    salida.features[i]=-1;
                }
            }
        }
        parametros par = new parametros();
//        if(par.printFunciones)System.out.println("Truncar: "+hijo1.featuresToString()+" = "+salida.featuresToString());
        }
}
