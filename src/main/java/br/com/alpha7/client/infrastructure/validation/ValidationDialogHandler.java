package br.com.alpha7.client.infrastructure.validation;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import br.com.alpha7.client.infrastructure.dto.FieldErrorDTO;

/**
 * Classe utilitária para exibição de erros de validação de formulários
 * na interface Swing da aplicação cliente Alpha7.
 *
 * <p>
 * Fornece métodos estáticos para exibir erros encapsulados em {@link FieldErrorDTO}
 * em um diálogo modal. Após o fechamento do diálogo, o primeiro campo com erro
 * é limpo e recebe foco automaticamente.
 * </p>
 *
 * <p>
 * Esta classe não pode ser instanciada.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class ValidationDialogHandler {

    /**
     * Construtor privado para impedir instanciação da classe.
     */
	private ValidationDialogHandler() {}
	
    /**
     * Exibe os erros de validação em um {@link JDialog} modal.
     *
     * <p>
     * Caso a lista de erros seja nula ou vazia, nenhum diálogo é exibido.
     * O primeiro campo com erro é limpo e recebe foco após o fechamento do diálogo.
     * </p>
     *
     * @param parent componente pai do diálogo
     * @param errors lista de {@link FieldErrorDTO} contendo os erros de validação
     */
    public static void showErrors(
            Component parent,
            List<FieldErrorDTO> errors) {

        if (errors == null || errors.isEmpty()) {
            return;
        }

        StringBuilder message = new StringBuilder("Foram encontrados erros:\n\n");

        for (FieldErrorDTO error : errors) {
            message.append("• ").append(error.getMessage()).append("\n");
        }

        JOptionPane optionPane = new JOptionPane(
            message.toString(),
            JOptionPane.ERROR_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            null,
            new Object[]{}
        );

        JDialog dialog = optionPane.createDialog(parent, "Erro de validação");
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clearAndFocus(errors);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                clearAndFocus(errors);
            }
        });

        dialog.setVisible(true);
    }
    
    /**
     * Limpa o primeiro campo com erro e posiciona o foco nele.
     *
     * @param errors lista de {@link FieldErrorDTO} contendo os erros de validação
     */
    private static void clearAndFocus(List<FieldErrorDTO> errors) {
        FieldErrorDTO first = errors.get(0);
        first.getField().setText("");
        first.getField().requestFocusInWindow();
    }
}
