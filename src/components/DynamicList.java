package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import java.lang.*;
import java.util.*;
public abstract class DynamicList extends JPanel{
	
	private ArrayList<ArrayList<Object>> formattedDataObject = null;
	
	protected Boolean modPermission = false;
	protected Boolean delPermission = false;
	protected Boolean addPermission = false;
	
	private JTable tb = null;
	Object[][] objects = null;
	private int selectedRowIndex = -1;
	
	private ArrayList<Integer> removedObjects = null;
	private ArrayList<Integer> addedObjects = null;
	
	private DefaultTableModel tbModel = null;
	
	private String[] headers;
	
	private ArrayList<String> getRowAt(int i) {
		ArrayList<String> arr = new ArrayList<String>();
		for(int x = 0;x < tbModel.getColumnCount() ;x++) {
			arr.add((String)tb.getValueAt(i, x));
		}
		return arr;
	}
	
	private ArrayList<ArrayList<ArrayList<String>>> getModifiedRows() {
		ArrayList<ArrayList<ArrayList<String>>> arr = new ArrayList<ArrayList<ArrayList<String>>>();
		for(int i = 0;i< formattedDataObject.size();i++) {
			ArrayList<ArrayList<String>> coupleRows = new ArrayList<ArrayList<String>>() ;
			coupleRows.add((ArrayList<String>)(ArrayList<?>)(formattedDataObject.get(i)));
			coupleRows.add(getRowAt(i));
			if(!coupleRows.get(0).equals(coupleRows.get(1))) {
				if(removedObjects.indexOf(i) == -1) {
					arr.add(coupleRows);
				}
			}
		}
		return arr;
	}
	
	private ArrayList<ArrayList<String>> getRemovedRows() {
		
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		for(Integer u : removedObjects) {
			//check if it needs to be deleted
			int pos = addedObjects.indexOf(u);
			
			
			//the element is meant to be deleted so skip
			if(pos != -1) {
				continue;
			}
			
			arr.add(getRowAt(u));
			
		}
		return arr;
	}
	
	private ArrayList<ArrayList<String>> getNewRows() {
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		for(Integer u : addedObjects) {
			//check if it needs to be deleted
			int pos = removedObjects.indexOf(u);
			
			//the element is meant to be deleted so skip
			if(pos != -1) {
				continue;
			}
			
			arr.add(getRowAt(u));
			
		}
		return arr;
	}
	
	protected void UpdateData(String[] headers,ArrayList<ArrayList<String>> data) {
		
		
		removeAll();
		
		
		this.headers = headers;
		formattedDataObject = new ArrayList<ArrayList<Object>>(data.size());

		
		//format it to match the correct permissions
		for(ArrayList<String> os : data) {
			ArrayList<Object> als = new ArrayList<Object>();
			for(Object obj : os) {
				als.add(obj);
			}
			

			formattedDataObject.add(als);
		}
		
		
		//init the data for the table
		objects = new Object[formattedDataObject.size()][];
		for(int i = 0;i < objects.length;i++ ) {
			objects[i] = formattedDataObject.get(i).toArray();
		}
		
		setLayout(new BorderLayout());

		
		//create the table
		tbModel = new DefaultTableModel(objects,headers);
		tb = new JTable(tbModel) {

			@Override
			public boolean isCellEditable(int row,int column) {
				return modPermission;
			}
			
			@Override
	         public Component prepareRenderer(TableCellRenderer renderer,int row, int column) 
             {
                    Component c = super.prepareRenderer(renderer, row, column);
                	if(removedObjects.contains(row)) {
                		c.setBackground(Color.red);
                	}else if(addedObjects.contains(row)) {
                		c.setBackground(Color.gray);
                	}else {
                		c.setBackground(Color.white);
                	}
                    return c;
             }
		};
		
		tb.setMaximumSize(new Dimension(1000,250));
		tb.setPreferredSize(new Dimension(1000,200));
		tb.getTableHeader().setReorderingAllowed(false);

		if(modPermission) {
			tb.setCellEditor(null);
		}
		
		ListSelectionModel lm = tb.getSelectionModel();
		lm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		lm.addListSelectionListener(e->{
			selectedRowIndex = tb.getSelectedRow();
		});
		

		JPanel btnContainer = new JPanel();
		btnContainer.setLayout(new GridLayout(0,3));
		 
		JButton removeBtn = new RemoveButton("Remove Row");
		JButton addBtn = new AddButton("Add a Row");
		JButton saveBtn = new SaveButton("Save Work");
	
		
		JScrollPane scroll = new JScrollPane(tb);
		tb.setFillsViewportHeight(true);

		//add the table renderer
		 add(scroll,BorderLayout.NORTH);
		
		if(delPermission)
		 btnContainer.add(removeBtn,BorderLayout.EAST);
		if(modPermission || delPermission ||  modPermission)
		 btnContainer.add(saveBtn,BorderLayout.WEST);
		if(addPermission)
		 btnContainer.add(addBtn,BorderLayout.CENTER);

		add(btnContainer,BorderLayout.CENTER);
	
		setVisible(true);

	}
	
