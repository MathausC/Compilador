package br.com.unicap.compiladores.parser;

import br.com.unicap.compiladores.analisadorsemantico.Elemento;
import br.com.unicap.compiladores.analisadorsemantico.GerenciadorSemantico;
import br.com.unicap.compiladores.analisadorlexico.ScannerNosso;
import br.com.unicap.compiladores.analisadorlexico.Token;
import br.com.unicap.compiladores.analisadorlexico.TokensID;
import br.com.unicap.compiladores.excecoes.SyntacticException;
import br.com.unicap.compiladores.excecoes.SemanticException;

import java.util.Stack;

public class Parser extends Terminal{
    private Token token;
    private static Parser p;
    private static ScannerNosso s;
    private Stack<Token> parenteses;
    private boolean flagElse;
    private GerenciadorSemantico gS;
    private int nivel;
    private Elemento<String> e;
    
    private Parser(ScannerNosso s) {
        Parser.s = s;
        parenteses = new Stack<Token>();
        this.nivel = 0;
        gS = new GerenciadorSemantico(this);
    }

    public static Parser getContrutor(ScannerNosso s) {
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
    public int getLinha(){
        return s.getLinha();
    }
    public int getColuna(){
        return s.getColuna();
    }

    public void inicioClass() {
        token = s.getToken();
        if(T(token.getTipo())) {
            nomeClasse();
            token = s.getToken();
        }
        else {
             throw new SyntacticException(SyntacticException.ERRO_TYPE_CLASS, s.getLinha(), s.getColuna());
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
            e = new Elemento<String>(token.getTexto(), token, nivel);
            gS.addLista(e);
            gS.add(nivel);
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
        nivel--;
        gS.fechaEscopo();        
        if(token.getTipo() != TokensID.TK_SEPARADOR_FECHA_CHA) {
            throw new SyntacticException(SyntacticException.ERRO_CLOSE_BLOCK, s.getLinha(), s.getColuna());
        } else {
            return;
        }
    }
    
    private void bloco() {
        nivel++;
        gS.add(nivel);
        while(true) {
            if(!flagElse){
                token = s.getToken();
            }
            flagElse = false;
            if(T(token.getTipo())) {
                V(token.getTipo()); 
            }
            else if(token.getTipo() == TokensID.TK_PR_IF) IF();
            else if(token.getTipo() == TokensID.TK_PR_WHILE) W();
            else if(token.getTipo() == TokensID.TK_PR_DO) D();
            else if (token.getTipo() == TokensID.TK_IDENTIFICADOR) AT();
            else  return;
        }
    }
    
    private void V(TokensID t){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR){
            troca(t, token);
            e = new Elemento<String>("none", token, nivel);
            gS.addLista(e);
            AT();
        }else{throw new SyntacticException(SyntacticException.ERRO_DECLARATION, s.getLinha(), s.getColuna()); }
    }
    
    private void AT(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO) {
            return;
        }
        else if (token.getTipo() == TokensID.TK_ATRIBUICAO){
            token = s.getToken();
            switch(token.getTipo()) {
                case TK_PR_TRUE: 
                    if(e.getTipo().getTipo() != TokensID.TK_ID_BOOL) {
                        throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                    } else {
                        e.setValor(token.getTexto());
                        token = s.getToken();
                    }
                    break;
                case TK_PR_FALSE: 
                    if(e.getTipo().getTipo() != TokensID.TK_ID_BOOL) {
                        throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                    } else {
                        e.setValor(token.getTexto());
                        token = s.getToken();
                    }
                    break;
                case TK_CHAR: 
                    if(e.getTipo().getTipo() != TokensID.TK_ID_CHAR) {
                        throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                    } else {
                        e.setValor(token.getTexto());
                        token = s.getToken();
                    }
                    break;
                case TK_NUMERO_INT:
                    if(e.getTipo().getTipo() != TokensID.TK_ID_FLOAT && e.getTipo().getTipo() != TokensID.TK_ID_INT) {
                        throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                    } else {
                        boolean flag;
                        if(e.getTipo().getTipo() == TokensID.TK_ID_FLOAT){
                            token.setTipo(TokensID.TK_NUMERO_FLT);
                            flag = true;
                        } else {
                            flag = false;
                        }
                        e.setValor(token.getTexto());
                        E(flag);
                    }
                    break;
                case TK_NUMERO_FLT: E(true);
                    if(e.getTipo().getTipo() != TokensID.TK_ID_INT) {
                        throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                    } else {
                        e.setValor(token.getTexto());
                    }
                    break;
                case TK_IDENTIFICADOR:
                    TokensID tem = e.getTipo().getTipo();
                    e = gS.procurar(new Elemento<String>("", token, nivel));
                    if(e == null) {
                        throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                    } else {
                        TokensID t = e.getTipo().getTipo();
                        troca(t, token);
                        switch(token.getTipo()){
                            case TK_ID_INT:
                                if(tem != TokensID.TK_ID_INT && tem != TokensID.TK_ID_FLOAT){
                                    throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                                } else {
                                    if(tem == TokensID.TK_ID_FLOAT)
                                    E(true);
                                }
                                break;
                            case TK_ID_FLOAT:
                                if(tem != TokensID.TK_ID_FLOAT){
                                    throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                                } else {
                                    E(true);
                                }
                                break;
                            case TK_ID_BOOL:
                                if(tem != TokensID.TK_ID_BOOL){
                                    throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                                }
                                token = s.getToken();
                                break;
                            default:
                                //erro semantico
                                break;
                        }
                    }
                    break;
                case TK_SEPARADOR_ABRE_PAR:
                    if(isFloat(e.getTipo().getTipo())){
                        E(true); 
                    } else {
                        E(false);
                    }
                    break;
                default: throw new SyntacticException(SyntacticException.ERRO_ATTRIBUTION, s.getLinha(), s.getColuna()); 
            }
            if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO);
            else  throw new SyntacticException(SyntacticException.ERRO_CLOSE_DECLARATION, s.getLinha(), s.getColuna());
        }else { throw new SyntacticException(SyntacticException.ERRO_ATTRIBUTION, s.getLinha(), s.getColuna());}
    }
        
    private void IF(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            C();
            El();
        } else {
            throw new SyntacticException(SyntacticException.STATIMANT_MISSING, s.getLinha(), s.getColuna());
        }
    }

    private void El() {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_PR_ELSE) {
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_CHA) {
                bloco();
                fechaBloco();
            } else if(token.getTipo() == TokensID.TK_PR_IF) {
                IF();
            } else {
                throw new SyntacticException(SyntacticException.STATIMANT_MISSING, s.getLinha(), s.getColuna());
            }
        } else {
            flagElse = true;
            return;
        }
    }

    private void W() {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            C();
        } else {
            throw new SyntacticException(SyntacticException.STATIMANT_MISSING, s.getLinha(), s.getColuna());
        }
    }
    
    private void C(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_PR_TRUE || token.getTipo() == TokensID.TK_PR_FALSE) {
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) {
                abreBloco();
            } else {
                throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
            }
        }
        else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR || ID(token.getTipo()) )N();
        else {
            throw new SyntacticException(SyntacticException.CONDICIONAL_MISSING, s.getLinha(), s.getColuna());
        }
    }

    private void N() {
        E(true);
        if(OR(token.getTipo())) {
            token = s.getToken();
            E(true);
            if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) { 
                abreBloco();
            } else {
                throw new SyntacticException(SyntacticException.ERRO_CLOSE_COMPARING, s.getLinha(), s.getColuna());
            }
        } else {
            throw new SyntacticException(SyntacticException.ERRO_TYPE_COMPARING, s.getLinha(), s.getColuna());
        }
    }
 
        private void D(){
            abreBloco();
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_PR_WHILE) {
                token = s.getToken();
                if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR){
                    token = s.getToken();
                    if(token.getTipo() == TokensID.TK_PR_TRUE || token.getTipo() == TokensID.TK_PR_FALSE) {
                        token = s.getToken();
                        if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) {
                            token = s.getToken();
                            if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO) {
                                return;
                            } else {
                                throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
                            }
                        } else {
                            throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
                        }
                    }
                    else {
                        E(true);
                        if(OR(token.getTipo())) {
                            token = s.getToken();
                            E(true);
                            if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) {
                                token = s.getToken();
                                if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO) {
                                    return;
                                } else {
                                    throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
                                }
                            } else {
                                throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
                            }
                        } else {
                             throw new SyntacticException(SyntacticException.ERRO_TYPE_COMPARING, s.getLinha(), s.getColuna());
                        }
                    }
                }else{
                     throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_OPEN, s.getLinha(), s.getColuna());
                }
            } else {
                throw new SyntacticException(SyntacticException.STATIMANT_MISSING, s.getLinha(), s.getColuna());
            }
        }

    private void E(boolean flagFloat){
        if(ID(token.getTipo())) {
            if(!flagFloat){
                //Erro atribuição de float a um inteiro;
            } else {
                trocaInt(token);
            }
            A(flagFloat);
            if(!parenteses.isEmpty()){
                throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
            }
        }else if (token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            parenteses.push(token);
            B(flagFloat);
        }
        else {
            throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
        }
    }
