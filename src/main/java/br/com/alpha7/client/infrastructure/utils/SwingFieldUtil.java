package br.com.alpha7.client.infrastructure.utils;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;


/**
 * Classe utilitária para criação de campos Swing pré-configurados
 * na aplicação cliente Alpha7.
 *
 * <p>
 * Fornece métodos estáticos para criar campos de formulário com máscaras,
 * facilitando a padronização da interface gráfica da aplicação.
 * </p>
 *
 * <p>
 * Esta classe é composta apenas por métodos estáticos, mas possui
 * construtor público padrão para compatibilidade com frameworks,
 * embora a instanciação não seja necessária.
 * </p>
 * 
 * <p>
 * No caso de falha na criação da máscara, retorna um {@link JFormattedTextField}
 * padrão sem formatação.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class SwingFieldUtil {

    /**
     * Construtor público padrão.
     *
     * <p>
     * Não é necessário instanciar a classe para utilizar seus métodos.
     * </p>
     */
	public SwingFieldUtil() {}

    /**
     * Cria um {@link JFormattedTextField} configurado para entrada de
     * datas de publicação no formato "dd/MM/yyyy".
     *
     * <p>
     * Caso a máscara não possa ser aplicada, retorna um campo padrão
     * sem formatação.
     * </p>
     *
     * @return campo formatado para datas ou campo padrão em caso de erro
     */

    public static JFormattedTextField createPublicationDateField() {
        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setAllowsInvalid(false);
            return new JFormattedTextField(mask);

        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }
	
}
