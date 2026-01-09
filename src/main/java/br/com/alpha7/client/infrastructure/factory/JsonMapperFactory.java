package br.com.alpha7.client.infrastructure.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Fábrica responsável por fornecer uma instância configurada de
 * {@link ObjectMapper} para uso na aplicação cliente Alpha7.
 *
 * <p>
 * Esta classe centraliza a configuração do mapeamento JSON, garantindo
 * padronização no tratamento de datas e serialização/deserialização
 * de objetos em toda a aplicação.
 * </p>
 *
 * <p>
 * A classe é final e não pode ser instanciada, atuando apenas como
 * um provedor estático do mapper configurado.
 * </p>
 *
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public final class JsonMapperFactory {

    /**
     * Instância única e compartilhada de {@link ObjectMapper}.
     *
     * <p>
     * O mapper é configurado para:
     * <ul>
     *   <li>Suportar o módulo {@link JavaTimeModule} para datas do Java 8+</li>
     *   <li>Evitar escrita de datas como timestamps</li>
     * </ul>
     * </p>
     */
    private static final ObjectMapper MAPPER = create();

    /**
     * Cria e configura a instância do {@link ObjectMapper}.
     *
     * @return mapper configurado
     */
    private static ObjectMapper create() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    /**
     * Construtor privado para impedir instanciação.
     */
    private JsonMapperFactory() {}

    /**
     * Retorna a instância configurada do {@link ObjectMapper}.
     *
     * <p>
     * Sempre retorna a mesma instância compartilhada.
     * </p>
     *
     * @return instância única do mapper JSON
     */
    public static ObjectMapper get() {
        return MAPPER;
    }
}
