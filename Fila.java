import java.util.Random;

public class Fila {
    int servidores;
    int capacidade;
    double minArrival, maxArrival;
    double minService, maxService;
    public int clientes;
    int ocupados;
    int perdas;
    double[] tempos;
    double[] nextServiceEnd;
    double ultimoTempo;
    Random random;
    int contadorAleatorios;

    public Fila(int servidores, int capacidade, double minArrival, double maxArrival, double minService,
            double maxService, long seed) {
        this.servidores = servidores;
        this.capacidade = capacidade; // K é capacidade total do sistema
        this.minArrival = minArrival;
        this.maxArrival = maxArrival;
        this.minService = minService;
        this.maxService = maxService;
        this.clientes = 0;
        this.ocupados = 0;
        this.perdas = 0;
        this.tempos = new double[capacidade + 1];
        this.nextServiceEnd = new double[servidores];
        for (int i = 0; i < servidores; i++)
            nextServiceEnd[i] = Double.POSITIVE_INFINITY;
        this.ultimoTempo = 0.0;
        this.random = new Random(seed);
        this.contadorAleatorios = 0;
    }

    public int getPerdas() {
        return perdas;
    }

    public double[] getTempos() {
        return tempos;
    }

    public double getUltimoTempo() {
        return ultimoTempo;
    }

    public boolean podeEntrar() {
        return clientes < capacidade;
    }

    public void in() {
        clientes++;
    }

    public void perder() {
        perdas++;
    }

    public boolean temServidorDisponivel() {
        return ocupados < servidores;
    }

    public void iniciarServico(double tempoAtual, double duracao) {
        for (int i = 0; i < servidores; i++) {
            if (nextServiceEnd[i] == Double.POSITIVE_INFINITY) {
                nextServiceEnd[i] = tempoAtual + duracao;
                ocupados++;
                break;
            }
        }
    }

    public int proximoServidorFinalizando() {
        double menor = Double.POSITIVE_INFINITY;
        int indice = -1;
        for (int i = 0; i < servidores; i++) {
            if (nextServiceEnd[i] < menor) {
                menor = nextServiceEnd[i];
                indice = i;
            }
        }
        return indice;
    }

    public void finalizarServico(int servidor) {
        clientes--;
        ocupados--;
        nextServiceEnd[servidor] = Double.POSITIVE_INFINITY;
    }

    public void atualizarTempoEstado(double tempoAtual) {
        int estado = clientes;
        double delta = tempoAtual - ultimoTempo;
        if (estado >= 0 && estado < tempos.length)
            tempos[estado] += delta;
        ultimoTempo = tempoAtual;
    }

    public double gerarChegada() {
        contadorAleatorios++;
        return minArrival + (maxArrival - minArrival) * random.nextDouble();
    }

    public double gerarServico() {
        contadorAleatorios++;
        return minService + (maxService - minService) * random.nextDouble();
    }

    public int getContadorAleatorios() {
        return contadorAleatorios;
    }
}
