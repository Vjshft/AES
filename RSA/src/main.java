import javax.crypto.*;
import javax.crypto.spec.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;

//Clase Main principal 
public class main extends JFrame{
	private static String secretKey;
	private static String salt = "superCondimento";
	static IvParameterSpec ivspec;
	static SecretKeyFactory factory;
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	new main();
	
	}
	//Este programa usa JPnael para crear una interfaz gráfica para la interacción con el usuario
    //Aquí se setea el menu principal del Usuario

	public main() {
    
    //titulo de la ventana
	super("Cifrado y Descifrado AES-256");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 350);
    JPanel general = new JPanel();
    general.setLayout(new GridLayout(4, 1));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    add(general, BorderLayout.CENTER);

    //botones y labels del menu principal
    JLabel labelInstrucciones = new JLabel("Selecciona la operacion");
    labelInstrucciones.setHorizontalAlignment(0);
    general.add(labelInstrucciones,"North");
    JLabel labelInstrucciones2 = new JLabel("que quieras ejecutar:");
    general.add(labelInstrucciones2);
    JButton cifraTexto = new JButton("Cifrar Texto");
    general.add(cifraTexto);
    JButton cifraArchivo = new JButton("Cifrar Archivo");
    general.add(cifraArchivo);
    JButton descifraTexto = new JButton("Descifrar Texto");
    general.add(descifraTexto);
    JButton descifraArchivo = new JButton("Descifrar Archivo");
    general.add(descifraArchivo);
    JLabel Nombre1 = new JLabel("Creadores : Juan Jo A01654012");
    general.add(Nombre1);
    JLabel Nombre2 = new JLabel("Emilio !!!??  A0?????", JLabel.CENTER);
    general.add(Nombre2);


    //Los actionsListeners de los botones
    //El programa sepa que hacer cuando le das un click a uno de estos botones
    cifraTexto.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            CifradoTexto();
            setVisible(true);
        }
    });
    
    cifraArchivo.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            CifradoArchivo();
            setVisible(true);
        }
    });
    
    descifraTexto.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            DescifradoTexto();
            setVisible(true);
        }
    });
    
    descifraArchivo.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            DescifradoArchivo();
            setVisible(true);
        }
    });
    
    byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    ivspec = new IvParameterSpec(iv);
	try {
		factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		System.out.println("Algoritmo no encontrado");;
	}
    
    setVisible(true);
    }
	
	//clase para cifrar el texto
	public static void CifradoTexto() {
		JFrame ventanaCifradoTexto = new JFrame("Cifrado de Texto");
        ventanaCifradoTexto.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ventanaCifradoTexto.setSize(550, 400);
        ventanaCifradoTexto.setLocationRelativeTo(null);
        ventanaCifradoTexto.setResizable(false);
        JPanel panelDentro_1 = new JPanel();
        JPanel panelDentro_2 = new JPanel();
        JPanel panelGeneral = new JPanel(); 
        panelDentro_1.setLayout(new BorderLayout(10,10));
        panelDentro_2.setLayout(new BorderLayout(10,10));
        panelGeneral.setLayout(new FlowLayout());
        
        JLabel label_1 = new JLabel();
        JLabel label_2 = new JLabel();
        JLabel label_3 = new JLabel();
        label_1.setText("Inserte texto a cifrar");
        label_1.setHorizontalAlignment(0);
        label_2.setText("Inserte la contrasena");
        label_2.setHorizontalAlignment(0);
        label_3.setText("<html>NOTA: La contrasena debe ser de una longitud minima de 8 caracteres, y debe incluir como<br>"
        + "minimo un digito, una letra mayuscula, una minuscula y un caracter especial</html>");
        label_3.setFont(new Font("Arial", Font.ITALIC, 12));
        label_1.setFont(new Font("Arial", Font.BOLD, 16));
        label_2.setFont(new Font("Arial", Font.BOLD, 16));
        
        JTextArea textoCifrar = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textoCifrar);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        JPasswordField passwordField = new JPasswordField(15);
        char c = passwordField.getEchoChar();
        JButton cifra = new JButton("Cifrar");
        JButton desplegarCaracteres = new JButton("Desplegar Caracteres");
        
        panelDentro_1.add(label_1, "North");
        panelDentro_1.add(scrollPane, "Center");
        panelDentro_1.add(label_2, "South");
        panelDentro_2.add(passwordField, "North");
        panelDentro_2.add(desplegarCaracteres, "West");
        panelDentro_2.add(cifra, "East");
        panelDentro_2.add(label_3, "South");
        
        panelGeneral.add(panelDentro_1);
        panelGeneral.add(panelDentro_2);
        
        ventanaCifradoTexto.add(panelGeneral);
        ventanaCifradoTexto.setVisible(true);
        ventanaCifradoTexto.setAlwaysOnTop(true);
        
        desplegarCaracteres.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if(passwordField.getEchoChar() != 0) {
            		passwordField.setEchoChar((char) 0);
            		desplegarCaracteres.setText("Ocultar Caracteres");
            	}else {
            		passwordField.setEchoChar(c);
            		desplegarCaracteres.setText("Desplegar Caracteres");
            	}
            	
            	
            	
            }
        });
		
        
        cifra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	secretKey = passwordField.getText();
                String strToEncrypt = textoCifrar.getText();
                String salida;
                
                try {
        			KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
        			SecretKey temporal = factory.generateSecret(spec);
        		    SecretKeySpec key = new SecretKeySpec(temporal.getEncoded(), "AES");
        		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        			cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
        		    salida = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			salida = "Error al cifrar";
        		}
        	    
        	    textoCifrar.setText(salida);
        	    
            }
        });
	}
	
	public static void CifradoArchivo() {
		JFrame ventanaCifradoTexto = new JFrame("Cifrado de Archivo");
        ventanaCifradoTexto.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ventanaCifradoTexto.setSize(400, 250);
        ventanaCifradoTexto.setLocationRelativeTo(null);
        ventanaCifradoTexto.setResizable(false);
        JPanel panelDentro_1 = new JPanel();
        JPanel panelDentro_2 = new JPanel();
        JPanel panelGeneral = new JPanel(); 
        panelDentro_1.setLayout(new BorderLayout(10,10));
        panelDentro_2.setLayout(new BorderLayout(10,10));
        panelGeneral.setLayout(new FlowLayout());
        
        JLabel label_1 = new JLabel();
        JLabel label_2 = new JLabel();
        JLabel label_3 = new JLabel();
        label_1.setText("Introduzca la ruta completa del archivo a cifrar");
        label_1.setHorizontalAlignment(0);
        label_2.setText("Inserte la contrasena");
        label_2.setHorizontalAlignment(0);
        label_3.setText("<html>NOTA: La contrasena debe ser de una longitud minima de <br> 8 caracteres, y debe incluir como minimo"
        + "un digito, una letra <br>mayuscula, una minuscula y un caracter especial</html>");
        label_3.setFont(new Font("Arial", Font.ITALIC, 12));
        label_1.setFont(new Font("Arial", Font.BOLD, 16));
        label_2.setFont(new Font("Arial", Font.BOLD, 16));
        
        JTextField archivoCifrar = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        char c = passwordField.getEchoChar();
        JButton cifra = new JButton("Cifrar");
        JButton desplegarCaracteres = new JButton("Desplegar Caracteres");
        JButton seleccionaArchivo = new JButton("Selecciona un Archivo");
        
        panelDentro_1.add(label_1, "North");
        panelDentro_1.add(archivoCifrar, "West");
        panelDentro_1.add(seleccionaArchivo, "East");
        panelDentro_1.add(label_2, "South");
        panelDentro_2.add(passwordField, "North");
        panelDentro_2.add(desplegarCaracteres, "West");
        panelDentro_2.add(cifra, "East");
        panelDentro_2.add(label_3, "South");
        
        panelGeneral.add(panelDentro_1);
        panelGeneral.add(panelDentro_2);
        
        ventanaCifradoTexto.add(panelGeneral);
        ventanaCifradoTexto.setVisible(true);
        ventanaCifradoTexto.setAlwaysOnTop(true);
        
        desplegarCaracteres.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if(passwordField.getEchoChar() != 0) {
            		passwordField.setEchoChar((char) 0);
            		desplegarCaracteres.setText("Ocultar Caracteres");
            	}else {
            		passwordField.setEchoChar(c);
            		desplegarCaracteres.setText("Desplegar Caracteres");
            	}
            	
            	
            	
            }
        });
		
        seleccionaArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	ventanaCifradoTexto.setVisible(false);
            	JFileChooser fc = new JFileChooser();
            	int returnVal = fc.showOpenDialog(null);
            	 
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    archivoCifrar.setText(fc.getSelectedFile().getAbsolutePath()); 
                } 
                ventanaCifradoTexto.setVisible(true);
            	
            }
        });
        
        cifra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	secretKey = passwordField.getText();
                String path = archivoCifrar.getText();
                String extencion = path.substring(path.length()-3);
                String archivo = path.substring(0, path.length()-4);
                String salida;
                String line;
                
                try {
                	KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
         			SecretKey temporal = factory.generateSecret(spec);
         		    SecretKeySpec key = new SecretKeySpec(temporal.getEncoded(), "AES");
         		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
         			cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
                	BufferedReader reader = new BufferedReader(new FileReader(path));
                	BufferedWriter writer = new BufferedWriter(new FileWriter(archivo+".cfr."+extencion));
                	
                	while ((line = reader.readLine()) != null) {
                		salida = Base64.getEncoder().encodeToString(cipher.doFinal(line.getBytes("UTF-8")));
                		writer.write(salida);
                		writer.newLine();
        			}
                	reader.close();
                	writer.close();
                	
                	int result = JOptionPane.showOptionDialog(ventanaCifradoTexto, "Deseas sobreescribir el archivo?", "Advertencia",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
                    
                    if (result == JOptionPane.YES_OPTION){
                    	File sobreEscribir = new File(archivo+".cfr."+extencion);
                    	sobreEscribir.renameTo(new File(path));
                    	File borrar = new File(archivo+".cfr."+extencion);
                    	borrar.delete();
                    }
        			
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			JOptionPane.showMessageDialog(ventanaCifradoTexto, "Error al cifrar", "ERROR", JOptionPane.ERROR_MESSAGE);
        		}
        	    
            }
        });
	}

	public static void DescifradoTexto() {
		JFrame ventanaCifradoTexto = new JFrame("Descifrado de Texto");
        ventanaCifradoTexto.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ventanaCifradoTexto.setSize(550, 400);
        ventanaCifradoTexto.setLocationRelativeTo(null);
        ventanaCifradoTexto.setResizable(false);
        JPanel panelDentro_1 = new JPanel();
        JPanel panelDentro_2 = new JPanel();
        JPanel panelGeneral = new JPanel(); 
        panelDentro_1.setLayout(new BorderLayout(10,10));
        panelDentro_2.setLayout(new BorderLayout(10,10));
        panelGeneral.setLayout(new FlowLayout());
        
        JLabel label_1 = new JLabel();
        JLabel label_2 = new JLabel();
        JLabel label_3 = new JLabel();
        label_1.setText("Inserte texto a descifrar");
        label_1.setHorizontalAlignment(0);
        label_2.setText("Inserte la contrasena");
        label_2.setHorizontalAlignment(0);
        label_3.setText("<html>NOTA: La contrasena debe ser de una longitud minima de 8 caracteres, y debe incluir como<br>"
        + "minimo un digito, una letra mayuscula, una minuscula y un caracter especial</html>");
        label_3.setFont(new Font("Arial", Font.ITALIC, 12));
        label_1.setFont(new Font("Arial", Font.BOLD, 16));
        label_2.setFont(new Font("Arial", Font.BOLD, 16));
        
        JTextArea textoCifrar = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textoCifrar);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        JPasswordField passwordField = new JPasswordField(15);
        char c = passwordField.getEchoChar();
        JButton descifra = new JButton("Descifrar");
        JButton desplegarCaracteres = new JButton("Desplegar Caracteres");
        
        panelDentro_1.add(label_1, "North");
        panelDentro_1.add(scrollPane, "Center");
        panelDentro_1.add(label_2, "South");
        panelDentro_2.add(passwordField, "North");
        panelDentro_2.add(desplegarCaracteres, "West");
        panelDentro_2.add(descifra, "East");
        panelDentro_2.add(label_3, "South");
        
        panelGeneral.add(panelDentro_1);
        panelGeneral.add(panelDentro_2);
        
        ventanaCifradoTexto.add(panelGeneral);
        ventanaCifradoTexto.setVisible(true);
        ventanaCifradoTexto.setAlwaysOnTop(true);
        
        desplegarCaracteres.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if(passwordField.getEchoChar() != 0) {
            		passwordField.setEchoChar((char) 0);
            		desplegarCaracteres.setText("Ocultar Caracteres");
            	}else {
            		passwordField.setEchoChar(c);
            		desplegarCaracteres.setText("Desplegar Caracteres");
            	}
            	
            	
            	
            }
        });
        descifra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	secretKey = passwordField.getText();
                String strToDecrypt = textoCifrar.getText();
                String salida;
                
        	    try {
        			KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
        			SecretKey temporal = factory.generateSecret(spec);
        		    SecretKeySpec key = new SecretKeySpec(temporal.getEncoded(), "AES");
        		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        			cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
        		    salida = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        		    
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			salida = "Error al decifrar";
        			JOptionPane.showMessageDialog(ventanaCifradoTexto, "No se ha podido descifrar con esa llave", "ERROR", JOptionPane.ERROR_MESSAGE);
        		}
        	    
        	    textoCifrar.setText(salida);
            }
        });
	}
	
	public static void DescifradoArchivo() {
		JFrame ventanaCifradoTexto = new JFrame("Descifrado de Archivo");
        ventanaCifradoTexto.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ventanaCifradoTexto.setSize(400, 250);
        ventanaCifradoTexto.setLocationRelativeTo(null);
        ventanaCifradoTexto.setResizable(false);
        JPanel panelDentro_1 = new JPanel();
        JPanel panelDentro_2 = new JPanel();
        JPanel panelGeneral = new JPanel(); 
        panelDentro_1.setLayout(new BorderLayout(10,10));
        panelDentro_2.setLayout(new BorderLayout(10,10));
        panelGeneral.setLayout(new FlowLayout());
        
        JLabel label_1 = new JLabel();
        JLabel label_2 = new JLabel();
        JLabel label_3 = new JLabel();
        label_1.setText("Ruta completa del archivo a descifrar");
        label_1.setHorizontalAlignment(0);
        label_2.setText("Inserte la contrase�a");
        label_2.setHorizontalAlignment(0);
        label_3.setText("<html>NOTA: La contrase�a debe ser de una longitud m�nima de <br> 8 caracteres, y debe incluir como minimo"
        + "un digito, una letra <br>may�scula, una minuscula y un caracter especial</html>");
        label_3.setFont(new Font("Arial", Font.ITALIC, 12));
        label_1.setFont(new Font("Arial", Font.BOLD, 16));
        label_2.setFont(new Font("Arial", Font.BOLD, 16));
        
        JTextField archivoCifrar = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        char c = passwordField.getEchoChar();
        JButton cifra = new JButton("Descifrar");
        JButton desplegarCaracteres = new JButton("Desplegar Caracteres");
        JButton seleccionaArchivo = new JButton("Selecciona un Archivo");
        
        panelDentro_1.add(label_1, "North");
        panelDentro_1.add(archivoCifrar, "West");
        panelDentro_1.add(seleccionaArchivo, "East");
        panelDentro_1.add(label_2, "South");
        panelDentro_2.add(passwordField, "North");
        panelDentro_2.add(desplegarCaracteres, "West");
        panelDentro_2.add(cifra, "East");
        panelDentro_2.add(label_3, "South");
        
        panelGeneral.add(panelDentro_1);
        panelGeneral.add(panelDentro_2);
        
        ventanaCifradoTexto.add(panelGeneral);
        ventanaCifradoTexto.setVisible(true);
        ventanaCifradoTexto.setAlwaysOnTop(true);
        
        desplegarCaracteres.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if(passwordField.getEchoChar() != 0) {
            		passwordField.setEchoChar((char) 0);
            		desplegarCaracteres.setText("Ocultar Caracteres");
            	}else {
            		passwordField.setEchoChar(c);
            		desplegarCaracteres.setText("Desplegar Caracteres");
            	}
            	
            	
            	
            }
        });
		
        seleccionaArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	ventanaCifradoTexto.setVisible(false);
            	JFileChooser fc = new JFileChooser();
            	int returnVal = fc.showOpenDialog(null);
            	 
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    archivoCifrar.setText(fc.getSelectedFile().getAbsolutePath());
                } 
                ventanaCifradoTexto.setVisible(true);
            	
            }
        });
        
        cifra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	secretKey = passwordField.getText();
                String path = archivoCifrar.getText();
                String extencion = path.substring(path.length()-3);
                String archivo = path.substring(0, path.length()-4);
                String salida;
                String line;
                
                try {
                	KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
        			SecretKey temporal = factory.generateSecret(spec);
        		    SecretKeySpec key = new SecretKeySpec(temporal.getEncoded(), "AES");
        		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        			cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
                	BufferedReader reader = new BufferedReader(new FileReader(path));
                	BufferedWriter writer = new BufferedWriter(new FileWriter(archivo+".cfr."+extencion));
                	
                	while ((line = reader.readLine()) != null) {
                		salida = new String(cipher.doFinal(Base64.getDecoder().decode(line)));
                		writer.write(salida);
                		writer.newLine();
        			}
                	reader.close();
                	writer.close();
                	
                	JFileChooser guardar = new JFileChooser();
                    guardar.setDialogTitle("Selecciona donde guardar");   
                    int userSelection = guardar.showSaveDialog(ventanaCifradoTexto);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File archivoGuardar = new File(archivo+".cfr."+extencion);
                        archivoGuardar.renameTo(guardar.getSelectedFile());
                    }else {
                    	File archivoBorrar = new File(archivo+".cfr."+extencion);
                    	archivoBorrar.delete();
                    }
        			
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			JOptionPane.showMessageDialog(ventanaCifradoTexto, "Error al descifrar", "ERROR", JOptionPane.ERROR_MESSAGE);
        		}
            }
        });
	}
	
}
