import java.awt.*;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;




public class Freim extends JFrame  {
	
	private JMenuBar MenuBar =new JMenuBar();
	private JMenu MenuFile =new JMenu("Файл");
	private JMenuItem MFOpen=new JMenuItem("Открыть файл");
	private JMenuItem MFSave=new JMenuItem("Сохранить файл");
	private JMenuItem MFExit=new JMenuItem("Выход");
		
	private JMenu MenuMat =new JMenu("Расчеты с матрицей");
	private JMenuItem MDet=new JMenuItem("Определитель");
	private JMenuItem MSum=new JMenuItem("Сумма єлементов");
	private JMenuItem MMinMax=new JMenuItem("Єлементы");
	private JMenuItem MZero=new JMenuItem("Нулевые элементы");
	private JDesktopPane desctop=new JDesktopPane();
		
	private JPanel StatusBar = new JPanel();
	private JLabel labelTime = new JLabel();
	    
	    
				
		//Создание окна для исходной матрицы
	private final JInternalFrame RegFile = new JInternalFrame("Исходные данные", true,true,false,true);
	private final JTextPane dateText = new JTextPane();
		
		
	private final JInternalFrame detFrame = new JInternalFrame("Определитель", false,true,false,true);
	private final JTextPane detText = new JTextPane();
				
	private final JInternalFrame sumFrame = new JInternalFrame("Сумма матрицы", false,true,false,true);
	private final JTextPane sumText = new JTextPane();
		
	private final JInternalFrame minMaxFrame = new JInternalFrame("Минимальный, максимальный элемент", false,true,false,true);
	private final JTextPane minMaxText = new JTextPane();
		
	private final JInternalFrame zeroFrame = new JInternalFrame("Нулевые элементы", false,true,false,true);
	private final JTextPane zeroText = new JTextPane();
		
		private int rows,cols;
		private double [][] Matrix;
       
		
	
		
	Freim(){
		super("Kursovoi martix");	
		MenuFile.add(MFOpen);
		MFOpen.addActionListener(new fileData());
		MenuFile.add(MFSave);
		MFSave.addActionListener(new Save());
		MenuFile.add(MFExit);
		MFExit.addActionListener(new Exit());
		MenuBar.add(MenuFile);
		
		MDet.addActionListener(new DetArray());
		MenuMat.add(MDet);
		MSum.addActionListener(new SumArray());
		MenuMat.add(MSum);
		MenuMat.add(MMinMax);
		MMinMax.addActionListener(new MinMaxElem());
		MenuMat.add(MZero);
		MZero.addActionListener(new ZeroElem());
		MenuBar.add(MenuMat);
		
		
		StatusBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		
		
		setJMenuBar(MenuBar);
		
		 dateText.setFont(new Font("Dialog", Font.PLAIN, 20));
		 dateText.setEditable(false);
		 detText.setEditable(false);
		 	
		 
        //Настройки окна исходной мартицы
        RegFile.setSize(400, 200);
        RegFile.setMaximumSize(new Dimension(700,400));
        RegFile.setMinimumSize(new Dimension(400,200));
        RegFile.add(new JScrollPane(dateText));
        RegFile.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);				
		desctop.add(RegFile);
		
