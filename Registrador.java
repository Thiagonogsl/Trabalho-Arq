public class Registrador{
    private int valor;

    public void carregar(int entrada){
        this.valor = entrada;
    }

    public int saida(){
        return this.valor;
    }
}