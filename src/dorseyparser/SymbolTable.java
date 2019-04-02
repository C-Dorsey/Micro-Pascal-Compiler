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
    private HashMap<String, SymbolData> symbols = new HashMap<>();

    /**
     * Adds a variable identifier to the symbol table if the variable
     * name doesn't exist in the table already.
     * @param name lexeme containing a variable name.
     */
    public void addVarName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData(name, KindEnum.VAR_NAME));
        }
    }

    /**
     * Adds a program identifier to the symbol table if the program
     * name doesn't already exist in the table.
     * @param name lexeme containing a program name.
     */
    public void addProgramName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData(name, KindEnum.PROGRAM_NAME));
        }
    }

    /**
     * Adds an array identifier to the symbol table if the array name
     * doesn't already exist in the table.
     * @param name lexeme containing an array name.
     */
    public void addArrayName(String name)
    {
        if (!symbols.containsKey(name))
        {
            symbols.put(name, new SymbolData(name, KindEnum.ARRAY_NAME));
        }
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
