package br.com.unicap.compiladores.analisadorsemantico;

public class Lista {

    private No inicio;
    private No fim;
    private int cont;
    private static int reg;

    public boolean isEmpty(){
        return inicio == null;
    }

    public boolean contains(Elemento<String> e){
        No i = inicio;
        while(i != null) {
            if(i.getItem().equals(e)) {
                return true;
            } else {
                i = i.getPosterior();
            }
        }
        return false;
    }

    public void add(Elemento<String> e) {
        e.setRegistrador("R" + reg);
        reg++;
        No no = new No(e);
        if(cont == 0){
            inicio = no;
            fim = no;
        } else {
            fim.setPosterior(no);
            no.setAnterior(fim);
            fim = no;
        }
        cont++;
    }

    public int size(){
        return cont;
    }

    public Elemento<String> get(int o) {
        int j = 0;
        if (o < cont) {
            No i = inicio;
            while(j < o) {
                i = i.getPosterior();
                j++;
            }
            return i.getItem();
        }
        return null;
    }

    public int indexOf(Elemento<String> e){
        int j = 0;
        No i = inicio;
        while(i != null) {
            if(i.getItem().equals(e)) {
                return j;
            } else {
                i = i.getPosterior();
            }
        }
        return -1;
    }
    
    public void set(int index, Elemento<String> e) {
        No no = inicio;
        int i = 0;
        if(index < cont) {
            while (i < index){
                no = no.getPosterior();
            }
            No aux = no;
            no = new No(e);
            no.setAnterior(aux.getAnterior());
            no.setPosterior(aux.getPosterior());
        }
    }

    public static int getReg() {
        return reg;
    }

    public class No{
        private No anterior;
        private No posterior;
        private Elemento<String> item;

        No(Elemento<String> e) {
            item = e;
        }

        public Elemento<String> getItem() {
            return item;
        }

        public void setItem(Elemento<String> item) {
            this.item = item;
        }

        public No getAnterior() {
            return anterior;
        }

        public No getPosterior() {
            return posterior;
        }

        public void setAnterior(No anterior) {
            this.anterior = anterior;
        }

        public void setPosterior(No posterior) {
            this.posterior = posterior;
        }
    }
}
