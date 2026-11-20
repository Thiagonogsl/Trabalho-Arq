public class Cache {

    private Memoria memoriaPrincipal;

    private int quantidadeLinhas;
    private int tamanhoBloco;
    private LinhaCache[] linhas;

    // Representa uma linha da cache
    private static class LinhaCache {
        int tag;
        boolean valido;
        boolean dirty;
        int[] dados;

        LinhaCache(int tamanhoBloco) {
            this.tag = -1;
            this.valido = false;
            this.dirty = false;
            this.dados = new int[tamanhoBloco];
        }
    }

    public Cache(Memoria memoriaPrincipal,
                 int quantidadeLinhas,
                 int tamanhoBloco) {

        if (quantidadeLinhas <= 0 || tamanhoBloco <= 0) {
            throw new IllegalArgumentException("Dimensoes invalidas");
        }

        this.memoriaPrincipal = memoriaPrincipal;
        this.quantidadeLinhas = quantidadeLinhas;
        this.tamanhoBloco = tamanhoBloco;

        this.linhas = new LinhaCache[quantidadeLinhas];

        for (int i = 0; i < quantidadeLinhas; i++) {
            linhas[i] = new LinhaCache(tamanhoBloco);
        }
    }

    // Retorna o dado solicitado
    public int leitura(int endereco) {

        verificarEndereco(endereco);

        int bloco = endereco / tamanhoBloco;
        int indice = bloco % quantidadeLinhas;
        int tag = bloco / quantidadeLinhas;
        int offset = endereco % tamanhoBloco;

        LinhaCache linha = linhas[indice];

        // Cache miss
        if (!linha.valido || linha.tag != tag) {
            carregarBloco(indice, tag);
        }

        // Cache hit
        return linha.dados[offset];
    }

    // Escreve um dado na cache
    public void escrita(int dado, int endereco) {

        verificarEndereco(endereco);

        int bloco = endereco / tamanhoBloco;
        int indice = bloco % quantidadeLinhas;
        int tag = bloco / quantidadeLinhas;
        int offset = endereco % tamanhoBloco;

        LinhaCache linha = linhas[indice];

        // Cache miss: carrega o bloco primeiro
        if (!linha.valido || linha.tag != tag) {
            carregarBloco(indice, tag);
        }

        linha.dados[offset] = dado;

        // Indica que o bloco foi modificado
        linha.dirty = true;
    }

    // Carrega um bloco da memoria principal
    private void carregarBloco(int indice, int novaTag) {

        LinhaCache linha = linhas[indice];

        // Se a linha estiver ocupada e modificada,
        // escreve o bloco antigo na memoria principal
        if (linha.valido && linha.dirty) {
            escreverBlocoMemoria(indice);
        }

        int bloco = novaTag * quantidadeLinhas + indice;
        int enderecoInicial = bloco * tamanhoBloco;

        // Busca os dados na memoria principal
        for (int i = 0; i < tamanhoBloco; i++) {

            int endereco = enderecoInicial + i;

            linha.dados[i] = memoriaPrincipal.leitura(endereco);
        }

        linha.tag = novaTag;
        linha.valido = true;
        linha.dirty = false;
    }

    // Escreve o bloco modificado na memoria principal
    private void escreverBlocoMemoria(int indice) {

        LinhaCache linha = linhas[indice];

        int bloco = linha.tag * quantidadeLinhas + indice;
        int enderecoInicial = bloco * tamanhoBloco;

        for (int i = 0; i < tamanhoBloco; i++) {

            int endereco = enderecoInicial + i;

            memoriaPrincipal.escrita(linha.dados[i], endereco);
        }

        linha.dirty = false;
    }

    // Verifica se o endereco existe na memoria principal
    private void verificarEndereco(int endereco) {

        if (endereco < 0 || endereco >= memoriaPrincipal.getTamanho()) {
            throw new IndexOutOfBoundsException("Endereco invalido");
        }
    }

    // Escreve todos os blocos modificados na memoria principal
    public void flush() {

        for (int i = 0; i < quantidadeLinhas; i++) {

            if (linhas[i].valido && linhas[i].dirty) {
                escreverBlocoMemoria(i);
            }
        }
    }
}