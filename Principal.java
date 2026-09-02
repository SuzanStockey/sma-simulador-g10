public class Principal {
    public static void main(String[] args) {
        System.out.println("1. G/G/1/5, chegadas entre 2...5, atendimento entre 3...5:");
        simular(1, 5, 2.0, 5.0, 3.0, 5.0, 3.0, 1);
        
        System.out.println("\n2. G/G/2/5, chegadas entre 2...5, atendimento entre 3...5:");
        simular(2, 5, 2.0, 5.0, 3.0, 5.0, 3.0, 1);
    }

    public static void simular(int servidores, int capacidade, double minArrival, double maxArrival,
            double minService, double maxService, double tempoInicial, long seed) {
        Fila fila = new Fila(servidores, capacidade, minArrival, maxArrival, minService, maxService, seed);
        
        double tempoAtual = tempoInicial;
        double proximaChegada = tempoAtual;
        int maxAleatorios = 100000;
        
        fila.atualizarTempoEstado(tempoAtual);
        
        while (fila.getContadorAleatorios() < maxAleatorios) {
            int servidor = fila.proximoServidorFinalizando();
            double proximaSaida = (servidor >= 0) ? fila.nextServiceEnd[servidor] : Double.POSITIVE_INFINITY;
            
            if (proximaChegada <= proximaSaida) {
                tempoAtual = proximaChegada;
                fila.atualizarTempoEstado(tempoAtual);
                
                if (fila.podeEntrar()) {
                    fila.in();
                    if (fila.temServidorDisponivel()) {
                        double duracao = fila.gerarServico();
                        fila.iniciarServico(tempoAtual, duracao);
                    }
                } else {
                    fila.perder();
                }
                
                if (fila.getContadorAleatorios() < maxAleatorios) {
                    proximaChegada = tempoAtual + fila.gerarChegada();
                }
            } else {
                tempoAtual = proximaSaida;
                fila.atualizarTempoEstado(tempoAtual);
                fila.finalizarServico(servidor);
                
                if (fila.clientes > fila.ocupados && fila.temServidorDisponivel()) {
                    double duracao = fila.gerarServico();
                    fila.iniciarServico(tempoAtual, duracao);
                }
            }
        }
        
        fila.atualizarTempoEstado(tempoAtual);
        imprimirRelatorio(fila, servidores, capacidade);
    }

    public static void imprimirRelatorio(Fila fila, int servidores, int capacidade) {
        double tempoTotal = 0.0;
        for (double t : fila.getTempos()) {
            tempoTotal += t;
        }
        
        System.out.println("Tempo global da simulação: " + String.format("%.4f", tempoTotal));
        System.out.println("Número de perdas de clientes: " + fila.getPerdas());
        System.out.println("Distribuição de probabilidades:");
        
        for (int i = 0; i < fila.getTempos().length; i++) {
            if (fila.getTempos()[i] > 0) {
                double prob = fila.getTempos()[i] / tempoTotal;
                System.out.println("P(" + i + ") = " + String.format("%.6f", prob));
            }
        }
        
        System.out.println("Tempos acumulados para os estados da fila:");
        for (int i = 0; i < fila.getTempos().length; i++) {
            if (fila.getTempos()[i] > 0) {
                System.out.println("Estado " + i + ": " + String.format("%.4f", fila.getTempos()[i]));
            }
        }
    }
}
