public class Memoria{
    private int[] dados;

    public Memoria(int tamanho){
        this.dados = new int[tamanho];
    }

    public int leitura(int endereco){
        return this.dados[endereco];
    }

    public void escrita(int dado, int endereco){
        this.dados[endereco] = dado;
    }

}