	abstract Boolean onDelete(ArrayList<ArrayList<String>> arr);
	abstract Boolean onInsert(ArrayList<ArrayList<String>> arr);
	abstract Boolean onModify(ArrayList<ArrayList<ArrayList<String>>> arr);
	abstract void RefreshTable();


	public DynamicList(Boolean modPermission, Boolean delPermission,Boolean addPermission) {
		
		this.modPermission = modPermission;
		this.delPermission = delPermission;
		this.addPermission = addPermission;
		removedObjects = new ArrayList<Integer>();
		addedObjects = new ArrayList<Integer>();

	}
	
	class RemoveButton extends JButton implements ActionListener {
		public RemoveButton(String name) {
			super(name);
			this.addActionListener(this);
			setBackground(Color.RED);
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() != this)
				return;
			
			//if in added rows : delete
			/*int aed = addedObjects.indexOf(selectedRowIndex ) ;
			if(aed != -1) 
				addedObjects.remove(aed );*/
				
			int ied=removedObjects.indexOf(selectedRowIndex ) ;
			if(ied != -1)
				removedObjects.remove(ied );
			else 
				removedObjects.add(selectedRowIndex);
			System.out.println(removedObjects.contains(selectedRowIndex));
			
			repaint();
			revalidate();
		}
	}
		
	class SaveButton extends JButton implements ActionListener {
		public SaveButton(String name) {
			super(name);
			this.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(JOptionPane.showConfirmDialog(null, "Save Changes ?") != 0)
					return;
			
			ArrayList<ArrayList<String>> removed = getRemovedRows();
			ArrayList<ArrayList<String>> inserted = getNewRows();
			ArrayList<ArrayList<ArrayList<String>>> modified = getModifiedRows();

			Boolean sucessdelete = onDelete(removed);
			Boolean sucessinsert  = onInsert(inserted);
			Boolean sucessmodify = onModify(modified);
			if(removed.size()> 0) {
				if(sucessdelete) {
					JOptionPane.showMessageDialog(this, "Removed Data Succefully  ! ");
				}else {
					JOptionPane.showMessageDialog(this, "Error","Error while removing data  ! ",JOptionPane.ERROR_MESSAGE);
				}
			}
	
			if(inserted.size() > 0) {
				if(sucessinsert) {
					JOptionPane.showMessageDialog(this, "Inserted Data Succefully  ! ");
				}else {
					JOptionPane.showMessageDialog(this, "Error","Error while Inserting data  ! ",JOptionPane.ERROR_MESSAGE);
				}
			}
			if(modified.size()>0) {
				if(sucessmodify) {
					JOptionPane.showMessageDialog(this, "Modified Data Succefully  ! ");
				}else {
					JOptionPane.showMessageDialog(this, "Error","Error while Modifying data  ! ",JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(sucessdelete || sucessinsert || sucessmodify ) {
				RefreshTable();
				removedObjects.clear();
				addedObjects.clear();
			}
		}

	}
	
	class AddButton extends JButton implements ActionListener {
		public AddButton(String name) {
			super(name);
			this.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() != this)
				return;
			
			ArrayList<String> vals = new ArrayList<String>();
			for(String elString : headers) {
				String value = JOptionPane.showInputDialog(this, "Give the value of " + elString, elString, JOptionPane.PLAIN_MESSAGE);
				vals.add(value);
			}
			
			
			addedObjects.add(tbModel.getRowCount());
			tbModel.addRow(vals.toArray());	
			revalidate();		
			repaint();
		}
	}
}
