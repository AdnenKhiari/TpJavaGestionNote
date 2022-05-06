package components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gestion.Classe;
import gestion.Etudiant;
import model.Matiere;
import model.NoteMatiere;

public class ModifierNoteFrame extends MainFrame implements ActionListener {
	
	JButton s1Button ;
	JButton s2Button;
	
	JPanel s1Frame;
	JPanel s2Frame;
	
	Etudiant etud;
	
	public ModifierNoteFrame() {
		
		super(500,500,50, 50);
		
		setLayout(new BorderLayout());
		
		JLabel titleLabel = new JLabel("Chosir le semestre approprie");
		
		
		//the buttons Ui
		JPanel scontainerFrame = new JPanel();
		scontainerFrame.setLayout(new BorderLayout(20,20));
		
		s1Button= new JButton("Semestre 1");
		s2Button= new JButton("Semestre 2");
		
		
		s1Button.addActionListener(this);
		s2Button.addActionListener(this);

		scontainerFrame.add(titleLabel,BorderLayout.NORTH);
		scontainerFrame.add(s1Button,BorderLayout.EAST);
		scontainerFrame.add(s2Button,BorderLayout.WEST);
		scontainerFrame.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		
		s1Frame = new JPanel();
		s2Frame = new JPanel();
		

		s1Frame.setVisible(true);
		s2Frame.setVisible(true);

		
		add(s1Frame);
		add(s2Frame);


		add(scontainerFrame,BorderLayout.NORTH);
		
		setVisible(true);
	}
	
	
	void displayNotes(JPanel panel) {
		
		ArrayList<Matiere> mtsArrayList = new ArrayList<Matiere>();
		mtsArrayList.add(new Matiere(1, 1, 1, 1,"Matiere 1",-1,-1,-1));
		mtsArrayList.add(new Matiere(1, 1, 1, 1,"Matiere 2",-1,-1,-1));
		mtsArrayList.add(new Matiere(1, 1, 1, 1,"Matiere 3",-1,-1,-1));
		mtsArrayList.add(new Matiere(1, 1, 1, 1,"Matiere 4",-1,-1,-1));

		etud= new Etudiant("01-1", "00000000", "Ahmed");
		
		etud.setListeMatiereS1(mtsArrayList);
		
		panel.removeAll();
		
		panel.setLayout(new BorderLayout(20,20));
		add(new JLabel("Hii"));
		JComboBox<String> matieresBox = new JComboBox<String>();
		
		for (Matiere nm : mtsArrayList) {
			System.out.println((nm).toString());
			matieresBox.addItem((nm).toString());
		}

		JPanel nt = new JPanel();
		nt.setLayout(new FlowLayout());

		
		panel.add(matieresBox,BorderLayout.WEST);
		panel.setVisible(true);
		//System.out.println(panel.isVisible());
		panel.repaint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==s1Button) {
			System.out.println(etud.toString());
			displayNotes(s1Frame);
			s2Frame.setVisible(false);
			s1Frame.setVisible(true);
			
			this.repaint();
		}else {
			displayNotes(s2Frame);
			s2Frame.setVisible(false);
			if(s1Frame != null)
				s1Frame.setVisible(true);
		}
	}
}
