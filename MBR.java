public class MBR{
    private int valor;

    public void carregar(int dado){
        this.valor = dado & 0xFF; //o dado possui 8 bits
    }

    public int getValor(){
        return valor;
    }
}