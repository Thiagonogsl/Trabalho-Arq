public class Multiplexador{

    public int saida(int entrada1, int entrada2, int sinalControle){
        if(sinalControle){
            return entrada1;
        }else{
            return entrada2;
        }
    }
}