# Trabalho P2 - Sistema Eventos VIP

Sistema desenvolvido em Java para gerenciamento completo de eventos da empresa "Eventos VIP". Permite o controle de convidados, mesas, garçons, pedidos, pagamentos e geração de relatórios.

## Grupo
- Luna Parentes Fortes Mendes
- Lígia Vitória Araújo dos Santos

## Matéria
Programaçao Orientada a Objetos - Professor Samuel

## Funcionalidades Principais
**Cadastros e Configurações**
- `Eventos:` Criação com tema personalizado
- `Mesas:` Configuração com limite de 8 convidados e decoração VIP
- `Convidados:` Cadastro com tipos VIP (10% desconto) e Regular
- `Garçons:` Atribuição e controle de disponibilidade
- `Cardápio:` Itens com categorias e exclusividade VIP

**Operações do Sistema**
- `Pedidos:` Registro com validação de itens VIP
- `Atendimento:` Garçons por mesa (máximo 2 mesas/garçom)
- `Pagamentos:` Cálculo automático com descontos VIP
- `Relatórios:` Geração de contas e faturamento por evento
- `Persistência:` Salvamento em arquivos JSON

## Como Executar

1. **Pré-requisitos**
   - Java JDK 8 ou superior
   - Terminal/Command Prompt

2. **Baixar o projeto**
   - Faça download dos arquivo
   - Extraia em uma pasta

3. **Compilar** (pelo terminal):
   ```markdown
   javac -d bin src/entidade/.java src/Desconto/.java src/Main.java

4. **Executar**:
   ```markdown
   java -cp bin Main
   
## Classes Principais
- `Convidado` - controle de convidados VIP/Regular
- `Mesa` - gerencia mesas e convidados
- `Pedido` - controle de pedidos
- `Garcom` - atendimento das mesas
- `Evento` - configuração do evento

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

## Estrutura de Pastas
```markdown
src/
├── entidade/     - Classes do sistema
├── Desconto/     - Classes de desconto  
└── Main.java     - Programa principal

