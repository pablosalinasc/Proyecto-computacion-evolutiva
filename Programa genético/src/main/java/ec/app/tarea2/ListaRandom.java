/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.app.tarea2;

import ec.app.tarea2.parametros;
import ec.*;
import ec.gp.*;
import ec.util.*;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author obi
 */
public class ListaRandom extends GPNode
{
    public String toString() { return "ListaRandom"; }

    public void checkConstraints(final EvolutionState state,
                                 final int tree,
                                 final GPIndividual typicalIndividual,
                                 final Parameter individualBase)
        {
        super.checkConstraints(state,tree,typicalIndividual,individualBase);
        if (children.length!=0)
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
        
        CaractSelectData rd = ((CaractSelectData)(input));
        parametros par=new parametros();
        rd.features=new int[par.cantidadTotalFeatures];
        for(int i=0;i<par.cantidadTotalFeatures;i++){
            rd.features[i]=-1;
        }
        Random  rnd = new Random();
        int contador=0;
        for(int i=0;i<(Math.abs(rnd.nextInt(par.cantidadTotalFeatures-2))+1);i++){
            int numero=Math.abs(rnd.nextInt(par.cantidadTotalFeatures-1));
            //System.out.println("Número random: "+numero);
            if(!rd.featuresContains(numero)){
                rd.features[contador]=numero;
                contador++;
            }
        }
//        if(par.printFunciones)System.out.println("Lista Random: "+rd.featuresToString());
        }
}
