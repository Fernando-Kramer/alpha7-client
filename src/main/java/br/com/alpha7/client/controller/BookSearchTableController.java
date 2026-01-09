package br.com.alpha7.client.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import br.com.alpha7.client.infrastructure.dto.AuthorDTO;
import br.com.alpha7.client.infrastructure.dto.BookDTO;
import br.com.alpha7.client.infrastructure.dto.PublisherDTO;
import br.com.alpha7.client.view.book.BookSearchTablePanel;

/**
 * Controlador da tabela de livros na tela de busca da aplicação cliente Alpha7.
 *
 * <p>
 * Este controlador gerencia a exibição de dados em um {@link JTable},
 * incluindo:
 * <ul>
 *   <li>Preenchimento da tabela com uma lista de {@link BookDTO}</li>
 *   <li>Formatação de datas e concatenação de autores e editoras</li>
 *   <li>Limpeza da tabela</li>
 * </ul>
 * </p>
 *
 * <p>
 * O controlador garante que a seleção da tabela seja limpa após cada atualização.
 * </p>
 * 
 * <p>
 * Integra-se diretamente ao painel {@link BookSearchTablePanel} fornecido no construtor.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class BookSearchTableController {

    private final JTable table;
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Cria um novo controlador para a tabela de busca de livros.
     *
     * @param tablePanel painel que contém a tabela de livros
     */
    public BookSearchTableController(BookSearchTablePanel tablePanel) {
        this.table = tablePanel.getTable();
    }

    /**
     * Preenche a tabela com os livros fornecidos.
     *
     * <p>
     * Para cada {@link BookDTO}, os campos de autores e editoras são concatenados
     * em uma string separada por vírgulas, e a data de publicação é formatada como "dd/MM/yyyy".
     * </p>
     *
     * <p>
     * Após o preenchimento, a seleção da tabela é limpa.
     * </p>
     *
     * @param books lista de livros a serem exibidos na tabela
     */
    public void fillTable(List<BookDTO> books) {

        table.clearSelection();
        table.getSelectionModel().clearSelection();
        table.getColumnModel().getSelectionModel().clearSelection();

        DefaultTableModel model =
                (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        for (BookDTO book : books) {

            String authors = book.getAuthors() != null
                    ? book.getAuthors().stream()
                        .map(AuthorDTO::getName)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(", "))
                    : "";

            String publishers = book.getPublishers() != null
                    ? book.getPublishers().stream()
                        .map(PublisherDTO::getName)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(", "))
                    : "";

            String publicationDate = book.getPublicationDate() != null
                    ? book.getPublicationDate().format(formatter)
                    : "";

            model.addRow(new Object[] {
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                authors,
                publishers,
                publicationDate,
                "Editar"
            });
        }

        SwingUtilities.invokeLater(() -> {
            table.clearSelection();
            table.getSelectionModel().clearSelection();
            table.getColumnModel().getSelectionModel().clearSelection();
        });
    }

    /**
     * Limpa todo o conteúdo da tabela.
     */
    public void clear() {
        ((DefaultTableModel) table.getModel()).setRowCount(0);
    }
    
}
