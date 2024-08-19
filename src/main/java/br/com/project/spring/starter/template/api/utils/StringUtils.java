package br.com.project.spring.starter.template.api.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.util.Objects;

public class StringUtils {
    public static String currencyFormat(BigDecimal valor, Integer casasDecimais) {
        if (Objects.isNull(valor)) {
            valor = BigDecimal.ZERO;
        }

        int tempDigit = casasDecimais <= 0 ? 2 : casasDecimais;
        String valueFormat = String.format("%,.2f", valor.setScale(tempDigit, RoundingMode.DOWN));

        if (casasDecimais <= 0) {
            return valueFormat.split(",")[0];
        }

        return valueFormat;
    }

    public static String removeAccentuation(String string) {
        CharSequence charSequence = new StringBuilder(string);

        return Normalizer.normalize(charSequence, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
