/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dorseyScanner;
/**
 *
 * @author steinmee
 */
public class Token
{
    public String lexeme;
    public TokenType type;

    public Token( String l, TokenType t) {
        this.lexeme = l;
        this.type = t;
    }

    public TokenType getType()
    {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }
}
