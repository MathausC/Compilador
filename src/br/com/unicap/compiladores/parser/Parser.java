package br.com.unicap.compiladores.parser;

import br.com.unicap.compiladores.analisadorsemantico.Elemento;
import br.com.unicap.compiladores.analisadorsemantico.GerenciadorSemantico;
import br.com.unicap.compiladores.analisadorlexico.ScannerNosso;
import br.com.unicap.compiladores.analisadorlexico.Token;
import br.com.unicap.compiladores.analisadorlexico.TokensID;
import br.com.unicap.compiladores.excecoes.SyntacticException;
import br.com.unicap.compiladores.excecoes.SemanticException;
import br.com.unicap.compiladores.codigo.GeradorCodigo;
import br.com.unicap.compiladores.codigo.ClusterDeOperacoes;

import java.util.Stack;
import java.io.IOException;

public class Parser extends Terminal{
    private static Token token;
    private static Parser p;
    private static ScannerNosso s;
    private Stack<Token> parenteses;
    private Stack<ClusterDeOperacoes> clusters;
    private boolean flagElse;
    private GerenciadorSemantico gS;
    private int nivel;
    private static Elemento<String> e;
    private static Elemento<String> eG;
    private static  GeradorCodigo gc;
    private ClusterDeOperacoes cluster;
    private int cont = 0;
    
    private Parser(ScannerNosso s) throws IOException {
        Parser.s = s;
        parenteses = new Stack<Token>();
        clusters = new Stack<ClusterDeOperacoes>();
        this.nivel = 0;
        gS = new GerenciadorSemantico(this);
        gc = GeradorCodigo.getConstrutor(this);
        cluster = new ClusterDeOperacoes();
    }

    public static ScannerNosso getS() {
        return s;
    }

    public static Parser getContrutor(ScannerNosso s) throws IOException{
        if (p == null) {
            p = new Parser(s);
        } else {
            if(s == null) {
                Parser.s = s;
            }
        }
        return p;
    }

    public GerenciadorSemantico getGerenciadorSemantico(){
        return gS;
    }

    public void getParser() throws IOException{
        inicioClass();
        gc.close();
    }
    public int getLinha(){
        return s.getLinha();
    }
    public int getColuna(){
        return s.getColuna();
    }

    public void inicioClass() throws IOException{
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

    private void nomeClasse() throws IOException{
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_PR_MAIN || token.getTipo() == TokensID.TK_IDENTIFICADOR){
            if(token.getTipo() == TokensID.TK_IDENTIFICADOR) {
                token.setTipo(TokensID.TK_CLASS);
            }
            e = new Elemento<String>(token.getTexto(), token, nivel);
            cluster.setDestino(e);
            cluster.setOperacao("START");
            gS.addLista(e);
            gS.add(nivel);
            gc.start(cluster);
            abreBloco();
        }
        else {
            throw new SyntacticException(SyntacticException.ERRO_NAME_CLASS, s.getLinha(), s.getColuna());
        }
    }

