public class ULA{
    private boolean flagN; //Negativo
    private boolean flagZ; //Zero

    public int computar(int entradaA, int entradaB, int controle){
        int resultado = 0;
        int a = entradaA & 0xFFFF; 
        int b = entradaB & 0xFFFF;

        switch(controle){
            case 0: //A + B
                resultado = a + b;
                break;
            case 1: //A AND B
                resultado = a & b;
                break;
            case 2: //A OR b
                resultado = a | b;
                break;
            case 3: //Not a
                resultado = ~a;
                break;
            case 4: //Passa A direto
                resultado = a;
                break;
            default:
                throw new IllegalArgumentException("Sinal de controle inválido");
        }

        //Restringe o valor do resultado a 16 bits
        resultado = resultado & 0xFFFF;

        atualizarFlags(resultado);

        return resultado;
    }

    private void atualizarFlags(int resultado){
        this.flagZ = (resultado == 0);                  //Se o resultado for igual a 0 a flagZ é acionada
        
        this.flagN = (resultado & 0x8000) != 0;         //0x8000 representa um valor onde apenas o bit mais significativo = 1
                                                        //se resultado for negativo essa conta dará 1 e se for positivo será 0
    }

    public boolean getFlagN() { return flagN; }
    public boolean getFlagZ() { return flagZ; }
}   

//O int em java tem 32 bits, porém no MIC-1 a entrada tem que ter 16 bits 
//Se usa o '& 0xFFFF' para caso a entrada tenha mais que 16 bits os bits extras sejam cortados
//Como 0xFFFF = 0000 0000 0000 0000 1111 1111 1111 1111, se você usar a operação '&' junto com ela, todos os bits extras serão removidos(Overflow)