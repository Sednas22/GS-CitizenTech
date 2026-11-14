package br.fiap.pulso.modelo;

import java.time.LocalDateTime;

/**
 * Representa uma tentativa de chamada (ENVIADA, CONFIRMADA ou FALHA).
 * @author Cainã Sandes Batista
 * @since 2026
 * @version 1.0
 */
public class Chamada {
    private final LocalDateTime dataHoraDisparo = LocalDateTime.now();
    private LocalDateTime dataHoraConfirmacao;
    private String status = "ENVIADA";

    /** @param status Status resultante da tentativa de disparo. */
    public void definirStatus(String status) {
        this.status = status;
    }

    /**
     * Confirma o recebimento somente quando a chamada foi enviada.
     * @return Verdadeiro se a confirmação foi registrada.
     */
    public boolean confirmar() {
        if (!"ENVIADA".equals(status)) return false;
        dataHoraConfirmacao = LocalDateTime.now();
        status = "CONFIRMADA";
        return true;
    }

    /** @return Status da chamada. */
    public String getStatus() {
        return status;
    }

    /** @return Horário em que a chamada foi disparada. */
    public LocalDateTime getDataHoraDisparo() {
        return dataHoraDisparo;
    }

    /** @return Horário de confirmação ou null, se nao houve confirmação. */
    public LocalDateTime getDataHoraConfirmacao() {
        return dataHoraConfirmacao;
    }
}
