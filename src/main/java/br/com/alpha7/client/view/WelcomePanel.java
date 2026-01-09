package br.com.alpha7.client.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Painel de boas-vindas da aplicação Alpha7 Client.
 *
 * <p>
 * Este painel é exibido inicialmente no {@link MainFrame} e contém
 * uma mensagem centralizada de boas-vindas para o usuário.
 * </p>
 *
 * <p>
 * Configura o layout como {@link BorderLayout} e exibe um {@link JLabel}
 * centralizado com texto em fonte negrito de 18pt.
 * </p>
 * 
 * <p>
 * Pode ser utilizado como painel inicial ou placeholder em outras áreas da interface.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @version 1.0.0
 * @since 1.0.0
 */
public class WelcomePanel extends JPanel {

	private static final long serialVersionUID = 1L;

    /**
     * Cria um painel de boas-vindas configurado com layout e mensagem centralizada.
     */
	public WelcomePanel() {
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("Bem-vindo ao Alpha7 Client", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));

        add(lbl, BorderLayout.CENTER);
    }
    
}
