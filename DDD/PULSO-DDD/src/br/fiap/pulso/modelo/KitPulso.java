package br.fiap.pulso.modelo;

import java.util.Arrays;

/**
 * Agrupa os dois dispositivos e controla disparos, rechamadas e confirmações.
 * As preferências ficam fixas durante o atendimento.
 * @author Cainã Sandes Batista
 * @since 2026
 * @version 1.2
 */
public class KitPulso {
    private final Pulseira pulseira = new Pulseira("PUL-01");
    private final Transmissor transmissor = new Transmissor("TR-01");
    private boolean[] preferencias; // Fixadas no primeiro disparo.
    private Chamada ultimaChamada;
    private int tentativas;

    /**
     * Dispara ou repete a chamada, mantendo os alertas escolhidos pela pessoa.
     * As falhas representam condições do teste e podem variar por tentativa.
     * @param ativos Preferências de vibração, luz e áudio da pessoa.
     * @param falhas Falhas de vibração, luz e áudio nesta tentativa.
     * @param falhaComunicacao Falha de comunicação nesta tentativa.
     * @param nova Nova tentativa registrada no simulador.
     * @return Resultado do disparo e de cada alerta.
     * @throws IllegalArgumentException Se nenhum alerta estiver habilitado.
     * @throws IllegalStateException Se tentarem mudar preferências antes da confirmação.
     */
    public String disparar(boolean[] ativos, boolean[] falhas,
                           boolean falhaComunicacao, Chamada nova) {
        if (ativos.length != 3 || falhas.length != 3) {
            throw new IllegalArgumentException("Informe os três modos de alerta.");
        }
        if (preferencias == null) {
            if (!ativos[0] && !ativos[1] && !ativos[2]) {
                throw new IllegalArgumentException("Marque pelo menos um alerta.");
            }
            preferencias = ativos.clone();
        } else if (!Arrays.equals(preferencias, ativos)) {
            throw new IllegalStateException("Os alertas so podem mudar após a confirmação.");
        }

        boolean rechamada = ultimaChamada != null;
        if (rechamada && "ENVIADA".equals(ultimaChamada.getStatus())) {
            ultimaChamada.definirStatus("FALHA"); // Tentativa anterior sem confirmação.
        }

        pulseira.configurar(preferencias, falhas, falhaComunicacao);
        Dispositivo emissor = transmissor;
        Dispositivo receptor = pulseira;
        String disparo = falhaComunicacao
                ? "Disparo: FALHOU (sem comunicação)" : emissor.acionar();
        String resultado = receptor.acionar();
        boolean sucesso = pulseira.algumAlertaFuncionou();
        nova.definirStatus(sucesso ? "ENVIADA" : "FALHA");
        ultimaChamada = nova;
        tentativas++;

        return (rechamada ? "RECHAMADA " : "CHAMADA ") + tentativas + "\n"
             + disparo + "\n" + resultado + "\nStatus: " + nova.getStatus()
             + (sucesso ? "\nAguardando confirmação da pessoa."
                        : "\nFalha geral: nenhum alerta selecionado funcionou."
                        + "\nAjuste as falhas do teste e tente novamente.");
    }

    /**
     * Confirma somente a tentativa mais recente que chegou a algum alerta.
     * Ao confirmar, libera a configuração dos alertas para outro atendimento.
     * @param chamada Tentativa que a pessoa está confirmando.
     * @return Verdadeiro se foi possível confirmar.
     */
    public boolean confirmar(Chamada chamada) {
        if (chamada == null || ultimaChamada != chamada || !chamada.confirmar()) {
            return false;
        }
        preferencias = null;
        ultimaChamada = null;
        tentativas = 0;
        return true;
    }
}
