// File 	: JEnhancedForStatement.java
// Created 	: 12.04.2020
// Author 	: Ahmed Khalil
// Note     : Ref: 4.5.4 TypeChecking


package jminusminus;

import static jminusminus.CLConstants.*;
import java.util.ArrayList;

/**
 * The AST node for a for-statement.
 */

class JEnhancedForStatement extends JStatement {

    /** Formal params. */
    private JFormalParameter formalParameter;

    /** Test expression. */
    private JExpression condition;

    /** The body. */
    private JStatement body;

    private LocalContext context;

    /** Initialisations */
    private JForInit init;

    /** Update statements. */
    private ArrayList<JStatement> forUpdate;

    /** Test expression. */
    private JExpression test;


    /**
     * Construct an AST node for an enhanced for-statement given its line number,
     * initialisations as array,the
     * test expression, updates as array and the body.
     *
     * @param line
     *            line in which the for-statement occurs in the source file.
     * @param formalParameter
     *            params.
     * @param condition
     *            test expression.
     * @param body
     *            the body.
     */

    public JEnhancedForStatement(int line,
                         JFormalParameter formalParameter,
                         JExpression condition,
                         JStatement body) {
        super(line);
        this.formalParameter = formalParameter;
        this.condition = condition;
        this.body = body;

        /////
        JVariable iteratorIndex             = new JVariable(line, "vIteratorIndex" );
        ArrayList<JVariableDeclarator> vars = new ArrayList<JVariableDeclarator>(2);

        // (0)-> iteratorIndex, (1)-> formalParameter
        // formalParameter ::= [final] type <IDENTIFIER> {[]}
        vars.add(new JVariableDeclarator(line, iteratorIndex.name(), Type.INT, new JLiteralInt(line, "0")));
        vars.add(new JVariableDeclarator(line, formalParameter.name(), formalParameter.type(), null));

        // mods aren't needed
        JVariableDeclaration declaration
                = new JVariableDeclaration(line, new ArrayList<String>(), vars);


        JForInit init = new JForInit(line, declaration);
        this.init = init;

        JExpression test =
                new JLogicalNotOp(line, new JEqualOp(line,
                                                    iteratorIndex,
                                                    // applying the workaround
                                                    new JFieldSelection(line, condition, "length")));
        this.test = test;

        // forUpdate (0) -> JPreIncrementOp
        // forUpdate (1) -> JAssignOp
        ArrayList<JStatement> forUpdate = new ArrayList<JStatement>();
        forUpdate.add(new JPreIncrementOp(line, iteratorIndex));
        ((JExpression) forUpdate.get(0)).isStatementExpression = true;
        forUpdate.add(new JAssignOp(line,
                                    new JVariable(line, formalParameter.name()),
                                    new JArrayExpression(line, condition, iteratorIndex)));
        ((JExpression) forUpdate.get(1)).isStatementExpression = true;
        this.forUpdate = forUpdate;
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     *
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JEnhancedForStatement analyze(Context context) {

        this.context = new LocalContext(context);

        init = (JForInit) init.analyze(this.context);

        this.test = (JExpression) this.test.analyze(this.context);
        this.test.type().mustMatchExpected(line(),Type.BOOLEAN);

        for(JStatement eachUpdate : forUpdate) {
            eachUpdate = (JStatement) eachUpdate.analyze(this.context);
        }

        body = (JStatement) body.analyze(this.context);

        return this;
    }

    /**
     * Generate code for the for loop.
     *
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {

        String testLabel = output.createLabel();
        String out = output.createLabel();

        if(init != null){
            init.codegen(output);
        }

        // Branch out of the loop on the test condition
        // being false
        output.addLabel(testLabel);
        test.codegen(output, out, false);

        // Codegen body
        body.codegen(output);

        // for each update
        for(JStatement eachUpdate: forUpdate) {
            eachUpdate.codegen(output);
        }

        // Unconditional jump back up to test
        output.addBranchInstruction(GOTO, testLabel);
        output.addLabel(out);
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {

        /** AST header. */
        p.printf("<JEnhancedForStatement line=\"%d\">\n", line());
        p.indentRight();

        /** SubTree for formalParameters. */
        p.printf("<formalParameters>\n");
        p.indentRight();
        formalParameter.writeToStdOut(p);
        p.indentLeft();
        p.printf("</formalParameters>\n");


        /** SubTree for TestExpressions. */
        if (condition != null) {
            p.printf("<TestExpression>\n");
            p.indentRight();
            condition.writeToStdOut(p);
            p.indentLeft();
            p.printf("</TestExpression>\n");
        }

        /** End of AST. */
        p.printf("<Body>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n");

        p.indentLeft();
        p.printf("</JEnhancedForStatement>\n");

    }
}
