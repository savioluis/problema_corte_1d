import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combinacoes {
    public static void main(String[] args) {
        int[] numeros = {5, 8};
        int alvo = 20;

        Arrays.sort(numeros);

        ArrayList<ArrayList<Integer>> combinacoesEncontradas = encontrarCombinacoes(numeros, alvo);
        ArrayList<ArrayList<Integer>> combinacoesValidas = new ArrayList<>();

        System.out.println(combinacoesEncontradas);

        int menorNumero = numeros[0];

        for (int i = 0; i < combinacoesEncontradas.size(); i++) {
            int somador = 0;
            for (int j = 0; j < combinacoesEncontradas.get(i).size(); j++) {
                somador += combinacoesEncontradas.get(i).get(j);
            }
            if (alvo - somador < menorNumero) {
                combinacoesValidas.add(combinacoesEncontradas.get(i));
            }
        }

        System.out.println(combinacoesValidas);
    }

    public static ArrayList<ArrayList<Integer>> encontrarCombinacoes(int[] numeros, int alvo) {
        ArrayList<ArrayList<Integer>> resultado = new ArrayList<>();
        ArrayList<Integer> combinacaoAtual = new ArrayList<>();
        int indiceAtual = 0;

        encontrarCombinacoesHelper(numeros, alvo, indiceAtual, combinacaoAtual, resultado);

        return resultado;
    }

    private static void encontrarCombinacoesHelper(int[] numeros, int alvo, int indiceAtual,
                                                   List<Integer> combinacaoAtual, ArrayList<ArrayList<Integer>> resultado) {
        if (alvo >= 0) {
            // Se o alvo for maior ou igual a 0, adiciona a combinação atual ao resultado
            resultado.add(new ArrayList<>(combinacaoAtual));
        } else {
            // Se o alvo for menor que 0, retorna sem adicionar a combinação
            return;
        }

        for (int i = indiceAtual; i < numeros.length; i++) {
            int currentNumber = numeros[i];

            if (alvo - currentNumber >= 0) {
                // Adiciona o número atual à combinação
                combinacaoAtual.add(currentNumber);

                // Chama recursivamente a função para encontrar combinações com o restante do alvo
                encontrarCombinacoesHelper(numeros, alvo - currentNumber, i, combinacaoAtual, resultado);

                // Remove o número atual da combinação para tentar outras combinações
                combinacaoAtual.remove(combinacaoAtual.size() - 1);
            }
        }
    }
}
