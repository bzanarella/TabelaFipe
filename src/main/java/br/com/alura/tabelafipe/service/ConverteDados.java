package br.com.alura.tabelafipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverteDados implements IConverteDados {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterListaDaados(String json, Class<T> classe) {

        CollectionType lista = mapper.getTypeFactory()
                    .constructCollectionType(List.class, classe);

        //        List<Dado> marcas = mapper.readValue(json, new TypeReference<List<Dado>>(){});
//        marcas.forEach(System.out::println);


        try {
                return mapper.readValue(json, lista);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
}
