package br.com.alpha7.client.infrastructure.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa um livro na aplicação cliente Alpha7.
 *
 * <p>
 * Esta classe é utilizada para transportar informações de livros
 * entre o cliente e o servidor, incluindo dados como ISBN, título,
 * autores, editoras e data de publicação.
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

	/** Identificador único do livro. */
	private Long id;
	
	/** Código ISBN do livro. */
	private String isbn;
	
	/** Título do livro. */
	private String title;
	
	/** Lista de autores associados ao livro. */
	private List<AuthorDTO> authors;
	
	/** Lista de editoras associadas ao livro. */
	private List<PublisherDTO> publishers;
	
	/** Data de publicação do livro. */
	private LocalDate publicationDate;
	
}
