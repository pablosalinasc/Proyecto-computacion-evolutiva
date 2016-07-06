/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_ce;
import java.io.*;
import java.util.StringTokenizer;
/**
 *
 * @author obi
 */
public class main {
    

    static File[] ficheros;
    static String[] stopwords;   
    static documento[] documentos;
    static palabra[] palabrasTotales=null;
    
    public static void main (String [] args) {
        String sDirectorio = "datasets\\";
        File f = new File(sDirectorio);
        int[] clasificaciones=null;
        if (f.exists()){ // Directorio existe
            ficheros = f.listFiles();
            clasificaciones=new int[ficheros.length];
            for (int x=0;x<ficheros.length;x++){
                String aux=ficheros[x].getName().substring(0,4);
                if(ficheros[x].getName().contains("ham")){
                    clasificaciones[x]=0;
                }else if(ficheros[x].getName().contains("spam")){
                    clasificaciones[x]=1;
                }
//                System.out.println("nombre: "+ficheros[x].getName()+"  clasificacion: "+clasificaciones[x]);
            }
        }else { //Directorio no existe
            System.out.println("El directorio no existe");
        }
        //importa stopwords
        stopwords=new String[665];
        File stopwordf = null;
        FileReader fr = null;
        BufferedReader br = null;
        try{
            stopwordf= new File("stopwords.in");
            fr = new FileReader (stopwordf);
            br = new BufferedReader(fr);
             String linea;
             while((linea=br.readLine())!=null)
                insertarStopword(linea);
        }catch(Exception e){
         e.printStackTrace();
        }finally{
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
                e2.printStackTrace();
            }
        }
        //indice de frecuencia palabras y frecuencias de cada documento
        documentos=new documento[ficheros.length];
        for(int i=0;i<ficheros.length;i++){
            documentos[i]=new documento(ficheros[i].getName(),clasificaciones[i]);
            try{
                fr = new FileReader (ficheros[i]);
                br = new BufferedReader(fr);
                String linea;
                while((linea=br.readLine())!=null){
                    StringTokenizer st = new StringTokenizer(linea, " #%_•-*·.,;:|/\\(){}[]=&+'\"?¿¡!");
                    while(st.hasMoreTokens()) {
                        String palabra = st.nextToken();
                        palabra=palabra.toLowerCase();
                        palabra=stem(palabra);
                        if(contieneNumero(palabra)==false&&palabra.length()>2&&esStopWord(palabra)==false){
                            documentos[i].insertarPalabra(palabra);
                        }
                    }
                }
                documentos[i].print();
                generaRankingDoc(documentos[i]);
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try{                    
                    if( null != fr ){   
                        fr.close();     
                    }                  
                }catch (Exception e2){ 
                    e2.printStackTrace();
                }
            }
        }
        palabra[] top100=generarTop100();
        //generar dataset
        generaDataset(top100);
        generaDocumentacion(top100);
    }
    
    public static void insertarStopword(String stop){
        for(int i=0;i<stopwords.length;i++){
            if(stopwords[i]==null){
                String[] datos=stop.split("\n");
                stopwords[i]=datos[0];
                break;
            }
        }
    }
    
    public static boolean esStopWord(String palabra){
        for(int i=0;i<stopwords.length;i++){
            if(stopwords[i].equals(palabra)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean contieneNumero(String string){
        for(int i=0;i<string.length();i++){
            if((int)string.charAt(i)>47&&(int)string.charAt(i)<58){
                return true;
            }
        }
        return false;
    }
    
    public static String stem(String word){

            Stemmer s = new Stemmer();
//		char[] array = word.toCharArray();

            for(char c:word.toCharArray()){

                    s.add(c);
            }
//		s.add(word.toCharArray(), word.length());
//		System.out.print(s.getResultBuffer());

            s.stem();
//		for(int i = 0;i<s.getResultLength();i++){
//			System.out.print(s.getResultBuffer()[i]);
//		}

//		}
            //array = s.getResultBuffer();
            //		for(int i = 0;i<s.getResultLength();i++){
            //			System.out.print(array[i]);
            //		}

            //System.out.print("||" + s.toString() + "||");
            return s.toString();
    }
    
    public static void generaRankingDoc(documento doc){
        if(palabrasTotales==null){
            documento temp=doc.clone();
            palabrasTotales=temp.palabras;
        }else{
            for(int i=0;i<doc.palabras.length;i++){
                int bandera=0;
                if(doc.palabras[i]!=null){
                    for(int j=0;j<palabrasTotales.length;j++){
                        if(palabrasTotales[j]!=null){
                            if(palabrasTotales[j].palabra.equals(doc.palabras[i].palabra)){
                                palabrasTotales[j].frecuencia+=doc.palabras[i].frecuencia;
                                palabrasTotales[j].nDocumentos=1+palabrasTotales[j].nDocumentos;
                                bandera=1;
                                break;
                            }
                        }
                    }
                    if(bandera==0){
                        for(int k=0;k<palabrasTotales.length;k++){
                            if(palabrasTotales[k]==null){
                                palabrasTotales[k]=doc.palabras[i].clone();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static palabra[] generarTop100(){
        palabra[] palabras=new palabra[100];
        for(int j=0;j<palabras.length;j++){
            palabra mejor=new palabra("holi",0);
            int indicePalabra=-1;
            for(int i=0;i<palabrasTotales.length;i++){
                if(palabrasTotales[i]!=null&&palabrasTotales[i].revisada==0){
                    if(palabrasTotales[i].frecuencia>mejor.frecuencia){
                        mejor=palabrasTotales[i];
                        indicePalabra=i;
                    }
                }
            }
            //inserta mejor
            if(indicePalabra>=0){
                palabrasTotales[indicePalabra].revisada=1;
                palabras[j]=new palabra();
                palabras[j].palabra=mejor.palabra;
                palabras[j].frecuencia=mejor.frecuencia;
                palabras[j].nDocumentos=mejor.nDocumentos;
            }
        }
        System.out.println("\nTOP 100");
        for(int i=0;i<palabras.length;i++){
            if(palabras[i]!=null){
                System.out.println(" - "+palabras[i].palabra+" f: "+palabras[i].frecuencia+" n: "+palabras[i].nDocumentos);
            }
        }
        return palabras;
    }
    
    static public void generaDataset(palabra[] top100){
        FileWriter archivo = null;
        PrintWriter pw = null;
        try
        {
            archivo = new FileWriter("dataset.out");
            pw = new PrintWriter(archivo);
            for (int i = 0; i < documentos.length; i++){
                //para cada documento, buscar cada una de las 100 palabras e imprimir frecuencia
                for(int j=0;j<100;j++){
                    if(top100[j]!=null){
//                        System.out.println(documentos[i].frecuenciaPalabra(top100[j].palabra));
                        pw.print(documentos[i].frecuenciaPalabra(top100[j].palabra)+" ");
                    }
                }
                pw.print(documentos[i].clasificacion+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != archivo)
              archivo.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    static public void generaDocumentacion(palabra[] top100){
        FileWriter archivo = null;
        PrintWriter pw = null;
        try
        {
            archivo = new FileWriter("documentacion_datasest.out");
            pw = new PrintWriter(archivo);
            pw.println("Columnas en orden: ");
            int j=0;
            for(j=0;j<100;j++){
                if(top100[j]!=null){
                    pw.println(j+": "+top100[j].palabra);
                }
            }
            pw.println((j)+": columna de clasificación");
            pw.println("\nCantidad de filas del dataset: "+ficheros.length);
            pw.println("\nCantidad de columnas del dataset: "+(top100.length+1));
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != archivo)
              archivo.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
}
