/**
 * A Recognizer program that will recognize tokens for the parser.
 * This program will take in a file or a token, and using our grammar,
 * will recognize token types and handles them how they are defined to
 * be by the grammar of our micro pascal language.
 */

package dorseyparser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import dorseyScanner.Scanner;
import dorseyScanner.Token;
import dorseyScanner.TokenType;
import static sun.management.Agent.error;

public class Recognizer
{
    private Token lookahead;
    private Scanner scanner;

    /**
     * The parser constructor that takes in a file path or text
     * containing tokens. If isFilename is true, it signifies that
     * the parser is looking at a file. If false, it signifies that
     * the parser is looking at a lexeme and not a file.
     * @param text - file path or text containing tokens to be
     * recognized.
     * @param isFilename - boolean expression to  help recognize if it
     * is a file path or text.
     */
    public Recognizer( String text, boolean isFilename)
    {
        // If the String object passed in is a file, the following
        // statement is run.
        if(isFilename)
        {
            FileInputStream fis = null; // Create file object stream
            try
            {
                fis = new FileInputStream(text); // Assign the file to the stream
            }
            catch (FileNotFoundException ex)
            {
                // Error is thrown if file passed in can't be found
                error("File Not Found");
            }
            InputStreamReader isr = new InputStreamReader(fis); // Pass file into the stream reader
            scanner = new Scanner(isr); // Pass the reader into the scanner
        }
        // If the String object passed in is instead a token
        else
        {
            scanner = new Scanner(new StringReader(text)); // Pass token to scanner
        }
        try
        {
            //Initializing lookahead by reading a token.
            lookahead = scanner.nextToken();
        }
        catch (IOException ex)
        {
            // Error is thrown if not a string.
            error("Scan error");
        }
    }

    ///////////////////////////////////
    // Implementation of the grammar //
    ///////////////////////////////////

    /**
     * Executes the rule for the program symbol.
     */
    public void program()
    {
        match(TokenType.PROGRAM);
        match(TokenType.ID);
        match(TokenType.SEMI);
        declarations();
        subprogram_declarations();
        compound_statement();
        match(TokenType.PERIOD);
    }

    /**
     * Executes the rule for identifier list
     */
    public void identifier_list()
    {
        match(TokenType.ID);
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(this.lookahead.getType() == TokenType.COMMA)
        {
            match(TokenType.COMMA);
            identifier_list();
        }
        else
        {
            //Do nothing. This is for handling the empty/lambda option.
        }
    }

    /**
     * Executes the rule for declarations
     */
    public void declarations()
    {
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(this.lookahead.getType() == TokenType.VAR)
        {
            match(TokenType.VAR);
            identifier_list();
            match(TokenType.COLON);
            type();
            match(TokenType.SEMI);
            declarations();
        }
        else
        {
            //Do nothing. This is for handling the empty/lambda option.
        }
    }


    /**
     * Handles the rule for type
     */
    public void type()
    {
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(this.lookahead.getType() == TokenType.INTEGER ||
                this.lookahead.getType() == TokenType.REAL)
        {
            standard_type();
        }
        /* Else compare the current lookahead with a different
        declaration.*/
        else if(this.lookahead.getType() == TokenType.ARRAY)
        {
            match(TokenType.ARRAY);
            match(TokenType.LBRACKET);
            match(TokenType.NUMBER);
            match(TokenType.COLON);
            match(TokenType.NUMBER);
            match(TokenType.RBRACKET);
            match(TokenType.OF);
            standard_type();
        }
        // Else not a type
        else
        {
            error("Not a Type");
        }
    }

    /**
     * Handles the rule for standard type
     */
    public void standard_type()
    {
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(this.lookahead.getType() == TokenType.INTEGER)
        {
            match(TokenType.INTEGER);
        }
        /* Else compare the current lookahead with a different
        declaration.*/
        else if(this.lookahead.getType() == TokenType.REAL)
        {
            match(TokenType.REAL);
        }
        // Else not a standard type
        else
        {
            error("Not Standard Type");
        }
    }

    /**
     * Handles the rule for subprogram declarations
     */
    public void subprogram_declarations()
    {
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(lookahead.getType() == TokenType.FUNCTION ||
                lookahead.getType() == TokenType.PROCEDURE)
        {
            subprogram_declaration();
            match(TokenType.SEMI);
            subprogram_declarations();
        }
        else
        {
            //Do nothing. This is for handling the empty/lambda option.
        }
    }

    /**
     * Handles the rule for subprogram declaration
     */
    public void subprogram_declaration()
    {
        subprogram_head();
        declarations();
        compound_statement();
    }

