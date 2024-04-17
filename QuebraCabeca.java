import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public record QuebraCabeca(int[] estado, QuebraCabeca pai) {

    public boolean igual(QuebraCabeca outroEstado) {
        return Arrays.equals(this.estado, outroEstado.estado);
    }

    public List<QuebraCabeca> estadosPossiveis() {
        int espacoVazio = IntStream.range(0, estado.length)
                .filter(i -> estado[i] == 0)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Espaço vazio (0) não encontrado!"));

        int linhas = 3;
        int colunas = 3;
        List<QuebraCabeca> proximos = new ArrayList<>();

        if (espacoVazio % colunas > 0) {
            proximos.add(new QuebraCabeca(movePeca(estado, espacoVazio, espacoVazio - 1), this));
        }

        if (espacoVazio % colunas < colunas - 1) {
            proximos.add(new QuebraCabeca(movePeca(estado, espacoVazio, espacoVazio + 1), this));
        }

        if (espacoVazio >= colunas) {
            proximos.add(new QuebraCabeca(movePeca(estado, espacoVazio, espacoVazio - colunas), this));
        }

        if (espacoVazio < (linhas - 1) * colunas) {
            proximos.add(new QuebraCabeca(movePeca(estado, espacoVazio, espacoVazio + colunas), this));
        }

        return proximos;
    }

    private int[] movePeca(int[] estado, int peca1, int peca2) {
        int[] novoEstado = estado.clone();
        novoEstado[peca1] = estado[peca2];
        novoEstado[peca2] = estado[peca1];
        return novoEstado;
    }

    public List<QuebraCabeca> buscaCaminhoPercorrido() {
        List<QuebraCabeca> caminhoPercorrido = new ArrayList<>();
        caminhoPercorrido.add(this);

        QuebraCabeca estadoPercorrido = this;
        while (!Objects.isNull(estadoPercorrido.pai)) {
            caminhoPercorrido.add(estadoPercorrido.pai);
            estadoPercorrido = estadoPercorrido.pai;
        }

        return caminhoPercorrido;
    }
}
