package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import components.MainFrame;
import components.NotesList;
import dbmodels.DBUtils;
import gestion.Etudiant;
import model.NoteMatiere;

public class EtudiantFrame extends MainFrame{
	
	NotesList nls = null;
	JPanel content = null;
	RequestNote req = null;
	Etudiant currentEtud = null;
	public static EtudiantFrame currentEtudiantFrame = null;
	
	public EtudiantFrame(Etudiant e) {
		
		super(500, 500, 0, 0);
		if(currentEtudiantFrame == null)
				currentEtudiantFrame = this;
		
		currentEtud = e;
		MainMenu nav = new MainMenu();

		content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setPreferredSize(new Dimension(1000,500));		
		
		nls = new NotesList(e, false, false, false);
		req = new RequestNote();
			
		nls.setVisible(true);
		req.setVisible(true);
		setJMenuBar(nav);

		
		//Init you have the notes
		content.add(nls);
		add(content,BorderLayout.CENTER);
		pack();
	}	
	
	class RequestNote extends JPanel {
		public RequestNote (){
			setLayout(new GridLayout(4,0));
			currentEtud.getNotesFromDb(1);
			currentEtud.getNotesFromDb(2);
			JComboBox<String> matsBox = new JComboBox<String>();
			matsBox.setPreferredSize(new Dimension(150,35));
			matsBox.addItem("Semestre 1");
			matsBox.addItem("Semestre 2");
			
			JComboBox<NoteMatiere> matiereBox = new JComboBox<NoteMatiere>();
			matiereBox.setPreferredSize(new Dimension(150,35));
			
			for (NoteMatiere nm : currentEtud.getNotesS1()) {
				matiereBox.addItem(nm);
			}
			
			JComboBox<String> noteBox = new JComboBox<String>();
			noteBox.setPreferredSize(new Dimension(150,35));

			noteBox.addItem("Note Ds");
			noteBox.addItem("Note Tp");
			noteBox.addItem("Note Examen");
			
			JPanel cont = new JPanel();
			cont.setLayout(new BorderLayout(10,10));
			cont.setPreferredSize(new Dimension(1000,80));
			cont.setVisible(true);
			
			matsBox.addItemListener(e -> {
			
				String sm = (String)(matsBox.getSelectedItem());
				matiereBox.removeAllItems();
				if(sm == "Semestre 1") {
					for (NoteMatiere nm : currentEtud.getNotesS1()) {
						matiereBox.addItem(nm);
					}
				}else {
					for (NoteMatiere nm : currentEtud.getNotesS2()) {
						matiereBox.addItem(nm);
					}
				}
				
				revalidate();
				repaint();

			});

			JTextArea comm = new JTextArea();
			comm.setBorder(BorderFactory.createTitledBorder("Commentaire "));
			comm.setPreferredSize(new Dimension(1000,200));
			
			JButton submitBtn = new JButton("Send");
			submitBtn.addActionListener(e->{
				Integer semestre =  matsBox.getSelectedIndex() + 1;
				NoteMatiere nm = (NoteMatiere)matiereBox.getSelectedItem();
				Integer typenote = noteBox.getSelectedIndex()+1;
				
				
				try {
					PreparedStatement prd = DBUtils.execute("INSERT INTO Requete VALUES (null,?,?,?,?)", new Object[] {semestre,nm.getId(),typenote,comm.getText()}); 

					if(prd != null) {
						System.out.println("moawjoud");
						int added = prd.executeUpdate();
						if(added == 1)
							JOptionPane.showMessageDialog(this, "Sent !"); 
						else
							JOptionPane.showMessageDialog(this, "Error In DB connection !"); 

					}else {
						System.out.println("null");
					}
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			});
			
			add(matsBox);
			cont.add(matiereBox,BorderLayout.EAST);
			cont.add(noteBox,BorderLayout.WEST);
			add(cont,BorderLayout.SOUTH);				
			add(comm);
			add(submitBtn);
			pack();
		}
	}
	
	class MainMenu extends JMenuBar{
		String currentItem = "req";

		public MainMenu() {
			// TODO Auto-generated constructor stub
			
			setPreferredSize(new Dimension(1000,30));
			
			JMenuItem logoutItem = new JMenuItem("LogOut");
			JMenuItem notesItem = new JMenuItem("Notes");
			JMenuItem noteRequestItem = new JMenuItem("Faire Demande");
			
			logoutItem.addActionListener(e-> {
				if(e.getSource()!=logoutItem)
					return;
				
				LoginUserFrame.openedLogin.setVisible(true);
				EtudiantFrame.currentEtudiantFrame.setVisible(false);
				JOptionPane.showMessageDialog(this, "Disconnected");
				System.out.println("Log Out");
			});
			
			
			noteRequestItem.addActionListener(e-> {
				if(e.getSource()!=noteRequestItem)
					return;

				
				//mark the current item
				currentItem = "req";
				
				content.removeAll();
				
				content.add(req);
				
				content.revalidate();
				content.repaint();
				pack();
				
				System.out.println("Demande verif");

			});
			
			notesItem.addActionListener(e-> {
				if(e.getSource()!=notesItem)
					return;
				
				//mark the current item
				currentItem = "nls";
				
				content.removeAll();
				
				content.add(nls);
				
				content.revalidate();
				content.repaint();
				pack();
				
				System.out.println("Check Notes");
				
			});
			

			add(notesItem);
			add(noteRequestItem);
			add(logoutItem);
		}
	}
}
	

