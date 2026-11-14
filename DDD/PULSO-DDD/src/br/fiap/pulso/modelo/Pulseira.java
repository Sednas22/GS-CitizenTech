package br.fiap.pulso.modelo;

/**
 * Simula os alertas de vibração, luz e áudio da pulseira.
 * @author Cainã Sandes Batista
 * @since 2026
 * @version 1.1
 */
public class Pulseira extends Dispositivo {
    // Posições: 0 = vibração, 1 = luz, 2 = áudio.
    private boolean[] alertasAtivos;
    private boolean[] alertasComFalha;
    private boolean falhaComunicacao;

    /** @param identificador Identificação fictícia da pulseira. */
    public Pulseira(String identificador) {
        super(identificador);
    }

    /**
     * Configura os alertas e as falhas simuladas para a chamada atual.
     * @param alertasAtivos Modos de alerta selecionados pela pessoa.
     * @param alertasComFalha Modos com falha escolhidos no simulador.
     * @param falhaComunicacao Indica ausência de comunicação entre os dispositivos.
     */
    public void configurar(boolean[] alertasAtivos, boolean[] alertasComFalha,
                           boolean falhaComunicacao) {
        this.alertasAtivos = alertasAtivos.clone();
        this.alertasComFalha = alertasComFalha.clone();
        this.falhaComunicacao = falhaComunicacao;
    }

    private String resultado(int indice, String nome) {
        if (!alertasAtivos[indice]) return nome + ": desativado";
        if (falhaComunicacao || alertasComFalha[indice]) return nome + ": FALHOU";
        return nome + ": OK";
    }

    /** @return Resultado de cada modo de alerta. */
    @Override
    public String acionar() {
        return resultado(0, "Vibração") + "\n"
             + resultado(1, "Luz") + "\n"
             + resultado(2, "Áudio");
    }

    /** @return Verdadeiro se pelo menos um alerta selecionado funcionou. */
    public boolean algumAlertaFuncionou() {
        if (falhaComunicacao) return false;
        for (int i = 0; i < 3; i++) {
            if (alertasAtivos[i] && !alertasComFalha[i]) return true;
        }
        return false;
    }
}
