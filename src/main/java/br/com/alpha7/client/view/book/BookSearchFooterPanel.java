package br.com.alpha7.client.view.book;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Painel de rodapé para a tela de busca de livros na aplicação Alpha7 Client.
 *
 * <p>
 * Este painel contém botões para ações na tela de busca de livros:
 * <ul>
 *   <li>{@code Cadastrar} — abre o formulário para criar um novo livro</li>
 *   <li>{@code Pesquisar} — aplica os filtros da busca</li>
 *   <li>{@code Limpar} — limpa os filtros e a tabela de resultados</li>
 *   <li>{@code Importar} — importa livros a partir de um arquivo CSV</li>
 * </ul>
 * </p>
 *
 * <p>
 * Os botões são dispostos à direita utilizando {@link FlowLayout} dentro de um {@link JPanel}
 * e podem ser acessados individualmente via getters para configuração de listeners
 * e integração com controladores.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @version 1.0.0
 * @since 1.0.0
 */
public class BookSearchFooterPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JButton btnRegister;
	private JButton btnSearch;
	private JButton btnClean;
	private JButton btnImport;
	
    /**
     * Cria o painel de rodapé da tela de busca de livros,
     * inicializando os botões e adicionando-os ao layout.
     */
	public BookSearchFooterPanel() {
		setLayout(new BorderLayout());
		initComponents();
	}
	

    /**
     * Inicializa os componentes do painel e adiciona os botões.
     */
	private void initComponents() {
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
		panel.setBorder(new EmptyBorder(5, 10, 10, 10));
		
		btnRegister = new JButton("Cadastrar");
		btnImport = new JButton("Importar");
		btnClean = new JButton("Limpar");
        btnSearch = new JButton("Pesquisar");

        panel.add(btnRegister);
        panel.add(btnImport);
        panel.add(btnClean);
        panel.add(btnSearch);
		
        add(panel, BorderLayout.NORTH);
	}
	

    /** Retorna o botão "Pesquisar". */
	public JButton getBtnSearch() {
		return btnSearch;
	}

	/** Retorna o botão "Cadastrar". */
	public JButton getBtnRegister() {
		return btnRegister;
	}

	/** Retorna o botão "Limpar". */
	public JButton getBtnClean() {
		return btnClean;
	}

	/** Retorna o botão "Importar". */
	public JButton getBtnImport() {
		return btnImport;
	}
	
}

