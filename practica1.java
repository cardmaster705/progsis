package nuevo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;
import javax.swing.JFileChooser;
import java.util.*;
public class practica1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Archivo uno= new Archivo();
		String texto=uno.leerArchivos();
		String lista=uno.leerLista();
		uno.acomodo(texto);
		//uno.buscarLista();
		
	}

}
