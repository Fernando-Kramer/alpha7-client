package br.com.alpha7.client.service;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alpha7.client.configuration.ServerConfig;
import br.com.alpha7.client.infrastructure.dto.BookDTO;
import br.com.alpha7.client.infrastructure.dto.BookToSearchDTO;
import br.com.alpha7.client.infrastructure.dto.ErrorResponseDTO;
import br.com.alpha7.client.infrastructure.dto.ImportReportDTO;
import br.com.alpha7.client.infrastructure.exception.ServerException;

/**
 * Serviço responsável pela comunicação HTTP relacionada a operações de livros
 * no cliente da aplicação Alpha7.
 *
 * <p>
 * Esta classe realiza chamadas à API do servidor para salvar, remover,
 * consultar e importar livros, utilizando a infraestrutura fornecida por
 * {@link BaseHttpService}.
 * </p>
 *
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class BookService extends BaseHttpService {

    /**
     * Mapper utilizado para conversão de objetos Java em JSON.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Envia uma requisição para salvar ou atualizar um livro.
     *
     * <p>
     * Caso a operação seja bem-sucedida, retorna o {@link BookDTO} resultante
     * enviado pelo servidor.
     * </p>
     *
     * <p>
     * Em caso de erro de validação ou erro de negócio retornado pelo servidor,
     * uma janela com detalhes do erro é exibida ao usuário.
     * Em caso de falha inesperada (ex.: falha de comunicação), uma mensagem
     * genérica também é exibida.
     * </p>
     *
     * <p>
     * Nenhuma exceção é propagada para o chamador e, em falha, este método
     * retorna {@code null}.
     * </p>
     *
     * @param book livro a ser salvo
     * @return livro retornado pela API ou {@code null} em caso de erro
     */
    public BookDTO saveBook(BookDTO book) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(ServerConfig.BASE_URL + "/book");
            connection = openConnectionWithBody(url, "POST");

            try (OutputStream os = connection.getOutputStream()) {
                objectMapper.writeValue(os, book);
            }

            validateStatus(connection, HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_CREATED);
            return readResponse(connection, BookDTO.class);

        } catch (ServerException se) {
            showErrorDialog(se.getErrorResponse(), "Erro ao salvar livro");
        } catch (Exception e) {
            showUnexpectedErrorDialog(e, "Erro ao salvar livro");
        } finally {
            disconnect(connection);
        }
        return null;
    }

    /**
     * Remove um livro no servidor com base em seu identificador.
     *
     * <p>
     * Em caso de falha, uma mensagem de erro é exibida ao usuário.
     * Nenhuma exceção é propagada.
     * </p>
     *
     * @param idDoBook identificador do livro
     */
    public void deleteBook(Long idDoBook) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(ServerConfig.BASE_URL + "/book/" + idDoBook);
            connection = openConnection(url, "DELETE");
            validateStatus(connection, HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_NO_CONTENT);

        } catch (ServerException se) {
            showErrorDialog(se.getErrorResponse(), "Erro ao remover livro");
        } catch (Exception e) {
            showUnexpectedErrorDialog(e, "Erro ao remover livro");
        } finally {
            disconnect(connection);
        }
    }

    /**
     * Pesquisa livros no servidor a partir dos filtros informados.
     *
     * <p>
     * Em caso de sucesso retorna a lista de livros.
     * </p>
     *
     * <p>
     * Em caso de erro tratado pelo servidor ({@link ServerException}),
     * uma mensagem descritiva é exibida ao usuário.
     * Em caso de erro inesperado (falha de rede, parsing etc),
     * uma mensagem genérica também é exibida.
     * </p>
     *
     * <p>
     * Nenhuma exceção é propagada para o chamador.
     * Em caso de erro, o método retorna {@code null}.
     * </p>
     *
     * @param filter filtros de busca
     * @return lista de livros encontrados ou {@code null} em caso de erro
     */
    public List<BookDTO> searchBooks(BookToSearchDTO filter) {
        HttpURLConnection connection = null;
        try {
            URI uri = buildUri(filter);
            connection = openConnection(uri.toURL(), "GET");
            validateStatus(connection, HttpURLConnection.HTTP_OK);

            return readResponse(connection, new TypeReference<List<BookDTO>>() {});

        } catch (ServerException se) {
            showErrorDialog(se.getErrorResponse(), "Erro ao consultar livros");
        } catch (Exception e) {
            showUnexpectedErrorDialog(e, "Erro ao consultar livros");
        } finally {
            disconnect(connection);
        }
        return null;
    }

    /**
     * Consulta um livro na Open Library pelo ISBN através do servidor.
     *
     * @param isbn código ISBN do livro
     * @return livro encontrado ou {@code null} em caso de erro
     */
    public BookDTO searchByIsbnOpenLibrary(String isbn) {
        HttpURLConnection connection = null;
        try {
            String urlStr = ServerConfig.BASE_URL + "/open-library?isbn=" + encode(isbn);
            connection = openConnection(new URL(urlStr), "GET");
            validateStatus(connection, HttpURLConnection.HTTP_OK);

            return readResponse(connection, BookDTO.class);

        } catch (ServerException se) {
            showErrorDialog(se.getErrorResponse(), "Erro ao consultar Open Library");
        } catch (Exception e) {
            showUnexpectedErrorDialog(e, "Erro ao consultar Open Library");
        } finally {
            disconnect(connection);
        }
        return null;
    }

    /**
     * Envia um arquivo CSV para importação de livros pelo servidor.
     *
     * <p>
     * Em caso de sucesso retorna o relatório de importação.
     * Em caso de erro tratado pelo servidor, exibe mensagem ao usuário.
     * Em falha inesperada, exibe mensagem genérica.
     * </p>
     *
     * <p>
     * Nenhuma exceção é propagada.
     * Em falha retorna {@code null}.
     * </p>
     *
     * @param csvFile arquivo CSV contendo dados de livros
     * @return relatório de importação ou {@code null} em caso de erro
     */
    public ImportReportDTO importBooksFromCsv(File csvFile) {
        HttpURLConnection connection = null;
        String boundary = "----Alpha7Boundary" + System.currentTimeMillis();

        try {
            URL url = new URL(ServerConfig.BASE_URL + "/book/import");
            connection = openConnectionWithBody(url, "POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream output = connection.getOutputStream();
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true)) {

                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                      .append(csvFile.getName()).append("\"\r\n");
                writer.append("Content-Type: text/csv\r\n");
                writer.append("\r\n");
                writer.flush();

                Files.copy(csvFile.toPath(), output);
                output.flush();

                writer.append("\r\n");
                writer.append("--").append(boundary).append("--").append("\r\n");
                writer.flush();
            }

            validateStatus(connection, HttpURLConnection.HTTP_OK);
            return readResponse(connection, new TypeReference<ImportReportDTO>() {});

        } catch (ServerException se) {
            showErrorDialog(se.getErrorResponse(), "Erro ao importar CSV");
        } catch (Exception e) {
            showUnexpectedErrorDialog(e, "Erro ao importar CSV");
        } finally {
            disconnect(connection);
        }
        return null;
    }

    /**
     * Monta a URI de consulta de livros a partir do filtro informado.
     *
     * @param filter filtros de pesquisa
     * @return URI construída
     */
    private URI buildUri(BookToSearchDTO filter) {
    	
        StringBuilder sb = new StringBuilder();
        
        sb.append(ServerConfig.BASE_URL).append("/book?");

        if (filter.getId() != null) sb.append("id=").append(filter.getId()).append("&");
        
        if (filter.getIsbn() != null) sb.append("isbn=").append(filter.getIsbn()).append("&");
        
        if (filter.getTitle() != null && !filter.getTitle().trim().isEmpty())
            sb.append("title=").append(encode(filter.getTitle())).append("&");
        
        if(filter.getAuthor() != null && !filter.getAuthor().trim().isEmpty())
        	sb.append("author=").append(encode(filter.getAuthor())).append("&");
        
        if(filter.getPublisher() != null && !filter.getPublisher().trim().isEmpty())
        	sb.append("publisher=").append(encode(filter.getPublisher())).append("&");
        
        if (filter.getPublicationDate() != null) {
            String publicationDate =
                    filter.getPublicationDate()
                          .format(DateTimeFormatter.ISO_LOCAL_DATE);
            sb.append("publicationDate=").append(encode(publicationDate)).append("&");
        }
        
        String url = sb.toString();
        
        if (url.endsWith("&") || url.endsWith("?")) url = url.substring(0, url.length() - 1);

        return URI.create(url);
    }

    /**
     * Exibe uma mensagem de erro retornado pelo servidor.
     *
     * @param error detalhes do erro
     * @param title título da janela
     */
    private void showErrorDialog(ErrorResponseDTO error, String title) {
        JOptionPane.showMessageDialog(
                null,
                "Erro: " + error.getMessage() +
                "\nStatus: " + error.getStatus() +
                "\nCaminho: " + error.getPath(),
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Exibe mensagem para erros inesperados.
     *
     * @param e exceção lançada
     * @param title título da janela
     */
    private void showUnexpectedErrorDialog(Exception e, String title) {
        JOptionPane.showMessageDialog(
                null,
                "Ocorreu um erro inesperado: " + e.getMessage(),
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }
}
