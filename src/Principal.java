import java.io.FileNotFoundException;
import java.util.Arrays;

public class Principal {
    public static void main(String[] args) throws FileNotFoundException {
        String filename = "input.txt";
        Data data = new Data(filename);
        data.exibirArquivo();

//        data.exibirQuantidades();
//        data.exibirQuantidadesOrdenadasComTamanhos();
//        data.exibirDesperdicios();
//        data.exibirCombinacoes();
//        data.exibirResultadoRestricoes();

        Modelo modelo = new Modelo(data);
        modelo.solveModel();
    }
}
