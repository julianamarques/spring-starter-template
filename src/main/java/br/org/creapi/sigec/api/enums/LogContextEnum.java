package br.org.creapi.sigec.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogContextEnum {
    API_CONTEXT("api");

    private final String descricao;

    public String getDescricao(String arg) {
        return this.descricao + " :: " + arg;
    }
}
