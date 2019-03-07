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

}
