package br.com.alpha7.client.view.book;

import java.awt.BorderLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.alpha7.client.infrastructure.utils.SwingFieldUtil;
import net.miginfocom.swing.MigLayout;


/**
 * Painel de formulário para busca de livros na aplicação Alpha7 Client.
 *
 * <p>
 * Este painel contém campos para filtrar livros na tela de busca:
 * <ul>
 *   <li>{@code Código} — campo de identificação do livro</li>
 *   <li>{@code ISBN} — campo do código ISBN do livro</li>
 *   <li>{@code Título} — campo do título do livro</li>
 *   <li>{@code Autor} — campo do nome do autor</li>
 *   <li>{@code Editora} — campo do nome da editora</li>
 *   <li>{@code Data de publicação} — campo formatado para a data de publicação</li>
 * </ul>
 * </p>
 *
 * <p>
 * Os campos são organizados em duas linhas usando {@link MigLayout} e
 * podem ser acessados individualmente através de getters para integração
 * com validadores e controladores.
 * </p>
 *
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class BookSearchFormPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField txtId;
    private JTextField txtIsbn;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtPublisher;
    private JFormattedTextField txtPublicationDate;


    /**
     * Cria o painel de formulário da busca de livros, inicializando os campos.
     */
	public BookSearchFormPanel() {
		setLayout(new BorderLayout());
		initComponents();
	}
	

    /**
     * Inicializa os componentes do painel, criando as linhas de campos.
     */
	private void initComponents() {
		
		JPanel panel = new JPanel(
				new MigLayout("insets 10, fillx", "[grow]", "[]10[]")
				);
		
		panel.add(createFirstRow(), "growx, wrap");
		panel.add(createSecondRow(), "growx");
		
		add(panel, BorderLayout.NORTH);
	}
	
    /**
     * Cria a primeira linha de campos: Código, ISBN, Título e Data de Publicação.
     * 
     * @return JPanel contendo os campos da primeira linha
     */
	private JPanel createFirstRow() {
		
	    JPanel panel = new JPanel(new MigLayout(
	            "fillx",
	            "[right][grow 15] [right][grow 20] [right][grow 55] [right][grow 10]",
	            "[]"
	    ));
	    
	    panel.add(new JLabel("Código"));
	    txtId = new JTextField();
	    txtId.setName("Código");
	    panel.add(txtId, "growx");

	    panel.add(new JLabel("ISBN"));
	    txtIsbn = new JTextField();
	    txtIsbn.setName("ISBN");
	    panel.add(txtIsbn, "growx");

	    panel.add(new JLabel("Título"));
	    txtTitle = new JTextField();
	    txtTitle.setName("Título");
	    panel.add(txtTitle, "growx");
	    
	    panel.add(new JLabel("Data de publicação"));
	    txtPublicationDate = SwingFieldUtil.createPublicationDateField();
	    txtPublicationDate.setName("Data de publicação");
	    panel.add(txtPublicationDate, "growx");

	    return panel;
	}
	
    /**
     * Cria a segunda linha de campos: Autor e Editora.
     * 
     * @return JPanel contendo os campos da segunda linha
     */
	private JPanel createSecondRow() {

	    JPanel panel = new JPanel(new MigLayout(
	            "fillx",
	            "[right][grow 40][right][grow 40][pref][right][grow 20]",
	            "[]"
	    ));
	    
	    panel.add(new JLabel("Autor"));
	    txtAuthor = new JTextField();
	    txtAuthor.setName("Autor");
	    panel.add(txtAuthor, "growx");

	    panel.add(new JLabel("Editora"));
	    txtPublisher = new JTextField();
	    txtPublisher.setName("Editora");
	    panel.add(txtPublisher, "growx");

	    return panel;
	}

	/** Retorna o campo "Código". */
	public JTextField getTxtId() {
		return txtId;
	}

	/** Retorna o campo "ISBN". */
	public JTextField getTxtIsbn() {
		return txtIsbn;
	}

	/** Retorna o campo "Título". */
	public JTextField getTxtTitle() {
		return txtTitle;
	}

	/** Retorna o campo "Autor". */
	public JTextField getTxtAuthor() {
		return txtAuthor;
	}

	/** Retorna o campo "Editora". */
	public JTextField getTxtPublisher() {
		return txtPublisher;
	}

	/** Retorna o campo "Data de publicação". */
	public JFormattedTextField getTxtPublicationDate() {
		return txtPublicationDate;
	}

}