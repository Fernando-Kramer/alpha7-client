package br.com.alpha7.client.infrastructure.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import br.com.alpha7.client.infrastructure.dto.FieldErrorDTO;
import br.com.alpha7.client.infrastructure.utils.ConvertJTextFieldUtil;

/**
 * Classe utilitária para validação de campos de formulários Swing
 * na aplicação cliente Alpha7.
 *
 * <p>
 * Esta classe encapsula uma lista de {@link FieldErrorDTO} para armazenar
 * erros de validação e fornece métodos para conversão e validação de
 * valores de {@link JTextField} em tipos específicos, delegando para
 * {@link ConvertJTextFieldUtil}.
 * </p>
 *
 * <p>
 * Permite verificar se existem erros e recuperar a lista completa de
 * erros encontrados durante a validação.
 * </p>
 * 
 * <p>
 * Cada método de conversão adiciona um {@link FieldErrorDTO} na lista de
 * erros caso o valor seja inválido.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormValidator {

    /**
     * Lista de erros de validação encontrados.
     */
    private final List<FieldErrorDTO> errors = new ArrayList<>();

    /**
     * Converte o valor do {@link JTextField} em {@link Long} e registra
     * erros de validação.
     *
     * @param field campo de texto a ser convertido
     * @return valor convertido em {@link Long} ou {@code null} se inválido
     */
    public Long getLong(JTextField field) {
        return ConvertJTextFieldUtil.toLong(field, errors);
    }
    
    /**
     * Converte o valor do {@link JTextField} em {@link LocalDate} usando
     * o {@link DateTimeFormatter} fornecido e registra erros de validação.
     *
     * @param field campo de texto a ser convertido
     * @param formatter formatação usada para parsing da data
     * @return valor convertido em {@link LocalDate} ou {@code null} se inválido
     */
    public LocalDate getLocalDate(JTextField field, DateTimeFormatter formatter) {
        return ConvertJTextFieldUtil.toLocalDate(field, formatter, errors);
    }
    
    /**
     * Converte o valor do {@link JTextField} em {@link String} e registra
     * erros de validação caso esteja vazio.
     *
     * @param field campo de texto a ser convertido
     * @return valor como {@link String} ou {@code null} se inválido ou vazio
     */
    public String getString(JTextField field) {
        return ConvertJTextFieldUtil.toString(field, errors);
    }
    
    /**
     * Converte o valor do {@link JTextField} em ISBN válido e registra
     * erros de validação caso inválido.
     *
     * @param field campo de texto contendo o ISBN
     * @return ISBN validado ou {@code null} se inválido
     */
    public String getISBN(JTextField field) {
        return ConvertJTextFieldUtil.toISBN(field, errors);
    }

    /**
     * Verifica se existem erros de validação.
     *
     * @return {@code true} se houver erros, {@code false} caso contrário
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Retorna a lista de erros de validação encontrados.
     *
     * @return lista de {@link FieldErrorDTO}
     */
    public List<FieldErrorDTO> getErrors() {
        return errors;
    }
}
