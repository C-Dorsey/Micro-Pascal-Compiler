/**
 * A JUnit Java Program that illustrates reading from a text file or
 * strings using the Recognizer class in JUnit testing. During the tests,
 * if the method successfully returns, then the test is a happy path. It
 * passes and prints out an expression saying if the test worked or not.
 * If the method doesn't successfully return, then the test is a bad path.
 * It doesn't pass and prints out the exception.
 */

package dorseyparser;

import org.junit.Test;
import static org.junit.Assert.*;
import dorseyScanner.TokenType;

public class RecognizerTest
{

    /**
     * This method uses JUnit to test the program method from the
     * Recognizer class by testing to see if the current token matches
     * the expected token type in the program method. 
     */
    @Test
    public void testProgram()
    {
        System.out.println("####    Test program    ####\n");

        //Pascal file test
        Recognizer instance = new Recognizer
                ( "src\\dorseyparser\\rectest1.pas", true);

        //Happy path, with good pascal.
        try
        {
            instance.program();
            //If it's good pascal, it should print out the following.
            System.out.println("Passed, parsed the happy path." + "\n");
        }
        catch (Exception actual)
        {
            fail("Didn't want to throw exception");
        }


        //Pascal file test
        instance = new Recognizer
                ("src\\dorseyparser\\rectest2.pas", true);

        //Happy path, with good pascal.
        try
        {
            instance.program();
            //If it's good pascal, it should print out the following.
            System.out.println("Passed, parsed the happy path." + "\n");
        }
        catch (Exception actual)
        {
            fail("Didn't want to throw exception");
        }


        //Pascal file test
        instance = new Recognizer
                ("src\\dorseyparser\\rectest3.pas", true);

        //Happy path, with good pascal.
        try
        {
            instance.program();
            //If it's good pascal, it should print out the following.
            System.out.println("Passed, parsed the happy path." + "\n");
        }
        catch (Exception actual)
        {
            fail("Didn't want to throw exception");
        }


        //Pascal string test. Happy path, with good pascal.
        String test = "program foo ; begin end .";
        instance = new Recognizer( test, false);
        try
        {
            instance.program();
            //If it's good pascal, it should print out the following.
            System.out.println("Passed, parsed the happy path." + "\n");
        }
        catch (Exception actual)
        {
            fail("Didn't want to throw exception");
        }

        //Pascal string test. Bad path, with bad pascal.
        test = "program foo begin end .";
        instance = new Recognizer( test, false);
        try
        {
            instance.program();
            //If it's bad pascal, it should throw an exception.
            fail("Didn't throw exception");
        }
        catch (Exception actual)
        {
            String expected = "Match of SEMI found BEGIN instead.";
            assertEquals(expected, actual.getMessage());
            System.out.println("Passed, caught the error.");
        }

    }

    /**
     * This method uses JUnit to test the declarations
     * method from the Recognizer class by testing to see if the
     * current token matches the expected token type in the
     * declarations method. This is a text string test.
     */
    @Test
    public void testDeclarations()
    {
        System.out.println("####    Test declarations      ####\n");

        //Pascal string test. Happy path, with good pascal.
        String test = "var declarations : integer ;";
        Recognizer instance = new Recognizer( test, false);
        try
        {
            instance.declarations();
            //If it's good pascal, it should print out the following.
            System.out.println("Passed, parsed the happy path." + "\n");
        }
        catch (Exception actual)
        {
            fail("Didn't want to throw exception");
        }

        //Pascal string test. Bad path, with bad pascal.
        test = "var declarations : integer";
        instance = new Recognizer( test, false);
        try
        {
            instance.declarations();
            //If it's bad pascal, it should throw an exception.
            fail("Didn't throw exception");
        }
        catch (Exception actual)
        {
            String expected = "Match of SEMI found null instead.";
            assertEquals(expected, actual.getMessage());
            System.out.println("Passed, caught the error.");
        }
    }

