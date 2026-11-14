package br.fiap.pulso;

import br.fiap.pulso.tela.TelaPulso;
import javax.swing.SwingUtilities;

/**
 * Ponto de entrada da demonstração de DDD do PULSO.
 * @author Cainã Sandes Batista
 * @since 2026
 * @version 1.0
 */
public class Main {
    /** @param args Argumentos não utilizados. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPulso().setVisible(true));
    }
}
