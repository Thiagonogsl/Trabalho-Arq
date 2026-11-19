/**
 * Enum que define o layout de cada campo da microinstrução de 32 bits do MIC-1.
 *
 * Layout completo:
 * | AMUX | COND | ALU | SH | MBR | MAR | RD | WR | ENC |  C   |  B   |  A   |  ADDR    |
 * |  1b  |  2b  | 2b  | 2b | 1b  | 1b  | 1b | 1b | 1b  | 4b   | 4b   | 4b   |  8b      |
 * | bit31| 30-29|28-27|26-25| 24  | 23  | 22 | 21 | 20  |19-16 |15-12 |11-8  |  7-0     |
 */
public enum Campo {
    ADDR(0, 8),     // Próximo endereço na memória de controle
    A(8, 4),        // Seleção do registrador A (barramento A)
    B(12, 4),       // Seleção do registrador B (barramento B)
    C(16, 4),       // Seleção do registrador destino (barramento C)
    ENC(20, 1),     // Habilita escrita no registrador C
    WR(21, 1),      // Escrita na memória principal
    RD(22, 1),      // Leitura da memória principal
    MAR(23, 1),     // Carrega o MAR com saída do barramento B
    MBR(24, 1),     // Carrega o MBR com dado da memória
    SH(25, 2),      // Deslocador: 0=nenhum, 1=SRA1, 2=SLL8
    ALU(27, 2),     // ULA: 0=A+B, 1=A AND B, 2=A OR B, 3=NOT A
    COND(29, 2),    // Condição de salto: 0=nenhum, 1=JMPN, 2=JMPZ, 3=sempre
    AMUX(31, 1);    // Seleção do AMux: 0=MBR, 1=Latch(barramento A)

    private final int posicao;   // Bit mais baixo do campo
    private final int largura;   // Quantos bits o campo ocupa

    Campo(int posicao, int largura) {
        this.posicao = posicao;
        this.largura = largura;
    }

    public int getPosicao() { return posicao; }
    public int getLargura() { return largura; }

    /** Retorna a máscara com 'largura' bits ligados. Ex: largura=4 → 0b1111 */
    public int mascara() {
        return (1 << largura) - 1;
    }

    /**
     * Extrai o valor deste campo de uma microinstrução de 32 bits.
     * Usado pelo MIR para decodificar.
     *
     * Ex: Campo.ALU.extrair(0x08400005) → 2
     */
    public int extrair(int microInstrucao) {
        return (microInstrucao >>> posicao) & mascara();
    }

    /**
     * Insere um valor neste campo dentro de uma microinstrução existente.
     * Usado pelo Montador para codificar.
     *
     * Ex: Campo.ALU.inserir(0, 2) → seta os bits 28-27 para 10 (binário)
     */
    public int inserir(int microInstrucao, int valor) {
        // Limpa os bits deste campo
        int limpo = microInstrucao & ~(mascara() << posicao);
        // Insere o novo valor na posição correta
        return limpo | ((valor & mascara()) << posicao);
    }
}
