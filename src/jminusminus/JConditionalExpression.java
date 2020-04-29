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
    protected JExpression condition;

    /** Then clause. */
    protected JExpression thenPart;

    /** Else clause. */
    protected JExpression elsePart;

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

        // based on JIfStatement's analyze,
        // elsePart and thenPart must coexist
        // and must be of the same type.
        condition = (JExpression) condition.analyze(context);
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);

        thenPart = (JExpression) thenPart.analyze(context);
        elsePart = (JExpression) elsePart.analyze(context);

        if (thenPart.type().equals(Type.INT) &&
            elsePart.type().equals(Type.INT)) {
            type = Type.INT;

        } else if (thenPart.type().equals(Type.DOUBLE) &&
                elsePart.type().equals(Type.DOUBLE)) {
            type = Type.DOUBLE;

        } else if (thenPart.type().equals(Type.STRING) &&
                elsePart.type().equals(Type.STRING)) {
            type = Type.STRING;

        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "thenPart with type: " + thenPart.type().toString()
                            + " doesn't match with elsePart's: "
                            + elsePart.type().toString());
        }

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