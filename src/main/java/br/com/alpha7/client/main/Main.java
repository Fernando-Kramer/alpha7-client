package br.com.alpha7.client.main;

import javax.swing.SwingUtilities;

import br.com.alpha7.client.view.MainFrame;

/**
 * Classe principal da aplicação Alpha7 Client.
 *
 * <p>
 * Esta classe contém o método {@code main}, que é o ponto de entrada da aplicação.
 * Inicializa a interface gráfica criando e exibindo o {@link MainFrame}.
 * </p>
 *
 * <p>
 * A inicialização do frame é feita na Event Dispatch Thread (EDT) utilizando
 * {@link SwingUtilities#invokeLater(Runnable)} para garantir que todos os componentes
 * Swing sejam criados e manipulados de forma segura.
 * </p>
 * 
 * <p>
 * Esta classe não mantém estado e sua única responsabilidade é iniciar a aplicação.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class Main {

    /**
     * Ponto de entrada da aplicação Alpha7 Client.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			
			MainFrame mainFrame = new MainFrame();
			mainFrame.setVisible(true);
			
		});
		
	}
	
}
