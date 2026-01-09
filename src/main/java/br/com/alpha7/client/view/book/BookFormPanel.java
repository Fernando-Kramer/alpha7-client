package br.com.alpha7.client.view.book;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.alpha7.client.infrastructure.dto.AuthorDTO;
import br.com.alpha7.client.infrastructure.dto.BookDTO;
import br.com.alpha7.client.infrastructure.dto.PublisherDTO;
import br.com.alpha7.client.infrastructure.utils.SwingFieldUtil;
import br.com.alpha7.client.infrastructure.validation.FormValidator;
import br.com.alpha7.client.infrastructure.validation.ValidationDialogHandler;
import net.miginfocom.swing.MigLayout;

/**
 * Painel de formulário de livros na aplicação Alpha7 Client.
 *
 * <p>
 * Este painel contém campos para entrada e edição de informações de um livro:
 * <ul>
 *   <li>Código</li>
 *   <li>ISBN</li>
 *   <li>Título</li>
 *   <li>Autor</li>
 *   <li>Editora</li>
 *   <li>Data de publicação</li>
 * </ul>
 * </p>
 *
 * <p>
 * Permite preencher o formulário com os dados de um {@link BookDTO} através do método {@link #setFormData(BookDTO)}.
 * Também fornece métodos para acessar os campos individuais e limpar o formulário.
 * </p>
 * 
 * <p>
 * Utiliza {@link MigLayout} para organização dos campos em duas linhas e {@link SwingFieldUtil#createPublicationDateField()}
 * para máscara de data de publicação.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class BookFormPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtId;
    private JTextField txtIsbn;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtPublisher;
    private JFormattedTextField txtPublicationDate;
	

    /**
     * Cria o painel de formulário de livros e inicializa os campos.
     */
	public BookFormPanel() {
		setLayout(new BorderLayout());
		initComponents();
	}
	

    /**
     * Inicializa os componentes e organiza os campos em duas linhas.
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
     * Cria a primeira linha do formulário com Código, ISBN e Data de publicação.
     */
	private JPanel createFirstRow() {
		
	    JPanel panel = new JPanel(new MigLayout(
	            "fillx",
	            "[right][grow 20][right][grow 60][right][grow 20]",
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
	    
	    panel.add(new JLabel("Data de publicação"));
	    txtPublicationDate = SwingFieldUtil.createPublicationDateField();
	    txtPublicationDate.setName("Data de publicação");
	    panel.add(txtPublicationDate, "growx");

	    return panel;
	}
	
    /**
     * Cria a segunda linha do formulário com Título, Autor e Editora.
     */
	private JPanel createSecondRow() {

	    JPanel panel = new JPanel(new MigLayout(
	            "fillx",
	            "[right][grow]",
	            "[]10[]10[]"
	    ));

	    panel.add(new JLabel("Título"));
	    txtTitle = new JTextField();
	    txtTitle.setName("Título");
	    panel.add(txtTitle, "growx, wrap");

	    panel.add(new JLabel("Autor"));
	    txtAuthor = new JTextField();
	    txtAuthor.setName("Autor");
	    panel.add(txtAuthor, "growx, wrap");

	    panel.add(new JLabel("Editora"));
	    txtPublisher = new JTextField();
	    txtPublisher.setName("Editora");
	    panel.add(txtPublisher, "growx");

	    return panel;
	}
	
    /**
     * Preenche o formulário com os dados de um {@link BookDTO}.
     * Caso o parâmetro seja {@code null}, limpa todos os campos.
     *
     * @param dto {@link BookDTO} com os dados do livro
     */
	public void setFormData(BookDTO dto) {

	    if (dto == null) {
	        clearForm();
	        return;
	    }

	    txtId.setText(
	            dto.getId() != null ? dto.getId().toString() : ""
	    );

	    txtIsbn.setText(
	            dto.getIsbn() != null ? dto.getIsbn() : ""
	    );

	    txtTitle.setText(
	            dto.getTitle() != null ? dto.getTitle() : ""
	    );

	    txtAuthor.setText(parseAuthors(dto.getAuthors()));

	    txtPublisher.setText(parsePublisher(dto.getPublishers()));
	    
	    txtPublicationDate.setValue(
	            dto.getPublicationDate()
	    );
	}
	
	private String parseAuthors(List<AuthorDTO> authors) {
		return authors == null || authors.isEmpty() ? "" : authors.get(0).getName();
	}
	
	private String parsePublisher(List<PublisherDTO> publisher) {
		return publisher == null || publisher.isEmpty() ? "" : publisher.get(0).getName();
	}
	

    /**
     * Retorna os dados preenchidos no formulário como {@link BookDTO}.
     * 
     * @return {@link BookDTO} com os dados do formulário (implementação futura)
     */
	public BookDTO getFormData() {
		
		FormValidator validator = new FormValidator();
		
        Long id = validator.getLong(txtId);
        String isbn = validator.getISBN(txtIsbn);
        String title = validator.getString(txtTitle);
        List<AuthorDTO> authors = getAutor(validator.getString(txtAuthor));
        List<PublisherDTO> publishers = getPublisher(validator.getString(txtPublisher));
        LocalDate publicationDate = validator.getLocalDate(
        		txtPublicationDate,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );

        if (validator.hasErrors()) {
            ValidationDialogHandler.showErrors(this, validator.getErrors());
            return null;
        }
        
        return BookDTO.builder()
        		.id(id)
        		.isbn(isbn)
        		.title(title)
        		.authors(authors)
        		.publishers(publishers)
        		.publicationDate(publicationDate)
        		.build();
	}
	
    /**
     * Limpa todos os campos do formulário.
     */
	private void clearForm() {
	    txtId.setText("");
	    txtIsbn.setText("");
	    txtTitle.setText("");
	    txtAuthor.setText("");
	    txtPublisher.setText("");
	    txtPublicationDate.setValue(null);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTextField getTxtId() {
		return txtId;
	}

	public JTextField getTxtIsbn() {
		return txtIsbn;
	}

	public JTextField getTxtTitle() {
		return txtTitle;
	}

	public JTextField getTxtAuthor() {
		return txtAuthor;
	}

	public JTextField getTxtPublisher() {
		return txtPublisher;
	}

	public JFormattedTextField getTxtPublicationDate() {
		return txtPublicationDate;
	}
	
	private List<AuthorDTO> getAutor(String author) {
	    return Arrays.asList(
	        AuthorDTO.builder()
	                 .name(author)
	                 .build()
	    );
	}
	
	private List<PublisherDTO> getPublisher(String publisher) { 
	    return Arrays.asList(
	    		PublisherDTO.builder()
		                 .name(publisher)
		                 .build()
		    );
	}
	
}
