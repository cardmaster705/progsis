package nuevo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;

public class Archivo {
	final int error=-1;
	final int a=97 ,z=122 ,A=65 ,Z=90 ,ZERO=48 ,NINE=57;
	String texto="";
	String ETIQUETA="";
	String CODOP="";
	String OPERANDO="";
	String lista="";
	String nueva;
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
public String leerLista(){
		
		JFileChooser buscador= new JFileChooser();
		buscador.showOpenDialog(buscador);
		try{
			String patch= buscador.getSelectedFile().getAbsolutePath();
			BufferedReader bf = new BufferedReader(new FileReader(patch));
			String temp="";
			String bfRead;
			
			while((bfRead= bf.readLine())!= null){
				temp=temp+bfRead+"\n";
				lista=temp;
				
			}
			}catch(Exception e){
		System.err.println("el archivo no ha sido encontrado");
	}
	
		return lista;
	
		}

public void acomodo(String cadenan){
	String arreglo[]=texto.split("\n");
	
	int i;
	
	for(i=0;i<=arreglo.length-1;i++){
		
		
		ETIQUETA="";
		CODOP="";
		OPERANDO="";
		
		int j=0;
		//tamaño[i]=arreglo[i].length();
		if(arreglo[i].charAt(j)==' '||arreglo[i].charAt(j)=='\t'){
				System.out.println("ETIQUETA: NULL");
			//ignora los espacios en blanco
			do{//COMENTARIO,CODIGO DE OPERACION, OPERANDO
				
				j++;
			}while((arreglo[i].charAt(j)==' '||arreglo[i].charAt(j)=='\t')&&j<arreglo[i].length());
			if(j<arreglo[i].length()){
				if(arreglo[i].charAt(j)==';'){
					System.out.println("COMENTARIO");
				}
			
			else if(arreglo[i].charAt(j)!=';'){
				do{
				CODOP=CODOP+arreglo[i].charAt(j);
				j++;
			}while(arreglo[i].charAt(j)!=' '&&arreglo[i].charAt(j)!='\t');
				codigoReglas(CODOP);
				
			
				
				while((arreglo[i].charAt(j)!=' '&&arreglo[i].charAt(j)!='\t')&&j<=arreglo[i].length()){
					
					j++;
					
				}
				
				if(arreglo[i].charAt(j)!=';'){
				do{
					OPERANDO=OPERANDO+arreglo[i].charAt(j);
					
					j++;
				}while(j<arreglo[i].length());
				operandoReglas(OPERANDO);
						}//final del elseif
					//final if	
				}//final del else if
			}//final del if
		}//final del if
		
		
		
		else if(arreglo[i].charAt(j)==';'){
			System.out.println("COMENTARIO");
		}//final else if
		
		
		else {
			do{//COMENTARIO,ETIQUETA,CODIGO DE OPERACION, OPERANDO
				ETIQUETA=ETIQUETA+arreglo[i].charAt(j);
				j++;
			}while((arreglo[i].charAt(j)!=' '&&arreglo[i].charAt(j)!='\t')&&j<=arreglo[i].length());
			etiquetaReglas(ETIQUETA);
			if(j<arreglo[i].length()){
				while(arreglo[i].charAt(j)==' '||arreglo[i].charAt(j)=='\t'){
					j++;
				}
				if(arreglo[i].charAt(j)==';'){
					System.out.println("COMENTARIO");
				}//fin del if
				else if(arreglo[i].charAt(j)!=';'){
					do{
						CODOP=CODOP+arreglo[i].charAt(j);
						j++;
					}while((arreglo[i].charAt(j)!=' '&&arreglo[i].charAt(j)!='\t')&&j<=arreglo[i].length());
					codigoReglas(CODOP);
					
				if(j<arreglo[i].length()){
				while((arreglo[i].charAt(j)==' '&&arreglo[i].charAt(j)=='\t')&&j<=arreglo[i].length()){
					j++;
					
				}
				
				if(arreglo[i].charAt(j)==';'){
					System.out.println("COMENTARIO");
				}//fin del if
				else if(arreglo[i].charAt(j)!=';'){
					do{
						OPERANDO=OPERANDO+arreglo[i].charAt(j);
						j++;
					}while(j<arreglo[i].length());
					operandoReglas(OPERANDO);
				}//fin del else if
			}
				
		}//fin del else if
	}
}
}//final del ciclo for
}//final del metodo






void codigoReglas(String op){
	int n=op.length();
  System.out.println("CODOP :"+op);
    if ( n <= 5)
    {
        int i=0;
        int estado=0;
        while ( i < n && estado != error)
        {
            switch(estado)
            {
            case 0:
                if(!(op.charAt(i)>=a&&op.charAt(i)<=z||op.charAt(i)>=A&&op.charAt(i)<=Z))
                {
                    estado=error;
                }//end if
                else
                {
                    estado=1;
                }//end else
                break;

            case 1:
                if (op.charAt(i)=='.')
                {
                    estado=2;
                }//end if
                else if (!(op.charAt(i)>=a&&op.charAt(i)<=z||op.charAt(i)>=A&&op.charAt(i)<=Z))
                {
                    estado=error;
                }//end else if
                break;

            case 2:
                if (!(op.charAt(i)>=a&&op.charAt(i)<=z||op.charAt(i)>=A&&op.charAt(i)<=Z))
                {
                    estado=error;
                }//end if
                break;
            }//switch
            i++;
        }//end while
        if ( estado == error )
        {
           System.out.println("error");
        }//end if
    }//end if
    else
    {
        System.out.println("error el tamaño maximo es de 5 elementos");
    }//end else
}
void operandoReglas(String ope){
System.out.println("OPERANDO: "+ope);
	
}
void etiquetaReglas(String lab){
	System.out.println("ETIQUETA: "+lab);
	int n=lab.length();
	if(n<=8){
		int i=0;
		int estado=0;
		while(i<n&&estado!=error){
			switch(estado){
			case 0:
			if (!(lab.charAt(i)>=a&&lab.charAt(i)<=z||lab.charAt(i)>=A&&lab.charAt(i)<=Z))
            {
                estado=error;
            }//end if
            else
            {
                estado=1;
            }//end else
            break;
			
		case 1:
            if (!(lab.charAt(i)>=a&&lab.charAt(i)<=z||lab.charAt(i)>=A&&lab.charAt(i)<=Z||lab.charAt(i)>=ZERO&&lab.charAt(i)<=NINE)||lab.charAt(i)=='_')
            {
                estado=error;
            }//end if
            break;
        }//switch
        i++;
    }//end while
    if ( estado == error )
    {
        System.out.println("error");
    }//end if
}//end if
else
{
    System.out.println("la etiqueta tiene mas de 8 caracteres");
}//end else
		
	
}
public void buscarLista(String listas){
String list[]=lista.split("\n");

for(int i=0;i<list.length;i++){
	String split[]=list[i].split("\t");
	for(int j=0;j<split.length;j++){
	if(CODOP.equals(split[j])){
		System.out.println("funciono");
	}
	}
	


	
}
}
}			
			
		
		
	

	
