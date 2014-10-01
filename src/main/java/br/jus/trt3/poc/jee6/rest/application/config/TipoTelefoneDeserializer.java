/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt3.poc.jee6.rest.application.config;

import br.jus.trt3.poc.jee6.entity.TipoTelefone;
import br.jus.trt3.poc.jee6.entity.TipoTelefone_;
import br.jus.trt3.poc.jee6.repository.TipoTelefoneRepository;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 *
 * @author alexadb
 */
public class TipoTelefoneDeserializer extends JsonDeserializer<TipoTelefone> {
    
    @Inject
    private TipoTelefoneRepository tipoTelefoneRepository;

    @Override
    public TipoTelefone deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String descricao = node.get("tipo").getTextValue();
        TipoTelefone tipoTelefone = new TipoTelefone();
        tipoTelefone.setDescricao(descricao);
        List<TipoTelefone> tipoTelefones = tipoTelefoneRepository.findBy(tipoTelefone, TipoTelefone_.descricao);
        if (tipoTelefones == null || tipoTelefones.isEmpty()) {
            throw dc.mappingException("TipoTelefone inexistente");
        }
        return tipoTelefones.get(0);
    }
    
}
