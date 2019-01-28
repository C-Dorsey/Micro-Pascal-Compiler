/**
 * The AllenScanner is scanner program that is going to use JFlex
 * to generate a Java scanner that will scan through the input text
 * file and return a lexeme, yytext returns the matched lexeme.
 * The lexeme is going to be the string containing the actual characters
 * that were read in that make up one particular token in the language.
 *
 * @author Cohl Dorsey
 */

/* Declarations */

package dorseyScanner;
import java.util.HashMap;

%%

%public				/* Makes the class public. */
%class  Scanner   /* Names the produced java file. */
/*Makes all of the generated methods and fields of the class private, except for the constructor and the next_token method.*/

%function nextToken /* Renames the yylex() function. */
%type   Token     /* Defines the return type of the scanning function.*/
%eofval{
  return null;
%eofval}

%{
	/*Declaring an instance variable of a HashMap called lookupTable
	Copies the HashMap code inside the brackets and puts it
	inside the class itself. */
	private HashMap<String, TokenType> lookupTable;
%}

%init{
	/*Copies the HashMap code into the constructor of the
	generated class. Puts the lexeme, and the type of the
	token, into the HashMap to be used as a lookup table
	for the all the types of Tokens.*/

	lookupTable = new HashMap<String, TokenType>();
	lookupTable.put("+", TokenType.PLUS);
	lookupTable.put("-", TokenType.MINUS);
	lookupTable.put(";", TokenType.SEMI);
	lookupTable.put(":=", TokenType.ASSIGN);
	lookupTable.put("program", TokenType.PROGRAM);
	lookupTable.put("var", TokenType.VAR);
	lookupTable.put("array", TokenType.ARRAY);
	lookupTable.put("of", TokenType.OF);
	lookupTable.put("function", TokenType.FUNCTION);
	lookupTable.put("procedure", TokenType.PROCEDURE);
	lookupTable.put("begin", TokenType.BEGIN);
	lookupTable.put("end", TokenType.END);
	lookupTable.put("if", TokenType.IF);
	lookupTable.put("then", TokenType.THEN);
	lookupTable.put("else", TokenType.ELSE);
	lookupTable.put("while", TokenType.WHILE);
	lookupTable.put("do", TokenType.DO);
	lookupTable.put("not", TokenType.NOT);
	lookupTable.put("or", TokenType.OR);
	lookupTable.put("and", TokenType.AND);
	lookupTable.put("div", TokenType.DIV);
	lookupTable.put("mod", TokenType.MOD);
	lookupTable.put("integer", TokenType.INTEGER);
	lookupTable.put("real", TokenType.REAL);
	lookupTable.put("read", TokenType.READ);
	lookupTable.put("write", TokenType.WRITE);
	lookupTable.put("return", TokenType.RETURN);
	lookupTable.put(",", TokenType.COMMA);
	lookupTable.put("]", TokenType.RBRACKET);
	lookupTable.put("[", TokenType.LBRACKET);
	lookupTable.put(":", TokenType.COLON);
	lookupTable.put(")", TokenType.RPAREN);
	lookupTable.put("(", TokenType.LPAREN);
	lookupTable.put("*", TokenType.MULTI);
	lookupTable.put("=", TokenType.EQUIV);
	lookupTable.put("<>", TokenType.NOTEQUAL);
	lookupTable.put(">", TokenType.GTHAN);
	lookupTable.put("<", TokenType.LTHAN);
	lookupTable.put(">=", TokenType.GTHANEQUAL);
	lookupTable.put("<=", TokenType.LTHANEQUAL);
	lookupTable.put("/", TokenType.FSLASH);
	lookupTable.put(".", TokenType.PERIOD);

%init}

/* Patterns */

other         = .		   /*Matches any character other than newline*/
letter        = [A-Za-z]   /*Matches any characters between a-z or A-Z*/

whitespace    = [ \n\t\r\rn]    /*Matches any characters that produce whitespace*/
digit	=	[0-9]          /*Matches any characters between 0-9*/
digits = {digit}+     	   /*Definition for creating whole numbers*/
float = {digits}\.{digits}	/*Definition for creating floating point numbers.*/
id 		= {letter}({letter}|{digit})* /*Definition for ids. Matches a letter
										pattern followed by a number, or letter
										zero or more times.*/
optional_exponent = (((E|e)[\+|\-]?){digits})?  /*Definition for optional exponents.*/
scientific_notation = {digit}\.{digits}{optional_exponent}  /*Matches a single digit,
															followed by a period and an
															exponent with digits.*/
optional_fraction = (\.{digits})?  /*definition for a fraction*/

num = {digits}{optional_fraction}{optional_exponent} /*Matches numbers in
													   the grammar as
													   digits followed by
													   optional fractions
													   and optional
													   exponents.*/

symbols = <>|<=|>=|:=|[\;\,\[\]\:\)\(\+\-\*\=\<\>\/\.] /*Matches all of
														the symbols listed
													 	in the grammar.*/

%%
/* Lexical Rules */


		 /*Prints out the number that was found. Invokes the constructor
		 with two arguments, the lexeme and the token type.*/
{num} { return(new Token(yytext(), TokenType.NUMBER));}

			 /*Prints out a letter that was found and returns it. Invokes
			 the constructor with two arguments, the lexeme and the token
			 type.*/
{letter}     { return(new Token(yytext(),
			   lookupTable.get(yytext())));}



{whitespace}  {  /* Ignore whitespace, do nothing. */
              }

             /*Prints out the id that was found and returns it. Invokes a
             constructor with two arguments, the lexeme and the token
             type. If the lexeme isn't empty when it returns from the
             lookup table, then it's a Token. If it is empty, it's an
             identifier. */
{id}  		{
				if(lookupTable.get(yytext()) != null)
				{return(new Token(yytext(), lookupTable.get(yytext())));}

				else{
				return(new Token(yytext(), TokenType.ID));}
			}

			/*Prints out the symbol that was found and returns it. Invokes
			the constructor with two arguments, the lexeme and the token
			type.*/
{symbols}	{ return(new Token(yytext(),
			 lookupTable.get(yytext())));}

            /*Prints out the float that was found and returns it. Invokes
             the constructor with two arguments, the lexeme and the token
             type.*/
{float}  	{ return(new Token(yytext(),
			  lookupTable.get(yytext())));}

			/*Prints out an illegal character that was found
			and returns it. Invokes the constructor
		 with two arguments, the lexeme and the token type.*/
{other}    {System.out.println("Unidentified Token: " + yytext());}