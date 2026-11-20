public class UnidadeControle {

    private MPC mpc;
    private MIR mir;
    private Memoria memoriaControle;

    public UnidadeControle(MPC mpc, MIR mir,
                           Memoria memoriaControle) {
        this.mpc = mpc;
        this.mir = mir;
        this.memoriaControle = memoriaControle;
    }

    // Busca a microinstrucao na memoria de controle
    public void buscarMicroinstrucao() {
        int endereco = mpc.getValor();

        int microinstrucao =
            memoriaControle.leitura(endereco);

        mir.carregar(microinstrucao);
    }

    // Decodifica os campos da microinstrucao
    public void decodificar() {

        int amux = mir.getAMUX(0);
        int cond = mir.getCOND(0);
        int alu = mir.getALU(0);
        int sh = mir.getSH(0);

        int mbr = mir.getMBR(0);
        int mar = mir.getMAR(0);
        int rd = mir.getRD(0);
        int wr = mir.getWR(0);
        int enc = mir.getENC(0);

        int c = mir.getC(0);
        int b = mir.getB(0);
        int a = mir.getA(0);
        int addr = mir.getADDR(0);

        // Aqui serão utilizados os sinais
        // para coordenar os componentes.
    }

    // Executa um ciclo de busca e decodificacao
    public void ciclo() {
        buscarMicroinstrucao();
        decodificar();
    }
}
