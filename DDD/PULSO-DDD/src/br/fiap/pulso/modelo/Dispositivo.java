package br.fiap.pulso.modelo;

/**
 * Abstração comum para os dois dispositivos do PULSO.
 * @author Cainã Sandes Batista
 * @since 2026
 * @version 1.0
 */
public abstract class Dispositivo {
    private final String identificador;

    /** @param identificador Identificação fictícia do dispositivo. */
    protected Dispositivo(String identificador) {
        this.identificador = identificador;
    }

    /** @return Identificador do dispositivo. */
    public String getIdentificador() {
        return identificador;
    }

    /** @return Resultado simulado do acionamento. */
    public abstract String acionar();
}
