package br.com.project.spring.starter.template.api.utils;

import br.com.project.spring.starter.template.api.enums.LogContextEnum;
import br.com.project.spring.starter.template.api.exceptions.ApiExternalException;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;

import java.util.UUID;

@Log4j2
public class LogUtils {
    private static final String CD_TRANSACAO = "cdTransacao";
    private static final String CD_CONTEXTO = "contexto";

    public static String gerarIdentificacaoUnica() {
        return UUID.randomUUID().toString();
    }

    public static void adicionarCdTransacao(String cdTransacao) {
        try {
            String codigoTransacao = cdTransacao != null ? cdTransacao : gerarIdentificacaoUnica();
            MDC.put(LogUtils.CD_TRANSACAO, String.format("[%s]", codigoTransacao));
        } catch (Exception e) {
            log.warn("Erro ao adicionar código de transação", e);
        }
    }

    public static void adicionarContextoMDC(LogContextEnum contexto, String arg) {
        try {
            MDC.put(LogUtils.CD_CONTEXTO, String.format("[%s]", contexto.getDescricao(arg)));
        } catch (Exception e) {
            log.warn("Erro ao adicionar contexto ao MDC", e);
        }
    }

    public static void limparContextoMDC(LogContextEnum logContext) {
        try {
            if (existeContexto(logContext)) {
                MDC.remove(CD_CONTEXTO);
            }
        } catch (Exception e) {
            log.warn(" Erro ao limpar o MDC", e);
        }
    }

    public static boolean existeContexto(LogContextEnum logContext) {
        try {
            String log = MDC.get(LogUtils.CD_CONTEXTO);
            if (log != null) {
                return log.matches(LogUtils.buildContextoRegex(logContext));
            }
        } catch (Exception e) {
            log.warn(" Erro ao recuperar código de transação do MDC", e);
        }
        return false;
    }

    private static String buildContextoRegex(LogContextEnum logContext) {
        return "[" + logContext.getDescricao() + ".*" + "]";
    }

    public static void logAvisoExterno(ApiExternalException exception) {
        log.warn("Um erro de client externo com o status {}: [ {} ] chamada: [ {} ]", exception.getHttpStatus().value(),
                exception.getReason(), exception.getMethodKey());
        log.warn("Response: [ {} ]", exception.getResponseBody());
    }

    public static void logAvisoExterno(Integer status, String exception) {
        log.error("Um erro externo de client com status {} ocorreu", status);
        log.error("Response: [ {} ]", exception);
    }
}