    /**
     * Handles the rule for subprogram head
     */
    public void subprogram_head()
    {
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(lookahead.getType() == TokenType.FUNCTION)
        {
            match(TokenType.FUNCTION);
            match(TokenType.ID);
            arguments();
            match(TokenType.COLON);
            standard_type();
            match(TokenType.SEMI);
        }
        /* Else compare the current lookahead with a different
        declaration.*/
        else if(lookahead.getType() == TokenType.PROCEDURE)
        {
            match(TokenType.PROCEDURE);
            match(TokenType.ID);
            arguments();
            match(TokenType.SEMI);
        }
        // Else not a subprogram head
        else
        {
            error("Not Subprogram Head");
        }
    }

    /**
     * Handles the rule for arguments
     */
    public void arguments()
    {
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(this.lookahead.getType() == TokenType.LPAREN)
        {
            match(TokenType.LPAREN);
            parameter_list();
            match(TokenType.RPAREN);
        }
        else
        {
            //Do nothing. This is for the empty/lambda option.
        }
    }

    /**
     * Handles the rule for parameter list
     */
    public void parameter_list()
    {
        identifier_list();
        match(TokenType.COLON);
        type();
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(lookahead.getType() == TokenType.SEMI)
        {
            match(TokenType.SEMI);
            parameter_list();
        }
        else
        {
            //Do nothing. This is for the empty/lambda option.
        }
    }

    /**
     * Handles the rule for compound statement
     */
    public void compound_statement()
    {
        match(TokenType.BEGIN);
        optional_statements();
        match(TokenType.END);
    }

    /**
     * Handles the rule for optional statements
     */
    public void optional_statements()
    {
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(isStatement(lookahead))
        {
            statement_list();
        }
        else
        {
            //Do nothing. This is for the empty/lambda option.
        }
    }

    /**
     * Handles the rule for statement list
     */
    public void statement_list()
    {
        statement();
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(lookahead.getType() == TokenType.SEMI)
        {
            match(TokenType.SEMI);
            statement_list();
        }
        else
        {
            //Do nothing. This is for the empty/lambda option.
        }
    }

    /**
     * Handles the rule for statement
     */
    public void statement()
    {
        // All if statements compare current token to next.
        if(lookahead.getType() == TokenType.ID)
        {
            variable();
            assignop();
            expression();
        }
               /* procedure_statement() path goes here.
               This path is future work we don't have to do yet.
                */

        else if(lookahead.getType() == TokenType.BEGIN)
        {
            compound_statement();
        }
        else if(lookahead.getType() == TokenType.IF)
        {
            match(TokenType.IF);
            expression();
            match(TokenType.THEN);
            statement();
            match(TokenType.ELSE);
            statement();
        }
        else if(lookahead.getType() == TokenType.WHILE)
        {
            match(TokenType.WHILE);
            expression();
            match(TokenType.DO);
            statement();
        }
        else if(lookahead.getType() == TokenType.READ)
        {
            match(TokenType.READ);
            match(TokenType.LPAREN);
            match(TokenType.ID);
            match(TokenType.RPAREN);
        }
        else if(lookahead.getType() == TokenType.WRITE)
        {
            match(TokenType.WRITE);
            match(TokenType.LPAREN);
            expression();
            match(TokenType.RPAREN);
        }
        else if(lookahead.getType() == TokenType.RETURN)
        {
            match(TokenType.RETURN);
            expression();
        }
        // Else not a statement
        else
        {
            error("Not a Statement");
        }
    }

    /**
     * Handles the rule for variable
     */
    public void variable()
    {
        match(TokenType.ID);
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(this.lookahead.getType() == TokenType.LBRACKET)
        {
            match(TokenType.LBRACKET);
            expression();
            match(TokenType.RBRACKET);
        }
        else
        {
            //Do nothing. This is for the empty/lambda option.
        }
    }

    /**
     * Handles the rule for procedure statement
     */
    public void procedure_statement()
    {
        match(TokenType.ID);
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(this.lookahead.getType() == TokenType.LPAREN)
        {
            match(TokenType.LPAREN);
            expression_list();
            match(TokenType.RPAREN);
        }
        else
        {
            //Do nothing. This is for the empty/lambda option.
        }
    }

    /**
     * Handles the rule for expression list
     */
    public void expression_list()
    {
        expression();
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(this.lookahead.getType() == TokenType.COMMA)
        {
            match(TokenType.COMMA);
            expression_list();
        }
        else
        {
            //Do nothing. This is for the empty/lambda option.
        }
    }

