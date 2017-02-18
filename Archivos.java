package nuevo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
public class Archivos {
		String instruccion[][]= new String[10][3];
		int iInstruccion=0;
	
		String datosm[][]= new String[10][10];
		int dDatos=0;
		String texto="";
		String temp;
		int acumulador;
		public String leerArchivos(){
		
			JFileChooser buscador= new JFileChooser();
			buscador.showOpenDialog(buscador);
			try{
				String patch= buscador.getSelectedFile().getAbsolutePath();
				BufferedReader bf = new BufferedReader(new FileReader(patch));
				String temp="";
				String bfRead;
				String file = patch.substring(49);
				System.out.println("Archivo:"+file);
				while((bfRead= bf.readLine())!= null){
					temp=temp+bfRead+"\n";
					texto=temp;
					
				}
				}catch(Exception e){
			System.err.println("el archivo no ha sido encontrado");
		}
		
			return texto;
		
			}
		
		public void cadenas(){
			StringTokenizer tokens = new StringTokenizer(texto,"\n");	

			while(tokens.hasMoreTokens()){
				
				temp=tokens.nextToken();
				//System.out.println(temp);
			}
			}
		
		public void split(String nuevacadena){
			
			
			
			String nueva[]=texto.split("\\s");
			System.out.println("N° de instrucciones :"+nueva[0]);
			System.out.println("N° de datos :"+nueva[1]);
			System.out.println("N° inicial de instrucciones :"+nueva[2]);
			System.out.println("N° inicial de datos :"+nueva[3]);
			System.out.println("INSTRUCCIONES");
			int dato2=Integer.parseInt(nueva[1]);
			int tamaño=nueva.length;
			int instrucciones=Integer.parseInt(nueva[2]);
			int dato=Integer.parseInt(nueva[3]);
			int i;
			for(i=4;i<tamaño;i++){
				if ( nueva[i].charAt(0) != 48) {
					System.out.println(instrucciones+" "+"COD: "+nueva[i].charAt(0)+ " "+"Dir: "+nueva[i].charAt(1)+nueva[i].charAt(2)+nueva[i].charAt(3));
					instruccion[iInstruccion][0]=String.valueOf(instrucciones);
					instruccion[iInstruccion][2]="sin usar";
					instruccion[iInstruccion++][1]=nueva[i];
					instrucciones++;
					//System.out.println(instruccion[iInstruccion-1]);
				} else {
					break;
				}
			}
			
			System.out.println("Datos:");
			for (; i < tamaño;i++) {
				System.out.println(dato+"  "+"Valor: "+nueva[i]);
				
				datosm[dDatos][0] = String.valueOf(dato);
				datosm[dDatos++][1]=nueva[i];
				dato++;
				//System.out.println(datosm[dDatos-1]);
			}
			
			System.out.println("ejecutando");
			System.out.printf("%-5s %-6s %-3s\n","PC","IR","AC");
			
			
			for(int i2=0;i2<iInstruccion;i2++){
				int direcciona;
				int direcciond;
				switch(instruccion[i2][1].charAt(0)){
				case '0':
					if(instruccion[i2][2].equals("sin usar")){
					direcciona=Integer.parseInt(instruccion[i2][0]);
					direcciond=Integer.parseInt(instruccion[i2][1].substring(1));
					System.out.println(String.valueOf(instruccion[i2][0]+"  "+instruccion[i2][1]+"  "+acumulador+"\n"));
					instruccion[i2][2]="ejecutado";
					i2=i2+(direcciond-direcciona)-1;
					}
					break;
				case '1':
					acumulador=Integer.parseInt(buscarDato(instruccion[i2][1].substring(1)));
					
		            	System.out.println(String.valueOf(instruccion[i2][0]+"  "+instruccion[i2][1]+"  "+acumulador+"\n"));
		            
					break;
					
				case '2':
					ponDato(instruccion[i2][1].substring(1),String.valueOf(acumulador));
					System.out.println(String.valueOf(instruccion[i2][0]+"  "+instruccion[i2][1]+"  "+acumulador+"\n"));
					break;
				case '3':
					sumaDato(instruccion[i2][1].substring(1),acumulador);
					System.out.println(String.valueOf(instruccion[i2][0]+"  "+instruccion[i2][1]+"  "+acumulador+"\n"));
					break;
				case '4':
					restaDato(instruccion[i2][1].substring(1),acumulador);
					System.out.println(String.valueOf(instruccion[i2][0]+"  "+instruccion[i2][1]+"  "+acumulador+"\n"));
					break;
				case '5':
					multiplicaDato(instruccion[i2][1].substring(1),acumulador);
					System.out.println(String.valueOf(instruccion[i2][0]+"  "+instruccion[i2][1]+"  "+acumulador+"\n"));
					break;
				case '6':
					divideDato(instruccion[i2][1].substring(1),acumulador);
					System.out.println(String.valueOf(instruccion[i2][0]+"  "+instruccion[i2][1]+"  "+acumulador+"\n"));
					//System.out.printf("%-5i %-6i %-3i\n",instruccion[i2][0],instruccion[i2][1],acumulador);
					
					break;
				case '7':
					abrirArchivotxt();
					System.out.println(String.valueOf(instruccion[i2][0]+"  "+instruccion[i2][1]+"  "+acumulador+"\n"));
					break;
					
				case '8':
					crearArchivotxt();
					System.out.println(String.valueOf(instruccion[i2][0]+"  "+instruccion[i2][1]+"  "+acumulador+"\n"));
					break;
				case '9':
					crearSecundariotxt();
					System.out.println(String.valueOf(instruccion[i2][0]+"  "+instruccion[i2][1]+"  "+acumulador+"\n"));
					break;
				default:
					System.out.println("error");
					break;
				}
				System.out.println("Datos: ");
			for(int j=0;j<dato2;j++){
				
				System.out.println(datosm[j][0]+" "+datosm[j][1]);
				
			}
			System.out.println("______________________________________________:)");
			System.out.println("PC: |"+"IR: |"+"AC: |");
			}
			
			
		}

