/**
 * This class is the main class for the micro pascal compiler. The class
 * reads a pascal file from args and writes a formatted symbol table to a
 * file. The file read in from args is checked to see if a file was entered.
 * If no file is entered then the user is asked to enter a file name and then
 * proceeds to exit the file. If the file is present it is then read and passed
 * into the Recognizer class to the program method. Then a toString of the
 * symbol table is generated and written to a file.
 */

package compiler;
import dorseyparser.Recognizer;
import java.io.PrintWriter;

public class CompilerMain
{
    public static void main(String[] args)
    {
        //Tests to see if a file was entered.
        if (args.length == 0)
        {
            System.out.println("Please enter a file name");
            System.exit(0);
        }
        else
        {
            /*Takes an input file from the command line argument and
            stores it in a String called fileName.*/
            String fileName = args[0];
            Recognizer instance = new Recognizer
                    (fileName, true);
            /*Calls Recognizer Object method program. Constructor
            is automatically called when an object of the class is
            created. */
            instance.program();
            /*The symbol table toString generated by passing the file
            into the program method. */
            String tableString = instance.getSymbolTable().toString();
            int dotIndex = fileName.indexOf(".");
            /*The file name from the first letter of the file name up
            to the dot. */
            String baseFileName = fileName.substring(0, dotIndex);
            /*Changing the file extension by adding the file extension
            .table on to the file name. */
            String fileOutName = baseFileName + ".table";
            try
            {
                PrintWriter printWriter = new PrintWriter(fileOutName);
                //Writing symbol table toString out to the .table file
                printWriter.print(tableString);
                printWriter.close();
            }
            catch (Exception e)
            {
                System.out.println("Failed to write to file");
            }
        }
    }
}
