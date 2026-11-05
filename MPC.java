public class MPC {
    private int valor;

    public void carregar(int endereco){
        valor = endereco & 0xFF;
    }

    public int getValor(){
        return valor;
    }

    public int incrementar(){
        return (valor + 1) & 0xFF;
    }
}
