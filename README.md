# Simulador de Filas G/G/c/K

Este projeto implementa um simulador de filas simples com distribuição geral de chegadas e atendimentos (G/G/c/K).

## Descrição

O simulador permite modelar sistemas de filas com:
- **G**: Distribuição geral (uniforme) para tempos entre chegadas
- **G**: Distribuição geral (uniforme) para tempos de atendimento
- **c**: Número de servidores
- **K**: Capacidade do sistema (fila + servidores)

## Requisitos

- Java 17 ou superior
- JDK para compilação

## Estrutura do Projeto

```
sma-simulador-g10/
├── Fila.java              # Classe principal da fila
├── Principal.java         # Classe principal para execução
└── README.md
```

## Compilação

```bash
javac *.java
```

## Execução

```bash
java Principal
```