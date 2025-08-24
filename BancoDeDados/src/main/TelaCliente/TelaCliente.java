import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Esta classe representa a janela principal que exibe os clientes em uma tabela.
 * Ela estende JFrame para se tornar uma janela.
 */
public class TelaCliente extends JFrame {
    // Estas variáveis foram geradas pelo Swing Designer e conectam o código aos componentes visuais.
    private JPanel MainCliente;
    private JTable TabelaCliente;

    /**
     * Construtor da classe. É aqui que a janela é configurada.
     */
    public TelaCliente() {
        // 1. Configurações essenciais da janela
        setTitle("Cadastro de Clientes");
        setSize(900, 600); // Define um tamanho para a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Faz o programa fechar ao clicar no 'X'
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // 2. Define o JPanel 'MainCliente' (do designer) como o conteúdo principal da janela
        setContentPane(MainCliente);

        // 3. Chama o método para buscar os dados do banco e preencher a tabela
        carregarDados();
    }

    /**
     * Método responsável por conectar ao banco, buscar os dados e popular a JTable.
     */
    private void carregarDados() {
        try (Connection conn = ConexaoBanco.conectar()) {
            // Usa o método que criamos no GerenciadorBancoDados.
            // Ele já retorna os dados no formato que a JTable precisa (DefaultTableModel).
            // Lembre-se que renomeamos a tabela para "clientes" (minúsculo).
            DefaultTableModel model = GerenciadorBancoDados.buscarDadosParaTabela(conn, "clientes");

            // Define o modelo de dados na nossa JTable. É esta linha que preenche a tabela visualmente.
            TabelaCliente.setModel(model);
            System.out.println("Dados carregados com sucesso na tabela!");

        } catch (SQLException e) {
            // Se der um erro, exibe uma mensagem amigável para o usuário.
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar dados do banco de dados: " + e.getMessage(),
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace(); // Também imprime o erro detalhado no console para o desenvolvedor.
        }
    }

    /**
     * Ponto de entrada principal para iniciar esta tela.
     */
    public static void main(String[] args) {
        // O Swing precisa que a interface seja criada em uma thread específica (Event Dispatch Thread).
        // O SwingUtilities.invokeLater garante isso.
        SwingUtilities.invokeLater(() -> {
            TelaCliente tela = new TelaCliente();
            tela.setVisible(true); // Torna a janela visível
        });
    }
}
