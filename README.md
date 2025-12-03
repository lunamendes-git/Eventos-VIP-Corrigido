# Trabalho P2 - Sistema Eventos VIP

Sistema interativo desenvolvido em Java para gerenciamento completo de eventos da empresa "Eventos VIP". O sistema simula um ambiente real de buffet, permitindo agendamento de eventos, controle de mesas, garçons, pedidos e fechamento de contas com regras de negócio específicas.

## Grupo
- Luna Parentes Fortes Mendes
- Lígia Vitória Araújo dos Santos

## Matéria
Programação Orientada a Objetos - Professor Samuel

## Funcionalidades Principais

**Gestão de Eventos e Agenda**
- `Criação de Eventos:` Cadastro com Nome, Dono/Pagante e seleção de Tema.
- `Agenda Inteligente:` Controle de dias (Terça, Quinta, Sábado) e horários (09:00, 14:00, 20:00). O sistema remove horários já ocupados.
- `Temas Pré-definidos:` Seleção entre Party, Casual e Exclusivo, cada um com seu cardápio próprio.

**Gestão de Mesas e Atendimento**
- `Validação de ID:` O número da mesa deve obrigatoriamente começar com o ID do evento (Ex: Evento 1 -> Mesa 10, 11...).
- `Controle de Ocupação:` Limite de 8 convidados por mesa.
- `Garçons:` Atribuição de garçons a mesas (limitado a 2 mesas por garçom).

**Clientes e Pedidos**
- `Convidados:` Cadastro de VIPs e Regulares.
- `Desconto VIP:` Aplicação automática de 10% de desconto na conta da mesa caso haja pelo menos um VIP sentado.
- `Pedidos:` Validação de itens exclusivos (itens VIP só podem ser pedidos se houver VIP na mesa).

**Financeiro e Relatórios**
- `Conta da Mesa:` Exibição detalhada dos itens consumidos (com quantidade agrupada, ex: "Carne (x3)") e subtotal.
- `Conta do Evento:` Relatório consolidado somando todas as mesas para o pagante do evento.

## Estrutura do Projeto

O projeto utiliza **Dados Pré-definidos** (em memória) para simular um banco de dados de Temas, Cardápios e Garçons, garantindo que o sistema sempre inicie pronto para uso.

### Estrutura de Pastas
```text
src/
├── entidade/          # Classes de domínio (Evento, Mesa, Pedido, Tema, DadosPredefinidos...)
├── Desconto/          # Padrão Strategy para descontos (Interface e Implementações)
└── Main.java          # Classe principal com Interface de Console e Menus
```

### Classes Principais
- `Main`: Gerencia toda a interação com o usuário e fluxo do sistema.
- `DadosPredefinidos`: Armazena a "memória" do sistema (Temas fixos, Garçons, Agenda).
- `Evento`: Gerencia dados do evento, dono e horário.
- `Mesa`: Centraliza convidados, pedidos e cálculo da conta individual.
- `DescontoVIP`: Implementação da regra de 10% de desconto.

## Como Executar

1. **Pré-requisitos**
   - Java JDK 8 ou superior
   - Terminal/Command Prompt

2. **Baixar o projeto**
   - Faça download dos arquivo
   - Extraia em uma pasta

3. **Compilar** (pelo terminal):
   ```markdown
   javac -d bin src/entidade/*.java src/Desconto/*.java src/Main.java
   
4. **Executar**:
   ```markdown
   java -cp bin Main

## Menu Principal
**Ao executar `MenuConsole.java`, você terá acesso às opções:**
```markdown
- Gerenciar Eventos - Criar e listar eventos
- Gerenciar Mesas - Criar mesas e atribuir garçons
- Gerenciar Convidados - Cadastrar convidados VIP/Regular
- Gerenciar Pedidos - Fazer pedidos com validação VIP
- Relatórios - Gerar contas e relatórios de faturamento
- Salvar Dados - Persistir informações em arquivos
```

### O que mudou em relação ao antigo
1. **Remoção** de algumas classes desnecessárias.
2. **Adicão** de Dias e Horários para o cadastro do evento.
3. **Adição** da classe `DadosPredefinidos`
4. **Atualização** da estrutura de pastas e comandos de compilação.
