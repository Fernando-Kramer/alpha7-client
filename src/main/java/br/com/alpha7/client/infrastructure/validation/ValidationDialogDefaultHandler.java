package br.com.alpha7.client.infrastructure.validation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 * Classe utilitária para exibição padronizada de diálogos de validação
 * e informações na interface Swing da aplicação cliente Alpha7.
 *
 * <p>
 * Fornece métodos estáticos para exibir mensagens de:
 * <ul>
 *   <li>Informação</li>
 *   <li>Atenção/aviso</li>
 *   <li>Erro</li>
 * </ul>
 * </p>
 *
 * <p>
 * Também permite exibir mensagens com tabelas de dados para apresentar
 * resultados de forma organizada.
 * </p>
 * 
 * <p>
 * Esta classe é composta apenas por métodos estáticos, mas possui
 * construtor público padrão para compatibilidade com frameworks,
 * embora a instanciação não seja necessária.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class ValidationDialogDefaultHandler {

    /**
     * Construtor público padrão.
     *
     * <p>
     * Não é necessário instanciar a classe para utilizar seus métodos.
     * </p>
     */
	public ValidationDialogDefaultHandler() {}
	
    /**
     * Exibe uma mensagem de informação simples.
     *
     * @param parent componente pai do diálogo
     * @param message mensagem a ser exibida
     */
    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Informação",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Exibe uma mensagem de aviso/atenção.
     *
     * @param parent componente pai do diálogo
     * @param message mensagem a ser exibida
     */
    public static void showWarning(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Atenção",
                JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Exibe uma mensagem de erro.
     *
     * @param parent componente pai do diálogo
     * @param message mensagem a ser exibida
     */
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Exibe uma mensagem de informação com uma tabela de dados.
     *
     * <p>
     * Se a lista de linhas for nula ou vazia, apenas a mensagem é exibida.
     * Caso contrário, a tabela é exibida abaixo da mensagem com colunas
     * e linhas fornecidas.
     * </p>
     *
     * @param parent componente pai do diálogo
     * @param message mensagem a ser exibida acima da tabela
     * @param columnNames nomes das colunas da tabela
     * @param rows conteúdo das linhas da tabela, cada elemento do tipo {@code Object[]}
     */
    public static void showInfoWithTable(
            Component parent,
            String message,
            String[] columnNames,
            List<Object[]> rows
    ) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Dimension fixedSize = new Dimension(550, 200);
        mainPanel.setPreferredSize(fixedSize);
        mainPanel.setMinimumSize(fixedSize);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFocusable(false);

        textPanel.add(textArea);
        mainPanel.add(textPanel, BorderLayout.NORTH);

        if (rows != null && !rows.isEmpty()) {

            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (Object[] row : rows) {
                model.addRow(row);
            }

            JTable table = new JTable(model);
            table.setFillsViewportHeight(true);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(fixedSize);

            mainPanel.add(scrollPane, BorderLayout.CENTER);

        } else {
            mainPanel.add(Box.createVerticalGlue(), BorderLayout.CENTER);
        }

        JOptionPane.showMessageDialog(
                parent,
                mainPanel,
                "Informação",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

}

