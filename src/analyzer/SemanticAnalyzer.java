/**
 * This is the Semantic Analyzer class.
 * This class takes in a ProgramNode and a symbol table and checks to make
 * sure the declarations and statements of a program are correct according
 * to the micro pascal grammar. It also checks to see if variables have
 * been declared before they are used, checks if the type of a declared
 * variable matches across assignment, and adds the type of the declared
 * variable to the syntax tree. The SemanticAnalyzer checks every
 * declaration and statement by finding all of the ExpressionNodes and
 * assigning a type to them and by finding all of the ExpressionNodes
 * within a StatementNode and assigning their type. The type of each
 * ExpressionNode is then added to the syntax tree indentedToString. If a
 * variable has been declared, code will be generated by the code generator.
 * If a variable hasn't been declared, the boolean flag will be set as false
 * and the code generator will fail to generate code.
 *
 * @author Cohl Dorsey
 */

package analyzer;
import dorseyparser.SymbolTable;
import dorseyparser.TypeEnum;
import syntaxtree.*;
import java.util.ArrayList;

public class SemanticAnalyzer
{
    private boolean flag = true;
    private ProgramNode pn;
    private SymbolTable symbols;

    /**
     * Constructor that takes and stores values from a ProgramNode and a
     * SymbolTable to use throughout the program.
     * @param program A ProgramNode generated by a pascal program.
     * @param symbols A SymbolTable generated by a pascal program.
     */
    public SemanticAnalyzer(ProgramNode program, SymbolTable symbols)
    {
        this.pn = program;
        this.symbols = symbols;
    }

    /**
     * A function that uses the ProgramNode and SymbolTable generated by
     * a pascal program to pass VariableNodes and CompoundStatementNodes
     * to other functions and assign a type to every ExpressionNode.
     */
    public void analyze()
    {
        DeclarationsNode dn = pn.getVariables();
        ArrayList<VariableNode> vn = dn.getVar();
        for (VariableNode s : vn)
        {
            assignExpressionType(s);
        }

        assignStatementTypes(pn.getMain());
    }

    /**
     * A function that assigns a type to every ExpressionNode.
     * The type of each ExpressionNode is added to the syntax tree
     * indentedToString.
     *
     * @param - An ExpressionNode that is either an OperationNode,
     *          VariableNode, or a ValueNode.
     * @return - The real or integer TypeEnum.
     */
    public TypeEnum assignExpressionType(ExpressionNode express)
    {
        TypeEnum type = null;

        /* If the ExpressionNode is an OperationNode, setting the type of
        the left and the right child.*/
        if (express instanceof OperationNode)
        {
            OperationNode op = (OperationNode) express;

            TypeEnum leftType = assignExpressionType(op.getLeft());

            TypeEnum rightType = assignExpressionType(op.getRight());

            //An OperationNode is the same type as both of its children.
            if (leftType == TypeEnum.REAL_TYPE ||
                    rightType == TypeEnum.REAL_TYPE)
            {
                type = TypeEnum.REAL_TYPE;
            }
            else
            {
                type = TypeEnum.INTEGER_TYPE;
            }
        }

        /* Setting the type of the VariableNode, if the ExpressionNode
        is a VariableNode. Makes sure that all variables are declared
        before they are used.*/
        else if (express instanceof VariableNode)
        {
            VariableNode var = (VariableNode) express;
            type = checkVar(var);
        }

        /* Setting the type of the ValueNode, if the ExpressionNode
        is a ValueNode.*/
        else if (express instanceof ValueNode)
        {
            if (express.toString().contains(".") ||
                    express.toString().contains("e") ||
                    express.toString().contains("E"))
            {
                type = TypeEnum.REAL_TYPE;
            }

            else
            {
                type = TypeEnum.INTEGER_TYPE;

            }
        }
        /* Once the type is stored, an OperationNode, ValueNode, or
        VariableNode is set as that type.*/
        express.setType(type);
        return type;
    }

    /**
     * This function assigns a type to every StatementNode by finding
     * all of the ExpressionNodes within a StatementNode.
     * The type of each ExpressionNode is added to the syntax tree
     * indentedToString.
     * @param statement StatementNode that is either a CompoundStatementNode,
     * AssignmentStatementNode, IfStatementNode, WriteStatementNode,
     * ReadStatementNode, or ReturnStatementNode.
     */
    public void assignStatementTypes(StatementNode statement)
    {
        if (statement instanceof CompoundStatementNode)
        {
            CompoundStatementNode csn = (CompoundStatementNode) statement;
            ArrayList<StatementNode> sn = csn.getStatements();
            /*Iterating over the list of statement nodes to check each
            StatementNode and assign its type.*/
            for (StatementNode s : sn)
            {
                assignStatementTypes(s);
            }
        }
        else if (statement instanceof AssignmentStatementNode)
        {
            TypeEnum varType = assignExpressionType((
                    (AssignmentStatementNode) statement).getLvalue());
            TypeEnum expressionType = assignExpressionType((
                    (AssignmentStatementNode) statement).getExpression());
            //Checking types across assignment.
            if (!checkType(varType, expressionType))
            {
                System.out.println("Variable " +
                        ((AssignmentStatementNode) statement).getLvalue()
                        + " type " + varType + " does not match " +
                        expressionType + " across assignment.");
            }
        }
        else if (statement instanceof IfStatementNode)
        {
            assignExpressionType(((IfStatementNode) statement).getTest());
            assignStatementTypes(
                    ((IfStatementNode) statement).getThenStatement());
            assignStatementTypes(
                    ((IfStatementNode) statement).getElseStatement());
        }
        else if (statement instanceof WriteStatementNode)
        {
            assignExpressionType(
                    ((WriteStatementNode) statement).getWriteTest());
        }
        else if (statement instanceof ReadStatementNode)
        {
            assignExpressionType(
                    ((ReadStatementNode) statement).getVarTest());
        }
        else if (statement instanceof ReturnStatementNode)
        {
            assignExpressionType(
                    ((ReturnStatementNode) statement).getReturnTest());
        }
    }

}
