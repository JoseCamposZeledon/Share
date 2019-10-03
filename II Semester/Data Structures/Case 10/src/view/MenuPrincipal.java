package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

public class MenuPrincipal extends JFrame {

	private JPanel contentPane;
	private JButton btnConectar, btnDesconectar, btnVerInfo, btnLeerJson;
	private JTree treeJerarquia;
	
	/**
	 * Create the frame.
	 */
	public MenuPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 700);
		setResizable(false);
		setTitle("aaa");
		
		// Ventana
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// Botones
		btnConectar= new JButton("Conectar Sensor");
		btnConectar.setBounds(15, 620, 150, 30);
		
		btnDesconectar= new JButton("Desconectar Sensor");
		btnDesconectar.setBounds(180, 620, 180, 30);
		
		btnVerInfo = new JButton("Ver Información");
		btnVerInfo.setBounds(375, 620, 150, 30);
		
		// Arbol
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("AAA");
		treeJerarquia = new JTree(top);
		
		
		
		// Render
		contentPane.add(btnConectar);
		contentPane.add(btnDesconectar);
		contentPane.add(btnVerInfo);
		contentPane.add(treeJerarquia);
	}
	
}
