package br.com.alpha7.client.infrastructure.dto;

import javax.swing.JTextField;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO que representa um erro de validação associado a um campo de formulário
 * na interface gráfica da aplicação cliente Alpha7.
 *
 * <p>
 * Cada instância desta classe encapsula o campo do formulário que apresentou
 * erro e a mensagem correspondente a ser exibida ao usuário.
 * </p>
 *
 * <p>
 * A classe é imutável:
 * <ul>
 *   <li>Os campos são finais</li>
 *   <li>Somente getters são gerados automaticamente (@Getter)</li>
 *   <li>Construtor com todos os argumentos é gerado (@AllArgsConstructor)</li>
 * </ul>
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public class FieldErrorDTO {
	
	/** Campo do formulário que apresentou erro. */
	private final JTextField field;
	
	/** Mensagem de erro a ser exibida relacionada ao campo. */
	private final String message;

}
