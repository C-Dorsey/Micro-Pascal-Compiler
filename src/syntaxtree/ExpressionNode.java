package syntaxtree;
import dorseyparser.TypeEnum;

/**
 * General representation of any expression.
 * @author erik
 */
public class ExpressionNode extends SyntaxTreeNode
{

    private TypeEnum type = null;

    public TypeEnum getType()
    {
        return type;
    }

    public void setType(TypeEnum type)
    {
        this.type = type;
    }
    
    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    public String indentedToString( int level) {
        String answer = "";
        if( level > 0) {
            answer = "|-- ";
        }
        for( int indent = 1; indent < level; indent++) answer += "--- ";
        return( answer);
    }

}
