package br.com.alpha7.client.infrastructure.exception;

import br.com.alpha7.client.infrastructure.dto.ErrorResponseDTO;

/**
 * Exceção personalizada que representa erros retornados pelo servidor
 * na aplicação cliente Alpha7.
 *
 * <p>
 * Esta exceção encapsula um {@link ErrorResponseDTO}, contendo detalhes
 * do erro ocorrido durante a comunicação com a API, como status HTTP,
 * mensagem, caminho e timestamp.
 * </p>
 *
 * <p>
 * A exceção estende {@link RuntimeException}, permitindo que seja lançada
 * sem a necessidade de declaração em {@code throws}.
 * </p>
 *
 * <p>
 * Utilizada principalmente em serviços HTTP que comunicam com o servidor
 * quando a resposta indica falha, para que o cliente possa tratar o erro
 * de forma uniforme.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @version 1.0.0
 * @since 1.0.0
 */
public class ServerException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
    /**
     * Objeto que encapsula detalhes do erro retornado pelo servidor.
     */
	private final ErrorResponseDTO errorResponse;

    /**
     * Cria uma nova instância da exceção com o {@link ErrorResponseDTO} fornecido.
     *
     * @param errorResponse detalhes do erro retornado pelo servidor
     */
    public ServerException(ErrorResponseDTO errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    /**
     * Retorna os detalhes do erro retornado pelo servidor.
     *
     * @return objeto {@link ErrorResponseDTO} com informações do erro
     */
    public ErrorResponseDTO getErrorResponse() {
        return errorResponse;
    }
}
