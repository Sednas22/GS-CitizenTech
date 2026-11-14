package br.fiap.pulso.tela;

import br.fiap.pulso.modelo.Chamada;
import br.fiap.pulso.modelo.KitPulso;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Simulador interativo: preferências fixas ate a confirmação e rechamadas livres.
 * @author Cainã Sandes Batista
 * @since 2026
 * @version 1.2
 */
public class TelaPulso extends JFrame {
    private final KitPulso kit = new KitPulso();
    private Chamada chamada;

    // Preferências da pessoa: ficam bloqueadas durante todo o atendimento.
    private final JCheckBox vibracao = new JCheckBox("Vibração", true);
    private final JCheckBox luz = new JCheckBox("Luz", true);
    private final JCheckBox audio = new JCheckBox("Áudio", false);

    // Condições do teste: podem variar de uma tentativa para a próxima.
    private final JCheckBox falhaVibracao = new JCheckBox("Vibração");
    private final JCheckBox falhaLuz = new JCheckBox("Luz");
    private final JCheckBox falhaAudio = new JCheckBox("Áudio");
    private final JCheckBox falhaComunicacao = new JCheckBox("Comunicação");

    private final JTextArea saida = new JTextArea(12, 47);
    private final JButton disparar = new JButton("Disparar chamada");
    private final JButton confirmar = new JButton("Confirmar recebimento");

    /** Cria a janela e configura as ações dos botões. */
    public TelaPulso() {
        setTitle("PULSO - Simulação de chamadas");
        setSize(710, 470);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topo = new JPanel(new GridLayout(4, 1));
        topo.add(new JLabel(" PULSO | Simulador acadêmico (sem hardware)"));

        JPanel modos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modos.add(new JLabel("Alertas escolhidos pela pessoa:"));
        modos.add(vibracao);
        modos.add(luz);
        modos.add(audio);
        topo.add(modos);

        JPanel testes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        testes.add(new JLabel("Simular falha em (pode marcar várias):"));
        testes.add(falhaVibracao);
        testes.add(falhaLuz);
        testes.add(falhaAudio);
        testes.add(falhaComunicacao);
        topo.add(testes);
        topo.add(new JLabel(" Alertas travam após o 1º disparo; falhas de teste podem mudar na rechamada."));
        add(topo, BorderLayout.NORTH);

        saida.setEditable(false);
        saida.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        saida.setText("Marque os alertas e clique em Disparar chamada.\n"
            + "Pode rechamar sem confirmar, mas não mudar os alertas da pessoa.\n"
            + "Se todos falharem, altere as falhas do teste e rechame.\n");
        add(new JScrollPane(saida), BorderLayout.CENTER);

        confirmar.setEnabled(false);
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botoes.add(disparar);
        botoes.add(confirmar);
        add(botoes, BorderLayout.SOUTH);

        disparar.addActionListener(e -> dispararChamada());
        confirmar.addActionListener(e -> confirmarChamada());
        getRootPane().setDefaultButton(disparar);
    }

    // Somente a configuração da pessoa fica travada (não os testes de falha).
    private void bloquearAlertas(boolean bloquear) {
        vibracao.setEnabled(!bloquear);
        luz.setEnabled(!bloquear);
        audio.setEnabled(!bloquear);
    }

    private void dispararChamada() {
        try {
            boolean[] ativos = {vibracao.isSelected(), luz.isSelected(), audio.isSelected()};
            boolean[] falhas = {falhaVibracao.isSelected(), falhaLuz.isSelected(), falhaAudio.isSelected()};
            Chamada nova = new Chamada();
            String resultado = kit.disparar(ativos, falhas, falhaComunicacao.isSelected(), nova);
            chamada = nova;
            saida.append("\n--- TENTATIVA DE CHAMADA ---\n" + resultado + "\n");

            bloquearAlertas(true);
            disparar.setText("Repetir disparo");
            confirmar.setEnabled("ENVIADA".equals(chamada.getStatus()));
            saida.setCaretPosition(saida.getDocument().getLength());
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void confirmarChamada() {
        if (chamada != null && kit.confirmar(chamada)) {
            String hora = chamada.getDataHoraConfirmacao().format(
                DateTimeFormatter.ofPattern("HH:mm:ss"));
            saida.append("Confirmação: OK (" + hora + ")\nStatus: CONFIRMADA\n"
                + "Configurações liberadas para o próximo atendimento.\n");
            confirmar.setEnabled(false);
            bloquearAlertas(false);
            disparar.setText("Disparar chamada");
            chamada = null;
            saida.setCaretPosition(saida.getDocument().getLength());
        }
    }
}
