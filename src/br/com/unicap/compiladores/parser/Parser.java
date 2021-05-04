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

    public void getParser() {
        inicioClass();
    }

    public void inicioClass() {
        token = s.getToken();
        if(T(token.getTipo())) {
            nomeClasse();
        }
        if(token.getTipo() == TokensID.TK_EOF) {
            return;
        } 
        else {
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
        } else {
            token = s.getToken();
            return;
        }
    }
    
    private void bloco() {
        while(true) {
            token = s.getToken();
            if(T(token.getTipo())) V();
            else if(token.getTipo() == TokensID.TK_PR_IF) IF();
            else if(token.getTipo() == TokensID.TK_PR_WHILE) C();
            else if(token.getTipo() == TokensID.TK_PR_DO) D();
            else if (token.getTipo() == TokensID.TK_IDENTIFICADOR) AT();
            else if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_CHA) return;
            //else /*erro throw new SyntacticException(SyntacticException.ERRO_DECLARATION, s.getLinha(), s.getColuna());*/;
        }
    }
    
    private void V(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR){
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_SEPARADOR_VIRGULA) Vl();
            else AT();
        }else{/*errothrow new SyntacticException(SyntacticException.ERRO_DECLARATION, s.getLinha(), s.getColuna());*/  }
    }
    
    private void Vl() {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR) {
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_SEPARADOR_VIRGULA) Vl();
            else if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO) return;
            else /*erro throw new SyntacticException(SyntacticException.ERRO_DECLARATION, s.getLinha(), s.getColuna());*/;
        } else /*erro throw new SyntacticException(SyntacticException.ERRO_DECLARATION, s.getLinha(), s.getColuna());*/;
    }
    
    private void AT(){
        if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO);
        else if (token.getTipo() == TokensID.TK_ATRIBUICAO){
            token = s.getToken();
            switch(token.getTipo()) {
                case TK_PR_TRUE: break;
                case TK_PR_FALSE: break;
                case TK_CHAR: break;
                case TK_NUMERO_INT: E(); break;
                case TK_NUMERO_FLT: E(); break;
                case TK_IDENTIFICADOR: E(); break;
                case TK_SEPARADOR_ABRE_PAR: E(); break;
                default: /*erro erro throw new SyntacticException(SyntacticException.ERRO_ATTRIBUTION, s.getLinha(), s.getColuna());*/; break;
            }
            if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO);
            else /*erro throw new SyntacticException(SyntacticException.ERRO_CLOSE_DECLARATION, s.getLinha(), s.getColuna());;*/;
        }else { /*erro throw new SyntacticException(SyntacticException.ERRO_ATTRIBUTION, s.getLinha(), s.getColuna());*/}
    }
        
        private void IF(){
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
                C();
            }
        }
        
        private void C(){
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_PR_TRUE || token.getTipo() == TokensID.TK_PR_FALSE);
            else N();
        }

        private void N() {
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
                token = s.getToken();
                if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR){
                    token = s.getToken();
                    if(token.getTipo() == TokensID.TK_PR_TRUE || token.getTipo() == TokensID.TK_PR_FALSE);
                    else {
                        E();
                        if(OR(token.getTipo())) {
                            E();
                            if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) {
                                token = s.getToken();
                                if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO) {
                                    return;
                                } else {
                                    //throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
                                }
                            } else {
                                //erro throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
                            }
                        } else {
                            //erro throw new SyntacticException(SyntacticException.ERRO_COMPARATION_TYPE, s.getLinha(), s.getColuna());
                        }
                    }
                }else{
                    //erro throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_OPEN, s.getLinha(), s.getColuna());
                }
            }                
        }

    private void E(){
        if(ID(token.getTipo())) {
            A();
        }else if (token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            B();
        }
        else {
            //erro;
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
