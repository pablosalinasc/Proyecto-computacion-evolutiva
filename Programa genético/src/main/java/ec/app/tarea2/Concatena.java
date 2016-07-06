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
import ec.*;
import ec.gp.*;
import ec.util.*;
import java.util.ArrayList;

public class Concatena extends GPNode
    {
    public String toString() { return "Concatenar"; }

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
//        System.out.print("Concatenar");
        parametros par=new parametros();
        CaractSelectData salida = ((CaractSelectData)(input));
        
        children[0].eval(state,thread,input,stack,individual,problem);
//        System.out.print("    Hijo 1: "+salida.features.toString());
        CaractSelectData hijo1=salida.clone();
        
        children[1].eval(state,thread,input,stack,individual,problem);
//        System.out.print("    Hijo 2: "+salida.features.toString());
        CaractSelectData hijo2=salida.clone();
        for(int feature: hijo1.features){
            if(!salida.featuresContains(feature)&&salida.featuresSize()<par.cantidadTotalFeatures){
                salida.features[salida.featuresSize()]=feature;
            }
        }
//        if(par.printFunciones)System.out.println("Concatenar: "+hijo1.featuresToString()+" | "+hijo2.featuresToString()+" = "+salida.featuresToString());
        }
    }
