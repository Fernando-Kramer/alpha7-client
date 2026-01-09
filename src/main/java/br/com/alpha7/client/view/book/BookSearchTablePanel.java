package br.com.alpha7.client.view.book;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Painel de exibição da tabela de livros.
 *
 * <p>
 * Este painel encapsula uma {@link JTable} com colunas padrão
 * ("Código", "ISBN", "Título", "Autor", "Editora", "Data de publicação", "Editar").
 * É utilizado para exibir os resultados das buscas de livros e pode ser integrado
 * com controladores para preenchimento e manipulação dos dados.
 * </p>
 * 
 * <p>
 * Fornece método {@link #getTable()} para acessar a {@link JTable} interna.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class BookSearchTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	
    /**
     * Cria o painel de tabela de livros.
     * Inicializa a tabela e adiciona ao painel com scroll.
     */
	public BookSearchTablePanel() {
		setLayout(new BorderLayout());
		initComponents();
	}
	
    /**
     * Inicializa os componentes visuais da tabela.
     */
	private void initComponents() {

	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(new EmptyBorder(10, 10, 10, 10));

	    table = new JTable(new DefaultTableModel(
	        new Object[][] {},
	        new String[] {
	            "Código", "ISBN", "Título", "Autor", "Editora", "Data de publicação", "Editar"
	        }
	    ));

	    JScrollPane scrollPane = new JScrollPane(table);
	    panel.add(scrollPane, BorderLayout.CENTER);

	    add(panel, BorderLayout.CENTER);
	}
	
    /**
     * Retorna a {@link JTable} interna do painel.
     * 
     * @return tabela de livros
     */
	public JTable getTable() {
	    return table;
	}
	
}
