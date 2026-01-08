package br.com.alpha7.client.infrastructure.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa o resultado de uma importação de livros a partir de um arquivo CSV
 * na aplicação cliente Alpha7.
 *
 * <p>
 * Esta classe encapsula duas listas:
 * <ul>
 *   <li>{@link BookDTO} – livros que foram importados com sucesso;</li>
 *   <li>{@link ImportErrorDTO} – erros ocorridos durante a importação.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Anotações Lombok são utilizadas para gerar automaticamente:
 * <ul>
 *   <li>Getters e setters (@Data)</li>
 *   <li>Construtor sem argumentos (@NoArgsConstructor)</li>
 *   <li>Construtor com todos os argumentos (@AllArgsConstructor)</li>
 * </ul>
 * </p>
 * 
 * <p>
 * A lista de erros é inicializada automaticamente como vazia para evitar {@code NullPointerException}.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportReportDTO {

	/** Lista de livros que foram importados com sucesso. */
	private List<BookDTO> books;
	
	/** Lista de erros ocorridos durante a importação. */
	private List<ImportErrorDTO> errors = new ArrayList<>();
	
}