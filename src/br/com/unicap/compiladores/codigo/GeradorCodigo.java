package br.com.unicap.compiladores.codigo;
import br.com.unicap.compiladores.analisadorsemantico.Lista;
import br.com.unicap.compiladores.analisadorsemantico.GerenciadorSemantico;
import br.com.unicap.compiladores.parser.Parser;

public class GeradorCodigo {
    private Lista lista;
    private TabelaDeComandos tabela;
    private Parser p;
    private GerenciadorSemantico gs;
    private static GeradorCodigo gerador;

    public static GeradorCodigo getConstrutor(Parser parser){
        if(gerador == null) {
            gerador = new GeradorCodigo(parser);
        }
        return gerador;
    }

    private GeradorCodigo(Parser parser){
        if(p == null) {
            p = parser;
            gs = p.getGerenciadorSemantico();
            lista = gs.getLista();
        }
        if(tabela == null){
            tabela = TabelaDeComandos.getConstrutor();
        }
    }

    
}
