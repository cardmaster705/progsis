package nuevo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	String modoDir="";
	boolean esHex = true;
	boolean esOctal = true;
	boolean esBinario = true;
	boolean esDecimal = true;
	private final int F = 70;	//Referencia al codigo ASCII
	private final int fo = 102;	//Referencia al codigo ASCII
	private final int D1 = 49;	//Referencia al codigo ASCII digito 1
	private final int D7 = 55;	//Referencia al codigo ASCII digito 7
	String einmediato="^#[0-9$@%].*";
	private final int FUERA_DE_RANGO = -257;
	private boolean ERROR = false;
	private boolean ERRORmodoDirec = true;
	private StringTokenizer st;
	String bytes="";
	String modoDirec = "";
	boolean t=false;
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
	//modoDir=buscarLista(CODOP,modoDirec);
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

public String operandoReglas(String ope){
		String open=ope.substring(1);
		modoDirec = "NO EXISTE";
		int valorOperando = FUERA_DE_RANGO;
		String auxValorOperando;
		int iteracion=0;
		ERROR = false;
		//////////////////////////Evalua INH/////////////////////////////////////////////////////////////////////////
		if(open.equals(""))	{
			modoDirec = "Inherente";
			//System.out.println("OPERANDO: NULL --->INH"+" BYTES:"+bytes);
			
		}//FIN if
			
		
		//////////////////////////Evalua IMM/////////////////////////////////////////////////////////////////////////
		else if(open.matches("#[$|@|%|0|1|2|3|4|5|6|7|8|9][A-Z0-9]*"))
		{
			valorOperando = baseNumerica(open.substring(1) );
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,0,255) == true)
				{
					modoDirec = "Inmediato";
					//System.out.println("OPERANDO: "+open+" "+"-->INMEDIATO"+" BYTES:"+bytes);
					//System.out.println("BYTES: 2\n");
				

					
				}else if(estaEntre(valorOperando,0,65535) == true){
					modoDirec = "Inmediato";	
					//System.out.println("OPERANDO: "+open+" "+"-->INMEDIATO"+" BYTES:"+bytes);
					//System.out.println("BYTES: 2\n");
					

				}else{
					System.out.printf("ERROR : LOS RANGOS VALIDOS DE INMEDIATO SON (0,255) O (0,65535)");
					ERROR = true;
				}//FIN else
			}//FIN	
		}//FIN else if(es IMM)
		
		//////////////////////////Evalua DIR o EXT///////////////////////////////////////////////////////////////////
		else if(open.matches("[$|@|%|0|1|2|3|4|5|6|7|8|9][A-Z0-9]*"))
		{
			valorOperando = baseNumerica(open);
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,0,255) == true){
					
					modoDirec = "Directo";
					//System.out.println("OPERANDO: "+open+" "+"-->DIRECTO");
					//System.out.println(bytes);

				}else if(estaEntre(valorOperando,256,65535) == true){
					modoDirec = "Extendido";
					//System.out.println("OPERANDO: "+open+" "+"-->EXTENDIDO"+" BYTES:"+bytes);
					//System.out.println("BYTES: 3\n");
					

				}else{
					System.out.printf("ERROR : RANGOS VALIDO DE (DIR) ES (0,255), DE (EXT) ES (256,65535)");
					ERROR = true;
				}//FIN else
			}	
		}//FIN else if(es DIR o EXT)
		
		//////////////////////////Evalua IDX,IDX1,IDX2///////////////////////////////////////////////////////////////
		else if(open.matches("[-]?[0-9]*,(X|Y|(SP)|(PC))"))
		{
			if(open.charAt(0) == ',')
				valorOperando = 0;
			else
			{
				st = new StringTokenizer(open, ",");
				auxValorOperando = st.nextToken();
				if(!(auxValorOperando.charAt(0) == '-' && auxValorOperando.length() == 1))	//Si la sub cadena NO esta compuesta solo por el caracter '-' entonces..
					valorOperando = Integer.parseInt(auxValorOperando);
			}//FIN else
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,-16,15) == true)
				{
					modoDirec = "IDX";
					//System.out.println("OPERANDO: "+open+" "+"-->IDX"+" BYTES:"+bytes);
					//System.out.println("BYTES: 2\n");
					

				}else if(estaEntre(valorOperando,-256,-17) == true)
				{
					modoDirec = "IDX1";
					//System.out.println("OPERANDO: "+open+" "+"-->IDX1"+" BYTES:"+bytes);
					//System.out.println("BYTES: 3\n");
					

				}else if (estaEntre(valorOperando,16,255) == true)
				{
					modoDirec = "IDX1";
					//System.out.println("OPERANDO: "+open+" "+"-->IDX1"+" BYTES:"+bytes);
					//System.out.println("BYTES: 3\n");
					

				}else if (estaEntre(valorOperando,256,65535) == true)
				{
					modoDirec = "IDX2";
					//System.out.println("OPERANDO: "+open+" "+"-->IDX2"+" BYTES:"+bytes);
					//System.out.println("BYTES: 4\n");
					

				}else{
					System.out.printf("ERROR : RANGO VALIDO DE (IDX) ES (0,255), (IDX1) (-256,-17) O (16,255), (IDX2) (256,65535)");
					ERROR = true;
				}
			}//FIN ERROR
		}//FIN else if(IDX)

		//////////////////////////Evalua IDX////////////////////////////////////////////////////////////////////////
		else if(open.matches("[0-9]+,[+|-](X|Y|(SP)|(PC))"))
		{
			st = new StringTokenizer(open, ",");
			valorOperando = Integer.parseInt(st.nextToken());
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,1,8) == true)
				{
					modoDirec = "IDX";
					//System.out.println("OPERANDO: "+open+" "+"-->IDX pre incremento"+" BYTES:"+bytes);
					//System.out.println("BYTES: 2\n");
					


				}else{
					System.out.printf("ERROR : RANGO VALIDO DE (IDX) PRE/POST INC/DEC ES (1,8)");
					ERROR = true;
				}//FIN ELSE
			}//FIN ERROR
		}//FIN else if(IDX INC / DEC)
		
		else if(open.matches("[0-9]+,(X|Y|(SP))[+|-]"))
		{
			st = new StringTokenizer(open, ",");
			valorOperando = Integer.parseInt(st.nextToken());
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,1,8) == true)
				{
					modoDirec = "IDX";
					//System.out.println("OPERANDO: "+open+" "+"-->IDX post incremento"+" BYTES:"+bytes);
					//System.out.println("BYTES: 2\n");
					

				}else{
					System.out.printf("ERROR : RANGO VALIDO DE (IDX) PRE/POST INC/DEC ES (1,8)");
					ERROR = true;
				}//FIN ELSE	
			}//FIN IF ERROR 
			
		}//FIN else if(IDX INC / DEC)
		
		//////////////////////////Evalua IDX///////////////////////////////////////////////////////////////////////
		else if(open.matches("[A|B|D],(X|Y|(SP)|(PC))"))
		{
			modoDirec = "IDX";
			//System.out.println("OPERANDO: "+open+" "+"-->IDX Acumulador"+" BYTES:"+bytes);
			//System.out.println("BYTES: 2\n");
			


		}//FIN else if
			

		//////////////////////////Evalua [IDX2]////////////////////////////////////////////////////////////////////
		else if(open.matches("\\[[0-9]+,((X)|(Y)|(SP)|(PC))\\]"))
		{
			st = new StringTokenizer(open.substring(1), ",");
			valorOperando = Integer.parseInt(st.nextToken());
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,0,65535) == true)
				{
					modoDirec = "[IDX2]";
					//System.out.println("OPERANDO: "+open+" "+"-->IDX2"+" BYTES"+bytes);
					//System.out.println("BYTES: 4\n");
					

				}else{
					System.out.printf("ERROR : RANGO VALIDO DE ([IDX2]) ES (0,65535)");
					ERROR = true;
				}//FIN ELSE
			}//FIN ERROR IF
			
				
		}//FIN else if
		
		//////////////////////////Evalua [D,IDX]///////////////////////////////////////////////////////////////////
		else if(open.matches("\\[D,(X|Y|(SP)|(PC))\\]"))
		{
			modoDirec = "[D,IDX]";
			//System.out.println("OPERANDO: "+open+" "+"-->D,IDX"+" BYTES:"+bytes);
			//System.out.println("BYTES: 2\n");
			

		}//FIN else if
			
		
			//////////////////////////Evalua REL///////////////////////////////////////////////////////////////////
		else if(open.matches("([A-Z]\\w*\\d*)"))
		{
			modoDirec = "REL";
			//System.out.println("OPERANDO: "+open+" "+"-->RELATIVO"+" BYTES:"+bytes);
			

		}//FIN else if
