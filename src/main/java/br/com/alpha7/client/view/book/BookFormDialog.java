package br.com.alpha7.client.view.book;

import java.awt.BorderLayout;
import java.awt.Window;
import java.util.function.Consumer;

import javax.swing.JDialog;

import br.com.alpha7.client.infrastructure.dto.BookDTO;

/**
 * Diálogo modal para cadastro, edição e pesquisa de livros na aplicação Alpha7 Client.
 *
 * <p>
 * Este diálogo combina:
 * <ul>
 *   <li>{@link BookFormPanel} — formulário para entrada de dados do livro</li>
 *   <li>{@link BookFormFooterPanel} — rodapé com botões de Ações: Salvar, Remover, Cancelar, Pesquisar por ISBN</li>
 * </ul>
 * </p>
 *
 * <p>
 * O diálogo é configurado como {@link ModalityType#APPLICATION_MODAL} e centralizado
 * em relação à janela pai.
 * </p>
 *
 * <p>
 * As ações dos botões podem ser configuradas externamente via:
 * <ul>
 *   <li>{@link #onSave(Consumer)} — disparado ao salvar o livro</li>
 *   <li>{@link #onRemove(Consumer)} — disparado ao remover o livro</li>
 *   <li>{@link #onFindByIsbn(Consumer)} — disparado ao pesquisar por ISBN</li>
 * </ul>
 * </p>
 *
 * <p>
 * Pode preencher automaticamente o formulário através de {@link #fillForm(BookDTO)}.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @version 1.0.0
 * @since 1.0.0
 */
public class BookFormDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private BookFormPanel formPanel;
	private BookFormFooterPanel footerPanel;
	
	private Consumer<BookDTO> onSave;
	private Consumer<BookDTO> onRemove;
	private Consumer<String> onFindByIsbn;

    /**
     * Cria o diálogo de cadastro/edição de livro.
     *
     * @param parent janela pai para centralização e modalidade
     * @param book {@link BookDTO} opcional para preenchimento inicial do formulário, pode ser {@code null}
     */
	public BookFormDialog(Window parent, BookDTO book) {
		super(parent, ModalityType.APPLICATION_MODAL);
		initComponents();
		formPanel.setFormData(book);
		setLocationRelativeTo(parent);
	}
	
    /**
     * Inicializa os componentes do diálogo: formulário e rodapé de ações.
     */
	private void initComponents() {
		setLayout(new BorderLayout(10, 10));
		setSize(600, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		formPanel = new BookFormPanel();
		footerPanel = new BookFormFooterPanel();

		add(formPanel, BorderLayout.CENTER);
		add(footerPanel, BorderLayout.SOUTH);
		
		footerPanel.getBtnFindByIsbn().addActionListener(e -> onFindByIsbn());
		
		footerPanel.getBtnCancel().addActionListener(e -> dispose());
        footerPanel.getBtnSave().addActionListener(e -> fireSave());
        footerPanel.getBtnRemove().addActionListener(e -> fireRemove());
	}
	
    /**
     * Dispara a ação de pesquisa por ISBN abrindo {@link BookFindByIsbnDialog}.
     */
	private void onFindByIsbn() {

	    BookFindByIsbnDialog dialog =
	            new BookFindByIsbnDialog(this);

	    dialog.onSearch(isbn -> {
	        if (onFindByIsbn != null) {
	            onFindByIsbn.accept(isbn);
	        }
	    });

	    dialog.setVisible(true);
	}
	

    /**
     * Dispara a ação de salvar livro e fecha o diálogo.
     */
    private void fireSave() {
        if (onSave != null) {
            onSave.accept(formPanel.getFormData());
        }
        dispose();
    }

    /**
     * Dispara a ação de remover livro e fecha o diálogo.
     */
    private void fireRemove() {
        if (onRemove != null) {
            onRemove.accept(formPanel.getFormData());
        }
        dispose();
    }

    /**
     * Define a ação a ser executada ao salvar o livro.
     *
     * @param action {@link Consumer} que recebe o {@link BookDTO} a ser salvo
     */
    public void onSave(Consumer<BookDTO> action) {
        this.onSave = action;
    }

    /**
     * Define a ação a ser executada ao remover o livro.
     *
     * @param action {@link Consumer} que recebe o {@link BookDTO} a ser removido
     */
    public void onRemove(Consumer<BookDTO> action) {
        this.onRemove = action;
    }
    
    /**
     * Define a ação a ser executada ao pesquisar um livro por ISBN.
     *
     * @param action {@link Consumer} que recebe o ISBN informado
     */
    public void onFindByIsbn(Consumer<String> action) {
        this.onFindByIsbn = action;
    }
    

    /**
     * Preenche o formulário com os dados do {@link BookDTO} informado.
     *
     * @param dto {@link BookDTO} com os dados a preencher no formulário
     */
    public void fillForm(BookDTO dto) {
        formPanel.setFormData(dto);
    }

}
