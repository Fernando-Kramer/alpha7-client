package br.com.alpha7.client.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.alpha7.client.infrastructure.dto.BookToSearchDTO;
import br.com.alpha7.client.infrastructure.validation.FormValidator;
import br.com.alpha7.client.infrastructure.validation.ValidationDialogHandler;
import br.com.alpha7.client.view.book.BookSearchFormPanel;

/**
 * Controlador do formulário de busca de livros na aplicação cliente Alpha7.
 *
 * <p>
 * Este controlador gerencia os campos do {@link BookSearchFormPanel},
 * realizando:
 * <ul>
 *   <li>Validação dos campos do formulário</li>
 *   <li>Conversão dos valores em tipos corretos</li>
 *   <li>Construção de {@link BookToSearchDTO} para filtros de pesquisa</li>
 *   <li>Limpeza dos campos do formulário</li>
 * </ul>
 * </p>
 *
 * <p>
 * Erros de validação são exibidos utilizando {@link ValidationDialogHandler}.
 * </p>
 * 
 * <p>
 * Todo o controle é feito a partir do formulário fornecido no construtor.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class BookSearchFormController {

    private final BookSearchFormPanel form;


    /**
     * Cria um novo controlador para o formulário de busca de livros.
     *
     * @param form painel do formulário de busca
     */
    public BookSearchFormController(BookSearchFormPanel form) {
        this.form = form;
    }

    /**
     * Recupera os filtros do formulário e valida os campos.
     *
     * <p>
     * Se houver erros de validação, exibe um diálogo de erros e retorna {@code null}.
     * Caso contrário, constrói e retorna um {@link BookToSearchDTO} com os valores do formulário.
     * </p>
     *
     * @return DTO com os filtros de pesquisa ou {@code null} caso existam erros
     */
    public BookToSearchDTO getFiltersOrShowErrors() {

        FormValidator validator = new FormValidator();

        Long id = validator.getLong(form.getTxtId());
        String isbn = validator.getISBN(form.getTxtIsbn());
        String title = validator.getString(form.getTxtTitle());
        String author = validator.getString(form.getTxtAuthor());
        String publisher = validator.getString(form.getTxtPublisher());
        LocalDate publicationDate = validator.getLocalDate(
                form.getTxtPublicationDate(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );

        if (validator.hasErrors()) {
            ValidationDialogHandler.showErrors(form, validator.getErrors());
            return null;
        }

        return BookToSearchDTO.builder()
                .id(id)
                .isbn(isbn)
                .title(title)
                .author(author)
                .publisher(publisher)
                .publicationDate(publicationDate)
                .build();
    }
    
    /**
     * Limpa todos os campos do formulário de busca e posiciona o foco no campo ID.
     */
    public void clear() {
        form.getTxtId().setText("");
        form.getTxtIsbn().setText("");
        form.getTxtTitle().setText("");
        form.getTxtAuthor().setText("");
        form.getTxtPublisher().setText("");
        form.getTxtPublicationDate().setText("");
        form.getTxtId().requestFocus();
    }
}
