import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;


/*

by: Cauan de Paula, Gustavo Candido e Heleno Matos - 4SIT

*/



public class Atividade_word extends JFrame {
	private JLabel label1, label2, label3;
	private JButton btGravar, btAbrir, btLimpar, btCriar, btExcluir, btConsultar;
	private JTextField tfTexto;
	private TextArea taTexto;
	private FileDialog fdAbrir, fdSalvar;
	private String nome_do_arquivo;

	public static void main(String args[]) {
		JFrame frame = new Atividade_word();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public Atividade_word() {
		inicializarComponentes();
		definirEventos();
	}

	public void inicializarComponentes() {
		setTitle("Salvando Arquivos");
		setLayout(null);
		setBounds(250, 50, 640, 300);
		setResizable(false);
		label1 = new JLabel("Digite o texto aqui: ");
		label1.setBounds(5, 5, 200, 20);
		label2 = new JLabel("Status: ");
		label2.setBounds(5, 240, 200, 20);
		label3 = new JLabel("Diretórios: ");
		label3.setBounds(500, 5, 200, 20);
		btGravar = new JButton("Gravar");
		btGravar.setBounds(200, 210, 100, 25);
		btAbrir = new JButton("Abrir");
		btAbrir.setBounds(80, 210, 100, 25);
		btLimpar = new JButton("Limpar");
		btLimpar.setBounds(320, 210, 100, 25);
		btCriar = new JButton("Criar");
		btCriar.setBounds(500, 40, 100, 25);
		btConsultar = new JButton("Consultar");
		btConsultar.setBounds(500, 80, 100, 25);
		btExcluir = new JButton("Excluir");
		btExcluir.setBounds(500, 120, 100, 25);
		tfTexto = new JTextField();
		tfTexto.setBounds(50, 240, 430, 20);
		tfTexto.setEditable(false);
		taTexto = new TextArea();
		taTexto.setBounds(5, 25, 480, 180);
		fdAbrir = new FileDialog(this, "Abrir arquivo", FileDialog.LOAD);
		fdSalvar = new FileDialog(this, "Salvar arquivo", FileDialog.SAVE);
		add(label1);
		add(label2);
		add(label3);
		add(tfTexto);
		add(taTexto);
		add(btGravar);
		add(btAbrir);
		add(btLimpar);
		add(btCriar);
		add(btConsultar);
		add(btExcluir);

	}

	public void definirEventos() {
		btCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String caminho = JOptionPane.showInputDialog(null,"Digite um caminho para criar um diretorio: ");
				String nome = JOptionPane.showInputDialog(null,"Digite o nome do diretorio: ");
				File dir = new File(caminho+"/"+nome);
				if (!dir.exists()){
					dir.mkdir();
				}
					
				System.out.println("Diretorios criados: "+caminho+"/"+nome);
			}
		});
		btConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String caminho = JOptionPane.showInputDialog(null,"Digite o caminho do diretorio que deseja consultar: ");
				File dir = new File(caminho);
				if(dir.isDirectory()){
				System.out.println("Conteudo do diretorio: " +caminho);
				String s[] = dir.list();
				for(int i = 0; i <s.length; i++){
					System.out.println(s[i]);
				}
		 	}	else{
					System.out.println("Diretório inválido");	
			}
			}
		});
		btExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String caminho = JOptionPane.showInputDialog(null,"Digite o caminho do diretorio que deseja excluir: ");
				File dir = new File(caminho);
				String x = "";
				if(dir.isDirectory()){
					if(dir.delete()){
					x = dir.getName() + " excluido com sucesso";
				} else{
					if(excluirFilhos(dir)){
						x = dir.getName() + " excluido com sucesso";
					} else {
						x= "Falha na exclusão do diretório: " +dir.getName();
					}
				}
					System.out.println(x);
				}
				
			}
			private boolean excluirFilhos(File dir){
				if(dir.isDirectory()){
					String [] arquivos = dir.list();
					for(int i = 0; i < arquivos.length; i++){
						boolean success = excluirFilhos(new File(dir, arquivos[i]));
						if (success){
							System.out.println("Excluido: "+arquivos[i]);
						} else {
							System.out.println("O diretório não pode ser excluido");
							return false;
						}
					}
				}
				return dir.delete();
			}
		});
		btLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taTexto.setText("");
				tfTexto.setText("");
			}
		});
		btGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fdSalvar.setVisible(true);
					if (fdSalvar.getFile() == null) {
						return;
					}
					nome_do_arquivo = fdSalvar.getDirectory()
							+ fdSalvar.getFile();
					FileWriter out = new FileWriter(nome_do_arquivo);
					out.write(taTexto.getText());
					out.close();
					tfTexto.setText("Arquivo gravado com sucesso");
				} catch (IOException erro) {
					tfTexto.setText("Erro ao gravar no arquivo"
							+ erro.toString());
				}

			}
		});
		btAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fdAbrir.setVisible(true);
					if (fdAbrir.getFile() == null) {
						return;
					}
					nome_do_arquivo = fdAbrir.getDirectory()
							+ fdAbrir.getFile();
					FileReader in = new FileReader(nome_do_arquivo);
					String s = "";
					int i = in.read();
					while (i != -1) {
						s = s + (char) i;
						i = in.read();
					}
					taTexto.setText(s);
					in.close();
					tfTexto.setText("Arquivo aberto com sucesso");
				} catch (IOException erro) {
					tfTexto.setText("Erro ao abrir no arquivo"
							+ erro.toString());
				}

			}
		});
	}

}