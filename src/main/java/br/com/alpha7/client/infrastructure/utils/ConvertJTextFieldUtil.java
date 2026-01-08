package br.com.alpha7.client.infrastructure.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JTextField;

import br.com.alpha7.client.infrastructure.dto.FieldErrorDTO;

/**
 * Classe utilitária para conversão e validação de valores de {@link JTextField}
 * em tipos específicos na aplicação cliente Alpha7.
 *
 * <p>
 * Os métodos desta classe permitem converter o conteúdo de campos de formulário
 * em {@link Long}, {@link LocalDate}, {@link String} ou ISBN, adicionando
 * instâncias de {@link FieldErrorDTO} em caso de erro de validação.
 * </p>
 *
 * <p>
 * Esta classe é composta apenas por métodos estáticos e não deve ser instanciada.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class ConvertJTextFieldUtil {

    /**
     * Converte o valor do {@link JTextField} em {@link Long}.
     *
     * <p>
     * Se o campo estiver vazio, retorna {@code null}. Em caso de valor inválido,
     * adiciona um {@link FieldErrorDTO} à lista de erros e retorna {@code null}.
     * </p>
     *
     * @param field campo de texto a ser convertido
     * @param errors lista de erros de validação
     * @return valor convertido em {@link Long} ou {@code null} se inválido ou vazio
     */
	public static Long toLong(JTextField field, List<FieldErrorDTO> errors) {
		
		String value = field.getText().trim();
		
		if(value.isEmpty())
			return null;
		
		try {
			
			return Long.valueOf(value);
			
		} catch (NumberFormatException e) {
			
			errors.add(new FieldErrorDTO(field, 
					String.format("O valor [%s] é inválido para o campo [%s].", 
							value, field.getName())));
	        return null;
		}
		
	}
	
    /**
     * Converte o valor do {@link JTextField} em {@link LocalDate} usando o {@link DateTimeFormatter} fornecido.
     *
     * <p>
     * Se o campo estiver vazio ou contiver apenas separadores "/", retorna {@code null}.
     * Em caso de valor inválido, adiciona um {@link FieldErrorDTO} à lista de erros e retorna {@code null}.
     * </p>
     *
     * @param field campo de texto a ser convertido
     * @param formatter formatação usada para parsing da data
     * @param errors lista de erros de validação
     * @return valor convertido em {@link LocalDate} ou {@code null} se inválido ou vazio
     */
	public static LocalDate toLocalDate(JTextField field, DateTimeFormatter formatter,
	        List<FieldErrorDTO> errors) {

	    String value = field.getText().trim();
	    
	    if (value.matches("\\s*/\\s*/\\s*")) {
	        return null;
	    }
	    
	    if (value.isEmpty()) {
	        return null;
	    }

	    try {
	        return LocalDate.parse(value, formatter);
	    } catch (DateTimeParseException e) {
	        errors.add(new FieldErrorDTO(
	            field,
	            "Data inválida no campo \"" + field.getName() + "\": " + value
	        ));
	        return null;
	    }
	}
	

    /**
     * Converte o valor do {@link JTextField} em {@link String}.
     *
     * <p>
     * Se o campo estiver vazio, retorna {@code null}.
     * </p>
     *
     * @param field campo de texto a ser convertido
     * @param errors lista de erros de validação
     * @return valor como {@link String} ou {@code null} se vazio
     */
	public static String toString(JTextField field, List<FieldErrorDTO> errors) {
		
		String value = field.getText().trim();
		
		if(value.isEmpty())
			return null;
		
		return value;
	}
	
    /**
     * Converte o valor do {@link JTextField} em um ISBN válido (10 ou 13 dígitos).
     *
     * <p>
     * Remove caracteres inválidos e valida o formato do ISBN. Em caso de valor inválido,
     * adiciona um {@link FieldErrorDTO} à lista de erros e retorna {@code null}.
     * Retorna o ISBN em maiúsculas se válido.
     * </p>
     *
     * @param field campo de texto contendo o ISBN
     * @param errors lista de erros de validação
     * @return ISBN validado e formatado ou {@code null} se inválido
     */
	public static String toISBN(JTextField field, List<FieldErrorDTO> errors) {

	    String value = field.getText().trim();

	    if (value.isEmpty())
	        return null;

	    String isbn = value.replaceAll("[^0-9Xx]", "");

	    boolean valido = isValidISBN10(isbn) || isValidISBN13(isbn);

	    if (!valido) {
	        errors.add(new FieldErrorDTO(
	                field,
	                String.format("O valor [%s] é um ISBN inválido para o campo [%s].",
	                        value, field.getName())
	        ));
	        return null;
	    }

	    return isbn.toUpperCase();
	}

    /**
     * Valida se a string fornecida é um ISBN-10 válido.
     *
     * @param isbn string contendo o ISBN
     * @return {@code true} se válido, {@code false} caso contrário
     */
	private static boolean isValidISBN10(String isbn) {

	    if (isbn.length() != 10)
	        return false;

	    int sum = 0;

	    for (int i = 0; i < 10; i++) {
	        char c = isbn.charAt(i);

	        int value;
	        if (i == 9 && (c == 'X' || c == 'x')) {
	            value = 10;
	        } else if (Character.isDigit(c)) {
	            value = Character.getNumericValue(c);
	        } else {
	            return false;
	        }

	        sum += value * (10 - i);
	    }

	    return sum % 11 == 0;
	}

    /**
     * Valida se a string fornecida é um ISBN-13 válido.
     *
     * @param isbn string contendo o ISBN
     * @return {@code true} se válido, {@code false} caso contrário
     */
	private static boolean isValidISBN13(String isbn) {

	    if (isbn.length() != 13 || !isbn.matches("\\d{13}"))
	        return false;

	    int sum = 0;

	    for (int i = 0; i < 12; i++) {
	        int digit = Character.getNumericValue(isbn.charAt(i));
	        sum += digit * ((i % 2 == 0) ? 1 : 3);
	    }

	    int checkDigit = (10 - (sum % 10)) % 10;
	    int lastDigit = Character.getNumericValue(isbn.charAt(12));

	    return checkDigit == lastDigit;
	}

}
