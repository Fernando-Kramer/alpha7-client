package br.com.alpha7.client.view.book;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Painel de rodapé para formulários de livros na aplicação Alpha7 Client.
 *
 * <p>
 * Este painel contém botões para ações do formulário de livro:
 * <ul>
 *   <li>{@code Pesquisar por ISBN}</li>
 *   <li>{@code Salvar}</li>
 *   <li>{@code Remover}</li>
 *   <li>{@code Cancelar}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Utiliza {@link FlowLayout} alinhado à direita para dispor os botões.
 * Os botões podem ser acessados individualmente via getters para configuração
 * de listeners e integração com controladores.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class BookFormFooterPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnSave;
	private JButton btnRemove;
	private JButton btnCancel;
	private JButton btnFindByIsbn;

    /**
     * Cria o painel de rodapé do formulário de livros,
     * inicializando os botões e adicionando-os ao layout.
     */
	public BookFormFooterPanel() {
		setLayout(new FlowLayout(FlowLayout.RIGHT));

		btnSave = new JButton("Salvar");
		btnRemove = new JButton("Remover");
		btnCancel = new JButton("Cancelar");
		btnFindByIsbn = new JButton("Pesquisar por ISBN");

		add(btnFindByIsbn);
		add(btnSave);
		add(btnRemove);
		add(btnCancel);
	}


    /**
     * Retorna o botão "Pesquisar por ISBN".
     *
     * @return botão "Pesquisar por ISBN"
     */
	public JButton getBtnFindByIsbn() {
		return btnFindByIsbn;
	}

    /**
     * Retorna o botão "Salvar".
     *
     * @return botão "Salvar"
     */
	public JButton getBtnSave() {
		return btnSave;
	}

    /**
     * Retorna o botão "Remover".
     *
     * @return botão "Remover"
     */
	public JButton getBtnRemove() {
		return btnRemove;
	}

    /**
     * Retorna o botão "Cancelar".
     *
     * @return botão "Cancelar"
     */
	public JButton getBtnCancel() {
		return btnCancel;
	}
}

