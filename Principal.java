import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Principal {

    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();

        int[] estadoInicial = {7, 2, 4, 5, 0, 6, 8, 3, 1};
        int[] estadoObjetivo = {1, 2, 3, 4, 5, 6, 7, 8, 0};

        QuebraCabeca quebraCabeca = new QuebraCabeca(estadoInicial, null);

        HashSet<String> fechados = new HashSet<>();
        //Queue<QuebraCabeca> abertos = new LinkedList<>();
        PriorityQueue<QuebraCabeca> abertos = new PriorityQueue<>(Comparator.comparingInt(
                estado -> estado.buscaCaminhoPercorrido().size() + estado.distancia(estadoObjetivo)
        ));
        abertos.add(quebraCabeca);

        while (!abertos.isEmpty()) {
            QuebraCabeca estadoAtual = abertos.poll();
            fechados.add(Arrays.toString(estadoAtual.estado()));

            if (estadoAtual.igual(new QuebraCabeca(estadoObjetivo, null))) {
                System.out.printf("Solução encontrada após analisar %d possibilidades.\n\n", fechados.size());

                System.out.println("Caminho de sucesso:");
                for (QuebraCabeca item : estadoAtual.buscaCaminhoPercorrido().reversed()) {
                    System.out.println(Arrays.toString(item.estado()));
                }
                System.out.printf("\nEstados no caminho de sucesso: %d\n", estadoAtual.buscaCaminhoPercorrido().size());
                break;
            }

            for (QuebraCabeca proximo : estadoAtual.estadosPossiveis()) {
                if (!fechados.contains(Arrays.toString(proximo.estado()))) {
                    abertos.add(proximo);
                }
            }

            if (abertos.isEmpty()) {
                System.out.println("O estado inicial informado não tem solução possível!");
            }
        }

        long fim = System.currentTimeMillis();
        long tempoDecorrido = fim - inicio;
        System.out.println("\nTempo decorrido: " + tempoDecorrido + " milissegundos");

        // Medição de memória e processamento ao fim do programa
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024 * 1024;
        System.out.println("Memória usada (aproximadamente): " + (runtime.totalMemory() - runtime.freeMemory()) / mb + " MB");

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        System.out.println("Uso de heap: " + heapMemoryUsage.getUsed() / mb + " MB");
    }
}