		String buscarDato(String busqueda){
			String r="";
			for(int i=0;i<dDatos;i++){
				if(datosm[i][0].equals(busqueda)){
					r= datosm[i][1];
				}
			}
			return r;
			
		}
		
		void ponDato(String busqueda, String poner){
		
			for(int i=0;i<dDatos;i++){
				if(datosm[i][0].equals(busqueda)){
					datosm[i][1]=poner;
				}
			}
		
			
		
		
		}
		void sumaDato(String busqueda, int poner){
			
			for(int i=0;i<dDatos;i++){
				if(datosm[i][0].equals(busqueda)){
					acumulador=Integer.parseInt(datosm[i][1])+poner;

				}
			}
		}
		
		void restaDato(String busqueda, int poner){
			
			for(int i=0;i<dDatos;i++){
				if(datosm[i][0].equals(busqueda)){
					acumulador=poner-Integer.parseInt(datosm[i][1]);

				}
			}
		}
		void multiplicaDato(String busqueda, int poner){
			
			for(int i=0;i<dDatos;i++){
				if(datosm[i][0].equals(busqueda)){
					acumulador=Integer.parseInt(datosm[i][1])*poner;

				}
			}
		}
		void divideDato(String busqueda, int poner){
			
			for(int i=0;i<dDatos;i++){
				if(datosm[i][0].equals(busqueda)){
					acumulador=Integer.parseInt(datosm[i][1])/poner;

				}
			}
		}
		void crearArchivotxt(){
			
			        String ruta = "archivo.txt";
			        try{
			        File archivo = new File(ruta);
			        BufferedWriter bw;
			        if(archivo.exists()) {
			            bw = new BufferedWriter(new FileWriter(archivo));
			            bw.write(String.valueOf(acumulador));
			        } else {
			            bw = new BufferedWriter(new FileWriter(archivo));
			           
			            bw.write(acumulador);
			        }
			        bw.close();
			        }catch(Exception e){
			        	System.out.println("hola");
			        }
			    
			
		}
		
		void crearSecundariotxt(){
			
	        String ruta = "secundario.txt";
	        try{
	        File archivo = new File(ruta);
	        BufferedWriter bw;
	        if(archivo.exists()) {
	            bw = new BufferedWriter(new FileWriter(archivo));
	            for(int i=0;i<iInstruccion;i++){
	            	bw.write(String.valueOf(instruccion[i][0])+" "+instruccion[i][1]+"\n");
	            }
	            for(int i=0;i<dDatos;i++){
	            	 bw.write(String.valueOf(datosm[i][0])+" "+datosm[i][1]+"\n");
	            }
	           
	        } else {
	            bw = new BufferedWriter(new FileWriter(archivo));
	            bw.write(String.valueOf(instruccion));
	            bw.write(String.valueOf(datosm));
	        }
	        bw.close();
	        }catch(Exception e){
	        	System.out.println("hola");
	        }
	    
	
		}
		
		void abrirArchivotxt(){
			String texto="";

			try
			{
			
			FileReader lector=new FileReader("archivo.txt");

			
			BufferedReader contenido=new BufferedReader(lector);

			
			while((texto=contenido.readLine())!=null)
			{
			acumulador=acumulador+Integer.parseInt(texto);
			}
			}

			
			catch(Exception e)
			{
			System.out.println("Error al leer");
			}
			
			
			
		}
}