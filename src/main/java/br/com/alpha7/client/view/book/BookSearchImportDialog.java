package br.com.alpha7.client.view.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;


/**
 * Diálogo para importação de livros via arquivo CSV.
 *
 * <p>
 * Este diálogo permite ao usuário arrastar e soltar um arquivo CSV ou selecionar
 * manualmente, exibindo a área de drop e botões de ação.
 * </p>
 *
 * <p>
 * O painel de conteúdo exibe a área de arraste e soltar, e o rodapé contém
 * os botões "Limpar" e "Importar". O arquivo selecionado fica disponível
 * através de {@link #getSelectedFile()}.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class BookSearchImportDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JButton btnClean;
	private JButton btnImport;
	
	private JPanel dropArea;
	
	private JLabel lblDrop;
	private File selectedFile;
	
    /**
     * Cria o diálogo de importação de livros.
     * 
     * @param owner janela pai do diálogo
     */
    public BookSearchImportDialog(Window owner) {
        super(owner, "Importar Livros", ModalityType.APPLICATION_MODAL);
        initComponents();
        initDragAndDrop();
    }
    
    /**
     * Inicializa os componentes visuais do diálogo.
     */
    private void initComponents() {
    	setLayout(new BorderLayout(10, 10));
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	
    	add(createContent(), BorderLayout.CENTER);
    	add(createFooter(), BorderLayout.SOUTH);
    	
    	setSize(500, 300);
    }
    
    /**
     * Cria o painel de conteúdo com a área de arraste e soltar arquivos.
     * 
     * @return painel de conteúdo
     */
    private JPanel createContent() {
    	
    	JPanel panel = new JPanel(new BorderLayout(10, 10));
    	panel.setBorder(new EmptyBorder(10, 10, 10, 10));
    	
    	dropArea = new JPanel(new BorderLayout());
    	dropArea.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
    	dropArea.setPreferredSize(new Dimension(200, 120));
    	
    	lblDrop = new JLabel("Arraste o arquivo aqui", SwingConstants.CENTER);
    	dropArea.add(lblDrop, BorderLayout.CENTER);
    	
    	panel.add(dropArea, BorderLayout.CENTER);
    	
    	return panel;
    }

    /**
     * Cria o rodapé com os botões "Limpar" e "Importar".
     * 
     * @return painel do rodapé
     */
    private JPanel createFooter() {
    	
    	JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	
    	btnClean = new JButton("Limpar");
    	btnImport = new JButton("Importar");
    	
    	footer.add(btnClean);
        footer.add(btnImport);
        
        btnClean.addActionListener(e -> clearSelection());
    	
    	return footer;
    }
    
    /**
     * Inicializa o suporte a arraste e soltar arquivos na área de drop.
     */
    private void initDragAndDrop() {

        dropArea.setTransferHandler(new TransferHandler() {

            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            @SuppressWarnings("unchecked")
            public boolean importData(TransferSupport support) {
                try {
                    List<File> files = (List<File>)
                            support.getTransferable()
                                   .getTransferData(DataFlavor.javaFileListFlavor);

                    if (!files.isEmpty()) {
                        setSelectedFile(files.get(0));
                        return true;
                    }
                } catch (Exception ignored) {}
                return false;
            }
        });
    }
    
    /**
     * Define o arquivo selecionado e atualiza o texto da área de drop.
     * 
     * @param file arquivo selecionado
     */
    private void setSelectedFile(File file) {
        this.selectedFile = file;
        lblDrop.setText("Arquivo selecionado: " + file.getName());
    }
    
    /**
     * Limpa a seleção do arquivo e atualiza o texto da área de drop.
     */
    private void clearSelection() {
        selectedFile = null;
        lblDrop.setText("Arraste o arquivo aqui");
    }

    /** Retorna o botão "Importar". */
	public JButton getBtnImport() {
		return btnImport;
	}
	/** Retorna o arquivo atualmente selecionado ou {@code null} se nenhum arquivo estiver selecionado. */
	public File getSelectedFile() {
		return selectedFile;
	}

}
