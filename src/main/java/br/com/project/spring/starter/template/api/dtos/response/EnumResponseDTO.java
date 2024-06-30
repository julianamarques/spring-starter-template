package br.com.project.spring.starter.template.api.dtos.response;

import br.com.project.spring.starter.template.api.enums.GenericEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnumResponseDTO {
    private String valor;
    private String descricao;

    public static <E extends Enum<E> & GenericEnum> List<EnumResponseDTO> converterParaListaDTO(Class<E> enumValue) {
        return Arrays.stream(enumValue.getEnumConstants()).map(EnumResponseDTO::converterParaDTO).toList();
    }

    public static <E extends Enum<E> & GenericEnum> EnumResponseDTO converterParaDTO(E enumValue) {
        return Objects.nonNull(enumValue) ? new EnumResponseDTO(enumValue.name(), enumValue.getDescricao()) : null;
    }
}
