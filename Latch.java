public class Latch{
    private int valorArmazenado;

    public Latch(int armazenado){
        this.valorArmazenado = armazenado;
    }

    public void habilitaEntrada(int entrada){
        this.valorArmazenado = entrada;
    }

    public int habilitaSaida(){
        return this.valorArmazenado;
    }
}