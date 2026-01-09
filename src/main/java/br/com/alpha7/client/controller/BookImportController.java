package br.com.alpha7.client.controller;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alpha7.client.infrastructure.dto.ImportReportDTO;
import br.com.alpha7.client.infrastructure.validation.ValidationDialogDefaultHandler;
import br.com.alpha7.client.service.BookService;
import br.com.alpha7.client.view.book.BookSearchImportDialog;


/**
 * Controlador responsável pela importação de livros via arquivo CSV
 * na aplicação cliente Alpha7.
 *
 * <p>
 * Este controlador gerencia o diálogo {@link BookSearchImportDialog},
 * executa a importação através de {@link BookService} e atualiza a tabela
 * de livros na tela principal utilizando {@link BookController}.
 * </p>
 *
 * <p>
 * Exibe mensagens de informação, aviso ou erro utilizando
 * {@link ValidationDialogDefaultHandler}, incluindo relatórios de erros
 * detalhados em tabela.
 * </p>
 * 
 * <p>
 * Todos os eventos do diálogo de importação são inicializados no método {@link #initListeners()}.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class BookImportController {
	
	private final BookSearchImportDialog dialog;
	private final BookService service;
	private final BookController controller;
	
    /**
     * Cria um novo controlador de importação de livros.
     *
     * <p>
     * Inicializa os listeners do diálogo e configura a integração
     * com o serviço e o controlador principal de livros.
     * </p>
     *
     * @param dialog diálogo de importação de livros
     * @param service serviço responsável pelas operações de backend
     * @param controller controlador principal da tela de livros
     */
	public BookImportController(BookSearchImportDialog dialog, BookService service, BookController controller) {
		this.dialog = dialog;
		this.service = service;
		this.controller = controller;
		initListeners();
	}
	

    /**
     * Inicializa os listeners do diálogo de importação.
     *
     * <p>
     * Associa o botão de importação ao método {@link #onImportConfirm(BookSearchImportDialog)}.
     * </p>
     */
	private void initListeners() {
        dialog.getBtnImport().addActionListener(e -> onImportConfirm(dialog));
	}
	
    /**
     * Executa a importação do arquivo CSV selecionado.
     *
     * <p>
     * Valida se um arquivo foi selecionado, processa a importação via
     * {@link BookService}, exibe relatórios de erros em tabela e atualiza
     * a tabela de livros na tela principal.
     * </p>
     *
     * @param dialog diálogo de importação de livros
     */
	private void onImportConfirm(BookSearchImportDialog dialog) {
		
		File file = dialog.getSelectedFile();
		
        if (file == null) {
        	ValidationDialogDefaultHandler.showWarning(
        			dialog,"Nenhum arquivo selecionado");
            return;
        }
        
        try {
        	
        	ImportReportDTO result = service.importBooksFromCsv(file);
        	
        	String[] columns = {"Linha", "Conteudo", "Erro"};
        	
        	List<Object[]> rows = result.getErrors().stream()
        	        .map(e -> new Object[]{
        	                e.getLineNumber(),
        	                e.getLineContent(),
        	                e.getMessage()
        	        })
        	        .collect(Collectors.toList());
        	
        	ValidationDialogDefaultHandler.showInfoWithTable(
        			dialog, getMessageToResponse(result), columns, rows);
        	
            if (!result.getBooks().isEmpty()) {
                controller.reloadTableByImport(result.getBooks());
            }
        	
        	dialog.dispose();
        	
		} catch (Exception e) {
			ValidationDialogDefaultHandler.showError(dialog,e.getMessage());
			
		}
	}
	
    /**
     * Gera a mensagem resumida para exibição após a importação.
     *
     * <p>
     * Informa se todos os itens foram importados com sucesso ou se
     * houve erros, incluindo a quantidade de itens importados e de erros.
     * </p>
     *
     * @param result resultado da importação {@link ImportReportDTO}
     * @return mensagem resumida para exibição
     */
	private String getMessageToResponse(ImportReportDTO result) {
		
		String header = result.getErrors().isEmpty() ? 
				"Importação finalizada com sucesso, todos os itens foram atualizados ou cadastrados\n\n" 
				: "Importação finalizada com sucesso, alguns itens não foram atualizados ou criados.\n\n";
		Integer createOrUpdate = result.getBooks().size();
		Integer errors = result.getErrors().size();

		StringBuilder sb = new StringBuilder();
		sb.append(header);
		sb.append("Resumo:\n");
		sb.append("• Importados: ").append(createOrUpdate).append("\n");
		sb.append("• Erros: ").append(errors).append("\n");
		return sb.toString();
	}
	
}
