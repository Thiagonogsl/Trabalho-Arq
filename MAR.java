public class MAR {
    private int endereco;

    public void carregar(int endereco) {
        this.endereco = endereco & 0xFFFF; //limita o endereço a 16 bits
    }

    public int getEndereco() {
        return endereco;
    }
}