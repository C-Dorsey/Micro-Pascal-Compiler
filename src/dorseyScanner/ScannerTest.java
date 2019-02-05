/**
 * A Java Program that illustrates reading from a text file and strings
 * using a scanner class using JUnit testing. This program creates a
 * StringReader, passing a String as parameter to the StringReader
 * constructor. It then reads the characters one at a time from the
 * StringReader class. This program also takes an input file
 * and creates an input file stream to read from. As long as the
 * scanner doesn't hit a null token, the scanner prints out any tokens
 * that are not whitespace tokens or null tokens.
 *
 * @author Cohl Dorsey
 */
package dorseyScanner;

import static org.junit.Assert.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import org.junit.Test;


public class ScannerTest
{
    /**
     * This method uses JUnit to test the nextToken method by testing
     * if the scanner returns the correct token type and lexeme.
     */
    @Test
    public void testToken()
    {

    }
}
