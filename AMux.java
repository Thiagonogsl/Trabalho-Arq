public class AMux{

    public int saida(int valorLatch, int valorMBR, int sinalControle){
        if(sinalControle == 1){
            return valorLatch;
        }else{
            return valorMBR;
        }
    }
}