package br.com.unicap.compiladores.parser;


import br.com.unicap.compiladores.analisadorlexico.ScannerNosso;
import br.com.unicap.compiladores.analisadorlexico.Token;
import br.com.unicap.compiladores.analisadorlexico.TokensID;
import br.com.unicap.compiladores.excecoes.SyntacticException;

public class Parser extends Terminal{
    private Token token;
    private static Parser p;
    private static ScannerNosso s;

    private Parser(ScannerNosso s) {
        Parser.s = s;
    }

    public Parser getContrutor(ScannerNosso s) {
        if (p == null) {
            p = new Parser(s);
        } else {
            if(s == null) {
                Parser.s = s;
            }
        }
        return p;
    }

    public void inicioClass() {
        token = s.getToken();
        if(T(token.getTipo())) {
            nomeClasse();
        } else {
            throw new SyntacticException(SyntacticException.ERRO_TYPE_CLASS, s.getLinha(), s.getColuna());
        }
    }

    private void nomeClasse(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_PR_MAIN || token.getTipo() == TokensID.TK_IDENTIFICADOR){
            if(token.getTipo() == TokensID.TK_IDENTIFICADOR) {
                token.setTipo(TokensID.TK_CLASS);
            }
            abreBloco();
        }
        else {
            throw new SyntacticException(SyntacticException.ERRO_NAME_CLASS, s.getLinha(), s.getColuna());
        }
    }

    private void abreBloco() {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_CHA) {
            bloco();
            fechaBloco();
        }
        else {
            throw new SyntacticException(SyntacticException.ERRO_OPEN_BLOCK, s.getLinha(), s.getColuna());
        }
    }

    private void fechaBloco() {
        if(token.getTipo() != TokensID.TK_SEPARADOR_FECHA_CHA) {
            throw new SyntacticException(SyntacticException.ERRO_CLOSE_BLOCK, s.getLinha(), s.getColuna());
        }
    }
    
    private void bloco() {
        token = s.getToken();
        if(T(token.getTipo())){
            V();
        }else if(token.getTipo() == TokensID.TK_PR_IF){
            IF();
        }else if(token.getTipo() == TokensID.TK_PR_WHILE){
            C();
        }else if(token.getTipo() == TokensID.TK_PR_DO){
            D();
        }else if (token.getTipo() == TokensID.TK_IDENTIFICADOR){
            AT();
        }else{}
        
    }

    private void V(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR){
           AT();
        }else{/*errothrow new SyntacticException(SyntacticException.ERRO_DECLARATION, s.getLinha(), s.getColuna());*/  }
    }
    private void IF(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            C();
        }
    }

    private void C(){
        E();
        if(OR(token.getTipo())) {
            E();
            if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) { 
                abreBloco();
            } else {
                //erro throw new SyntacticException(SyntacticException.ERRO_CLOSE_COMPARING, s.getLinha(), s.getColuna());
            }
        } else {
            //erro; throw new SyntacticException(SyntacticException.ERRO_TYPE_COMPARING, s.getLinha(), s.getColuna());
        }
    }

    private void D(){
        abreBloco();
        if(token.getTipo() == TokensID.TK_PR_WHILE) {
            E();
            if(OR(token.getTipo())) {
                E();
                if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) {
                    token = s.getToken();
                    if(token.getTipo() == TokensID.TK_SEPARADOR_VIRGULA) {
                        return;
                    } else {
                        //erro
                    }
                } else {
                    //erro
                }
            } else {
                //erro;
            }
        } else {
            //erro
        }
    }

    private void E(){
        token = s.getToken();
        if(ID(token.getTipo())) {
            A();
        }else if (token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            B();
        }
    }

    private void A() {
        token = s.getToken();
        if(OP(token.getTipo())) {
            F();
        } else {
            /*erro*/
        }
    }

    private void F() {
        token = s.getToken();
        if(ID(token.getTipo())) {
            A();
        }else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            B();
        } else {
            return;
        }
    }
    private void AT(){
        token = s.getToken();
            if(token.getTipo() == TokensID.TK_SEPARADOR_VIRGULA);
            else if (token.getTipo() == TokensID.TK_ATRIBUICAO){
                E();
                if(token.getTipo() == TokensID.TK_SEPARADOR_VIRGULA);
                else /*erro throw new SyntacticException(SyntacticException.ERRO_CLOSE_DECLARATION, s.getLinha(), s.getColuna());;*/;
            }else { /*erro throw new SyntacticException(SyntacticException.ERRO_ATTRIBUTION, s.getLinha(), s.getColuna());*/}
    }

    private void B() {
        if(ID(token.getTipo())) {
            A();
            if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) {
                /*Token t;
                while(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR){
                    t = pilha.pop();
                    if(t != null){
                        token = s.getToken();
                    }else {
                        //erro
                    }
                }*/
                if(OP(token.getTipo())) {
                    F();
                }
            }
        }else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            B();if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) {
                /*Token t;
                Token t;
                while(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR){
                    t = pilha.pop();
                    if(t != null){
                        token = s.getToken();
                    }else {
                        //erro
                    }
                }*/
                if(OP(token.getTipo())) {
                    F();
                }
            }
        }
    }
}