    private void abreBloco() throws IOException {        
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
    
    private void bloco() throws IOException{
        nivel++;
        gS.add(nivel);
        while(true) {
            if(!flagElse){
                token = s.getToken();
                if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO){
                    token = s.getToken();
                }
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
    
    private void V(TokensID t) throws IOException {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR){
            troca(t, token);
            e = new Elemento<String>("zero", token, nivel);
            gS.addLista(e);
            AT();
        }else{throw new SyntacticException(SyntacticException.ERRO_DECLARATION, s.getLinha(), s.getColuna()); }
    }
    
    private void AT() throws IOException{
        if(token.getTipo() ==TokensID.TK_IDENTIFICADOR) {
            e = gS.procurar(new Elemento<String>("valor", token, nivel));
            if(e == null) {
                throw new SemanticException(SemanticException.ERRO_UNDEFINED_VARIABLE, s.getLinha(), s.getColuna());
            } else {
                token = e.getTipo();
            }
        }
        cluster.setDestino(e);
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO) {
            cluster.setOperacao(token.getTexto());
            gc.escreveFunção(cluster);
            return;
        }
        else if (token.getTipo() == TokensID.TK_ATRIBUICAO){
            cluster.setOperacao(token.getTexto());
            token = s.getToken();
            switch(token.getTipo()) {
                case TK_PR_TRUE:
                    if(e.getTipo().getTipo() != TokensID.TK_ID_BOOL) {
                        throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                    } else {
                        e.setValor("1");
                        gc.escreveFunção(cluster);
                        token = s.getToken();
                    }
                    break;
                case TK_PR_FALSE: 
                    if(e.getTipo().getTipo() != TokensID.TK_ID_BOOL) {
                        throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                    } else {
                        e.setValor("zero");
                        gc.escreveFunção(cluster);
                        token = s.getToken();
                    }
                    break;
                case TK_CHAR: 
                    if(e.getTipo().getTipo() != TokensID.TK_ID_CHAR) {
                        throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                    } else {
                        e.setValor("" + (Character.getNumericValue((token.getTexto().charAt(1)))+87));
                        gc.escreveFunção(cluster);
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
                        e.setValor("zero");
                        gc.escreveFunção(cluster);
                        E(flag);
                    }
                    break;
                case TK_NUMERO_FLT: E(true);
                    if(e.getTipo().getTipo() != TokensID.TK_ID_FLOAT) {
                        throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                    } else {
                        e.setValor("zero");
                        gc.escreveFunção(cluster);
                        E(true);
                    }
                    break;
                case TK_IDENTIFICADOR:
                    TokensID tem = e.getTipo().getTipo();
                    eG = gS.procurar(new Elemento<String>("", token, nivel));
                    if(eG == null){
                        throw new SemanticException(SemanticException.ERRO_UNDEFINED_VARIABLE, s.getLinha(), s.getColuna());
                    }
                    token = eG.getTipo();
                    if(eG == null) {
                        throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                    } else {                       
                        switch(token.getTipo()){
                            case TK_ID_INT:
                                if(tem != TokensID.TK_ID_INT && tem != TokensID.TK_ID_FLOAT){
                                    throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                                } else {
                                    e.setValor("zero");
                                    gc.escreveFunção(cluster);
                                    if(tem == TokensID.TK_ID_FLOAT) {
                                        E(true);
                                    } else {
                                        E(false);
                                    }
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
                                    throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                                
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
        
    private void IF() throws IOException {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            C();
            gc.escreveLabel();
            El();
        } else {
            throw new SyntacticException(SyntacticException.STATIMANT_MISSING, s.getLinha(), s.getColuna());
        }
    }

    private void El() throws IOException { 
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

    private void W() throws IOException{
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            C();
        } else {
            throw new SyntacticException(SyntacticException.STATIMANT_MISSING, s.getLinha(), s.getColuna());
        }
    }
    
    private void C() throws IOException {
        token = s.getToken();
        if(token.getTipo() ==TokensID.TK_IDENTIFICADOR) {
            eG = gS.procurar(new Elemento<String>("valor", token, nivel));
            if(eG == null) {
                throw new SemanticException(SemanticException.ERRO_UNDEFINED_VARIABLE, s.getLinha(), s.getColuna());
            } else {
                token = eG.getTipo();
            }
        }
        if(token.getTipo() == TokensID.TK_PR_TRUE || token.getTipo() == TokensID.TK_PR_FALSE) {
            Elemento<String> temp = new Elemento<String>("0", token, nivel);
            temp.setRegistrador("zero");
            cluster.setOperacao(token.getTexto());
            cluster.setDestino(temp);
            cluster.setOperando1(temp);
            gc.escreveFunção(cluster);
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) {
                abreBloco();
            } else {
                throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
            }
        }
        else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR || ID(token.getTipo()) ) {
            N();
        }
        else {
            throw new SyntacticException(SyntacticException.CONDICIONAL_MISSING, s.getLinha(), s.getColuna());
        }
    }

    private void N() throws IOException {
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
 
        private void D() throws IOException {
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

    private void E(boolean flagFloat) throws IOException {
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR || token.getTipo() == TokensID.TK_ID_FLOAT || token.getTipo() == TokensID.TK_ID_INT) {
            eG = gS.procurar(new Elemento<String>("valor", token, nivel));
            if(eG == null) {
                throw new SemanticException(SemanticException.ERRO_UNDEFINED_VARIABLE, s.getLinha(), s.getColuna());
            }
        } else {
            eG = new Elemento<String>(token.getTexto(), token, nivel);
            eG.setRegistrador(token.getTexto());
        }
        if(ID(token.getTipo())) {
            if(isFloat(token.getTipo()) && !flagFloat){
                throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
            } else if(flagFloat){
                trocaInt(token);
            }
            cluster.setOperando1(eG);
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
    private void A(boolean flagFloat) throws IOException {
        token = s.getToken();
        if(OP(token.getTipo())) {
            cluster.setOperacao(token.getTexto());
            F(flagFloat);
        } else {
            return;
        }
    }
    // E -> 1 A -> ) F ->
    private void F(boolean flagFloat) throws IOException {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR || token.getTipo() == TokensID.TK_ID_FLOAT || token.getTipo() == TokensID.TK_ID_INT) {
            eG = gS.procurar(new Elemento<String>("valor", token, nivel));
            if(eG != null) {
                token = eG.getTipo();
            }
            else {
                throw new SemanticException(SemanticException.ERRO_UNDEFINED_VARIABLE, s.getLinha(), s.getColuna());
            }
        } else {
            eG = new Elemento<String>(token.getTexto(), token, nivel);
            eG.setRegistrador(token.getTexto());
        }
        if(ID(token.getTipo())) {
            if(isFloat(token.getTipo()) && !flagFloat){
                throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
            } else if(flagFloat){
                trocaInt(token);
            }
            cluster.setOperando2(eG);
            gc.escreveFunção(cluster);
            cluster.setOperando1(cluster.getDestino());
            A(flagFloat);
        }else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            parenteses.push(token);
            clusters.push(cluster);
            B(flagFloat);
        } else {
            throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
        }
    }

    private void Fl(boolean flagFloat) throws IOException{
        if(OP(token.getTipo())) {
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_IDENTIFICADOR || token.getTipo() == TokensID.TK_ID_FLOAT || token.getTipo() == TokensID.TK_ID_INT) {
                eG = gS.procurar(new Elemento<String>("valor", token, nivel));
                if(eG != null) {
                    token = eG.getTipo();
                }
                else {
                    throw new SemanticException(SemanticException.ERRO_UNDEFINED_VARIABLE, s.getLinha(), s.getColuna());
                }
            } else {
                eG = new Elemento<String>(token.getTexto(), token, nivel);
                eG.setRegistrador(token.getTexto());
            }
            if(ID(token.getTipo())) {
                if(isFloat(token.getTipo()) && !flagFloat){
                    throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
                } else if(flagFloat){
                    trocaInt(token);
                }
                cluster.setOperando2(eG);
                gc.escreveFunção(cluster);
                cluster.setOperando1(cluster.getDestino());
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

    private void Al(boolean flagFloat) throws IOException {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR || token.getTipo() == TokensID.TK_ID_FLOAT || token.getTipo() == TokensID.TK_ID_INT) {
            eG = gS.procurar(new Elemento<String>("valor", token, nivel));
            if(eG != null) {
                token = eG.getTipo();
            }
            else {
                throw new SemanticException(SemanticException.ERRO_UNDEFINED_VARIABLE, s.getLinha(), s.getColuna());
            }
        } else {
            eG = new Elemento<String>(token.getTexto(), token, nivel);
            eG.setRegistrador(token.getTexto());
        }
        if(OP(token.getTipo())) {
            cluster.setOperacao(token.getTexto());
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
            if(!clusters.empty()){
                Elemento<String> des = cluster.getDestino();
                cluster = clusters.pop();
                cluster.setOperando2(des);
                gc.escreveFunção(cluster);
                cluster.setOperando1(cluster.getDestino());
            }
            if(OP(token.getTipo())) {
                cluster.setOperacao(token.getTexto());
                F(flagFloat);
            } else {
                return;
            }
        } else {
            throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna()); 
        }
    }

    private void B(boolean flagFloat) throws IOException {
        eG = new Elemento<String>("zero", token, nivel);
        if(eG.getRegistrador() == null) {
            eG.setRegistrador("T" + cont);
            cont++;
        }
        cluster.setDestino(eG);
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR || token.getTipo() == TokensID.TK_ID_FLOAT || token.getTipo() == TokensID.TK_ID_INT) {
            eG = gS.procurar(new Elemento<String>("valor", token, nivel));
            if(eG != null) {
                token = eG.getTipo();
            }
            else {
                throw new SemanticException(SemanticException.ERRO_UNDEFINED_VARIABLE, s.getLinha(), s.getColuna());
            }
        } else {
            eG = new Elemento<String>(token.getTexto(), token, nivel);
            eG.setRegistrador(token.getTexto());
        }
        if(ID(token.getTipo())) {
            if(isFloat(token.getTipo()) && !flagFloat){
                throw new SemanticException(SemanticException.ERRO_ATTRIBUTION_INCORRECT, s.getLinha(), s.getColuna());
            } else if(flagFloat){
                trocaInt(token);
            }
            cluster.setOperando1(eG);
            Al(flagFloat);
        }else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            clusters.push(cluster);
            parenteses.push(token);
            B(flagFloat);
        } else {
            throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
        }
    }
}
