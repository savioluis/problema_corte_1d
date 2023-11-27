import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

public class Modelo {
    Data data;
    double infinity = Double.POSITIVE_INFINITY;

    public Modelo(Data data) {
        this.data = data;
    }

    public void solveModel() {

        System.out.print("Tamanhos: "); data.exibirTamanhos();
        System.out.print("Quantidades: "); data.exibirQuantidades();
        System.out.println();
        System.out.print("Tamanhos Ordenados: "); data.exibirTamanhosOrdenados();
        System.out.print("Quantidades Ordenadas Com Tamanhos: "); data.exibirQuantidadesOrdenadasComTamanhos();
        System.out.println();
        System.out.println(data.resultadoRestricoes);

        Loader.loadNativeLibraries();
        MPSolver solver = MPSolver.createSolver("SCIP");

        //CRIANDO AS VARIAVEIS DE DECISAO E COLOCANDO EM UM ARRAY
        MPVariable[] variaveis = new MPVariable[data.resultadoRestricoes.size()];

        for (int i = 0; i < data.resultadoRestricoes.size(); i++) {
            variaveis[i] = solver.makeIntVar(0, infinity, "X" + (i + 1) + " " + data.resultadoRestricoes.get(i));
        }

        //CRIANDO A FUNCAO OBJETIVO
        // 10x1 + 20x2 + 30x3 + 40x4 + 0x5
        MPObjective fObjetivo = solver.objective();
        for (int i = 0; i < data.desperdicios.size(); i++) {
            fObjetivo.setCoefficient(variaveis[i], data.desperdicios.get(i));
        }
        fObjetivo.setMinimization();

        //RESTRICOES DE TAMANHO
        // x2 + x4 + 3x5 >= 120
        // x1 + 2x3 + x4 >= 100
        // x1 + x2 >= 70
        // 50 -> 120
        // 60 -> 100
        // 80 -> 70

        for (int i = 0; i < data.quantidades.length; i++) {
            MPConstraint restricaoQuantidade = solver.makeConstraint(data.quantidadesOrdenadasComTamanhos[i], infinity, "Restricao " + (i + 1));

            for (int j = 0; j < data.resultadoRestricoes.size(); j++) {
                restricaoQuantidade.setCoefficient(variaveis[j], data.resultadoRestricoes.get(j).get(i+1));
            }
        }

        System.out.println("Numero de restricoes = " + solver.numConstraints());

        MPSolver.ResultStatus resultStatus = solver.solve();

        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {

            System.out.println("Custo da funcao objetivo = " + fObjetivo.value());
            System.out.println("Solucao:");
            for (int i = 0; i < data.resultadoRestricoes.size(); i++) {
                System.out.println("X" + (i + 1) + " " + data.resultadoRestricoes.get(i) + " " + variaveis[i].solutionValue());
            }
            System.out.println("Tempo de resolucao = " + solver.wallTime() + " milissegundos");
            System.out.println(solver.exportModelAsLpFormat());
        } else {
            System.out.println("Solucao otima nao encontrada!");
        }
    }
}
