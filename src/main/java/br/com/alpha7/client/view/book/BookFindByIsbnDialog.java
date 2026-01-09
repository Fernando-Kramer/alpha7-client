package br.com.alpha7.client.view.book;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * Diálogo modal para pesquisar um livro por ISBN na aplicação Alpha7 Client.
 *
 * <p>
 * Este diálogo exibe um campo de texto para o ISBN e dois botões:
 * <ul>
 *   <li>{@code Pesquisar} — dispara a ação de pesquisa definida via {@link #onSearch(Consumer)}</li>
 *   <li>{@code Cancelar} — fecha o diálogo sem ação</li>
 * </ul>
 * </p>
 *
 * <p>
 * O diálogo é configurado como {@link ModalityType#APPLICATION_MODAL} e centralizado
 * em relação à janela pai.
 * </p>
 * 
 * <p>
 * O comportamento do botão Pesquisar é definido via {@link Consumer} passado
 * para o método {@link #onSearch(Consumer)}.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class BookFindByIsbnDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtIsbn;
    private JButton btnSearch;
    private JButton btnCancel;

    private Consumer<String> onSearch;

    /**
     * Cria o diálogo de pesquisa por ISBN.
     *
     * @param parent janela pai para centralização e modalidade
     */
    public BookFindByIsbnDialog(Window parent) {
        super(parent, "Pesquisar por ISBN", ModalityType.APPLICATION_MODAL);
        initComponents();
        setLocationRelativeTo(parent);
    }

    /**
     * Inicializa os componentes do diálogo: campo de texto e botões.
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(400, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel center = new JPanel(new MigLayout(
                "insets 10, fillx",
                "[right][grow]",
                "[]"
        ));

        center.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField();
        center.add(txtIsbn, "growx");

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSearch = new JButton("Pesquisar");
        btnCancel = new JButton("Cancelar");

        footer.add(btnSearch);
        footer.add(btnCancel);

        add(center, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> dispose());
        btnSearch.addActionListener(e -> fireSearch());
    }

    /**
     * Dispara a ação de pesquisa definida via {@link #onSearch(Consumer)} e fecha o diálogo.
     */
    private void fireSearch() {
        if (onSearch != null) {
            onSearch.accept(txtIsbn.getText());
        }
        dispose();
    }

    /**
     * Define a ação a ser executada quando o botão Pesquisar for pressionado.
     *
     * @param action {@link Consumer} que recebe o ISBN informado no campo de texto
     */
    public void onSearch(Consumer<String> action) {
        this.onSearch = action;
    }
}

