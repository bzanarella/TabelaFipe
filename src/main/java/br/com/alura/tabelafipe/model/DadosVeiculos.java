package br.com.alura.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculos(@JsonProperty("Valor")String valor,
                            @JsonProperty("Marca")String marca,
                            @JsonProperty("Modelo")String modelo,
                            @JsonProperty("AnoModelo")String ano,
                            @JsonProperty("Combustivel")String combustivel) {
}
