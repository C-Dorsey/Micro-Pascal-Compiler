/**
 * This is a simple example of a jflex lexer definition
 */

/* Declarations */


%%

%public				/* Make the class public */
%class  Scanner   /* Names the produced java file */
%function nextToken /* Renames the yylex() function */
%type   String      /* Defines the return type of the scanning function */
%eofval{
  return null;
%eofval}
/* Patterns */

other         = .
letter        = [A-Za-z]
word          = {letter}+
whitespace    = [ \n\t\rn]
digit		  = [0-9]
number		  = {digit}+
float		  = {number}\.{number} 
variable      = {letter}+{digit}*{letter}*

%%
/* Lexical Rules */
{number}	{
				/** Print out the digits or numbers that were found. */
				System.out.println("Found number: "+yytext());
				return(yytext());
			}

{float}		{
				/** Print out any floating point numbers that are found. */
				System.out.println("Found float: "+yytext());
				return(yytext());
			}

{word}     {
             /** Print out the word that was found. */
             System.out.println("Found a word: " + yytext());
             return( yytext());
            }
            
{whitespace}  {  /* Ignore Whitespace */ 
                 return "";
              }

{variable}  {
                /** Print out the variable that was found. */
                System.out.println("Found a variable: " + yytext());
                return( yytext());
            }

{other}    {
             //System.out.println("Illegal char: '" + yytext() + "' found.");
             return "";
           }

