/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmldom;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.FileOutputStream;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 *
 * @author xp
 */
public class XmlDOM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            File f = new File("ej1.xml");
            Document documento = builder.parse(f); 
            
            //inicializamos la variable que vamos a ir modificando manualmente para
            //indicar al sistema qué vamos a querer realizar.
            int var1= 2;
            if (var1== 0){
            //procedimiento que muestra todo el documento
                recorrerRamaDom(documento);
                
                }else if (var1==1){
                //procedimiento que muestra los datos de un alumno en concreto
                   recorrerRamaDomSecuencial(documento, "74857485F");
                    
                    }else if(var1==2){
                    //procedimiento que modifica un DNI
                         modificarDNI(documento, "50187884S", "74857485F");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void recorrerRamaDom(Node nodo){
        //recibimos el nodo y evaluemos si está vacío
        if (nodo!=null){
        //mostramos su nombre y su valor por pantalla
            System.out.println(nodo.getNodeName()+": "+ nodo.getNodeValue());
            //generamos una lista de nodos hijos a partir del nodo actual
            NodeList hijos = nodo.getChildNodes();
            //generamos un bucle que recorra los nodos hijos de la lista
        for (int i=0;i < hijos.getLength();i++){
        /*declaramos una variable de tipo nodo y le asignamos el nodo actual de
            la lista*/
             Node nodoNieto = hijos.item(i);
             /*volvemos a llamar de manera recursiva a este mismo procedimeinto,
            así hasta que ya no haya más nodos hijos*/
             recorrerRamaDom(nodoNieto);
             }
        }
 }
    public static void recorrerRamaDomSecuencial(Document miDocumento, String dni){
    try {
        boolean encontrado= false;
        //Generamos una lista que contenga todos los nodos cuyo nombre es DNI
            NodeList list = miDocumento.getElementsByTagName("dni");
        int i=0;
        //recorremos la lista para buscar el DNI
        while(i<list.getLength()) {
                     Node n = list.item(i);
                 //evaluamos si el nodo actual de la lista su valor es el dni
                if (n.getFirstChild().getNodeValue().equals(dni)){
                          //nos posicionamos en el nodo padre, que es el nodo alumno
                        Node Padre= n.getParentNode();
                        /*llamamos al procedimiento que muestra por pantalla todos los
                       nodos hijos junto a sus valores*/
                        recorrerRamaDom(Padre);
                        //ponemos la variable de control a verdadero
                        encontrado = true;
                break;
                }
                  i++;
                  }
                if (encontrado==false){
                    System.out.println("DNI no se encuentra como alumno");
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
 }

public static void modificarDNI(Document miDocumento, String dni, String dniNuevo){
 try {
        boolean encontrado= false;
        //Generamos una lista que contenga todos los nodos cuyo nombre es DNI
        NodeList list = miDocumento.getElementsByTagName("dni");
        int i=0;
        //recorremos la lista para buscar el DNI
        while(i<list.getLength()) {
        Node n = list.item(i);
        //evaluamos si el nodo actual de la lista su valor es el dni
        if (n.getFirstChild().getNodeValue().equals(dni)){
        //modificamos el valor del primer hijo, que es el tecto donde se

        n.getFirstChild().setNodeValue(dniNuevo);

        //nos posicionamos en el nodo padre, que es el nodo alumno
        Node Padre= n.getParentNode();
        /*llamamos al procedimiento que muestra por pantalla todos los
       nodos hijos junto a sus valores para comprobar que se ha cambiado el DNI*/
        recorrerRamaDom(Padre);
        //ponemos la variable de control a verdadero
        encontrado = true;
        guardarDOMcomoFILE(miDocumento);
 break;    
  }
                i++;
         }
                if (encontrado==false){
                    System.out.println("DNI no se encuentra como alumno");
                         }
            } catch (Exception e) {
                e.printStackTrace();
        }
 }
  
    
  public static void guardarDOMcomoFILE(Document doc){
        try{

             // Crea un fichero llamado salida.xml
            File archivo_xml = new File("alumnos.xml");
            // Especifica el formato de salida
            OutputFormat format = new OutputFormat(doc);

            // Especifica que la salida esté indentada
            format.setIndenting(true);

            // Escribe el contenido en el FILE
            XMLSerializer serializer = new XMLSerializer(new
            FileOutputStream(archivo_xml), format);
 
            serializer.serialize(doc);


        }
        catch (Exception e) {
             e.printStackTrace();
        }
 }

  
    
}
