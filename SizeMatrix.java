import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.*;


public class SizeMatrix  extends JDialog {
	private int rows,cols;
	private double Matrix[][];
	
	private JLabel labelRows=new JLabel("���������� �����");
	private JLabel labelCols=new JLabel("���������� ��������");
	private JPanel panel=new JPanel();
	private GridBagLayout fl= new GridBagLayout();
	
	private JTextField rowsText=new JTextField(5);
	private JTextField colsText=new JTextField(5);
	private JButton OK=new JButton("Yes");
			
		SizeMatrix(){
			panel.setLayout(fl);
			
			GridBagConstraints C1 = new GridBagConstraints();
			C1.gridx = 0;
	        C1.gridy = 0;
	        C1.fill = GridBagConstraints.NONE;
	        panel.add(labelRows, C1);
			
	        GridBagConstraints C2 = new GridBagConstraints();
			C2.gridx = 1;
	        C2.gridy = 0;
	        C2.insets=new Insets(0,30,5,5);
	        C2.anchor = GridBagConstraints.CENTER;
	        panel.add(rowsText, C2);
			
	        GridBagConstraints C3 = new GridBagConstraints();
			C3.gridx = 0;
	        C3.gridy = 1;
	        C3.fill = GridBagConstraints.NONE;
	        panel.add(labelCols, C3);
			
	        GridBagConstraints C4 = new GridBagConstraints();
			C4.gridx = 1;
	        C4.gridy = 1;
	        C4.insets=new Insets(0,30,5,5);
	        C4.anchor = GridBagConstraints.CENTER;
	        panel.add(colsText, C4);
			
	        GridBagConstraints C5 = new GridBagConstraints();
			C5.gridx = 0;
	        C5.gridy = 2;
	        C5.gridwidth = 2;
	        C5.insets=new Insets(10,0,0,0);
	        C5.anchor = GridBagConstraints.CENTER;
	        panel.add(OK, C5);
	        OK.addActionListener(new openFile());

			add(panel);
			
			setModal(true);
			setTitle("��������");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setSize(250,150);
			add(panel);
			setVisible(true);
			
		}
		
		public int getRows() {
			return rows;
		}
		
	    public int getCols() {
			return cols;
		}
	    public double[][] getMatrix() {
	    	return Matrix;
	    }
		
		//������� �������� �����
		private class openFile  implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				//��������� ���� � ���� ����������� �������
				if(rowsText.getText().isEmpty()||colsText.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "�� �� ����� ��������");
				}
				else { 
					rows=Integer.parseInt(rowsText.getText());
					cols=Integer.parseInt(colsText.getText());
					 Matrix=new double[rows][cols];
					dispose();//����� �������� �����
					//��������� ����� �����
				JFileChooser chooser=new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
				chooser.setFileFilter(filter);
				if(chooser.showDialog(null, "������� ����") == JFileChooser.APPROVE_OPTION) {
					File file =chooser.getSelectedFile();
					try {
						
						BufferedReader reader =new BufferedReader(new FileReader(file));
						
						String strLine;
						//��������� ������ �� ����� � ����
						ArrayList<String> list= new ArrayList<String>();
						while((strLine=reader.readLine()) !=null) {
							if(!strLine.isEmpty()) {
								list.add(strLine);
					         	System.out.println(strLine);	
							}
						}
						//��������� ������ �� ����� �� �����
						for(int i=0; i<rows;i++) {
							String [] strMas=list.get(i).split("[ ]");
								for (int j = 0; j <cols; j++) {
									if(j<=strMas.length-1) {
										if(isNumber(strMas[j])) {
											Matrix[i][j] = Double.parseDouble(strMas[j]);
										}
										else {
											JOptionPane.showMessageDialog(null, "� ��������� ����� ���� �����.");
											break;
										}
										}
									else { Matrix[i][j] = 0; }
									}	
						}										
					}		
					catch(Exception e1) {
						}
						}}}}		
		
		//����� �������� ������ �� ����� 
		private  static boolean isNumber(String str) {
			try {
				double b =Double.parseDouble(str);
			}
			catch(NumberFormatException | NullPointerException nfe) {
				return false;
			}
			return true;
		}		
}

