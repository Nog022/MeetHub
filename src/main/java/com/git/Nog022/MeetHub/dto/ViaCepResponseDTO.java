package com.git.Nog022.MeetHub.dto;

public record ViaCepResponseDTO(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf   ) {

}
