package br.fiap.pulso.modelo;

/**
 * Representa o dispositivo entregue temporariamente ao atendente.
 * @author Cainã Sandes Batista
 * @since 2026
 * @version 1.0
 */
public class Transmissor extends Dispositivo {
    /** @param identificador Identificação fictícia do transmissor. */
    public Transmissor(String identificador) {
        super(identificador);
    }

    /** @return Mensagem de disparo no ambiente simulado. */
    @Override
    public String acionar() {
        return "Disparo: OK";
    }
}