/*		
		else if(operando.equals("#"))
		{
			System.out.print("ERROR : BASES NUMERICAS DISPONIBLES ($<valor> , Hexadecimal) (@<valor>, Octal) (%<valor>, Binario) (<valor> , Decimal)");
			ERROR = true;
		}//FIN else if ERROR INMEDIATO
*/		
			bytes=buscarLista(CODOP ,modoDirec);	
			System.out.println("OPERANDO: "+open+" BYTES:"+bytes);
		
		return modoDirec;	
	}//FIN 
	
	

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
public String buscarLista( String codop, String modoDirec){
	  String list[]=lista.split("\n");

	  for(int i=0;i<list.length;i++){
	  	String split[]=list[i].split("\t");
	  	if(codop.equals(split[0]) && modoDirec.equals(split[2])){
	  		t=true;
	  		return split[6];
	     
	  	}//if
	  }//for
	  
	  return "NO EXISTE";
	}//buscarLista
/*if(modoDirec.equals("Inmediato")){
	System.out.println("BYTES: 2\n");
}
if(modoDirec.equals("Directo")){
	System.out.println("BYTES: 2\n");
}
if(modoDirec.equals("Extendido")){
	System.out.println("BYTES: 3\n");
}
if(modoDirec.equals("Inmediato")){
	System.out.println("BYTES: 2\n");
}
if(modoDirec.equals("IDX")){
	System.out.println("BYTES: 2\n");
}
if(modoDirec.equals("IDX1")){
	System.out.println("BYTES: 3\n");
}
if(modoDirec.equals("IDX2")){
	System.out.println("BYTES: 4\n");
}
if(modoDirec.equals("[IDX2]")){
	System.out.println("BYTES: 4\n");
}
if(modoDirec.equals("[D,IDX]")){
	System.out.println("BYTES: 2\n");
}*/



