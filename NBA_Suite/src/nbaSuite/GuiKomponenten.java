package nbaSuite;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Diese Klasse stellt Methoden zur Verfuegung, die die GUI Komponenten wie z.B. Buttons erzeugen
 * @author Andreas Heckl
 */
public class GuiKomponenten extends JFrame{
	
	public static JButton createButton(JPanel beinhaltendeKomponente, String beschriftung, int x, int y, int breite, int hoehe){
		JButton btn = new JButton(beschriftung);
		btn.setBounds(x, y, breite, hoehe);
		beinhaltendeKomponente.add(btn);
		return btn;
	}
	
	
	public static JLabel createJLabel(JPanel beinhaltendeKomponente, String beschriftung, int x, int y, int breite, int hoehe) {
		JLabel lbl = new JLabel(beschriftung);
		lbl.setFont(new Font("Calibri", Font.PLAIN, 16));
		lbl.setBounds(x, y, breite, hoehe);
		beinhaltendeKomponente.add(lbl);
		return lbl;
	}
	
	public static JTextField createTextField(JPanel beinhaltendeKomponente, int x, int y, int breite, int hoehe) {
		JTextField tf = new JTextField();
		tf.setBounds(x, y, breite, hoehe);
		beinhaltendeKomponente.add(tf);
		tf.setColumns(10);
		return tf;
	}
	
	public static JScrollPane createJScrollPane(JPanel beinhaltendeKomponente, int x, int y, int breite, int hoehe) {
		JScrollPane sp = new JScrollPane();
		sp.setBounds(x, y, breite, hoehe);
		beinhaltendeKomponente.add(sp);
		return sp;
	}
	


}