    /**
     * Handles the rule for expression
     */
    public void expression()
    {
        simple_expression();
        if(isRelop(lookahead))
        {
            relop();
            simple_expression();
        }
        else
        {
            //Do nothing. This is for the empty/lambda option.
        }
    }

    /**
     * This method is used to parse an expression. The simple
     * expression in the micro pascal grammar.
     */
    public void simple_expression()
    {
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(this.lookahead.getType() == TokenType.ID ||
                this.lookahead.getType() == TokenType.NUMBER ||
                this.lookahead.getType() == TokenType.LPAREN ||
                this.lookahead.getType() == TokenType.NOT)
        {
            term();
            simple_part();
        }
        else if(this.lookahead.getType() == TokenType.PLUS ||
                this.lookahead.getType() == TokenType.MINUS)
        {
            sign();
            term();
            simple_part();
        }
        // Else not a simple expression
        else
        {
            error("Not a Simple Expression");
        }
    }

    /**
     * Handles the rule for simple part
     */
    public void simple_part()
    {
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if( lookahead.getType() == TokenType.PLUS ||
                lookahead.getType() == TokenType.MINUS ||
                lookahead.getType() == TokenType.OR) {
            addop();
            term();
            simple_part();
        }
        else
        {
            //Do nothing. This is for the empty/lambda option.
        }
    }

    /**
     * Handles the rule for term
     */
    public void term() {
        factor();
        term_part();
    }

    /**
     * Handles the rule for term part
     */
    public void term_part()
    {
        if( isMulop( lookahead) )
        {
            mulop();
            factor();
            term_part();
        }
        else
        {
            //Do nothing. This is for the empty/lambda option.
        }
    }

    /**
     * Handles the rule for factor
     */
    public void factor()
    {
        // All if statements compare the current lookahead token to the next
        if(this.lookahead.getType() == TokenType.ID)
        {
            match(TokenType.ID);
            if(this.lookahead.getType() == TokenType.LBRACKET)
            {
                match(TokenType.LBRACKET);
                expression();
                match(TokenType.RBRACKET);
            }
            else if(this.lookahead.getType() == TokenType.LPAREN)
            {
                match(TokenType.LPAREN);
                expression_list();
                match(TokenType.RPAREN);
            }
            else
            {
                //Do nothing. This is for the empty/lambda option.
            }
        }
        else if(this.lookahead.getType() == TokenType.NUMBER)
        {
            match(TokenType.NUMBER);
        }
        else if(this.lookahead.getType() == TokenType.LPAREN)
        {
            match(TokenType.LPAREN);
            expression();
            match(TokenType.RPAREN);
        }
        else if(this.lookahead.getType() == TokenType.NOT)
        {
            match(TokenType.NOT);
            factor();
        }
        // Else not a factor
        else
        {
            error("Not a Factor");
        }
    }

    /**
     * Handles the rule for sign
     */
    public void sign()
    {
        /* Compare the current lookahead token with a token type to
        see if it matches the same type. */
        if(this.lookahead.getType() == TokenType.PLUS)
        {
            match(TokenType.PLUS);
        }
        else if(this.lookahead.getType() == TokenType.MINUS)
        {
            match(TokenType.MINUS);
        }
        // Else not a sign
        else
        {
            error("Not a Sign");
        }
    }

    /**
     * This method is based on the relop rules in the micro pascal
     * grammar. It is used to compare the lookahead token with
     * a token type to see if it matches that of the same type.
     */
    private void relop()
    {
        // All if statements compare the current token to the next
        if(this.lookahead.getType() == TokenType.EQUIV)
        {
            match(TokenType.EQUIV);
        }
        else if(this.lookahead.getType() == TokenType.NOTEQUAL)
        {
            match(TokenType.NOTEQUAL);
        }
        else if(this.lookahead.getType() == TokenType.LTHAN)
        {
            match(TokenType.LTHAN);
        }
        else if(this.lookahead.getType() == TokenType.GTHAN)
        {
            match(TokenType.GTHAN);
        }
        else if(this.lookahead.getType() == TokenType.LTHANEQUAL)
        {
            match(TokenType.LTHANEQUAL);
        }
        else if(this.lookahead.getType() == TokenType.GTHANEQUAL)
        {
            match(TokenType.GTHANEQUAL);
        }
        // Else not a relop
        else
        {
            error("Not a Relop");
        }
    }

