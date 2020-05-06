// File 	: JForInit.java
// Created 	: 05.05.2020
// Author 	: Ahmed Khalil
// Note     : Specific to JEnhancedFor

package jminusminus;

/**
 * The AST node for an enhanced for-statement inits.
 */

public class JForInit  extends JAST {

    private JVariableDeclaration init;

    public JForInit(int line, JVariableDeclaration vdeclInit) {
        super(line);
        this.init = vdeclInit;
    }


    public JAST analyze(Context context) {
        init = (JVariableDeclaration) init.analyze(context);
        return this;
    }


    public void codegen(CLEmitter output) {
        init.codegen(output);
    }


    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JForInit>\n");
        p.indentRight();
        p.printf("<InitDeclarations>\n");
        p.indentRight();
        init.writeToStdOut(p);
        p.indentLeft();
        p.printf("</InitDeclarations>\n");
        p.indentLeft();
        p.printf("</JForInit>\n");
    }
}
