package br.com.alpha7.client.controller;

import java.awt.Window;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import br.com.alpha7.client.infrastructure.dto.BookDTO;
import br.com.alpha7.client.infrastructure.dto.BookToSearchDTO;
import br.com.alpha7.client.service.BookService;
import br.com.alpha7.client.view.book.BookFormDialog;
import br.com.alpha7.client.view.book.BookSearchFooterPanel;
import br.com.alpha7.client.view.book.BookSearchFormPanel;
import br.com.alpha7.client.view.book.BookSearchImportDialog;
import br.com.alpha7.client.view.book.BookSearchTablePanel;

/**
 * Controlador principal da tela de busca e cadastro de livros
 * na aplicação cliente Alpha7.
 *
 * <p>
 * Gerencia os componentes da interface relacionados à pesquisa de livros,
 * incluindo:
 * <ul>
 *   <li>Formulário de busca ({@link BookSearchFormController})</li>
 *   <li>Tabela de resultados ({@link BookSearchTableController})</li>
 *   <li>Rodapé com botões de ação ({@link BookSearchFooterPanel})</li>
 * </ul>
 * </p>
 *
 * <p>
 * Também coordena a comunicação com a camada de serviço {@link BookService}
 * para operações de CRUD e importação de livros.
 * </p>
 * 
 * <p>
 * Todos os eventos de botões são inicializados no método {@link #initListeners()},
 * incluindo busca, limpeza, cadastro e importação de livros.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class BookController {

	private final BookSearchFooterPanel footer;
	private final BookSearchFormController formController;
    private final BookSearchTableController tableController;
	private final BookService service;
	
    /**
     * Cria um novo controlador para a tela de busca e cadastro de livros.
     *
     * <p>
     * Inicializa os subcontroladores de formulário e tabela, além do serviço
     * responsável pelas operações de backend.
     * </p>
     *
     * @param form painel de formulário de busca de livros
     * @param table painel de tabela de resultados de livros
     * @param footer painel de rodapé com botões de ação
     */
	public BookController(
			BookSearchFormPanel form, 
			BookSearchTablePanel table,
			BookSearchFooterPanel footer) {
		this.footer = footer;
		this.service = new BookService();
		this.formController = new BookSearchFormController(form);
		this.tableController = new BookSearchTableController(table);
		initListeners();
	}
	
    /**
     * Inicializa os listeners dos botões do rodapé.
     *
     * <p>
     * Associa os botões de busca, limpeza, cadastro e importação aos
     * respectivos métodos internos de tratamento.
     * </p>
     */
	private void initListeners() {
		footer.getBtnSearch().addActionListener(e -> onSearch());
		footer.getBtnClean().addActionListener(e -> onClean());
		footer.getBtnRegister().addActionListener(e -> onRegister());
		footer.getBtnImport().addActionListener(e -> onImport());
	}
	
    /**
     * Executa a busca de livros usando os filtros do formulário.
     *
     * <p>
     * Caso os filtros sejam inválidos, a operação é cancelada.
     * Os resultados são exibidos na tabela.
     * </p>
     */
    private void onSearch() {
        BookToSearchDTO filters = formController.getFiltersOrShowErrors();
        if (filters == null) {
            return;
        }
        List<BookDTO> books = service.searchBooks(filters);
        tableController.fillTable(books);
    }
    

    /**
     * Limpa os filtros do formulário e os dados exibidos na tabela.
     */
    private void onClean() {
        formController.clear();
        tableController.clear();
    }
    
    /**
     * Abre o diálogo de cadastro/edição de livros.
     *
     * <p>
     * Configura os callbacks de salvar, remover e buscar por ISBN
     * interagindo com o {@link BookService}.
     * </p>
     */
    private void onRegister() {
	    Window parent = SwingUtilities.getWindowAncestor(footer);

	    BookFormDialog dialog = new BookFormDialog(parent, null);

	    dialog.onSave(book -> {
	        // regra de negócio
	        System.out.println("Salvando: " + book.getTitle());
	        // service.save(book);
	        // reloadTable();
	    });

	    dialog.onRemove(book -> {
	        System.out.println("Removendo: " + book.getTitle());
	        // service.delete(book);
	        // reloadTable();
	    });
	    
	    dialog.onFindByIsbn(isbn -> {
	    	
	        BookDTO dto = service.searchByIsbnOpenLibrary(isbn);
	        
	        System.out.println(dto.toString());

	        if (dto != null) {
	            dialog.fillForm(dto);
	        } else {
	            JOptionPane.showMessageDialog(
	                parent,
	                "Livro não encontrado para o ISBN informado",
	                "Aviso",
	                JOptionPane.WARNING_MESSAGE
	            );
	        }
	    });
	    
	    dialog.setVisible(true);
    }
	
    /**
     * Abre o diálogo de importação de livros.
     *
     * <p>
     * Configura o controlador de importação {@link BookImportController}
     * e posiciona o diálogo centralizado em relação à janela pai.
     * </p>
     */
	private void onImport() {
		Window parent = SwingUtilities.getWindowAncestor(footer);
		BookSearchImportDialog dialog = new BookSearchImportDialog(parent);
		new BookImportController(dialog, service, this);
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}
	
    /**
     * Recarrega a tabela de livros após a importação.
     *
     * @param books lista de livros importados
     */
	public void reloadTableByImport(List<BookDTO> books) {
		tableController.fillTable(books);
	}
	
}

