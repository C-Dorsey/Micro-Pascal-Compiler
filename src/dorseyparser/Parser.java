/**
 * This Java program illustrates reading from either a string or a file
 * which the path name will be passed into the constructor in the Parser
 * class when a method of the class is called. This class creates a parser,
 * implemented as a top-down recursive descent parser. In the middle of the
 * recursive decent the tree is built for the program. The parser builds a
 * syntax tree using the non-terminal symbols from the grammar and the nodes
 * from the classes in the syntaxtree package. Every time a node returns,
 * it returns a part that is added to the syntax tree. The parser also tells
 * whether an input string is from the language that is described by the
 * micro pascal grammar or not.
 *
 * @author Cohl Dorsey
 */

package dorseyparser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import dorseyScanner.Scanner;
import dorseyScanner.Token;
import dorseyScanner.TokenType;
import syntaxtree.*;

public class Parser
{

}