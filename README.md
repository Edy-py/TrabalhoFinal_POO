# 🎮 Sistema de Gerenciamento de Locadora de Jogos

## 📖 Sobre o Projeto
Este é um sistema de **desktop** para gerenciamento de uma locadora de jogos, desenvolvido como projeto final da disciplina de **Programação Orientada a Objetos**.  

A aplicação permite realizar operações essenciais como o **cadastro e gerenciamento de clientes e jogos**, além de controlar o processo de **locação e devolução**.  

A interface gráfica foi construída utilizando **Java Swing** com o auxílio do GUI Designer da IDE **IntelliJ IDEA**, e a persistência dos dados é garantida por um **banco de dados PostgreSQL**, hospedado na plataforma **Supabase**.

---

## ✨ Funcionalidades
O sistema oferece uma interface centralizada com quatro módulos principais:

### 👤 Clientes
- Cadastro, edição e visualização de clientes.  
- Busca de clientes por CPF.  
- Filtro de clientes por status (Ativo, Inativo, Pendente).  

### 🎮 Jogos
- Cadastro e exclusão de jogos no acervo.  
- Visualização do catálogo completo.  
- Filtro de jogos por console.  

### 📦 Locar
- Realização de novas locações, associando um cliente a um jogo.  
- Cálculo dinâmico do preço total com base nos dias de aluguel.  

### 📥 Receber
- Visualização de todas as locações ativas e pendentes.  
- Finalização de locações com cálculo de multas por atraso.  
- Atualização automática do status do cliente e da disponibilidade do jogo.  

### 🐞 Funcionalidade de Debug
- **Avançar Dia:** um botão especial na tela principal que simula a passagem do tempo, atualizando o status das locações para "Devendo" caso o prazo de entrega tenha sido ultrapassado.

---

## 🛠️ Tecnologias Utilizadas
- **Linguagem:** Java 17+  
- **Interface Gráfica:** Java Swing  
- **Banco de Dados:** PostgreSQL (Supabase)  
- **IDE:** IntelliJ IDEA  
- **Build Tool:** Maven  
- **Driver de Conexão:** JDBC Driver for PostgreSQL  

---

## ⚙️ Configuração do Ambiente

### ✅ Pré-requisitos
- **JDK** versão 17 ou superior.  
- **Apache Maven** configurado nas variáveis de ambiente.  
- Uma conta no **Supabase** com um projeto PostgreSQL criado.  

### 🚀 Passos para Configuração

**Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
