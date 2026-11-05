import java.util.Arrays;

public class MemoriaControle extends Memoria {
    private static final int TAMANHO = 256;

    public MemoriaControle(int[] vetor){
        super(TAMANHO);
        this.dados = Arrays.copyOf(vetor, TAMANHO);
    }

    @Override 
    public int leitura(int endereco){
        return this.dados[endereco];
    }
}