    /**
     * This method uses JUnit to test the subprogram_declaration
     * method from the Recognizer class by testing to see if the
     * current token matches the expected token type in the
     * subprogram_declaration method. This is a text string test.
     */
    @Test
    public void testSubprogram_declaration()
    {
        System.out.println("####  Test subprogram declaration  ####");

        //Pascal string test. Happy path, with good pascal.
        String test = "procedure fooz (identifier : integer) ;\n" +
                "var declarations2 : integer ;\n" +
                "begin read ( foo2 ) end";
        Recognizer instance = new Recognizer( test, false);
        try
        {
            instance.subprogram_declaration();
            //If it's good pascal, it should print out the following
            System.out.println("Passed, parsed the happy path." + "\n");
        }
        catch (Exception actual)
        {
            fail("Didn't want to throw exception");
        }

        //Pascal string test. Bad path, with bad pascal.
        test = "procedure id";
        instance = new Recognizer( test, false);
        try
        {
            instance.subprogram_declaration();
            //If it's bad pascal, it should throw an exception.
            fail("Didn't throw exception");
        }
        catch (Exception actual)
        {
            String expected = "Match of SEMI found null instead.";
            assertEquals(expected, actual.getMessage());
            System.out.println("Passed, caught the error.");
        }
    }

    /**
     * This method uses JUnit to test the statement method from the
     * Recognizer class by testing to see if the current token matches the
     * expected token type in the statement method. This is a text
     * string test.
     */
    @Test
    public void testStatement()
    {
        System.out.println("####    Test statement    ####");

        //Pascal string test. Happy path, with good pascal.
        String test = "read (foo)";
        Recognizer instance = new Recognizer( test, false);
        try
        {
            instance.statement();
            //If it's good pascal, it should print out the following
            System.out.println("Passed, parsed the happy path." + "\n");
        }
        catch (Exception actual)
        {
            fail("Didn't want to throw exception");
        }

        //Pascal string test. Bad path, with bad pascal.
        test = "read ; (foo)";
        instance = new Recognizer( test, false);
        try
        {
            instance.statement();
            //If it's bad pascal, it should throw an exception.
            fail("Didn't throw exception");
        }
        catch (Exception actual)
        {
            String expected = "Match of LPAREN found SEMI instead.";
            assertEquals(expected, actual.getMessage());
            System.out.println("Passed, caught the error.");
        }
    }


    /**
     * This method uses JUnit to test the simple_expression method
     * from the Recognizer class by testing to see if the current token
     * matches the expected token type in the simple_expression method.
     * This is a text string test.
     */
    @Test
    public void testSimple_expression()
    {
        System.out.println("####   Test simple expression   ####");
        String test = "34 + 17 * 7";
        Recognizer instance = new Recognizer
                ( test, false);
        try
        {
           /*Calls Recognizer Object method exp. Constructor is
           automatically called when an object of the class is created.*/
            instance.simple_expression();
            System.out.println("It Parsed!");
        }
        catch (Exception e)
        {
            fail("Didn't want to throw exception");
        }

        //Pascal string test. Bad path, with bad pascal.
        test = "34 or";
        instance = new Recognizer( test, false);
        try
        {
            instance.simple_expression();
            //If it's bad pascal, it should throw an exception.
            fail("Didn't throw exception");
        }
        catch (Exception actual)
        {
            String expected = "Factor";
            assertEquals(expected, actual.getMessage());
            System.out.println("Passed, caught the error.");
        }
    }

    /**
     * This method uses JUnit to test the factor method from the
     * Recognizer class by testing to see if the current token matches
     * the expected token type in the factor method. This is a text
     * string test.
     */
    @Test
    public void testFactor()
    {
        System.out.println("####     Test factor    ####");
        Recognizer instance = new Recognizer( "87654321", false);
        try
        {
        /*Calls Recognizer Object method factor. Constructor
        is automatically called when an object of the class is created.*/
            instance.factor();
            System.out.println("Recognized the factor token.");
        }
        catch (Exception e)
        {
            fail("Didn't want to throw exception");
        }

        //Pascal string test. Bad path, with bad pascal.
        String test = "[";
        instance = new Recognizer( test, false);
        try
        {
            instance.factor();
            //If it's bad pascal, it should throw an exception.
            fail("Didn't throw exception");
        }
        catch (Exception actual)
        {
            String expected = "Factor";
            assertEquals(expected, actual.getMessage());
            System.out.println("Passed, caught the error.");
        }
    }

