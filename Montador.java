
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Montador {
    public int[] montar(String caminhoArquivo) throws IOException {
        List<String> linhas = lerELimpar(caminhoArquivo);
        primeiraPassada(linhas);
        return segundaPassada(linhas);
    }

    private String limparLinha(String linha){
        if(linha == null) return "";

        int indiceComentario = linha.indexOf("//");
        if(indiceComentario != -1){
            linha = linha.substring(0, indiceComentario);
        }

        return linha.trim();
    }

    public List<String> lerELimpar(String caminhoArquivo) throws IOException{
        List<String> linhasLimpas = new ArrayList<String>();
        List<String> todasLinhas = Files.readAllLines(Paths.get(caminhoArquivo));

        for(String linha : todasLinhas){
            String limpa = limparLinha(linha);

            if(!(limpa.isEmpty())){
                linhasLimpas.add(limpa);
            }
        }
        return linhasLimpas;
    }

    private Map<String, Integer> tabelaSimbolos = new LinkedHashMap<>();

    private void primeiraPassada(List<String> linhas){
        int endereco = 0;

        for(String linha : linhas){
            String instrucao = linha;

            if(linha.contains(":")){
                int indiceDoisPontos = linha.indexOf(":");
                String label = linha.substring(0, indiceDoisPontos).trim();

                if(!(label.isEmpty())){
                    tabelaSimbolos.put(label, endereco);
                }

                instrucao = linha.substring(indiceDoisPontos + 1).trim();
            }

            if(!instrucao.isEmpty()){
                endereco++;
            }
        }
    }

    private int[] segundaPassada(List<String> linhas){
        int[] memoria = new int[256];
        int endereco = 0;

        for(int i = 0; i < linhas.size(); i++){
            String linha = linhas.get(i); // já vêm limpas do lerELimpar()

            if(linha.isEmpty())
                continue;

            if(linha.contains(":")){
                int indiceDoisPontos = linha.indexOf(":");
                linha = linha.substring(indiceDoisPontos + 1).trim();

                if(linha.isEmpty())
                    continue;
            }

            memoria[endereco] = parsearMicroInstrucao(linha, i + 1, endereco);
            endereco++;
        }
        return memoria;
    }

    private int parsearMicroInstrucao(String linha, int numLinha, int endereco) {
        int microInstrucao = 0;

        String[] pares = linha.split("\\s+"); // separa por espaços: ["AMUX=1", "ALU=0", ...]
        for (String par : pares) {
            if (par.isEmpty()) continue;

            // Separa cada par pelo '='  ex: "ALU=2" → ["ALU", "2"]
            String[] chaveValor = par.split("=", 2);
            if (chaveValor.length != 2) {
                throw new RuntimeException("Linha " + numLinha + ": formato inválido '" + par + "'. Esperado: CAMPO=valor");
            }

            String nomeCampo = chaveValor[0].trim().toUpperCase();
            String valorStr = chaveValor[1].trim();

            // Identifica o campo pelo nome no enum
            Campo campo = Campo.valueOf(nomeCampo);

            // Resolve o valor (número ou label)
            int valor = resolverValor(valorStr, numLinha);

            microInstrucao = campo.inserir(microInstrucao, valor);
        }

        return microInstrucao;
    }

    private int resolverValor(String valorStr, int numLinha) {
        // Tenta como número
        try {
            if (valorStr.startsWith("0x") || valorStr.startsWith("0X")) 
                return Integer.parseInt(valorStr.substring(2), 16);
            else if (valorStr.startsWith("0b") || valorStr.startsWith("0B")) 
                return Integer.parseInt(valorStr.substring(2), 2);
            else 
                return Integer.parseInt(valorStr);
        } catch (NumberFormatException e) {
            // Não é número — tenta como label
        }

        // Tenta como label na tabela de símbolos
        if (tabelaSimbolos.containsKey(valorStr)) {
            return tabelaSimbolos.get(valorStr);
        }

        throw new RuntimeException("Linha " + numLinha + ": '" + valorStr + "' não é número nem label conhecida");
    }
}
