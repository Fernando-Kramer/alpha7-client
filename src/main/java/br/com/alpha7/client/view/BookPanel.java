package br.com.alpha7.client.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import br.com.alpha7.client.controller.BookController;
import br.com.alpha7.client.view.book.BookSearchFooterPanel;
import br.com.alpha7.client.view.book.BookSearchFormPanel;
import br.com.alpha7.client.view.book.BookSearchTablePanel;

/**
 * Painel principal de gerenciamento de livros na aplicação Alpha7 Client.
 *
 * <p>
 * Este painel combina:
 * <ul>
 *   <li>{@link BookSearchFormPanel} no topo para filtros e pesquisa</li>
 *   <li>{@link BookSearchTablePanel} no centro para exibir resultados</li>
 *   <li>{@link BookSearchFooterPanel} na parte inferior com ações (Pesquisar, Limpar, Cadastrar, Importar)</li>
 * </ul>
 * </p>
 *
 * <p>
 * O painel também inicializa o {@link BookController}, que gerencia a lógica de interação
 * entre o formulário, a tabela e o serviço de livros.
 * </p>
 * 
 * <p>
 * Utiliza {@link BorderLayout} com espaçamento de 5 pixels entre componentes.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @version 1.0.0
 * @since 1.0.0
 */
public class BookPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private BookSearchFormPanel formPanel;
	private BookSearchTablePanel tablePanel;
	private BookSearchFooterPanel footerPanel;
	private BookController controller;
	
    /**
     * Cria um painel de gerenciamento de livros.
     *
     * <p>
     * Inicializa os subpainéis (formulário, tabela e rodapé) e
     * cria o {@link BookController} para gerenciar os eventos da interface.
     * </p>
     */
	public BookPanel() {
		setLayout(new BorderLayout(5, 5));
		initComponents();
		
		controller = new BookController(
				formPanel,
				tablePanel,
				footerPanel
				);
	}
	
    /**
     * Inicializa os subpainéis do painel de livros e os adiciona
     * ao layout {@link BorderLayout}.
     */
	private void initComponents() {
		formPanel = new BookSearchFormPanel();
		tablePanel = new BookSearchTablePanel();
		footerPanel = new BookSearchFooterPanel();
		
		add(formPanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		add(footerPanel, BorderLayout.SOUTH);
	}
	
    /**
     * Retorna o controlador do painel de livros.
     *
     * @return {@link BookController} associado a este painel
     */
	public BookController getController() {
		return controller;
	}

}

