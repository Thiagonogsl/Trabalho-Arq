public class Multiplexador{

    public int saida(int entrada1, int entrada2, int sinalControle){
        if(sinalControle == 1){
            return entrada1;
        }else{
            return entrada2;
        }
    }
}