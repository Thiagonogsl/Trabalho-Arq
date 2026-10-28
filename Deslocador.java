public class Deslocador {
    public int computar(int entrada, int controle){
        int v = entrada & 0xFFFF;
        int resultado;

        switch(controle){
            case 0: //sem deslocamento
                resultado = v;
                break;
            case 1: //SRA1
            //se v for negativo o sinal se perderia com (v>>1) ex: 1010 -> v>>1 -> 0101(sinal se perdeu)
            //0x8000 = 1000 0000 0000 0000
            //Se v é negativo (bit 15 = 1): resultado 0x8000
            //Se v é positivo (bit 15 = 0): resultado 0x0000  (0 & 1 = 0)
            // o '|' junta as duas partes, dessa forma, se for negativo continua negativo e positivo continua positivo
            // ex: 1100(-4) -> v>>1 -> 1110(-2)

                resultado = (v >> 1) | (v & 0x8000);
                break;       
            case 2: //SLL8
                resultado = v << 8;
                break;
            default:
                throw new IllegalArgumentException("Controle inválido");  
        }

        return resultado & 0xFFFF;
    }
}

//Operação '>> x' pula os digitos x casas para direita
//Deslocamento à esquerda(SLL8): empurra todos os bits 8 posições para a esquerda
//Deslocamento à direita(SRA1): empurra todos os bits 1 posição para a direita, apagando o último bit(bit de sinal preservado)(equivalente a dividir por 2)