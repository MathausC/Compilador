package br.com.unicap.compiladores.codigo;

import java.util.Hashtable;

class TabelaDeComandos {
    private static TabelaDeComandos tabela;

    private Hashtable<String, String> tabelaHash;

    private TabelaDeComandos() {
        if(tabelaHash == null){
            tabelaHash = new Hashtable<String, String>();
            run();
        }
    }

    protected static TabelaDeComandos getConstrutor() {
        if(tabela == null){
            tabela = new TabelaDeComandos();
        }
        return tabela;
    }

    void run() {
        tabelaHash.put("+", "ADD");
        tabelaHash.put("-", "SUV");
        tabelaHash.put("*", "MULT");
        tabelaHash.put("/", "DIV");

        tabelaHash.put("=", "MOV");
        tabelaHash.put(";", "MOV");

        tabelaHash.put("<", "JL");
        tabelaHash.put("<=", "JLE");
        tabelaHash.put(">", "JG");
        tabelaHash.put(">=", "JGE");
        tabelaHash.put("!=", "JNE");
        tabelaHash.put("==", "JE");

        tabelaHash.put("true", "JE");
        tabelaHash.put("false", "JNE");
    }

    protected String get(String nome) {
        return tabelaHash.get(nome);
    }
}