public boolean baseHex(String operando)			//Validar base hexadecimal
{
	
	
	for(int i = 0; i < operando.length();i++)	//leer caracter por caracter y valdiarlo
		if(!((operando.charAt(i) >= A &&operando.charAt(i) <= F) || (operando.charAt(i) >= a && operando.charAt(i) <= fo) || (operando.charAt(i)>=ZERO&&operando.charAt(i)<=NINE)))	//Si no es un caracter valido de un hexadecimal retornar falso 
			esHex = false;
					
	return esHex;
}//FIN baseHex


public boolean baseOctal(String operando)		//IValidar base octal
{
	
	for(int i = 0; i < operando.length();i++)	//leer caracter por caracter y valdiarlo
		if(!(operando.charAt(i) >= ZERO && operando.charAt(i) <= D7))	//Si no es un caracter entre el 0 y 7 (incluyendolos) es falso
			esOctal = false;
				
	return esOctal;
}//FIN baseOctal

public boolean baseBinario(String operando)		//Validar base binaria
{
	
	if(operando.equals(""))
		esBinario = false;
	
	for(int i = 0; i < operando.length();i++)	//leer caracter por caracter y valdiarlo
	{
		if(!(operando.charAt(i) >= ZERO && operando.charAt(i) <= D1))	//Si no es un '1' o un '0' es incorrecto
			esBinario = false;
	}//FIN for
					
	return esBinario;
}//FIN baseBinario

public boolean baseDecimal(String operando)		//Validar base decimal
{
	
	for(int i = 0; i < operando.length();i++)	//leer caracter por caracter y valdiarlo
		if(!(operando.charAt(i)>=ZERO&&operando.charAt(i)<=NINE))	//Si no es un digito del 0-9 es incorrecto
			esDecimal = false;
				
	return esDecimal;
}//FIN baseDecimal
public boolean estaEntre(int valor, int inicio, int fin)
{
	if(valor >= inicio && valor <= fin)
		return true;
	else
		return false;
}//FIN rango;
public boolean caracterDigito(char caracter)	//Devuelve verdadero si el caracter es un digito del '0' al '9'
{	
	if(caracter >= ZERO && caracter <= NINE)
		return true;
	else
		return false;
}//FIN caracterLetra
public int baseNumerica(String operando)
{
	ERROR = false;
	String baseNum = "";
	int integer = FUERA_DE_RANGO;		//Numero fuera de rango de cualquier modo de direccionamiento
	baseNum = operando.substring(1);
	
	if(operando.charAt(0) == '$')
	{
		if(baseNum.equals("")){
			System.out.printf("ERROR : DEBE DE CONTENER UN VALOR EN HEXADECIMAL\n");
			ERROR = true;
		}else if(baseHex(baseNum) == true){
			integer = Integer.parseInt(baseNum, 16);
			
		}else{
			System.out.printf("ERROR : LOS CARACTERES VALIDOS DE LA BASE HEXADECIMAL SON 'A-Fa-f' y '0..9'\n");
			ERROR = true;
		}//FIN else	
	}else if(operando.charAt(0) == '@')
	{
		if(baseNum.equals("")){
			System.out.printf("ERROR : DEBE DE CONTENER UN VALOR EN OCTAL\n");
			ERROR = true;
		}else if(baseOctal(baseNum) == true){
			integer = Integer.parseInt(baseNum,8);
		}else{
			System.out.printf("ERROR : LOS CARACTERES VALIDOS DE LA BASE OCTAL SON '0-7'\n");
			ERROR = true;
		}//FIN else
			
	}else if(operando.charAt(0) == '%')
	{	
		if(baseNum.equals("")){
			System.out.printf("ERROR : DEBE DE CONTENER UN VALOR EN DECIMAL\n");
			ERROR = true;
		}else if(baseBinario(baseNum) == true){
			integer = Integer.parseInt(baseNum,2);	
		}else{
			System.out.printf("ERROR : LOS CARACTERES VALIDOS DE LA BASE BINARIA SON '0-1'\n");
			ERROR = true;
		}//FIN else
	}else if(caracterDigito(operando.charAt(0)) == true )
	{
		if(baseDecimal(operando) == true)
			integer = Integer.parseInt(operando);
		else{
			System.out.printf("ERROR : LOS CARACTERES VALIDOS DE LA BASE DECIMAL SON '0-9'\n");
			ERROR = true;
		}//FIN else
			
	}else
	{
		System.out.printf("ERROR : BASES NUMERICAS DISPONIBLES ($<valor> , Hexadecimal) (@<valor>, Octal) (%<valor>, Binario) (<valor> , Decimal)\n");
		ERROR = true;
	}//FIN else
		

	return integer;
}//FIN baseNumerica
}//fin de la clase