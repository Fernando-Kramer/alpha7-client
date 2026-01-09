package br.com.alpha7.client.infrastructure.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado para filtros de pesquisa de livros na aplicação cliente Alpha7.
 *
 * <p>
 * Esta classe encapsula os critérios de busca que podem ser enviados ao
 * servidor para localizar livros, incluindo id, ISBN, título, autor,
 * editora e data de publicação.
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookToSearchDTO {

	/** Identificador do livro para busca exata. */
	private Long id;
	
	/** ISBN do livro para busca exata ou parcial. */
	private String isbn;
	
	/** Título do livro para busca parcial ou exata. */
	private String title;
	
	/** Nome do autor do livro para busca parcial ou exata. */
	private String author;
	
	/** Nome da editora do livro para busca parcial ou exata. */
	private String publisher;
	
	/** Data de publicação do livro para filtragem por data. */
	private LocalDate publicationDate;
	
}

