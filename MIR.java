public class MIR{
    private int valor;

    public void carregar(int microInst){
        this.valor = microInst;
    }

    public int getAMUX(int dado){
        return (valor >>> 31) & 0b1;
    }
    
    public int getCOND(int dado){
        return (valor >>> 29) & 0b11; 
    }

    public int getALU(int dado){
        return(valor >>> 27) & 0b11;
    }

    public int getSH(int dado){
        return (valor >>> 25) & 0b11;
    }
    
    public int getMBR(int dado){
        return (valor >>> 24) & 0b1;
    }

    public int getMAR(int dado){
        return (valor >>> 23) & 0b1;
    }

    public int getRD(int dado){
        return (valor >>> 22) & 0b1;
    }
    
    public int getWR(int dado){
        return (valor >>> 21) & 0b1;
    }
    
    public int getENC(int dado){
        return (valor >>> 20) & 0b1;
    }
    
    public int getC(int dado){
        return (valor >>> 16) & 0xF;
    }
    
    public int getB(int dado){
        return (valor >>> 12) & 0xF;
    }

    public int getA(int dado){
        return (valor >>> 8) & 0xF;
    }

    public int getADDR(int dado){
        return valor & 0xFF;
    }
}
