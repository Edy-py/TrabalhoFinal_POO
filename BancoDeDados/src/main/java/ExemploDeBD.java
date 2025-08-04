import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ExemploDeBD {

    private static final String URL = "jdbc:sqlite:BancosDeDados_projeto.db";

    public static void main(String[] args) {
        // O try-with-resources garante que a conexão será fechada automaticamente.
        try (Connection conn = DriverManager.getConnection(URL)) {
            System.out.println("Conexão com o banco de dados SQLite estabelecida!");

            // 1. Chama o método para criar a tabela (se não existir)
            GerenciadorBancoDados.criarTabela(conn);

            // 2. Chama os métodos para inserir dados
            System.out.println("\n--- INSERINDO DADOS ---");
            GerenciadorBancoDados.inserirDados(conn, "Carlos Andrade", 42);
            GerenciadorBancoDados.inserirDados(conn, "Fernanda Lima", 31);
            GerenciadorBancoDados.inserirDados(conn, "Ricardo Souza", 28);

            // 3. Consulta os dados iniciais
            System.out.println("\n--- DADOS ATUAIS NA TABELA ---");
            GerenciadorBancoDados.consultarDados(conn);

            // 4. Atualiza um dos registros
            System.out.println("\n--- ATUALIZANDO DADOS ---");
            GerenciadorBancoDados.atualizarDados(conn, 2, "Fernanda Lima Costa", 32);

            // 5. Consulta novamente para ver a atualização
            System.out.println("\n--- DADOS APÓS ATUALIZAÇÃO ---");
            GerenciadorBancoDados.consultarDados(conn);

            // 6. Deleta um dos registros
            System.out.println("\n--- DELETANDO DADOS ---");
            GerenciadorBancoDados.deletarDados(conn, 3);

            // 7. Consulta uma última vez para ver o resultado final
            System.out.println("\n--- DADOS FINAIS NA TABELA ---");
            GerenciadorBancoDados.consultarDados(conn);

        } catch (SQLException e) {
            System.err.println("Ocorreu um erro de SQL: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\nOperações finalizadas. A conexão foi fechada com sucesso.");
    }

}
