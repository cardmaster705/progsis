package nuevo;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFileChooser;

public class Archivo {
	String texto="";
	String ETIQUETA[];
	String CODOP[];
	String OPERANDO[];
	String comentario[];
	public String leerArchivos(){
		
		JFileChooser buscador= new JFileChooser();
		buscador.showOpenDialog(buscador);
		try{
			String patch= buscador.getSelectedFile().getAbsolutePath();
			BufferedReader bf = new BufferedReader(new FileReader(patch));
			String temp="";
			String bfRead;
			
			while((bfRead= bf.readLine())!= null){
				temp=temp+bfRead+"\n";
				texto=temp;
				
			}
			}catch(Exception e){
		System.err.println("el archivo no ha sido encontrado");
	}
	
		return texto;
	
		}
public void acomodo(String cadenan){
	String arreglo[]=texto.split("\n");
	for(int i=0;i<arreglo.length;i++){
	if(arreglo[i].charAt(0)==59&&arreglo[i].length()<=80){
		System.out.println("comentario");
	}
	else {
		String evalua[]=arreglo[i].split("[\\s]");
		for(int j=0;j<evalua.length;j++){
			if(evalua[j].length()<=5||evalua[j].contains(".")){
				System.out.println("codigo de operacion"+evalua[j]);
			}
		}
			
			
			
		}
	
}
	}
}