		detFrame.setSize(300, 100);
		detFrame.add(new JScrollPane(detText));
		detFrame.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);				
		desctop.add(detFrame);
		
		sumFrame.setSize(200, 100);
		sumFrame.add(new JScrollPane(sumText));
		sumFrame.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);				
		desctop.add(sumFrame);
		
		minMaxFrame.setSize(500, 100);
		minMaxFrame.add(new JScrollPane(minMaxText));
		minMaxFrame.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);				
		desctop.add(minMaxFrame);
		
		zeroFrame.setSize(300, 250);
		zeroFrame.add(new JScrollPane(zeroText));
		zeroFrame.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);				
		desctop.add(zeroFrame);
		
		labelTime.setText(" ");
		StatusBar.setLayout(new BorderLayout());
		StatusBar.add(labelTime,BorderLayout.EAST);
		add(StatusBar,BorderLayout.SOUTH);
		
		add(desctop,BorderLayout.CENTER);
		setSize(1000,600);	
		setVisible(true);	
		timer.schedule(task,1000,1000);
	}
	
	private SimpleDateFormat formatForDateNow = new SimpleDateFormat("E dd.MM  hh:mm:ss");
	private Timer timer = new Timer();
		TimerTask task= new TimerTask() {
			public void run() {
				Date dateNow = new Date();
				labelTime.setText(formatForDateNow.format(dateNow));
			}
		};
	
	//Вызов слушателя открытия файла и ввода исходной матрицы
	class fileData implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {	
			
			SizeMatrix sm=new SizeMatrix();
			dateText.setText("");
			
			rows=sm.getRows();
			cols=sm.getCols();
			Matrix=sm.getMatrix();
					
			for (int i = 0; i < rows; i++) {
				for(int j =0; j<cols;j++) {
					insertString(Matrix[i][j]+"\t", dateText, normalStyle(dateText));
				}
				insertString("\n", dateText, normalStyle(dateText));
						}
			if(Matrix!=null) {
				RegFile.setVisible(true);
			}
	        
		}	
	}
	
	//Функция для вывода данных на окно 
		private void insertString(String s, JTextPane jtextPane, Style style) {
	        try{
	            Document document = jtextPane.getDocument();
	            document.insertString(document.getLength(), s + "\r", style);
	        }
	        catch (Exception e){
	            String msg = "Ошибка получения данных";
	            JOptionPane.showMessageDialog(null, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
	        }
	    }
		 private Style normalStyle(JTextPane textPane){
		        Style normal = textPane.addStyle("Normal", null);
		        StyleConstants.setFontFamily(normal, "Verdana");
		        StyleConstants.setFontSize(normal, 13);
		        return normal;
		    }	
			//Слушатель для вызова детерминанта
		 	class DetArray implements ActionListener{
		 		@Override
		 		public void actionPerformed(ActionEvent e) {
		 			if(Matrix!=null) {
		 			if(cols==rows) {
		 			Mathem mat = new Mathem();
		 			detText.setText("");
		 			insertString("Определитель матрицы : "+mat.detMatrix(Matrix, Matrix.length),detText,normalStyle(detText));
		 		detFrame.setVisible(true);
		 					}
		 			else JOptionPane.showMessageDialog(null, "Матрица не квадратная.");
		 		}	
		 		else { insertString("Матрица пустая",detText,normalStyle(detText));
			 		detFrame.setVisible(true);
			 		}

		 		}
		 }
		 	//Слушатель для вызова суммы матрицы
		 	class SumArray implements ActionListener{
		 		@Override
		 		public void actionPerformed(ActionEvent e) {
		 			if(Matrix!=null) {
		 			Mathem mat = new Mathem();
		 			sumText.setText("");
		 			insertString("Сумма матриц : "+mat.sumMatrix(Matrix, rows, cols),sumText,normalStyle(sumText));
		 			sumFrame.setVisible(true);
		 		}
		 			else {insertString("Матрица пустая",detText,normalStyle(detText));
			 		detFrame.setVisible(true);}
		 	}
		 }
		 	
		 	class MinMaxElem implements ActionListener{
		 		@Override
		 		public void actionPerformed(ActionEvent e) {
		 			if(Matrix!=null) {
		 			Mathem mat = new Mathem();
		 			minMaxText.setText("");
		 			insertString(mat.minElemArray(Matrix, rows, cols),minMaxText,normalStyle(minMaxText));
		 			insertString(mat.maxElemArray(Matrix, rows, cols),minMaxText,normalStyle(minMaxText));
		 			minMaxFrame.setVisible(true);
		 		}
		 			else {insertString("Матрица пустая",minMaxText,normalStyle(minMaxText));
		 			minMaxFrame.setVisible(true);}
		 	}
	}
		 	
		 	class ZeroElem implements ActionListener{
		 		@Override
		 		public void actionPerformed(ActionEvent e) {
		 			if(Matrix!=null) {
		 				Mathem mat=new Mathem();
		 				zeroText.setText("");
		 				String arrZero[]=mat.elemWithZezo(Matrix, rows, cols);
		 				for(int i=0; i<arrZero.length;i++) {
		 					insertString(arrZero[i]+"\n",zeroText,normalStyle(zeroText));
		 				}
		 				zeroFrame.setVisible(true);
		 			}
		 			else {insertString("Матрица пустая",zeroText,normalStyle(zeroText));
		 			zeroFrame.setVisible(true);}
		 		}
		 	}
		 	
		 	class Exit implements ActionListener{
		 		@Override
		 		public void actionPerformed(ActionEvent e) {
		 			System.exit(0);
		 		}
		 	}
		 	
		 	class Save implements ActionListener{
		 		@Override
		 		public void actionPerformed(ActionEvent e) {
		 			try {
		 			File file = new File("demo.html");	 			
		 			file.createNewFile();
		 			PrintWriter  writer=new PrintWriter(file);
		 			writer.println("<div>Исходная матрица<br>");
		 			for(int i=0;i<rows;i++) {
		 	    		for(int j=0;j<cols;j++) {
		 	    			writer.print(Matrix[i][j]+"\t");
		 	    		}
		 	    		writer.println("<br>");
		 	    	}
		 			writer.println("</div><br>");
		 			
		 			writer.println("<div>"+detText.getText()+"</div><br>");
		 			writer.println("<div>"+sumText.getText()+"</div><br>"); 			
		 			writer.println("<div>"+minMaxText.getText()+"</div><br>");
		 			//writer.write("<div>"+zeroText.getText()+"</div><br>",0,28);
		 			String str=zeroText.getText();
		 			writer.println("<div>");

		 			for(int i=0;i<str.length();i+=32) {
		 				writer.write(zeroText.getText(),i,32);
		 				writer.print("<br>");
		 			}
		 			writer.println("</div>");
		 			writer.close();
		 			}
		 			catch(IOException e1) {
		 				e1.printStackTrace();
		 			}
		 		}
		 	}
	
	public static void main(String args[]) {
		Freim fr= new Freim();
	}
}
