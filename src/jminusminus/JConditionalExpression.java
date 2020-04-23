// File 	: JConditionalExpression.java
// Created 	: 09.04.2020
// Author 	: Ahmed Khalil
// Note     : based on JIfStatement.java

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for an ConditionalExpression
 * (i.e. TernaryExp).
 */

abstract class JConditionalExpression extends JExpression {

    /** Test expression. */
    private JExpression condition;

    /** Then clause. */
    private JExpression thenPart;

    /** Else clause. */
    private JExpression elsePart;

    /**
     * Construct an AST node for an JConditionalExpression given its
     * line number, the test expression,
     * the consequent when true, and the alternate when false.
     *
     * @param line
     *            line in which the ConditionalExpression
     *            occurs in the source file.
     * @param condition
     *            test expression.
     * @param thenPart
     *            then clause.
     * @param elsePart
     *            else clause.
     */

    public JConditionalExpression(int line, JExpression condition,
                                  JExpression thenPart,
                                  JExpression elsePart) {
        super(line);
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }


    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {

        /** Printing head. */
        p.printf("<JConditionalExpression line=\"%d\">\n", line());
        p.indentRight();

        /** TestExpression. */
        p.printf("<TestExpression>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TestExpression>\n");

        /** ThenClause. */
        p.printf("<ThenClause>\n");
        p.indentRight();
        thenPart.writeToStdOut(p);
        p.indentLeft();
        p.printf("</ThenClause>\n");

        /** ElseClause. */
        if (elsePart != null) {
            p.printf("<ElseClause>\n");
            p.indentRight();
            elsePart.writeToStdOut(p);
            p.indentLeft();
            p.printf("</ElseClause>\n");
        }

        /** Printing head-closing. */
        p.indentLeft();
        p.printf("</JConditionalExpression>\n");
    }
}

class JTernaryOp extends JConditionalExpression {

    public JTernaryOp(int line, JExpression condition,
                      JExpression thenPart,
                      JExpression elsePart) {
        super(line, condition,
                thenPart,
                elsePart);
    }

    /**
     * Analyzing the ConditionalExpression means analyzing its components and checking
     * that the test is boolean.
     *
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {

        return this;
    }

    /**
     * // TODO
     *
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
    }

}