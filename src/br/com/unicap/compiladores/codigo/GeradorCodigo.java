package br.com.unicap.compiladores.codigo;

import br.com.unicap.compiladores.parser.Parser;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GeradorCodigo{
    private TabelaDeComandos tabela;
    private Parser p;
    private BufferedWriter bw;
    private static GeradorCodigo gerador;
    private FuncoesGeradoras funcoesGeradoras;

    public static GeradorCodigo getConstrutor(Parser parser) throws IOException{
        if(gerador == null) {
            gerador = new GeradorCodigo(parser);
        }
        return gerador;
    }

    private GeradorCodigo(Parser parser) throws IOException{
        funcoesGeradoras = new FuncoesGeradoras();
        bw = new BufferedWriter(new FileWriter(geraPontoS(Parser.getS().file)));
        if(p == null) {
            p = parser;
        }
        if(tabela == null){
            tabela = TabelaDeComandos.getConstrutor();
        }
    }

    public void escreveFunção(ClusterDeOperacoes co) throws IOException {
        String linha = funcoesGeradoras.geraFuncao(co);
        bw.append(linha + "\n");
    }

    public void escreveLabel() throws IOException {
        bw.append(funcoesGeradoras.getLabel());
    }

    public void start(ClusterDeOperacoes co) throws IOException {
        String linha = "START " + co.getDestino().getTipo().getTexto() + ":" + "\n";
        bw.append(linha);
    }

    public String geraPontoS(String file) {
        char[] f = file.toCharArray();
        int tam = f.length;
        boolean flag = false;
        boolean flag2 = false;
        for(int i = 0; i < tam; i++){
            if(f[i] == '.' && !flag){
                flag = true;
            }
            if(flag && !flag2) {
                f[i] = 's';
                flag2 = true;
            }
            if(flag2){
                f[i] = ' ';
            }
        }
        return f.toString();
    }

    public void close() throws IOException{
        bw.close();
    }
}
