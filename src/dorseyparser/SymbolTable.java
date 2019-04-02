/**
 * This program creates a symbol table that is implemented as a hash map
 * to store information about every pascal identifier added to the symbol
 * table. The symbol table will store identifier information such as the
 * character string (or lexeme), its datatype, and its kind. The kind of
 * identifier can be a program, variable, array, function, or procedure.
 *
 * @author Cohl Dorsey
 */

import java.util.HashMap;
package dorseyparser;

public class SymbolTable
{
    private HashMap<String, SymbolData> symbolsTable = new HashMap<>();

    /**
     * Adds a variable identifier to the symbol table if the variable
     * name doesn't exist in the table already.
     * @param name lexeme containing a variable name.
     */
    public void addVarName(String name)
    {
        if (!symbolsTable.containsKey(name))
        {
            symbolsTable.put(name, new SymbolData(name, KindEnum.VAR_NAME));
        }
    }

    /**
     * Adds a program identifier to the symbol table if the program
     * name doesn't already exist in the table.
     * @param name lexeme containing a program name.
     */
    public void addProgramName(String name)
    {
        if (!symbolsTable.containsKey(name))
        {
            symbolsTable.put(name, new SymbolData(name, KindEnum.PROGRAM_NAME));
        }
    }

    /**
     * Adds an array identifier to the symbol table if the array name
     * doesn't already exist in the table.
     * @param name lexeme containing an array name.
     */
    public void addArrayName(String name)
    {
        if (!symbolsTable.containsKey(name))
        {
            symbolsTable.put(name, new SymbolData(name, KindEnum.ARRAY_NAME));
        }
    }

    /**
     * Adds a procedure identifier to the symbol table if the procedure
     * name doesn't already exist in the table.
     * @param name lexeme containing a procedure name.
     */
    public void addProcedureName(String name)
    {
        if (!symbolsTable.containsKey(name))
        {
            symbolsTable.put(name, new SymbolData(name, KindEnum.PROCEDURE_NAME));
        }
    }

    /**
     * Adds a function identifier to the symbol table if the function
     * name doesn't already exist in the table.
     * @param name lexeme containing a function name.
     */
    public void addFunctionName(String name)
    {
        if (!symbolsTable.containsKey(name))
        {
            symbolsTable.put(name, new SymbolData(name, KindEnum.FUNCTION_NAME));
        }
    }

    /**
     * Checks to see if the identifier name exists and if it is a variable name.
     * @param name The symbol name that is being checked.
     * @return A True will be returned if the name exists in the Symbol Table
     * and if it's a variable. A False will be returned if the name does not
     * exist in the Symbol Table or it is not a variable.
     */
    public boolean isVarName(String name)
    {
        SymbolData s = symbolsTable.get(name);
        //If a variable name key exists and the kind is a variable name
        if (s != null && s.kind == KindEnum.VAR_NAME)
        {
            return true;
        }

        return false;
    }


    /**
     * Contains the datatype and information values that are stored
     * in the hash map.
     */
    private class SymbolData
    {
        String name;
        KindEnum kind;

        SymbolData(String name, KindEnum kind)
        {
            this.name = name;
            this.kind = kind;
        }

        /**
         * The string displayed for the returned SymbolData.
         *
         * @return - returns the SymbolData's name and kind.
         */
        @Override
        public String toString()
        {
            return  name + kind;
        }
    }
}
