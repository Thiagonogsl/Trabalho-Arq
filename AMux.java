public class AMux{

    public int saida(int valorLatch, int valorMBR, int sinalControle){
        if(sinalControle){
            return valorLatch;
        }else{
            return valorMBR;
        }
    }
}