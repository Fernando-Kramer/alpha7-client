package br.com.alpha7.client.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa um erro ocorrido durante a importação de dados
 * a partir de um arquivo CSV na aplicação cliente Alpha7.
 *
 * <p>
 * Cada instância desta classe encapsula detalhes do erro, incluindo o número
 * da linha do arquivo, o conteúdo da linha e a mensagem de erro associada.
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
public class ImportErrorDTO {

	/** Número da linha no arquivo CSV onde ocorreu o erro. */
	private int lineNumber;
	
	/** Conteúdo da linha que causou o erro. */
	private String lineContent;
	
	/** Mensagem descrevendo o erro ocorrido. */
	private String message;
	
}