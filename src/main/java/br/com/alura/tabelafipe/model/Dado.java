package br.com.alura.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dado(@JsonProperty("codigo") String codigo,
                   @JsonProperty("nome") String descricao) {
    @Override
    public String toString() {
        return "Cód: %s Descrição: %s".formatted(this.codigo(), this.descricao());
    }
}
