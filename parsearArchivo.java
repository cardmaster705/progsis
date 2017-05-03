package programaciondeSistemas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class parsearArchivo 
{
	//Variables estaticas
	private final int A = 65;	//Referencia al codigo ASCII
	private final int F = 70;	//Referencia al codigo ASCII
	private final int Z = 90;	//Referencia al codigo ASCII
	private final int a = 97;	//Referencia al codigo ASCII
	private final int f = 102;	//Referencia al codigo ASCII
	private final int z = 122;	//Referencia al codigo ASCII
	private final int D0 = 48;	//Referencia al codigo ASCII digito 0
	private final int D1 = 49;	//Referencia al codigo ASCII digito 1
	private final int D7 = 55;	//Referencia al codigo ASCII digito 7
	private final int D9 = 57;	//Referencia al codigo ASCII digito 9
	
	private final String tabop;
	
	//Variables del programa
	private StringBuilder Etiqueta = new StringBuilder();
	private StringBuilder Codop = new StringBuilder();
	private StringBuilder Operando = new StringBuilder();
	private StringBuilder Comentario = new StringBuilder();
	
	private int Linea = 0;
	private int byteDirec = 0;
	
	//Variables auxiliares
	private StringTokenizer st;
	private final int FUERA_DE_RANGO = -257;
	private boolean ERROR = false;
	private boolean ERRORmodoDirec = true;
	
	
	public parsearArchivo() 						//Constructor donde crea variable local con el TABOP para evitar leer el archivo continuamente.
	{
		this.tabop = guardarTabop();	
	}
	
	public String guardarTabop()					//Crear variable local con TABOP
	{
		String TABOP;	//Variable temporal que devolvera la funcion
		
		FileReader f;	//Clase para archivos
		BufferedReader b;	//Clase para lectura de datos
		
		StringBuilder estructTemp = new StringBuilder();	//Se crea un StringBuilder para evitar basura
		String ln;	//Auxiliar para lectura de archivo
		int aux = 0;	//Contador de renglones
		try
		{
			f = new FileReader("4.Archivos de prueba/Practica #02/TABOP.txt");	//ABRIR el archivo TABOP
			b = new BufferedReader(f);	//LEER el archivo TABOP
			
			/**
			 * Crear una estructura local del TABOP para evitar abrir el archivo cada que necesite verificar un CODOP
			 */
			
			while((ln = b.readLine()) != null)	//LEER MIENTRAS no sea fin de archivo 
			{
				if(aux == 0)	//Si es el primer renglon agrega la linea leida a la variable 
					estructTemp.append(ln);
				
				else		//Si no es el primer renglon agrega un salto de linea y despues agrega la linea leida en la variable
				{
					estructTemp.append("\n");
					estructTemp.append(ln);
				}//FIN else
				aux++;		//Aumenta el contador de renglones
			}//FIN while
			estructTemp.append("\0");	//Agrega fin de cadena
			b.close();	//Cerrar lector
			f.close();	//Cerrar archivo
		}//FIN try
		catch(IOException e)
		{
			System.out.println(e);	//En caso de error imprimir error
		}//FIN catch
		
		TABOP = estructTemp.toString();	//Guardar en el string la estructura temporal del archivo
		
		return TABOP;
		
	}//FIN 
	
	public boolean caracterLetra(char caracter)		//Devuelve verdadero si el caracter es una letra, ya sea minuscula o minuscula
	{
		if(caracter >= A && caracter <= Z)	//Entre la 'A' y la 'Z'
			return true;
		else if (caracter >= a && caracter <= z)	//Entre la 'a' y la 'z'
			return true;
		else
			return false;
	}//FIN caracterLetra
	
	public boolean caracterDigito(char caracter)	//Devuelve verdadero si el caracter es un digito del '0' al '9'
	{	
		if(caracter >= D0 && caracter <= D9)
			return true;
		else
			return false;
	}//FIN caracterLetra
	
	public void validarEtiqueta(String cadena)		//Validar etiqueta
	{
		boolean caracterValido = true;	//Auxiliar para determinar si existe un caracter invalido
		
		if(cadena.equals(""))			//Si es cadena vacia imprimir NULL
			System.out.printf("%-11s","NULL");		
		else
		{
			System.out.printf("%-11s",cadena);	//Si no es vacia imprimir la cadena que representa la etiqueta
			
			if(cadena.length() > 8)	//Si es mayor a 8 imprimir un ERROR
				System.out.println("\tERROR : LONGITUD MAXIMA DE ETIQUETA ES DE 8 CARACTERES");
			
			else	//Si no es mayor a 8 entonces realiza las siguientes validaciones
			{
				if(! caracterLetra(cadena.charAt(0)) == true)	//Lee el primer caracter y si NO es una letra, imprimir ERROR
					System.out.println("\tERROR : LA ETIQUETA DEBE INICIAR CON LETRA");
				else	//Si es una letra realiza las siguientes validaciones
				{
					for(int i = 1; i < cadena.length(); i++)	//Recorre el resto de la cadena para validar los caracteres utilizados
					{
						if(!(caracterLetra(cadena.charAt(i)) == true || caracterDigito(cadena.charAt(i)) == true || cadena.charAt(i) == '_' ))	//Si se encuentra un caracter invalido a la etiqueta asignar false a la variable auxiliar
							caracterValido = false;
					}//FIN for
					if(caracterValido == false)	//Si se encontro un caracter invalido imprimir el ERROR
						System.out.println("\tERROR : LOS CARACTERES VALIDOS DE LA ETIQUETA SON 'A-Za-z','0..9' y '-'");
				}//FIN else
				
			}//FIN else
			
		}//FIN else
		
	}//validarEtiqueta
	
	public void validarCodop(String cadena)			//Validar Codop	
	{
		boolean caracterValido = true;	//Auxiliar para determinar si existe un caracter invalido
		short contadorPunto = 0;		//Variable auxiliar para contar los '.' que aparezcan
		
		if(cadena.equals(""))			//Si es cadena vacia imprimir NULL
			System.out.printf("%-10s","NULL");
		else		
		{
			System.out.printf("%-10s",cadena);	//Si no es vacia imprimir la cadena que representa el codop
			
			if(cadena.length() > 5)		//Si es mayor a 5 imprimir un ERROR
				System.out.println("\tERROR : LONGITUD MAXIMA DEL CODOP ES DE 5 CARACTERES");
			
			else	//Si no es mayor a 5 entonces realiza las siguientes validaciones
			{
				if(! caracterLetra(cadena.charAt(0)) == true)		//Lee el primer caracter y si NO es una letra, imprimir ERROR
					System.out.println("\tERROR : EL CODOP DEBE INICIAR CON LETRA");
				else	//Si es una letra realiza las siguientes validaciones
				{
					for(int i = 1; i < cadena.length(); i++)	//Recorre el resto de la cadena para validar los caracteres utilizados
					{
						if (cadena.charAt(i) == '.')		//Si encuentra un '.' aumentar el contador de la variable temporal auxiliar
							contadorPunto++;
						else if(!(caracterLetra(cadena.charAt(i)) == true) )	//Si se encuentra un caracter invalido a la etiqueta asignar false a la variable auxiliar
							caracterValido = false;						
					}//FIN for
					if(caracterValido == false)		//Si se encontro un caracter invalido imprimir el ERROR
						System.out.println("\tERROR : LOS CARACTERES VALIDOS DEL CODOP SON 'A-Za-z' y '.'");
					else if(contadorPunto > 1)		//Si existen caracteres que permite el CODOP pero tiene mas de un '.' imprimir ERROR
						System.out.println("\tERROR : EL '.' SOLO PUEDE SER REPRESENTADO UNA UNICA VEZ");
				}//FIN else
				
			}//FIN else
			
		}//FIN else
		
	}//validarCodop
	
	public void validarOperando(String cadena)		//Validar operando
	{
		if(cadena.equals(""))	//Si es cadena vacia imprimir NULL
			System.out.printf("%-13s","NULL");
		
		else	//Si no es cadena vacia imprimir el operando
		{
			System.out.printf("%-13s",cadena);
		}//FIN else
		
	}//FIN validarOperando
	
	public void validarComentario(String cadena)	//Validar comentario
	{
		if(cadena.compareTo("") != 0)	//Si la cadena no es vacia imprimir COMENTARIO
			System.out.printf("%-15s\n","COMENTARIO");
	}
	
	public void validarLinea(String Etiqueta, String Codop, String Operando, String Comentario)	//Validar linea
	{
		
		/**				Validar sintaxis
		 * 					X = 1 | 0
		 * 	Etiqueta	Codop	Operando	Comentario
		 * 		0		  0			0			0
		 * 		0		  0			0			1
		 *  	0		  1			1			X	
		 *   	0		  1			0			X
		 *    	1		  1			0			X
		 * 		1		  1			1			X
		 */
		System.out.printf("%-6s ","["+Linea+"]");	
		if(Etiqueta.matches(".*") && Codop.matches(".+") && Operando.matches(".*") && Comentario.matches(".*")) //Validar sintaxis [Etiqueta]Codop[Operando][Comentario]		
		{
			validarEtiqueta(Etiqueta);
			validarCodop(Codop);
			validarOperando(Operando);
			validarComentario(Comentario);
			
			identificarCodop(Codop, Operando);
		}//FIN if				
		
		else if(Etiqueta.equals("") && Codop.equals("") && Operando.equals("") && Comentario.compareTo("") != 0)	//Validar sintaxis donde solo exista Comentario
			validarComentario(Comentario);		
				
		else	//Si no es ninguna de las anteriores imprimir un ERROR
			System.out.println("ERROR DE SINTAXIS, DEEBE DE EXISTIR POR LO MENOS UN CODOP");	
		
		System.out.printf("\n");
		Linea++;
	}//validarLinea
	
	public void imprimirReglaDireccionamiento()	//Imprimir las reglas de direccionamiento
	{
		if(ERRORmodoDirec == false)
		{
			System.out.printf("\n\t+-----------------------------------------------+--------------------------------------------+\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t+-----------------------------------------------+--------------------------------------------+\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t+-----------------------------------------------+--------------------------------------------+\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t|%-47s|%-44s|\n"
					+ "\t+-----------------------------------------------+--------------------------------------------+\n\n"
					,"<digito> ::= 0-9","<letra> ::= A-Z | a-z"
					,"<hexa> ::= $ { [A-F] | [a-f] | [<digito>] }+","<octal> ::= @ {0-7}+"
					,"<binario> ::= % {0|1}+","<decimal> ::= {<digito>}+"
					,"MODO DE DIRECCIONAMIENTO","RANGO DEL NUMERO"
					,"<INH>::= ",""
					,"<IMM>::= #{<hexa>|<octal>|<binario>|<decimal>}+","(0,255) | (0,65535)"
					,"<DIR>::= {<hexa>|<octal>|<binario>|<decimal>}+","0-255"
					,"<EXT>::= {<hexa>|<octal>|<binario>|<decimal>}+","(256,65535)|<letra>{<letra>}*{<digito>}*{_}*"
					,"<IDX>::= {<decimal>}* \",\" X|Y|SP|PC","(-16,15)"
					,"<IDX>::= {<decimal>}+ \",\" [+|-] X|Y|SP","(1,8)"
					,"<IDX>::= {<decimal>}+ [+|-] \",\" X|Y|SP","(1,8)"
					,"<IDX>::= A|B|D \",\" X|Y|SP|PC",""
					,"<IDX1>::= {<decimal>}+ \",\" X|Y|SP|PC","(-256,17) | (16,255)"
					,"<IDX2>::= {<decimal>}+ \",\" X|Y|SP|PC","(256,65535)"
					,"<[IDX2]>::= \"[\" {<decimal>}+ \",\" X|Y|SP|PC \"]\"","(0,65535)"
					,"<[D,IDX]>::= D \",\" X|Y|SP|PC",""
					,"<REL>::= <letra> {<letra>}* {<digito>}* {_}*","");
		}
		
	}//FIN imprimir reglas
	
	public void byteDireccionamiento(String modoDirec)	//Recibe el modoDireccionamiento resultante e imprime segun sea el direciconamiento
	{
		switch(modoDirec)
		{
		case "INH":
			System.out.printf("INHERENTE "+byteDirec+" BYTE(S)");
			break;
			
		case "IMM":
			System.out.printf("INMEDIATO "+byteDirec+" BYTE(S)");
			break;

		case "DIR":
			System.out.printf("DIRECTO "+byteDirec+" BYTE(S)");
			break;
			
		case "EXT":
			System.out.printf("EXTENDIDO "+byteDirec+" BYTE(S)");
			break;
			
		case "IDX":
			System.out.printf("INDIZADO DE 5 BITS,(IDX),"+byteDirec+" BYTE(S)");
			break;
			
		case "IDXpre+/-":
			System.out.printf("INDIZADO DE PRE INCREMENTO/DECREMENETO,(IDX),"+byteDirec+" BYTE(S)");
			break;
			
		case "IDXpost+/-":
			System.out.printf("INDIZADO DE POST INCREMENTO/DECREMENETO,(IDX),"+byteDirec+" BYTE(S)");
			break;
			
		case "IDXA":
			System.out.printf("INDIZADO DE ACUMULADOR,(IDX),"+byteDirec+" BYTE(S)");
			break;			
			
		case "IDX1":
			System.out.printf("INDIZADO DE 9 BITS,(IDX1), "+byteDirec+" BYTE(S)");
			break;
			
		case "IDX2":
			System.out.printf("INDIZADO DE 16 BITS ,(IDX2),"+byteDirec+" BYTE(S)");
			break;
			
		case "[IDX2]":
			System.out.printf("INDIZADO INDIRECTO DE 16 BITS,( [IDX2] ), "+byteDirec+" BYTE(S)");
			break;
			
		case "[D,IDX]":
			System.out.printf("INDIZADO INDIRECTO DE ACUMULADOR \"D\", ( [D,IDX] ), "+byteDirec+" BYTE(S)");
			break;
			
		case "REL":
			if(byteDirec == 2)
				System.out.printf("RELATIVO DE 8 BITS, "+byteDirec+" BYTE(S)");
			else if(byteDirec == 3 || byteDirec == 4)
				System.out.printf("RELATIVO DE 16 BITS, "+byteDirec+" BYTE(S)");
			break;
		}//FIN switch
		
		
	}//FIN
	
	public void identificarCodop(String codop, String operando)	//Identificar codop
	{			
		byteDirec = 0;
		
		codop = codop.toUpperCase();	//Convertir la cadena tomada en mayusculas para facilitar las validaciones
		
		boolean codopEncontrado = false; //Bandera auxiliar para saber si se encontro el codop
		boolean contieneOPERANDO;		//Bandera auxiliar para especificar si tiene o no operando
		boolean operandoValido = false;	//Bandera auxiliar para validar que el codop tenga o no tenga operando
		boolean modoDirecCORRECTO = false;
		
		String modoDirec = identificarOperando(operando);
		String auxbyte = "";
		
		String con_sin_Operando;	//Variable auxiliar que guarda uno de los registros del tokenizer
		String auxmodoDirec = "";
		
		
		int i = 0;	//Variable auxiliar para imprimir el encabezado de la tabla de la informacion a mostrar
		
		if(operando.equals(""))		//Si el operando esta vacio entonces contieneOPERANDO = false;
			contieneOPERANDO = false;
		else	//Si contiene operando entonces contieneOPERANDO = true
			contieneOPERANDO = true;
		
		st = new StringTokenizer(tabop, "\n|");	//Se crea un objeto StringTokenizer de la variable local que contiene el TABOP
		
		while(st.hasMoreTokens())	//LEER MIENTRAS existan mas registros
		{		
			if(codop.equals(st.nextToken()) )	//LEER el siguiente registro que indica el nombre del codop en el tabop y comparar si el codop recibido es igual al registro leido
			{	
				codopEncontrado = true;	//Si se encontro asignar true a la variable auxiliar y realizar las siguientes validaciones
				con_sin_Operando = st.nextToken();	//Guardar en la variable auxiliar el siguiente registro que indica si tiene que tener operando
				if("SI".equals(con_sin_Operando) && contieneOPERANDO == true){	//Si el tabop indica que si debe de tener operando y tiene operando, es valida la sentencia
					operandoValido = true;
				}
				else if("NO".equals(con_sin_Operando) && contieneOPERANDO == false)	//Si el tabop indica que no debe de tener operando y no tenemos operando, es valida la sentencia
					operandoValido = true;
				else	//Si no es valida ninguna de las dos anteriores entonces es falso
					operandoValido = false;
				
				if (i == 0)	//Si es el primer renglon entonces 
				{
					if(operandoValido == false)
					{
						if(contieneOPERANDO == false)	//Si contiene operando y nuestra variable auxiliar que determina  es falso, quiere decir que el codop debe de contener operando
							System.out.printf("\n\tEL CODOP DEBE DE TENER OPERANDO");
						else if(contieneOPERANDO == true)//Si no contiene operando y nuestra variable auxiliar que determina es falso, quiere decir que el codop no debe de contener operando
							System.out.printf("\n\tEL CODOP NO DEBE DE TENER OPERANDO");
						
						System.out.printf("\t+-----------+------------+------+---------------+----------+\n"	//Imprimir la cabecera de la tabla a mostrar
								+ "\t|%-11s|%-12s|%-6s|%-15s|%-11s","MODO DE DIR","COD.MAQ.","BYTES","BYTES POR CALC","SUMA BYTES|"
								+ "\n\t+-----------+------------+------+---------------+----------+\n");
						i++;	
					}//FIN if
					
					

				}//FIN if
				auxmodoDirec = st.nextToken();
				

				if((auxmodoDirec.equals(modoDirec) ))
				{
					modoDirecCORRECTO = true;
					st.nextToken(); //COD. MAQ
					st.nextToken(); //BYTES
					st.nextToken(); //BYTES POR CALCULAR
					auxbyte += st.nextToken().charAt(0);
					byteDirec = Integer.parseInt(auxbyte); //BYTES TOTAL
					if(operandoValido == false){
						System.out.printf("\t|%-11s|%-12s|%-6s|%-15s|%-10s|\n",auxmodoDirec,st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken());
					}	
				}else if(auxmodoDirec.equals("IDX") && (modoDirec.equals("IDXpre+/-") || modoDirec.equals("IDXpost+/-") || modoDirec.equals("IDXA")))
				{
					modoDirecCORRECTO = true;
					st.nextToken(); //COD. MAQ
					st.nextToken(); //BYTES
					st.nextToken(); //BYTES POR CALCULAR
					auxbyte += st.nextToken().charAt(0);
					byteDirec = Integer.parseInt(auxbyte); //BYTES TOTAL
					if(operandoValido == false){
						System.out.printf("\t|%-11s|%-12s|%-6s|%-15s|%-10s|\n",auxmodoDirec,st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken());
					}	
				}else if( auxmodoDirec.equals("EXT") && modoDirec.equals("REL"))
				{
					modoDirecCORRECTO = true;
					modoDirec = "EXT";	
					st.nextToken(); //COD. MAQ
					st.nextToken(); //BYTES
					st.nextToken(); //BYTES POR CALCULAR
					auxbyte += st.nextToken().charAt(0);
					byteDirec = Integer.parseInt(auxbyte); //BYTES TOTAL
					if(operandoValido == false){
						System.out.printf("\t|%-11s|%-12s|%-6s|%-15s|%-10s|\n",auxmodoDirec,st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken());
					}	
				}//FIN else if	
				
			}//FIN if

				
		}//FIN while
		if(i > 0)
			System.out.println("\t+-----------+------------+------+---------------+----------+\n"); //Al terminar el while imprimir el cierre de la tabla
		
		if(codopEncontrado == true)	
			if(modoDirecCORRECTO == true)
				byteDireccionamiento(modoDirec);///AQUI PONER una funcion
			else if(modoDirec.equals("NO EXISTE")){
				if(ERROR == false)
					System.out.print("ERROR : MODO DE DIRECCIONAMIENTO INCORRECTO");
				ERRORmodoDirec = false;
			}else
				System.out.printf("ERROR : NO EXISTE MODO DE DIRECCIONAMIENTO ("+modoDirec+") EN ("+codop.toUpperCase()+")\n");			
		else
		System.out.printf("NO SE ENCONTRO EL CODOP DE OPERACION");
		
	}//FIN identificarOperando
	
	public boolean baseHex(String operando)			//Validar base hexadecimal
	{
		boolean esHex = true;
		
		for(int i = 0; i < operando.length();i++)	//leer caracter por caracter y valdiarlo
			if(!((operando.charAt(i) >= A && operando.charAt(i) <= F) || (operando.charAt(i) >= a && operando.charAt(i) <= f) || (caracterDigito(operando.charAt(i)) == true)))	//Si no es un caracter valido de un hexadecimal retornar falso 
				esHex = false;
						
		return esHex;
	}//FIN baseHex
	
	public boolean baseOctal(String operando)		//IValidar base octal
	{
		boolean esOctal = true;
		
		for(int i = 0; i < operando.length();i++)	//leer caracter por caracter y valdiarlo
			if(!(operando.charAt(i) >= D0 && operando.charAt(i) <= D7))	//Si no es un caracter entre el 0 y 7 (incluyendolos) es falso
				esOctal = false;
					
		return esOctal;
	}//FIN baseOctal
	
	public boolean baseBinario(String operando)		//Validar base binaria
	{
		boolean esBinario = true;
		
		if(operando.equals(""))
			esBinario = false;
		
		for(int i = 0; i < operando.length();i++)	//leer caracter por caracter y valdiarlo
		{
			if(!(operando.charAt(i) >= D0 && operando.charAt(i) <= D1))	//Si no es un '1' o un '0' es incorrecto
				esBinario = false;
		}//FIN for
						
		return esBinario;
	}//FIN baseBinario
	
	public boolean baseDecimal(String operando)		//Validar base decimal
	{
		boolean esDecimal = true;
		
		for(int i = 0; i < operando.length();i++)	//leer caracter por caracter y valdiarlo
			if(!(caracterDigito(operando.charAt(i)) == true))	//Si no es un digito del 0-9 es incorrecto
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
	
	public int baseNumerica(String operando)
	{
		ERROR = false;
		String baseNum = "";
		
		int integer = FUERA_DE_RANGO;		//Numero fuera de rango de cualquier modo de direccionamiento
		baseNum = operando.substring(1);
		
		if(operando.charAt(0) == '$')
		{
			if(baseNum.equals("")){
				System.out.printf("ERROR : DEBE DE CONTENER UN VALOR EN HEXADECIMAL");
				ERROR = true;
			}else if(baseHex(baseNum) == true){
				integer = Integer.parseInt(baseNum, 16);
				
			}else{
				System.out.printf("ERROR : LOS CARACTERES VALIDOS DE LA BASE HEXADECIMAL SON 'A-Fa-f' y '0..9'");
				ERROR = true;
			}//FIN else	
		}else if(operando.charAt(0) == '@')
		{
			if(baseNum.equals("")){
				System.out.printf("ERROR : DEBE DE CONTENER UN VALOR EN OCTAL");
				ERROR = true;
			}else if(baseOctal(baseNum) == true){
				integer = Integer.parseInt(baseNum,8);
			}else{
				System.out.printf("ERROR : LOS CARACTERES VALIDOS DE LA BASE OCTAL SON '0-7'");
				ERROR = true;
			}//FIN else
				
		}else if(operando.charAt(0) == '%')
		{	
			if(baseNum.equals("")){
				System.out.printf("ERROR : DEBE DE CONTENER UN VALOR EN DECIMAL");
				ERROR = true;
			}else if(baseBinario(baseNum) == true){
				integer = Integer.parseInt(baseNum,2);	
			}else{
				System.out.printf("ERROR : LOS CARACTERES VALIDOS DE LA BASE BINARIA SON '0-1'");
				ERROR = true;
			}//FIN else
		}else if(caracterDigito(operando.charAt(0)) == true )
		{
			if(baseDecimal(operando) == true)
				integer = Integer.parseInt(operando);
			else{
				System.out.printf("ERROR : LOS CARACTERES VALIDOS DE LA BASE DECIMAL SON '0-9'");
				ERROR = true;
			}//FIN else
				
		}else
		{
			System.out.printf("ERROR : BASES NUMERICAS DISPONIBLES ($<valor> , Hexadecimal) (@<valor>, Octal) (%<valor>, Binario) (<valor> , Decimal)");
			ERROR = true;
		}//FIN else
			

		return integer;
	}//FIN baseNumerica

	public String identificarOperando(String operando)
	{
		operando = operando.toUpperCase();
		String modoDirec = "NO EXISTE";
		int valorOperando = FUERA_DE_RANGO;
		String auxValorOperando;
		
		ERROR = false;
		//////////////////////////Evalua INH/////////////////////////////////////////////////////////////////////////
		if(operando.equals(""))	{
			modoDirec = "INH";
		}//FIN if
			
		
		//////////////////////////Evalua IMM/////////////////////////////////////////////////////////////////////////
		else if(operando.matches("#[$|@|%|0|1|2|3|4|5|6|7|8|9][A-Z0-9]*"))
		{
			valorOperando = baseNumerica(operando.substring(1) );
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,0,255) == true)
				{
					modoDirec = "IMM";
				}else if(estaEntre(valorOperando,0,65535) == true){
					modoDirec = "IMM";	
				}else{
					System.out.printf("ERROR : LOS RANGOS VALIDOS DE INMEDIATO SON (0,255) O (0,65535)");
					ERROR = true;
				}//FIN else
			}//FIN	
		}//FIN else if(es IMM)
		
		//////////////////////////Evalua DIR o EXT///////////////////////////////////////////////////////////////////
		else if(operando.matches("[$|@|%|0|1|2|3|4|5|6|7|8|9][A-Z0-9]*"))
		{
			valorOperando = baseNumerica(operando);
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,0,255) == true){
					modoDirec = "DIR";
				}else if(estaEntre(valorOperando,256,65535) == true){
					modoDirec = "EXT";
				}else{
					System.out.printf("ERROR : RANGOS VALIDO DE (DIR) ES (0,255), DE (EXT) ES (256,65535)");
					ERROR = true;
				}//FIN else
			}	
		}//FIN else if(es DIR o EXT)
		
		//////////////////////////Evalua IDX,IDX1,IDX2///////////////////////////////////////////////////////////////
		else if(operando.matches("[-]?[0-9]*,(X|Y|(SP)|(PC))"))
		{
			if(operando.charAt(0) == ',')
				valorOperando = 0;
			else
			{
				st = new StringTokenizer(operando, ",");
				auxValorOperando = st.nextToken();
				if(!(auxValorOperando.charAt(0) == '-' && auxValorOperando.length() == 1))	//Si la sub cadena NO esta compuesta solo por el caracter '-' entonces..
					valorOperando = Integer.parseInt(auxValorOperando);
			}//FIN else
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,-16,15) == true)
				{
					modoDirec = "IDX";
				}else if(estaEntre(valorOperando,-256,-17) == true)
				{
					modoDirec = "IDX1";
				}else if (estaEntre(valorOperando,16,255) == true)
				{
					modoDirec = "IDX1";
				}else if (estaEntre(valorOperando,256,65535) == true)
				{
					modoDirec = "IDX2";
				}else{
					System.out.printf("ERROR : RANGO VALIDO DE (IDX) ES (0,255), (IDX1) (-256,-17) O (16,255), (IDX2) (256,65535)");
					ERROR = true;
				}
			}//FIN ERROR
		}//FIN else if(IDX)

		//////////////////////////Evalua IDX////////////////////////////////////////////////////////////////////////
		else if(operando.matches("[0-9]+,[+|-](X|Y|(SP))"))
		{
			st = new StringTokenizer(operando, ",");
			valorOperando = Integer.parseInt(st.nextToken());
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,1,8) == true)
				{
					modoDirec = "IDXpre+/-";
				}else{
					System.out.printf("ERROR : RANGO VALIDO DE (IDX) PRE/POST INC/DEC ES (1,8)");
					ERROR = true;
				}//FIN ELSE
			}//FIN ERROR
		}//FIN else if(IDX INC / DEC)
		
		else if(operando.matches("[0-9]+,(X|Y|(SP))[+|-]"))
		{
			st = new StringTokenizer(operando, ",");
			valorOperando = Integer.parseInt(st.nextToken());
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,1,8) == true)
				{
					modoDirec = "IDXpost+/-";
				}else{
					System.out.printf("ERROR : RANGO VALIDO DE (IDX) PRE/POST INC/DEC ES (1,8)");
					ERROR = true;
				}//FIN ELSE	
			}//FIN IF ERROR 
			
		}//FIN else if(IDX INC / DEC)
		
		//////////////////////////Evalua IDX///////////////////////////////////////////////////////////////////////
		else if(operando.matches("[A|B|D],(X|Y|(SP)|(PC))"))
		{
			modoDirec = "IDXA";
		}//FIN else if
			

		//////////////////////////Evalua [IDX2]////////////////////////////////////////////////////////////////////
		else if(operando.matches("\\[[0-9]+,((X)|(Y)|(SP)|(PC))\\]"))
		{
			st = new StringTokenizer(operando.substring(1), ",");
			valorOperando = Integer.parseInt(st.nextToken());
			if(ERROR == false)
			{
				if(estaEntre(valorOperando,0,65535) == true)
				{
					modoDirec = "[IDX2]";
				}else{
					System.out.printf("ERROR : RANGO VALIDO DE ([IDX2]) ES (0,65535)");
					ERROR = true;
				}//FIN ELSE
			}//FIN ERROR IF
			
				
		}//FIN else if
		
		//////////////////////////Evalua [D,IDX]///////////////////////////////////////////////////////////////////
		else if(operando.matches("\\[D,(X|Y|(SP)|(PC))\\]"))
		{
			modoDirec = "[D,IDX]";
		}//FIN else if
			
		
			//////////////////////////Evalua REL///////////////////////////////////////////////////////////////////
		else if(operando.matches("([A-Z]\\w*\\d*)"))
		{
			modoDirec = "REL";
		}//FIN else if
