package br.com.alpha7.client.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


/**
 * Janela principal da aplicação cliente Alpha7.
 *
 * <p>
 * Esta classe estende {@link JFrame} e gerencia a interface principal,
 * incluindo:
 * <ul>
 *   <li>Painel de conteúdo central, onde os painéis dinâmicos são exibidos</li>
 *   <li>Menu superior com navegação entre as telas (Início, Livros, etc.)</li>
 * </ul>
 * </p>
 *
 * <p>
 * Os painéis são trocados dinamicamente através do método {@link #showPanel(JPanel)}.
 * </p>
 * 
 * <p>
 * Ao inicializar, o frame exibe o painel de boas-vindas {@link WelcomePanel}.
 * </p>
 * 
 * <p>
 * A janela é configurada com título, tamanho inicial e posição centralizada na tela.
 * </p>
 * 
 * <p>
 * Todas as interações de menu são tratadas via listeners, carregando o painel correspondente.
 * </p>
 * 
 * @author Fernando Kramer De Souza
 * @since 1.0.0
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;

    /**
     * Cria a janela principal da aplicação.
     *
     * <p>
     * Configura título, tamanho, operação de fechamento padrão e inicializa
     * os componentes da interface.
     * </p>
     */
	public MainFrame() {
		setTitle("Alpha Client");
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
	}
	

    /**
     * Inicializa os componentes da interface.
     *
     * <p>
     * Define o layout do frame, cria o menu superior e o painel central,
     * exibindo inicialmente o painel de boas-vindas.
     * </p>
     */
	private void initComponents() {
		setLayout(new BorderLayout());
		setJMenuBar(createMenuBar());
		
		contentPanel = new JPanel(new BorderLayout());
		add(contentPanel, BorderLayout.CENTER);
		
		showPanel(new WelcomePanel());
	}
	
    /**
     * Substitui o painel central pelo painel informado.
     *
     * @param panel painel a ser exibido no conteúdo central
     */
	private void showPanel(JPanel panel) {
		contentPanel.removeAll();
		contentPanel.add(panel, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
    /**
     * Cria a barra de menu superior.
     *
     * @return {@link JMenuBar} configurada com os menus da aplicação
     */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.add(getMenuHome());
		
		menuBar.add(getMenuBooks());
		
		return menuBar;
	}
	
    /**
     * Cria o menu "Início" com listener para exibir o {@link WelcomePanel}.
     *
     * @return {@link JMenu} configurado para o menu Início
     */
	private JMenu getMenuHome() {

	    JMenu menuHome = new JMenu("Início");

	    menuHome.addMenuListener(new MenuListener() {

	        @Override
	        public void menuSelected(MenuEvent e) {
	            showPanel(new WelcomePanel());
	        }

	        @Override
	        public void menuDeselected(MenuEvent e) {
	            // nada
	        }

	        @Override
	        public void menuCanceled(MenuEvent e) {
	            // nada
	        }
	    });

	    return menuHome;
	}
	
    /**
     * Cria o menu "Livros" com listener para exibir o {@link BookPanel}.
     *
     * @return {@link JMenu} configurado para o menu Livros
     */
	private JMenu getMenuBooks() {

	    JMenu menuBooks = new JMenu("Livros");

	    menuBooks.addMenuListener(new MenuListener() {

	        @Override
	        public void menuSelected(MenuEvent e) {
	            showPanel(new BookPanel());
	        }

	        @Override
	        public void menuDeselected(MenuEvent e) {
	            // nada
	        }

	        @Override
	        public void menuCanceled(MenuEvent e) {
	            // nada
	        }
	    });

	    return menuBooks;
	}
}
