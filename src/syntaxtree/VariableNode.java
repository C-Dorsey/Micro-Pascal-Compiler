/**
 * This represents a variable node in the syntax tree.
 *
 * @author Cohl Dorsey
 */

package syntaxtree;

public class VariableNode extends ExpressionNode
{
    /**
     * The name of the variable that is associated with the node.
     */
    String name;

    /**
     * Creates a ValueNode with the given attribute.
     * @param attr The attribute for the value node.
     */
    public VariableNode(String attr)
    {
        this.name = attr;
    }

    /**
     * Returns the name of the variable of the node.
     * @return The name of the VariableNode.
     */
    public String getName()
    {
        return (this.name);
    }

    /**
     * Returns the name of the variable as the description of the node.
     * @return The attribute String of the node.
     */
    @Override
    public String toString()
    {
        return (name);
    }

    /**
     * Creates a String representation of the variable node.
     * @param level The tree level that the variable node resides at.
     * @return A String representing the variable node.
     */
    @Override
    public String indentedToString(int level)
    {
        String answer = super.indentedToString(level);
        answer += "Variable Name: " + this.name + "\n";
        return answer;
    }

    /**
     * Checks to see if the two VariableNodes are equal.
     * @param o A VariableNode.
     * @return Will return true if the OperationsNodes are equal and
     * will return false if they are not equal.
     */
    @Override
    public boolean equals(Object o)
    {
        boolean answer = false;
        if (o instanceof VariableNode)
        {
            VariableNode other = (VariableNode) o;
            if (this.name.equals(other.name)) answer = true;
        }
        return answer;
    }
}
