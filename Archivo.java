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
	int tamaño=0;
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
public void acomodo(String cadenan){
	String arreglo[]=texto.split("\n");
	
	int i;
	int [] tamaño;
	tamaño = new int[12];
	for(i=0;i<arreglo.length;i++){
		tamaño[i]=arreglo[i].length();
		int j=0;
		if(arreglo[i].charAt(j)==' '||arreglo[i].charAt(j)=='\t'){
			
			//ignora los espacios en blanco
			do{
				
				j++;
			}while((arreglo[i].charAt(j)==' '||arreglo[i].charAt(j)=='\t')&&j<=tamaño[i]);
			if(j<tamaño[i]){
				if(arreglo[i].charAt(j)==';'){
					System.out.println("COMENTARIO");
				}
			
			else if(arreglo[i].charAt(j)!=';'){
				do{
				CODOP=""+(arreglo[i].charAt(j));
				j++;
			}while((arreglo[i].charAt(j)!=' '&&arreglo[i].charAt(j)!='\t')&&j<=tamaño[i]);
				codigoReglas(CODOP);
				
			if(j<=tamaño[i]){
				while(arreglo[i].charAt(j)==' '||arreglo[i].charAt(j)=='\t'){
					j++;
				}
			if(arreglo[i].charAt(j)==';'){
				System.out.println("COMENTARIO");
			}
			else if(arreglo[i].charAt(j)!=';'){
				do{
					OPERANDO=""+(arreglo[i].charAt(j));
					j++;
				}while(j<tamaño[i]);
				operandoReglas(OPERANDO);
						}//final del elseif
					}//final if	
				}//final del else if
			}//final del if
		}//final del if
		
		else if(arreglo[i].charAt(j)==';'){
			System.out.println("COMENTARIO");
		}//final else if
		else{
			do{
				ETIQUETA=""+arreglo[i].charAt(j);
				j++;
			}while(arreglo[i].charAt(j)!=' '&&arreglo[i].charAt(j)!='\t');
			etiquetaReglas(ETIQUETA);
			if(j<tamaño[i]){
				while(arreglo[i].charAt(j)==' '||arreglo[i].charAt(j)=='\t'){
					j++;
				}
				if(arreglo[i].charAt(j)==';'){
					System.out.println("COMENTARIO");
				}//fin del if
				else if(arreglo[i].charAt(j)!=';'){
					do{
						CODOP=""+arreglo[i].charAt(j);
						j++;
					}while(j<tamaño[i]);
					codigoReglas(CODOP);
				if(j<tamaño[i]){
				while(arreglo[i].charAt(j)==' '||arreglo[i].charAt(j)=='\t'){
					j++;
				}
				if(arreglo[i].charAt(j)==';'){
					System.out.println("COMENTARIO");
				}//fin del if
				else if(arreglo[i].charAt(j)!=';'){
					do{
						OPERANDO=""+arreglo[i].charAt(j);
						j++;
					}while(j<tamaño[i]);
					operandoReglas(OPERANDO);
				}//fin del else if
			}//fin del if
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
void operandoReglas(String op){
System.out.println("OPERANDO: "+op);
	
}
void etiquetaReglas(String lab){
	System.out.println("ETIQUETA: "+lab);
	int n=lab.length();
	if(n<8){
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
}
			
			
		
		
	

	
