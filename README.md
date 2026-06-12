# POO-SPRINT-02: Motor de Regras - Sistema de Monitoramento e Priorização de Roçada

## Descrição Geral

Sistema inteligente de monitoramento e priorização de roçada de vegetação em rodovias. Este projeto implementa um **motor de regras** (inteligência) capaz de analisar diferentes trechos de estrada e gerar automaticamente um **Relatório de Prioridade** que indica quais KMs necessitam de intervenção mecanizada ou manual.

### Objetivo 
Construir um sistema completo de monitoramento, priorização e execução de serviços de roçada de vegetação nas rodovias, utilizando princípios de Programação Orientada a Objetos (POO).

---

## Objetivos da Sprint 2

### 1. **Comportamentos Dinâmicos de Crescimento**
- Implementar diferentes padrões de crescimento de vegetação
- Exemplo: trechos úmidos crescem mais rapidamente que trechos secos
- Levar em consideração fatores ambientais e climáticos

### 2. **Tipos de Intervenção Operacional**
- Diferentes estratégias de roçada 
- Algoritmo que avalia qual tipo de intervenção é mais apropriado
- Geração automática de relatórios de prioridade

### 3. **Entrega Principal**
**Algoritmo de Varredura**: Um sistema que:
- Varre um array de trechos de rodovia
- Analisa condições de cada trecho
- Gera um **"Relatório Geral"** automático
- Indica quais KMs precisam de roçada mecanizada ou manual
- Prioriza intervenções conforme altura da vegetação, umidade e disponibilidade de sensor IoT

---

## Arquitetura e Conceitos

### Classes Abstratas

#### `IntervencaoOperacional` (Classe Abstrata)
**Objetivo de Aprendizagem**: Analisar abstrações puras e modelos de base

**Responsabilidades**:
- Definir o contrato para todas as intervenções operacionais
- Método abstrato: `executarServico()`
- Base genérica para especializações

**Filhas Implementadoras**:
- `RocadaMecanizada`: Roçada realizada por máquinas
- `Pulverizacao`: Pulverização de herbicidas

---

### Interfaces

#### `MonitoravelViaIoT` (Interface)
**Objetivo de Aprendizagem**: Analisar contratos de comportamento desacoplados de hierarquia

**Responsabilidades**:
- Definir comportamento de monitoramento via IoT
- Método: `transmitirDadosSensor()`
- Permitir atualização automática sem inspeção visual

**Aplicação**:
- Aplicada a `TrechoRodovia` com tecnologia instalada
- Permite captura automática de dados de crescimento e umidade
- Integração com sensores e dispositivos IoT

---

## Estrutura de Classes

```
IntervencaoOperacional (abstrata)
├── RocadaMecanizada
└── Pulverizacao

MonitoravelViaIoT (interface)
└── implementado por TrechoRodovia (com tecnologia)
└── implementado por SensorMock (para testes)

TrechoRodovia
├── km (int)
├── alturaVegetacao (double)
├── umidade (double)
├── possuiSensorIoT (boolean)
├── calcularPrioridade()
└── recomendarIntervencao()

SensorMock (para testes - implementa MonitoravelViaIoT)
├── alturaVegetacao (double)
├── umidade (double)
├── transmitirDadosSensor()
├── classificarCrescimento()
└── simularEfeitoUmidade()

MotorPriorizacao
├── analisarTrechos(ArrayList<TrechoRodovia>)
├── calcularCrescimento(TrechoRodovia)
└── gerarRelatorio()
```

---

## Algoritmo de Priorização

O motor de regras implementa a seguinte lógica:

### Classificação de Prioridade da Altura da Grama

A prioridade é determinada pela altura da vegetação:
  
- **ALTO**: Altura da vegetação > 1,5 metro
  - Intervenção necessária em curto prazo
  - Vigilância recomendada
  
- **MÉDIO**: Altura da vegetação > 1 metro 
  - Intervenção planejada
  - Monitoramento periódico
  
- **BAIXO**: Altura da vegetação < 1 metro
  - Sem urgência de intervenção
  - Monitoramento contínuo
 
### Classificação de Prioridade da Altura da Umidade
A prioridade é determinada pela nivel da umidade:

- **ALTO**: umidade > 70 - possível crescimento rápido
  - Intervenção necessária em curto prazo
  - Vigilância recomendada
  
- **MÉDIO**: umidade >= 40 - possível crescimento um pouco rápido
  - Intervenção planejada
  - Monitoramento periódico
  
- **BAIXO**: umidade < 39 - sem problemas com a umidade
  - Sem urgência de intervenção
  - Monitoramento contínuo

---

## Como Executar

### Pré-requisitos

