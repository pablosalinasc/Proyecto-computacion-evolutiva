/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.app.tarea2;
import ec.util.*;
import ec.*;
import ec.gp.*;
import ec.gp.koza.*;
import ec.simple.*;
import edu.berkeley.compbio.jlibsvm.*;
import edu.berkeley.compbio.jlibsvm.binary.BinaryModel;
import edu.berkeley.compbio.jlibsvm.binary.C_SVC;
import edu.berkeley.compbio.jlibsvm.binary.MutableBinaryClassificationProblemImpl;
import edu.berkeley.compbio.jlibsvm.kernel.LinearKernel;
import edu.berkeley.compbio.jlibsvm.util.SparseVector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
/**
 *
 * @author obi
 */
public class FeatureSelection extends GPProblem implements SimpleProblemForm {
    public static final String P_DATA = "data";


    public CaractSelectData input;

    public Object clone()
        {
        FeatureSelection newobj = (FeatureSelection) (super.clone());
        newobj.input = (CaractSelectData
                )(input.clone());
        return newobj;
        }
    
    public void setup(final EvolutionState state,
                      final Parameter base)
        {
        // very important, remember this
        super.setup(state,base);

        // set up our input -- don't want to use the default base, it's unsafe here
        input = (CaractSelectData) state.parameters.getInstanceForParameterEq(
            base.push(P_DATA), null, CaractSelectData.class);
        input.setup(state,base.push(P_DATA));
        }
    
        public void evaluate( EvolutionState state,  Individual ind,  int threadnum, int i1){
            if (!ind.evaluated){
                double acc=0.0;
                //obtiene parametros
                parametros par = new parametros();
                //obtiene individuo
                ((GPIndividual)ind).trees[0].child.eval(
                        state,threadnum,input,stack,((GPIndividual)ind),this);
                                
                //Obtiene dataset
                File archivo = null;
                FileReader fr = null;
                BufferedReader br = null;

                float[][] dataset=new float[par.largoDataset][par.cantidadTotalFeatures+par.offsetFeatures+par.columnaExtra];

                try {
                    // Apertura del fichero y creacion de BufferedReader para poder
                    // hacer una lectura comoda (disponer del metodo readLine()).
                    archivo = new File (par.rutaDataset);
                    fr = new FileReader (archivo);
                    br = new BufferedReader(fr);

                    // Lectura del fichero
                    String linea;
                    int indiceLinea=0;
                    while((linea=br.readLine())!=null&&par.largoDataset>indiceLinea){
                        //System.out.println(linea);
                        indiceLinea++;
                        String[] parts = linea.split(" ");
                        for(int i=0;i<(par.cantidadTotalFeatures+par.offsetFeatures+par.columnaExtra);i++){
                            dataset[indiceLinea-1][i]=Float.parseFloat(parts[i]);
                            //System.out.print(dataset[indiceLinea-1][i]+" ");
                        }
                        //System.out.println("");
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }finally{
                    // En el finally cerramos el fichero, para asegurarnos
                    // que se cierra tanto si todo va bien como si salta 
                    // una excepcion.
                    try{
                        if( null != fr ){
                            fr.close();     
                        }                  
                    }catch (Exception e2){
                        e2.printStackTrace();
                    }
                }

                // create a new SVM implementation in the C SVC style.
                C_SVC svm = new C_SVC();
                // build parameters
                ImmutableSvmParameterGrid.Builder builder = ImmutableSvmParameterGrid.builder();

                // create training parameters ------------
                HashSet<Float> cSet;
                HashSet<LinearKernel> kernelSet;
                kernelSet = new HashSet<LinearKernel>();
                kernelSet.add(new LinearKernel());

                cSet = new HashSet<Float>();
                cSet.add(1.0f);


                // configure finetuning parameters
                builder.eps = 0.001f; // epsilon
                builder.Cset = cSet; // C values used
                builder.kernelSet = kernelSet; //Kernel used

                ImmutableSvmParameter params = builder.build();
                MutableBinaryClassificationProblemImpl problem= new MutableBinaryClassificationProblemImpl(String.class, par.largoDataset);
                
                //obtiene datos del dataset
                for(int i=0;i<(par.largoDataset);i++){
                    float[] lineaTemp=new float[input.featuresSize()];
                    int contador=0;
                    for(int feature:input.features){ //se toman solo las columnas seleccionadas
                        //System.out.println("Fitness: "+feature);
                        if(feature!=-1){
                            lineaTemp[contador]=dataset[i][feature+par.offsetFeatures];
                            contador++;
                        }
                    }
                    SparseVector x1 = generateFeatures(lineaTemp);
                    problem.addExample(x1, (dataset[i][par.indiceClasificaciones]-par.offsetClases)); //se le asigna una clasificacion a la tupla
                }
                BinaryModel model = svm.train(problem, params);
                int hits = 0;
                for(int i=0;i<(par.largoDataset);i++){
                    float[] lineaTemp=new float[input.featuresSize()];
                    for(int j=0;j<input.featuresSize();j++){ //se toman solo las columnas seleccionadas
                        if(input.features[j]!=-1){
                            lineaTemp[j]=dataset[i][input.features[j]+par.offsetFeatures];
                        }
                    }
                    SparseVector xTest = generateFeatures(lineaTemp);
                    float predictedLabel =  (float) model.predictLabel(xTest);
                    if(predictedLabel==(dataset[i][par.indiceClasificaciones]-par.offsetClases)){
                        hits++;
                    }
                }
                acc=(hits*100)/par.largoDataset;
                KozaFitness f = ((KozaFitness)ind.fitness);
                int largoFeatures=0;
                boolean bandera=true;
                while (bandera){
                    if(input.features[largoFeatures]!=-1&&largoFeatures<(par.cantidadTotalFeatures-1)){
                        largoFeatures++;
                    }else{
                       bandera=false; 
                    }
                }
                
                float largoRelativo=largoFeatures/par.cantidadTotalFeatures;
                float fitness=1.0f-(float)acc/100-par.lambda*largoRelativo;
                if(par.printFunciones)System.out.println("Individuo: "+input.featuresToString()+" Precisión: "+acc+"% Fitness: "+fitness+" K: "+largoFeatures);
                f.setFitness(state,fitness);
                f.hits = hits;
                ind.evaluated = true;
            }
        }
        
        private SparseVector generateFeatures(float[] floats) {
            SparseVector sparseVector = new SparseVector(floats.length);
            int[] indices = new int[floats.length];
            for (int i = 0; i < floats.length; i++) {
                indices[i] = new Integer(i);
            }
            sparseVector.indexes = indices;
            sparseVector.values = floats;
            return sparseVector;
        }
}