    /**
     * This method is used to compare the lookahead token with
     * a token type to see if it matches the same type.
     */
    private void addop()
    {
        // All if statements compare the current token to the next
        if( lookahead.getType() == TokenType.PLUS)
        {
            match( TokenType.PLUS);
        }
        else if( lookahead.getType() == TokenType.MINUS)
        {
            match( TokenType.MINUS);
        }
        else if ( lookahead.getType() == TokenType.OR)
        {
            match( TokenType.OR); //need to add OR here?
        }
        // Else not an addop
        else
        {
            error("Not an Addop");
        }
    }

    /**
     * Handles the rule for mulop
     */
    protected void mulop()
    {
        // All if statements compare the current token to the next
        if( lookahead.getType() == TokenType.MULTI)
        {
            match( TokenType.MULTI);
        }
        else if( lookahead.getType() == TokenType.FSLASH)
        {
            match( TokenType.FSLASH);
        }
        else if( lookahead.getType() == TokenType.DIV)
        {
            match( TokenType.DIV);
        }
        else if( lookahead.getType() == TokenType.MOD)
        {
            match( TokenType.MOD);
        }
        else if( lookahead.getType() == TokenType.AND)
        {
            match( TokenType.AND);
        }
        // Else not a mulop
        else
        {
            error("Not a Mulop");
        }
    }

    /**
     * Handles the rule for assignop
     */
    private void assignop()
    {
       /* Compares the lookahead token with a token type to see if it
         matches that of the same type. */
        if(lookahead.getType() == TokenType.ASSIGN)
        {
            match(TokenType.ASSIGN);
        }
        // Else not an assignop
        else
        {
            error("Not an Assignop");
        }
    }

    /**
     * Compares the token type that's expected with the actual token
     * type. If the token type passed in is the same as the token type
     * then it is the current token or the end of the file.
     * @param expected This is the expected token type.
     */
    protected void match( TokenType expected)
    {
        System.out.println("match( " + expected + ")");
        /* Compares the lookahead token with a token type to see if it
         matches that of the same expected type. */
        if( this.lookahead.getType() == expected)
        {
            try
            {
                this.lookahead = scanner.nextToken();
                if( this.lookahead == null)
                {
                    this.lookahead = new Token( "End of File", null);
                }
            }
            catch (IOException ex)
            {
                error("Scanner exception");
            }
        }
        else
        {
            error("Match of " + expected + " found " + this.lookahead.getType()
                    + " instead.");
        }
    }

    /**
     * A custom error method for the parser. When the parser errors, an
     * error message is printed out and the program is exited.
     * @param message The error message that is printed.
     */
    protected void error( String message)
    {
        throw new RuntimeException(message);
    }

    /**
     * Checks to see if the token from the input string is a mulop token
     * and returns a boolean expression if it's true or false.
     * @param token The lookahead token to check against from the input string.
     * @return If the token is a mulop it will be returned, otherwise nothing is returned
     */
    private boolean isMulop( Token token)
    {
        boolean answer = false;
        // Compares the lookahead token with a token type to see if it matches the same type.
        if( token.getType() == TokenType.MULTI ||
                token.getType() == TokenType.FSLASH ||
                token.getType() == TokenType.DIV ||
                token.getType() == TokenType.MOD ||
                token.getType() == TokenType.AND) {
            answer = true;
        }
        return answer;
    }


    /**
     * Checks to see if the token from the input string is a relop token
     * or not and returns a boolean expression if it's true or false.
     * @param token The lookahead token to check against from the input string.
     * @return If the token is a relop it will be returned, otherwise nothing is returned.
     */
    private boolean isRelop( Token token)
    {
        boolean answer = false;
        // Compares the lookahead token with a token type to see if it matches the same type.
        if(token.getType() == TokenType.EQUIV ||
                token.getType() == TokenType.NOTEQUAL ||
                token.getType() == TokenType.LTHAN ||
                token.getType() == TokenType.GTHAN ||
                token.getType() == TokenType.LTHANEQUAL ||
                token.getType() == TokenType.GTHANEQUAL)
        {
            answer = true;
        }
        return answer;
    }


    /**
     * Checks to see if the token from the input string is a statement
     * token or not and returns a boolean expression.
     * @param token The lookahead token to check against from the input string.
     * @return If the token is a statement it will be returned, otherwise nothing is returned.
     */
    private boolean isStatement( Token token)
    {
        boolean answer = false;
        // Compare the lookahead token with a token type to see if it matches the same type.
        if(token.getType() == TokenType.ID ||
                token.getType() == TokenType.BEGIN ||
                token.getType() == TokenType.IF ||
                token.getType() == TokenType.WHILE ||
                token.getType() == TokenType.READ ||
                token.getType() == TokenType.WRITE ||
                token.getType() == TokenType.RETURN)
        {
            answer = true;
        }
        return answer;
    }
}
