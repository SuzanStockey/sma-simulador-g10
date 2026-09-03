/**
 * Gerador de números pseudoaleatórios pelo Método Congruente Linear (LCG).
 *
 * X(n+1) = (a * X(n) + c) mod M
 *
 * A cada chamada de nextRandom(), o número inteiro gerado é normalizado
 * para o intervalo [0, 1) e o último valor gerado é armazenado para a
 * próxima chamada, conforme pedido no texto multimodal do módulo.
 */
public class GeradorLCG {

    private final long a;
    private final long c;
    private final long m;
    private long ultimo;

    /**
     * @param seed semente inicial (X0) da sequência
     */
    public GeradorLCG(long seed) {
        // Parâmetros clássicos de LCG (Numerical Recipes), com bom período
        // e boa distribuição para M = 2^32.
        this.a = 1664525L;
        this.c = 1013904223L;
        this.m = 4294967296L; // 2^32
        this.ultimo = seed;
    }

    /**
     * Gera o próximo número pseudoaleatório normalizado entre 0 e 1,
     * atualizando o último número gerado da sequência.
     */
    public double nextRandom() {
        ultimo = (a * ultimo + c) % m;
        return (double) ultimo / (double) m;
    }
}