- Java Development Kit (JDK) versão 8 ou superior
- Compilador javac
- Terminal ou prompt de comando

1. **Clone este repositório:**
    ```bash
    https://github.com/mig-2505/GS-Poo-Plataforma-de-Monitoramento-Espacial
    ```

2. **Abra o projeto no IntelliJ IDEA.**

3. **Compile e execute o arquivo principal:**
    - Basta executar o arquivo `SistemaMonitoramento.java`.

### 4. Exemplo de Uso

```java
// Criar trechos de rodovia
// Construtor: TrechoRodovia(int km, double alturaVegetacao, double umidade, boolean possuiSensorIoT)
TrechoRodovia trecho1 = new TrechoRodovia(10, 2.0, 80, true);
TrechoRodovia trecho2 = new TrechoRodovia(20, 1.2, 60, false);
TrechoRodovia trecho3 = new TrechoRodovia(30, 0.8, 30, true);

// Adicionar a uma lista
ArrayList<TrechoRodovia> trechos = new ArrayList<>();
trechos.add(trecho1);
trechos.add(trecho2);
trechos.add(trecho3);

// Criar motor de priorização
MotorPriorizacao motorPriorizacao = new MotorPriorizacao();

// Gerar relatório geral
motorPriorizacao.analisarTrechos(trechos);

// Analisar trecho específico
motorPriorizacao.analisarTrechos(new ArrayList<>(List.of(trecho1)));

// Calcular crescimento para um trecho
motorPriorizacao.calcularCrescimento(trecho2);
```

### 5. Menu do Sistema

O sistema oferece as seguintes opções:

```
==== MENU ====
1. Gerar Relatorio Geral
2. Gerar Relatorio Umidade
3. Analisar Trecho por KM
4. Testar Sensor Mock 
0. Encerrar Sistema
```

#### Opção 4: Teste com Sensor Mock

Esta opção permite testar o funcionamento da interface `MonitoravelViaIoT` usando um objeto Mock:

```java
// APENAS PARA TESTES - Simula um sensor IoT
SensorMock mock = new SensorMock(1.8, 75);
mock.transmitirDadosSensor();
System.out.println(mock.classificarCrescimento());
System.out.println(mock.simularEfeitoUmidade());
```

O `SensorMock` implementa a interface `MonitoravelViaIoT` e permite:
- Simular transmissão de dados de um sensor
- Classificar o nível de crescimento
- Simular efeitos da umidade no crescimento

---

## Conceitos de Aprendizagem

### Classes Abstratas
- Definem blueprints para subclasses
- Não podem ser instanciadas diretamente
- Forçam implementação de métodos abstratos
- Promovem reutilização de código
- Garantem que todas as subclasses implementem comportamentos essenciais

### Interfaces
- Definem contratos de comportamento
- Permitem múltipla implementação
- Desacoplam hierarquia de classes
- Facilitam testes com Mock
- Permitem que objetos diferentes façam as mesmas coisas sem herança

### Mock Objects
- Objetos simulados para testes
- Implementam interfaces sem precisar de implementação real
- Permitem testar comportamentos sem dependências externas
- Essenciais para teste unitário de componentes que dependem de sensores IoT

---

## Perguntas

### Pergunta 1
**Pergunta de Reflexão**: 
Por que não faz sentido para a Motiva que uma equipe execute apenas uma "Intervenção Operacional" genérica sem especificar qual é?

**Resposta**: Porque cada tipo de intervenção requer equipamentos, protocolos de segurança, recursos humanos e materiais específicos. A abstração força a especialização necessária.

### Pergunta 2
Qual a diferença arquitetural entre fazer um Trecho herdar de uma classe abstrata vs. implementar uma Interface?

**Resposta**: 
- **Herança**: Estabelece relação "é um". Um Trecho É uma subclasse especializada. Compartilha estado e comportamento.
- **Interface**: Estabelece contrato "pode fazer". Um Trecho PODE ser monitorado via IoT. Fornece capacidade sem dependência hierárquica. Um objeto pode implementar múltiplas interfaces, mas herdar de apenas uma classe.

## Exemplo de Saída

```
=== RELATORIO GERAL ===
10km - ALERTA: GRAMA ALTA
Sensor IoT ativo no KM 10. Altura atual da vegetação: 2.0m.
ALERTA: Possivel crecimento mais rapido da grama
Executando roçada mecanica

20km - ALERTA: GRAMA MEDIA
Não possui IoT.

30km - ALERTA: GRAMA BAIXA
Sensor IoT ativo no KM 30. Altura atual da vegetação: 0.8m.
Umidade normal - Sem alertas
Executando pulverização
```

---

## Integrantes

**Miguel Vanucci Delgado** - RM: 563491
**João Vitor** - RM: 566541
