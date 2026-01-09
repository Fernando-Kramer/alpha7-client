package br.com.alpha7.client.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa um autor na aplicação cliente Alpha7.
 *
 * <p>
 * Esta classe é utilizada para transportar informações de autores
 * entre o cliente e o servidor, incluindo identificador e nome.
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
public class AuthorDTO {

	/** Identificador único do autor. */
	private Long id;
	
	/** Nome completo do autor. */
	private String name;
	
}