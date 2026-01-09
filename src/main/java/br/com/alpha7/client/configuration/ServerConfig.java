package br.com.alpha7.client.configuration;

/**
 * Configurações do cliente da aplicação Alpha7.
 *
 * <p>
 * Esta classe centraliza as informações de conexão com o servidor,
 * permitindo fácil manutenção da URL base utilizada nas requisições da
 * aplicação cliente.
 * </p>
 *
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class ServerConfig {
	
    /**
     * URL base do servidor da aplicação Alpha7.
     *
     * <p>
     * Esta constante define o endpoint principal da API que será consumida
     * pelo cliente. Caso o endereço do servidor seja alterado, basta atualizar
     * este valor.
     * </p>
     */
	public static final String BASE_URL = "http://localhost:8080/alpha7-server/api";

    /**
     * Construtor padrão.
     *
     * <p>
     * Não possui lógica adicional, sendo definido apenas para permitir
     * instanciações explícitas caso necessário no futuro.
     * </p>
     */
	public ServerConfig() {}

}
