package br.com.alura.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosModelos(@JsonProperty("modelos")List<Dado> modelos) {

//    @Override
//    public String toString() {
//        return "Cód: %s Marca: %s".formatted(this.codigo(), this.nome());
//    }
}
