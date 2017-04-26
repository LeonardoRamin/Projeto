package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import dao.Conexao;
import dao.DAOGenerico;
import vo.Marca;

public class FrmMarca extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Marca marca;
	private JPanel contentPane;
	private JTextField textFieldNome;
	private JLabel imgSimbolo;
	private JTextArea textAreaDescricao;
	private File file;
	private JFileChooser fc;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMarca frame = new FrmMarca();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmMarca() {
		@SuppressWarnings("unused")
		EntityManager em = Conexao.getEntityManager();
		fc = new JFileChooser();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblMarca.setBounds(155, 11, 130, 32);
		contentPane.add(lblMarca);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 98, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setBounds(10, 123, 69, 14);
		contentPane.add(lblDescrio);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(50, 95, 106, 20);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(76, 118, 175, 130);
		contentPane.add(textAreaDescricao);
		
		imgSimbolo = new JLabel();
		imgSimbolo.setBounds(314, 11, 89, 82);
		contentPane.add(imgSimbolo);
		
		JButton btnSmbolo = new JButton("S\u00EDmbolo");
		btnSmbolo.addActionListener(new ActionListener() {
			

			

			public void actionPerformed(ActionEvent e) {
				fc.setDialogTitle("Ecolha uma imagem");
				fc.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos de Imagens", "jpg", "jpeg", "png", "gif", "bitmap"));
				fc.setAcceptAllFileFilterUsed(false);
				fc.showOpenDialog(FrmMarca.this);
				file = fc.getSelectedFile();
				
				BufferedImage myPicture;
				try {
					myPicture = ImageIO.read(file);

					ImageIcon img = new ImageIcon(myPicture);
					Image ni = img.getImage().getScaledInstance(imgSimbolo.getWidth(), imgSimbolo.getHeight(), Image.SCALE_SMOOTH);
					img = new ImageIcon(ni);
					
					imgSimbolo.setIcon(img);
					imgSimbolo.setText("Símbolo");
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(getContentPane(), "Erro ao carregar imagem");
				}
			}
		});
		btnSmbolo.setBounds(314, 150, 89, 23);
		contentPane.add(btnSmbolo);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marca = new Marca();
				marca.setNome(textFieldNome.getText());
				marca.setDescricao(textAreaDescricao.getText());
//				marca.setSimbolo(file.getAbsolutePath());
				
				
				new DAOGenerico<Marca>().salvar(marca);
				JOptionPane.showMessageDialog(null, "Salvou!");
			}
		});
		btnSalvar.setBounds(314, 227, 89, 23);
		contentPane.add(btnSalvar);
		

	}
}
