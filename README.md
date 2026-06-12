# POO-SPRINT-02: Motor de Regras - Sistema de Monitoramento e Priorização de Roçada

## Descrição Geral

Sistema inteligente de monitoramento e priorização de roçada de vegetação em rodovias. Este projeto implementa um **motor de regras** (inteligência) capaz de analisar diferentes trechos de estrada e gerar automaticamente um **Relatório de Prioridade** que indica quais KMs necessitam de intervenção mecanizada ou manual.

### Objetivo Macro
Construir um sistema completo de monitoramento, priorização e execução de serviços de roçada de vegetação nas rodovias, utilizando princípios de Programação Orientada a Objetos (POO).

---

## Objetivos da Sprint 2

### 1. **Comportamentos Dinâmicos de Crescimento**
- Implementar diferentes padrões de crescimento de vegetação
- Exemplo: trechos úmidos crescem mais rapidamente que trechos secos
- Levar em consideração fatores ambientais e climáticos

### 2. **Tipos de Intervenção Operacional**
- Diferentes estratégias de roçada (mecanizada vs. manual)
- Algoritmo que avalia qual tipo de intervenção é mais apropriado
- Geração automática de relatórios de prioridade

### 3. **Entrega Principal**
**Algoritmo de Varredura**: Um sistema que:
- Varre um array de trechos de rodovia
- Analisa condições de cada trecho
- Gera um **"Relatório de Prioridade"** automático
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

**Princípios de Clean Code**:
- Nomes de classes abstratas representam conceitos genéricos
- Impossibilidade de instanciação direta da classe base
- Interface clara e bem definida

**Pergunta de Reflexão**: 
> Por que não faz sentido para a Motiva que uma equipe execute apenas uma "Intervenção Operacional" genérica sem especificar qual é?

**Resposta**: Porque cada tipo de intervenção requer equipamentos, protocolos de segurança, recursos humanos e materiais específicos. A abstração força a especialização necessária.

**Teste Unitário Sugerido**:
```java
@Test
public void testImpossibilidadeInstanciacao() {
    assertThrows(Exception.class, () -> new IntervencaoOperacional());
}

@Test
public void testRocadaMecanizadaExecuta() {
    RocadaMecanizada rocada = new RocadaMecanizada();
    assertDoesNotThrow(() -> rocada.executarServico());
}

@Test
public void testPulverizacaoExecuta() {
    Pulverizacao pulverizacao = new Pulverizacao();
    assertDoesNotThrow(() -> pulverizacao.executarServico());
}
```

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

**Princípios de Clean Code**:
- Interface Segregation Principle (ISP)
- Manter interfaces enxutas e focadas
- Responsabilidade única e clara

**Pergunta de Reflexão**: 
> Qual a diferença arquitetural entre fazer um Trecho herdar de uma classe abstrata vs. implementar uma Interface?

**Resposta**: 
- **Herança**: Estabelece relação "é um". Um Trecho É uma subclasse especializada. Compartilha estado e comportamento.
- **Interface**: Estabelece contrato "pode fazer". Um Trecho PODE ser monitorado via IoT. Fornece capacidade sem dependência hierárquica. Um objeto pode implementar múltiplas interfaces, mas herdar de apenas uma classe.

**Teste Unitário Sugerido**:
```java
@Test
public void testMonitoravelViaIoT() {
    SensorMock mock = new SensorMock(1.8, 75);
    mock.transmitirDadosSensor();
    DadosSensor dados = mock.obterDados();
    
    assertNotNull(dados);
    assertEquals(1.8, dados.getAlturaVegetacao(), 0.01);
    assertEquals(75, dados.getUmidade());
}

@Test
public void testClassificacaoCrescimento() {
    SensorMock mock = new SensorMock(1.8, 75);
    String classificacao = mock.classificarCrescimento();
    
    assertTrue(classificacao.equals("CRÍTICO") || 
               classificacao.equals("ALTO") ||
               classificacao.equals("MÉDIO") ||
               classificacao.equals("BAIXO"));
}

@Test
public void testEfeitoUmidade() {
    SensorMock mock = new SensorMock(1.5, 80);
    String efeito = mock.simularEfeitoUmidade();
    
    assertNotNull(efeito);
    assertTrue(efeito.contains("umidade"));
}
```

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

### Classificação de Prioridade

A prioridade é determinada pela altura da vegetação e nível de umidade:

- **CRÍTICO**: Altura da vegetação > 80cm + Umidade > 70%
  - Intervenção imediata necessária
  - Risco alto de obstrução de via
  
- **ALTO**: Altura da vegetação entre 60cm e 80cm + Umidade entre 50% e 70%
  - Intervenção necessária em curto prazo
  - Vigilância recomendada
  
- **MÉDIO**: Altura da vegetação entre 40cm e 60cm + Umidade entre 30% e 50%
  - Intervenção planejada
  - Monitoramento periódico
  
- **BAIXO**: Altura da vegetação ≤ 40cm ou Umidade ≤ 30%
  - Sem urgência de intervenção
  - Monitoramento contínuo

### Algoritmo de Análise

1. **Leitura de Dados**
   - Se possui sensor IoT: transmitir dados automáticos
   - Se sem sensor: usar valores predefinidos ou inspecionar manualmente

2. **Cálculo de Crescimento**
   - Multiplicador de umidade: umidade alta = crescimento acelerado
   - Trechos úmidos (umidade > 60%): crescimento 1.5x mais rápido
   - Trechos secos (umidade ≤ 60%): crescimento normal

3. **Definição de Tipo de Intervenção**
   - **Roçada Mecanizada**: Para trechos críticos com altura > 80cm
   - **Pulverização**: Para trechos de médio a baixo risco com necessidade de controle químico

4. **Geração de Relatório**
   - Documento estruturado com recomendações ordenadas por prioridade
   - Indicação de qual KM necessita de qual tipo de intervenção

---

## Como Usar

### 1. Clonar o Repositório
```bash
git clone https://github.com/mig-2505/POO-SPRINT-02.git
cd POO-SPRINT-02
```

### 2. Compilar o Projeto
```bash
javac *.java
```

### 3. Executar o Sistema
```bash
java Main
```

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
4. Testar Sensor Mock (Apenas para testes)
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

## Exemplo de Saída

```
=== RELATORIO GERAL ===

KM: 10
Altura da Vegetação: 2.0m
Umidade: 80.0%
Possui Sensor IoT: Sim
Prioridade: CRÍTICO
Intervenção Recomendada: Roçada Mecanizada

KM: 20
Altura da Vegetação: 1.2m
Umidade: 60.0%
Possui Sensor IoT: Não
Prioridade: ALTO
Intervenção Recomendada: Roçada Mecanizada

KM: 30
Altura da Vegetação: 0.8m
Umidade: 30.0%
Possui Sensor IoT: Sim
Prioridade: BAIXO
Intervenção Recomendada: Monitoramento
```

---

## Autor

**mig-2505** - Estudante de Programação Orientada a Objetos

---

## Notas Importantes

- Todas as classes devem seguir convenções de nomenclatura Java
- Utilize nomes significativos que reflitam a responsabilidade de cada classe
- Implemente testes unitários para garantir qualidade do código
- O `SensorMock` é exclusivamente para fins de teste e demonstração
- Mantenha a documentação atualizada conforme evolui o projeto
- Reflita sobre as questões teóricas propostas em cada seção

---

**Status**: Sprint 2 - Desenvolvimento em Andamento

*Última atualização: 12 de Junho de 2026*