//  a+(b+b)+a)
    private void A(boolean flagFloat) {
        token = s.getToken();
        if(OP(token.getTipo())) {
            F(flagFloat);
        } else {
            return;
        }
    }
    // E -> 1 A -> ) F ->
    private void F(boolean flagFloat) {
        token = s.getToken();
        if(ID(token.getTipo())) {
            if(!flagFloat){
                //Erro atribuição de float a um inteiro;
            } else {
                trocaInt(token);
            }
            A(flagFloat);
        }else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            parenteses.push(token);
            B(flagFloat);
        } else {
            throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
        }
    }

    private void Fl(boolean flagFloat) {
        if(OP(token.getTipo())) {
            token = s.getToken();
            if(ID(token.getTipo())) {
                if(!flagFloat){
                    //Erro atribuição de float a um inteiro;
                } else {
                    trocaInt(token);
                }
                Al(flagFloat);
            }else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
                parenteses.push(token);
                B(flagFloat);
            } else {
                throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
            }
        } else {
            return;
        }
    }

    private void Al(boolean flagFloat) {
        token = s.getToken();
        if(OP(token.getTipo())) {
            Fl(flagFloat);
        } else if (token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR){
            do {
                if(parenteses.isEmpty()) {
                    throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna()); 
                } else {
                    parenteses.pop();
                }
                token = s.getToken();
            }while(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR);
            if(OP(token.getTipo())) {
                F(flagFloat);
            } else {
                return;
            }
        } else {
            throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna()); 
        }
    }

    private void B(boolean flagFloat) {
        token = s.getToken();
        if(ID(token.getTipo())) {
            if(!flagFloat){
                //Erro atribuição de float a um inteiro;
            } else {
                trocaInt(token);
            }
            Al(flagFloat);
        }else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            parenteses.push(token);
            B(flagFloat);
        } else {
             throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
        }
    }
}
