/** TokenType Class
* This class contains all the different types of 
* objects a token can be.
* @author Cohl Dorsey
*/
package dorseyScanner;

public enum TokenType {
    NUMBER, ID, PLUS, MINUS, SEMI, ASSIGN, WHILE, PROGRAM, VAR, ARRAY,
    OF, FUNCTION, PROCEDURE, BEGIN, END, IF, THEN, ELSE, DO, NOT, OR,
    AND, DIV, MOD, INTEGER, REAL, COMMA, RBRACKET, LBRACKET, COLON,
    RPAREN, LPAREN, LCURLY, RCURLY, ASTERISK, EQUAL, NOTEQUAL, GTHAN,
    LTHAN, GTHANEQUAL, LTHANEQUAL, FSLASH, PERIOD
}
