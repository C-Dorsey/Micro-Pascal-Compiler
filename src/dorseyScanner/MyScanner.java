/**
 * Java Program that illustrates reading from a text file using a scanner
 * class. This program takes an input file from the command line and
 * creates an input file stream to read from a file.
 */

package dorseyScanner;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MyScanner
{

    public static void main( String[] args)
    {
    	/*Takes an input file from the command line argument and stores
    	it in a String called filename.*/
        //String filename = args[0];
        String filename = "test2.txt";

        // Initializes FileInputStream object 
        FileInputStream fis = null;
        try {
            fis = new FileInputStream( filename);
        } catch (Exception e ) { e.printStackTrace();}

        // Initializes InputStreamReader object 
        InputStreamReader isr = new InputStreamReader( fis);
        
        /*Creates a Scanner object to read input from the
        InputStreamReader object. */
        Scanner scanner = new Scanner( isr);

        //Initialized String variable aToken to null to ensure it's empty.
        String aToken = null;


        //Goes through the while loop until the null at the end of the file is hit.
        do
        {
            try {
                //Used to return the next token from this string tokenizer.
                aToken = String.valueOf(scanner.nextToken());
            }
            catch( Exception e) { e.printStackTrace();}
            //Only prints tokens if they are not a whitespace token or a null token.
            if( aToken != null && !aToken.equals( ""))
                //Prints out the token that's returned.
                System.out.println("The token returned was " + aToken);
        } while( aToken != null);
    }


}