    /**
     * This method uses JUnit to test the simple_part method from the
     * Recognizer class by testing to see if the current token matches
     * the expected token type in the simple_part method.
     * This is a text string test.
     */
    @Test
    public void testSimple_part() {
        System.out.println("####  Test simple part  ####");
        String test = "+ 34";
        Recognizer instance = new Recognizer( test, false);
        try
        {
            /*Calls Recognizer Object method simple_part. Constructor
            is automatically called when an object of the class is
            created. */
            instance.simple_part();
            System.out.println("It Parsed!");
        }
        catch (Exception e)
        {
            fail("Didn't want to throw exception");
        }

        //Pascal string test. Bad path, with bad pascal.
        test = "+";
        instance = new Recognizer( test, false);
        try
        {
            instance.simple_part();
            //If it's bad pascal, it should throw an exception.
            fail("Didn't throw exception");
        }
        catch (Exception actual)
        {
            String expected = "Factor";
            assertEquals(expected, actual.getMessage());
            System.out.println("\n"+ "Passed, caught the error.");
        }
    }

    /**
     * This method uses JUnit to test the addop method from the Recognizer
     * class by testing to see if the current token matches the
     * expected token type in the addop method. This is a text string
     * test.
     */
    @Test
    public void testAddop() {
        System.out.println("####   Test addop  ####");
        TokenType plus = TokenType.PLUS;
        Recognizer instance = new Recognizer( "+", false);
        try
        {
            //Calls Recognizer Object method match. Constructor is
            // automatically called when an object of the class is created.
            instance.match(plus);
            System.out.println("Recognized the single addop.");
        }
        catch (Exception e)
        {
            fail("Didn't want to throw exception");
        }
    }

    /**
     * This method uses JUnit to test the term method from the Recognizer
     * class by testing to see if the current token matches the
     * expected token type in the term method. This is a text string
     * test.
     */
    @Test
    public void testTerm()
    {
        System.out.println("####     Test term      ####");
        Recognizer instance = new Recognizer
                ("23 / 17", false);
        try
        {
        /*Calls Recognizer Object method term. Constructor is
        automatically called when an object of the class is created.*/
            instance.term();
            System.out.println("Parsed a term.");
        }
        catch (Exception e)
        {
            fail("Didn't want to throw exception");
        }
    }

    /**
     *This method uses JUnit to test the term_part method from the
     * Recognizer class by testing to see if the current token matches
     * the expected token type in the term_part method. This is a text
     * string test.
     */
    @Test
    public void testTerm_part()
    {
        System.out.println("####   Test term_part   ####");
        Recognizer instance = new Recognizer( "* foo / foo2", false);
        try
        {
            //Calls Recognizer Object method term_part. Constructor is automatically
            //called when an object of the class is created.
            instance.term_part();
            System.out.println("Parsed a term.");
        }
        catch (Exception e)
        {
            fail("Didn't want to throw exception");
        }
    }

    /**
     * This method uses JUnit to test the mulop method from the Recognizer
     * class by testing to see if the current token matches the
     * expected token type in the mulop method. This is a text string
     * test.
     */
    @Test
    public void testMulop() {
        System.out.println("####     Test mulop     ####");
        Recognizer instance = new Recognizer( "*", false);
        try
        {
            //Calls Recognizer Object method mulop. Constructor is automatically
            //called when an object of the class is created.
            instance.mulop();
            System.out.println("Recognized the single mulop.");
        }
        catch (Exception e)
        {
            fail("Didn't want to throw exception");
        }
    }

    /**
     *This method uses JUnit to test the match method from the Recognizer
     * class by testing to see if the current token matches the
     * expected token type in the match method. This is a text string
     * test that tests the period token type as the expected token to
     * match against the string token passed into the parser. This is a
     * text string test.
     */
    @Test
    public void testMatch()
    {
        System.out.println("####     Test match     ####");
        TokenType ett = TokenType.PERIOD;
        Recognizer instance = new Recognizer( ".", false);
        try
        {
            //Calls Recognizer Object method match. Constructor is
            //automatically called when an object of the class is created.
            instance.match(ett);
            System.out.println("It matches!");
        }
        catch (Exception e)
        {
            fail("Didn't want to throw exception");
        }
    }

    /**
     * This method uses JUnit to test the error method from the Recognizer
     * class. This is a text string test.
     */
    @Test
    public void testError()
    {
        System.out.println("####     Test error     ####");
        String expected = "error test";
        Recognizer instance = new Recognizer( "", false);
        try
        {
            /*Calls Recognizer Object method error and passes the message
            directly into it. Constructor is automatically called
            when an object of the class is created.*/
            instance.error(expected);
            System.out.println("Did not want the error to pass.");
        }
        catch(Exception actual)
        {
            assertEquals(expected, actual.getMessage());
            System.out.println("Successfully tested the error.");
        }
    }
}