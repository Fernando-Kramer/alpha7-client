package br.com.alpha7.client.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.alpha7.client.infrastructure.dto.ErrorResponseDTO;
import br.com.alpha7.client.infrastructure.exception.ServerException;
import br.com.alpha7.client.infrastructure.factory.JsonMapperFactory;

/**
 * Classe base para serviços HTTP do cliente Alpha7.
 *
 * <p>
 * Fornece operações utilitárias para abertura de conexões HTTP,
 * leitura de respostas, validação de status e tratamento de erros
 * ao comunicar com a API do servidor.
 * </p>
 *
 * <p>
 * As classes concretas de serviço devem estender esta classe para reutilizar
 * o comportamento padrão de comunicação HTTP.
 * </p>
 *
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public abstract class BaseHttpService {

    /**
     * Tempo máximo de timeout em milissegundos para conexão e leitura.
     */
    private static final int TIMEOUT = 10000;

    /**
     * Lê a resposta HTTP e converte o conteúdo JSON para um tipo específico.
     *
     * <p>
     * Em caso de erro, tenta ler o {@code ErrorStream} antes de lançar exceção.
     * </p>
     *
     * @param connection conexão HTTP ativa
     * @param clazz tipo de retorno esperado
     * @param <T> tipo genérico do objeto esperado
     * @return objeto convertido a partir do JSON retornado
     * @throws Exception caso ocorra erro de comunicação ou conversão
     */
    protected <T> T readResponse(HttpURLConnection connection, Class<T> clazz) throws Exception {
        InputStream is = null;
        try {
            is = connection.getInputStream();
            
        } catch (IOException e) {
            is = connection.getErrorStream();
            if (is == null) throw e;
        }
        return JsonMapperFactory.get().readValue(is, clazz);
    }

    /**
     * Lê a resposta HTTP e converte para tipos genéricos complexos utilizando
     * {@link TypeReference}.
     *
     * @param connection conexão HTTP ativa
     * @param type referência de tipo genérico
     * @param <T> tipo genérico do retorno
     * @return objeto convertido a partir do JSON retornado
     * @throws Exception caso ocorra erro de comunicação ou conversão
     */
    protected <T> T readResponse(HttpURLConnection connection, TypeReference<T> type) throws Exception {
        try (InputStream is = connection.getInputStream()) {
            return JsonMapperFactory.get().readValue(is, type);
        }
    }

    /**
     * Abre uma conexão HTTP configurada para requisições padrão.
     *
     * @param url endereço da requisição
     * @param method método HTTP (GET, POST, PUT, DELETE, etc)
     * @return conexão configurada
     * @throws Exception caso ocorra falha ao abrir a conexão
     */
    protected HttpURLConnection openConnection(URL url, String method) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);
        connection.setRequestProperty("Accept", "application/json");
        return connection;
    }

    /**
     * Abre uma conexão HTTP preparada para envio de corpo JSON.
     *
     * <p>
     * Além das configurações padrão, esta conexão permite escrita de dados
     * no corpo da requisição.
     * </p>
     *
     * @param url endereço da requisição
     * @param method método HTTP (normalmente POST ou PUT)
     * @return conexão preparada para envio de dados
     * @throws Exception caso ocorra falha ao abrir a conexão
     */
    protected HttpURLConnection openConnectionWithBody(URL url, String method) throws Exception {
        HttpURLConnection connection = openConnection(url, method);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        return connection;
    }

    /**
     * Valida o código de status da resposta HTTP.
     *
     * <p>
     * Caso o status não esteja entre os esperados, tenta ler o corpo de erro
     * e lança {@link ServerException} com as informações retornadas pelo servidor.
     * </p>
     *
     * @param connection conexão HTTP utilizada
     * @param expected lista de códigos esperados (ex: 200, 201, 204)
     * @throws Exception caso o status seja inválido ou ocorra falha de leitura
     */
    protected void validateStatus(HttpURLConnection connection, int... expected) throws Exception {
        int status = connection.getResponseCode();

        for (int ok : expected) {
            if (status == ok) return;
        }

        ErrorResponseDTO errorResponse = null;
        try (InputStream is = connection.getErrorStream()) {
            if (is != null) {
                errorResponse = JsonMapperFactory.get().readValue(is, ErrorResponseDTO.class);
            }
        } catch (Exception ignored) {}

        if (errorResponse != null) {
            throw new ServerException(errorResponse);
        } else {
            throw new RuntimeException("Erro HTTP " + status);
        }
    }

    /**
     * Finaliza a conexão HTTP caso ainda esteja aberta.
     *
     * @param connection conexão a ser encerrada
     */
    protected void disconnect(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

    /**
     * Codifica valores para utilização segura em URLs.
     *
     * @param value valor a ser codificado
     * @return valor codificado em UTF-8
     * @throws RuntimeException caso ocorra erro na codificação
     */
    protected String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
