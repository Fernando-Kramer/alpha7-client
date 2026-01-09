package br.com.alpha7.client.infrastructure.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa a estrutura de resposta de erro retornada pelo servidor
 * na aplicação cliente Alpha7.
 *
 * <p>
 * Esta classe é utilizada para encapsular informações de erros de requisições HTTP,
 * incluindo status, mensagem, caminho da requisição e timestamp.
 * </p>
 *
 * <p>
 * Anotações Lombok são utilizadas para gerar automaticamente:
 * <ul>
 *   <li>Getters e setters (@Data)</li>
 *   <li>Construtor sem argumentos (@NoArgsConstructor)</li>
 *   <li>Construtor com todos os argumentos (@AllArgsConstructor)</li>
 *   <li>Builder para construção fluente (@Builder)</li>
 * </ul>
 * </p>
 *
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDTO {

    /** Código de status HTTP do erro. */
	private Integer status;
	
	/** Tipo ou categoria do erro. */
	private String error;
	
	/** Mensagem descritiva do erro. */
	private String message;
	
	/** Caminho da requisição que gerou o erro. */
	private String path;
	
	/** Momento em que o erro ocorreu. */
	private LocalDateTime timestamp;
	
}

