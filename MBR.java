public class MBR{
    private int valor;

    public void carregar(int dado){
        this.valor = dado & 0xFFFF; //o dado possui 16 bits
    }

    public int getValor(){
        return valor;
    }
}