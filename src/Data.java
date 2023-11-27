import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Data {

    int comprimentoBarra;
    int nTipos;
    int[] tamanhos;
    int[] quantidades;
    private ArrayList<ArrayList<Integer>> restricoes;

    ArrayList<Integer> desperdicios;
    int[] quantidadesOrdenadasComTamanhos;
    int[] tamanhosOrdenados;

    ArrayList<ArrayList<Integer>> resultadoRestricoes;


    String nomeArquivo;

    public Data(String filename) throws FileNotFoundException {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            comprimentoBarra = scanner.nextInt();
            nTipos = scanner.nextInt();

            tamanhos = new int[nTipos];
            quantidades = new int[nTipos];

            for (int i = 0; i < nTipos; i++) {
                tamanhos[i] = scanner.nextInt();
            }
            for (int i = 0; i < nTipos; i++) {
                quantidades[i] = scanner.nextInt();
            }

            restricoes = encontrarCombinacoesValidas(tamanhos, comprimentoBarra);

            int[] somaDasCombinacoes = new int[restricoes.size()];
            for (int i = 0; i < restricoes.size(); i++) {
                int somador = 0;
                for (int j = 0; j < restricoes.get(i).size(); j++) {
                    somador += restricoes.get(i).get(j);
                }
                somaDasCombinacoes[i] = somador;
            }

            desperdicios = new ArrayList<>();

            for (int i = 0; i < restricoes.size(); i++) {
                desperdicios.add(comprimentoBarra - somaDasCombinacoes[i]);
            }

            resultadoRestricoes = restricoesLista();

            Map<Integer, Integer> mapAuxiliar = new HashMap<>();
            for (int i = 0; i < tamanhos.length; i++) {
                mapAuxiliar.put(tamanhos[i], quantidades[i]);
            }
            int[] auxiliarTamanhos = new int[tamanhos.length];
            for (int i = 0; i < tamanhos.length; i++) {
                auxiliarTamanhos[i] = tamanhos[i];
            }
            Arrays.sort(auxiliarTamanhos);

            quantidadesOrdenadasComTamanhos = new int[quantidades.length];
            for (int i = 0; i < quantidades.length; i++) {
                quantidadesOrdenadasComTamanhos[i] = mapAuxiliar.get(auxiliarTamanhos[i]);
            }

            tamanhosOrdenados = new int[tamanhos.length];
            for (int i = 0; i < tamanhos.length; i++) {
                tamanhosOrdenados[i] = tamanhos[i];
            }
            Arrays.sort(tamanhosOrdenados);

            nomeArquivo = filename;
            scanner.close();

        } catch (FileNotFoundException erro) {
            System.out.println("Erro ao ler o arquivo !");
            throw erro;
        }
    }

    public void exibirQuantidades() {
        System.out.println(Arrays.toString(quantidades));
    }

    public void exibirQuantidadesOrdenadasComTamanhos() {
        System.out.println(Arrays.toString(quantidadesOrdenadasComTamanhos));
    }

    public void exibirTamanhos() {
        System.out.println(Arrays.toString(tamanhos));
    }

    public void exibirTamanhosOrdenados() {
        System.out.println(Arrays.toString(tamanhosOrdenados));
    }

    public void exibirCombinacoes() {
        System.out.println(restricoes);
    }

    public void exibirDesperdicios() {
        System.out.println(desperdicios);
    }

    public void exibirResultadoRestricoes() {
        System.out.println(restricoesLista());
    }



    public void exibirArquivo() {
        System.out.println("-----------------------");

        System.out.println("Arquivo lido: " + nomeArquivo);

        System.out.println(comprimentoBarra);
        System.out.println(nTipos);

        for (int i = 0; i < nTipos; i++) {
            System.out.print(tamanhos[i] + " ");
        }

        System.out.println();

        for (int i = 0; i < nTipos; i++) {
            System.out.print(quantidades[i] + " ");
        }

        System.out.println();

        System.out.println("-----------------------");
    }

    public ArrayList restricoesLista() {

        ArrayList<ArrayList<Integer>> lista = new ArrayList<>();
        int[] numeros = new int[tamanhos.length];

        for (int i = 0; i < tamanhos.length; i++) {
            numeros[i] = tamanhos[i];
        }
        Arrays.sort(numeros);

        for (int i = 0; i < restricoes.size(); i++) {
            Map<Integer, Integer> contagem = new HashMap<>();

            for (int j = 0; j < restricoes.get(i).size(); j++) {
                contagem.put(restricoes.get(i).get(j), contagem.getOrDefault(restricoes.get(i).get(j), 0) + 1);
                for (int k = 0; k < numeros.length; k++) {
                    if (!contagem.containsKey(numeros[k])) {
                        contagem.put(numeros[k], 0);
                    }
                }
                contagem.put(-1,desperdicios.get(i));

                Map<Integer, Integer> mapOrdenado = new TreeMap<>(contagem);
                ArrayList<Integer> listaDeChaves = new ArrayList<>(mapOrdenado.keySet());
                ArrayList<Integer> listaDeValores = new ArrayList<>();

                if (!lista.contains(contagem) && j == restricoes.get(i).size()-1) {
                    for (int k = 0; k < mapOrdenado.size(); k++) {
                        listaDeValores.add(mapOrdenado.get(listaDeChaves.get(k)));
                    }
                    lista.add(listaDeValores);
                }
            }
        }
        return lista;

    }

    private static ArrayList<ArrayList<Integer>> encontrarCombinacoes(int[] numeros, int alvo) {
        ArrayList<ArrayList<Integer>> resultado = new ArrayList<>();
        ArrayList<Integer> combinacaoAtual = new ArrayList<>();
        int indiceAtual = 0;

        encontrarCombinacoesHelper(numeros, alvo, indiceAtual, combinacaoAtual, resultado);

        return resultado;
    }

    private static void encontrarCombinacoesHelper(int[] numeros, int alvo, int indiceAtual,
                                                   List<Integer> combinacaoAtual, ArrayList<ArrayList<Integer>> resultado) {
        if (alvo >= 0) {
            // Se o alvo for maior ou igual a 0, adiciona a combinacao atual ao resultado
            resultado.add(new ArrayList<>(combinacaoAtual));
        } else {
            // Se o alvo for menor que 0, retorna sem adicionar a combinacao
            return;
        }

        for (int i = indiceAtual; i < numeros.length; i++) {
            int currentNumber = numeros[i];

            if (alvo - currentNumber >= 0) {
                // Adiciona o numero atual a combinacao
                combinacaoAtual.add(currentNumber);

                // Chama recursivamente a funcao para encontrar combinacoes com o restante do alvo
                encontrarCombinacoesHelper(numeros, alvo - currentNumber, i, combinacaoAtual, resultado);

                // Remove o numero atual da combinacao para tentar outras combinacoes
                combinacaoAtual.remove(combinacaoAtual.size() - 1);
            }
        }
    }

    private static ArrayList<ArrayList<Integer>> encontrarCombinacoesValidas(int[] numeros, int alvo) {
        ArrayList<ArrayList<Integer>> combinacoesEncontradas = encontrarCombinacoes(numeros, alvo);
        ArrayList<ArrayList<Integer>> combinacoesValidas = new ArrayList<>();

        int menorNumero = Arrays.stream(numeros).min().getAsInt();

        for (int i = 0; i < combinacoesEncontradas.size(); i++) {
            int somador = 0;
            for (int j = 0; j < combinacoesEncontradas.get(i).size(); j++) {
                somador += combinacoesEncontradas.get(i).get(j);
            }
            if (alvo - somador < menorNumero) {
                combinacoesValidas.add(combinacoesEncontradas.get(i));
            }
        }
        return combinacoesValidas;
    }
}