/*		
		else if(operando.equals("#"))
		{
			System.out.print("ERROR : BASES NUMERICAS DISPONIBLES ($<valor> , Hexadecimal) (@<valor>, Octal) (%<valor>, Binario) (<valor> , Decimal)");
			ERROR = true;
		}//FIN else if ERROR INMEDIATO
*/			
		return modoDirec;	
	}//FIN identificarOperando
		
	public void parsear(String nom)		//Funcion principal , toma como parametro el nombre del archivo que se va a parsear
	{
		/**
		 * Guardar contenido del Archivo en una estructura temporal para parsearlo
		 */
		Linea = 0;
		StringBuilder estructTemp = new StringBuilder();		
		String ln; //Auxiliar del lector
		
		ERRORmodoDirec = true;
		
		int aux = 0;	//contador de lineas
		
		FileReader f;	//inicializar fileReader
		BufferedReader b;	//inicializar lector
		
		try
		{
			f = new FileReader(nom);	//ABRIR el archivo indicado
			b = new BufferedReader(f);	//LEER el archivo indicado
			
			while((ln = b.readLine()) != null) //LEER MIENTRAS no sea fin de archivo
			{
				if(aux == 0)	//Si es el primer renglon solo guardar en la variable la linea
					estructTemp.append(ln);
				
				else	//Si no es el primer renglon agregar un salto de linea y despues la linea leida
				{
					estructTemp.append("\n");
					estructTemp.append(ln);
				}//FIN else
				aux++;	//Aumenta el contador de la linea
			}//FIN while
			estructTemp.append("\0");	//Agrega fin de cadena
			b.close();	//Cierra el lector
			f.close();	//Ciera el archivo
		//	System.out.printf(estructTemp);
		}//FIN try
		catch(IOException e)
		{
			System.out.println(e);		//Imprimir ERROR
		}//FIN catch
				
		
		/**
		 * Analizar la estructura
		 */
		short espacios,permiteContar;		//Contador de espacios en blanco y la variable que se utilizara como bandera para contar espacios
		espacios = 0;
		
		Etiqueta.delete(0, Etiqueta.length());
		Codop.delete(0, Codop.length());
		Operando.delete(0, Operando.length());
		Comentario.delete(0, Comentario.length());
							
								//Auxiliares para indicar que es lo que se esta leyendo
		boolean leyendoEtiqueta = false;		
		boolean leyendoCodop = false;
		boolean leyendoOperando = false;
		boolean leyendoComentario = false;
		
		permiteContar = 1;		//Auxiliar para el control del contador de espacios

		for(int i = 0 ; i < estructTemp.length(); i++)	//LEER caracter por caracter la estructura temporal
		{
			if(estructTemp.charAt(i) == '\n')		//Si es salto de linea validar la linea e inicializar variables
			{
				validarLinea(Etiqueta.toString(), Codop.toString(), Operando.toString(), Comentario.toString());
				
				espacios = 0;
				permiteContar = 1;
				
				Etiqueta.delete(0, Etiqueta.length());
				Codop.delete(0, Codop.length());
				Operando.delete(0, Operando.length());
				Comentario.delete(0, Comentario.length());
				
				leyendoEtiqueta = false;
				leyendoCodop = false;
				leyendoOperando = false;
				leyendoComentario = false;

			}//Fin validacion de Linea
			
			else if(estructTemp.charAt(i) == '\0')		//Si es fin de cadena validar linea sin inicializar variables
				validarLinea(Etiqueta.toString(), Codop.toString(), Operando.toString(), Comentario.toString());
						
			else if( (estructTemp.charAt(i) == ' ' || estructTemp.charAt(i) == '\t') && permiteContar == 1)	//Si es un espacio en blanco y nos permite contar, incrementar el contador de espacios
			{
				espacios++;
				leyendoEtiqueta = false;
				leyendoCodop = false;
				leyendoOperando = false;
				leyendoComentario = false;
			}//
			
			else if(estructTemp.charAt(i) == ';' && leyendoEtiqueta == false && leyendoCodop == false && leyendoOperando == false)	//Si el caracter es ';' y no esta leyendo algun TOKEN que no sea comentario, entonces es un comentario
			{
				Comentario.append(estructTemp.charAt(i));
				leyendoComentario = true;
				permiteContar = 0;
			}//FIN else if
					
			else if (espacios == 0 && estructTemp.charAt(i) != '\n' && leyendoComentario == false)		//Si espacios en blanco es 0 y NO es comentario lo que lee, entonces es etiqueta
			{
				Etiqueta.append(estructTemp.charAt(i));
				leyendoEtiqueta = true;
			}//FIN else if
			
			else if(espacios == 1 && estructTemp.charAt(i) != '\n' && leyendoComentario == false)		//Si espacios en blanco es 1 y NO es comentario lo que lee, entonces es codop
			{
				Codop.append(estructTemp.charAt(i));
				leyendoCodop = true;
			}//FIN else if
			
			/**
			 * Operando tipo cadena "	"
			 */
			else if(espacios == 2 && estructTemp.charAt(i) == '"' && leyendoComentario == false)		//Si espacios en blanco es 2,NO es comentario lo que lee y el caracter es " entonces es Operando y deja de contar espacios hasta encontrar otro "
			{
				Operando.append(estructTemp.charAt(i));
				permiteContar *= -1; 	//Si es 1 cuenta espacios en blanco e inicializa variables, mientras no , no inicializara variable y el programa identificara todo lo leido como operando ,  
				leyendoOperando = true;
			}//FIN else if
				
			else if(espacios == 2 && estructTemp.charAt(i) != '\n' && leyendoComentario == false)		//Si espacios en blanco es 2 y NO es comentario lo que lee, entonces es Operando
			{
				Operando.append(estructTemp.charAt(i));
				leyendoOperando = true;
			}//FIN else if
			
		}//FIN del for
		
		String ultimoCodop;	
		ultimoCodop = Codop.toString().toUpperCase();	//Convertir en mayusculas el ultimo codop

		if(!ultimoCodop.equals("END"))	//Si no es END el ultimo codop imprimir error
			System.out.printf("ERROR DE SINTAXIS, EL ULTIMO CODOP DEBE DE SER \"END\"");
		
		
		imprimirReglaDireccionamiento();

	}//FIN parsear(String nom)
}//parsear Archivo
