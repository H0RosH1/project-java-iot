import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.fazecast.jSerialComm.SerialPort;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.Component;
public class Project {
	
	static SerialPort chosenPort;
	private static int num;
	private static int T=1;
	private static final JLabel lblNewLabel_3 = new JLabel("DAY DATE MONTH yEAR ");
	public static void main(String[] args) {
		
		// create and configure the window
		JFrame window = new JFrame();
		window.setBackground(Color.WHITE);
		window.getContentPane().setBackground(Color.BLACK);
		window.getContentPane().setForeground(Color.WHITE);
		window.setTitle("Sensor Graph GUI");
		window.setSize(600, 400);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(null);
		
		// create a drop-down box and connect button, then place them at the top of the window
		JComboBox<String> portList = new JComboBox<String>();
		portList.setFont(new Font("TRS Million", Font.PLAIN, 15));
		portList.setForeground(new Color(0, 0, 0));
		portList.setBackground(new Color(255, 255, 255));
		JButton connectButton = new JButton("Connect");
		connectButton.setFont(new Font("TRS Million", Font.PLAIN, 15));
		connectButton.setForeground(new Color(0, 0, 0));
		connectButton.setBackground(new Color(255, 255, 255));
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);
		topPanel.setBounds(0, 0, 584, 33);
		topPanel.add(portList);
		topPanel.add(connectButton);
		window.getContentPane().add(topPanel);
		
		JLabel lblNewLabel = new JLabel("loading...");
		lblNewLabel.setForeground(new Color(50, 205, 50));
		lblNewLabel.setFont(new Font("TRS Million", Font.PLAIN, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 291, 153, 33);
		window.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Humidity(%)");
		lblNewLabel_1.setForeground(new Color(50, 205, 50));
		lblNewLabel_1.setFont(new Font("TRS Million", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(26, 238, 153, 42);
		window.getContentPane().add(lblNewLabel_1);
		
		JLabel TextC = new JLabel("loading...");
		TextC.setForeground(new Color(50, 205, 50));
		TextC.setHorizontalAlignment(SwingConstants.CENTER);
		TextC.setFont(new Font("TRS Million", Font.PLAIN, 22));
		TextC.setBounds(219, 291, 139, 33);
		window.getContentPane().add(TextC);
		
		JLabel TextF = new JLabel("loading...");
		TextF.setForeground(new Color(50, 205, 50));
		TextF.setHorizontalAlignment(SwingConstants.CENTER);
		TextF.setFont(new Font("TRS Million", Font.PLAIN, 22));
		TextF.setBounds(403, 291, 153, 33);
		window.getContentPane().add(TextF);
		
		JLabel lblNewLabel_1_1 = new JLabel("Celsius(C)");
		lblNewLabel_1_1.setForeground(new Color(50, 205, 50));
		lblNewLabel_1_1.setFont(new Font("TRS Million", Font.PLAIN, 24));
		lblNewLabel_1_1.setBounds(235, 238, 139, 42);
		window.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Fahrenheit(F)");
		lblNewLabel_1_2.setForeground(new Color(50, 205, 50));
		lblNewLabel_1_2.setFont(new Font("TRS Million", Font.PLAIN, 24));
		lblNewLabel_1_2.setBounds(403, 238, 171, 42);
		window.getContentPane().add(lblNewLabel_1_2);
		
		JLabel TextTime = new JLabel("");
		TextTime.setForeground(new Color(50, 205, 50));
		TextTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		TextTime.setHorizontalAlignment(SwingConstants.CENTER);
		TextTime.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(34, 139, 34)));
		TextTime.setBounds(71, 68, 442, 159);
		window.getContentPane().add(TextTime);
		
		JLabel lblNewLabel_2 = new JLabel("HH:mm:ss");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("TRS Million", Font.PLAIN, 60));
		lblNewLabel_2.setForeground(new Color(50, 205, 50));
		lblNewLabel_2.setBounds(129, 84, 342, 80);
		window.getContentPane().add(lblNewLabel_2);
		lblNewLabel_3.setFont(new Font("TRS Million", Font.PLAIN, 33));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(new Color(50, 205, 50));
		lblNewLabel_3.setBounds(92, 155, 386, 61);
		window.getContentPane().add(lblNewLabel_3);
		
		// populate the drop-down box
		//เลือกcomจากdrop-down box
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++) {
			 portList.addItem(portNames[i].getSystemPortName());
		}
		connectButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0) {
				//เมื่อกดปุ่มจะทำการดึงข้อมูลจากarduino
				if(connectButton.getText().equals("Connect")) {
					chosenPort = SerialPort.getCommPort( portList.getSelectedItem().toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					chosenPort.setComPortParameters(9600,8,1,0);
					if(chosenPort.openPort()) {
						connectButton.setText("disconnect");
						portList.setEnabled(false);
						
					}

					// เรียกค่าจากarduinoเข้าและแทนค่าใส่labal
					Thread thread = new Thread(){
						@Override public void run() {
							
							while(true) {
								Scanner in = new Scanner(chosenPort.getInputStream());
								Scanner scanner = new Scanner(chosenPort.getInputStream());
								PrintWriter output = new PrintWriter(chosenPort.getOutputStream()); 
								
								while(scanner.hasNextLine()) {
									clock cl = new clock();
									lblNewLabel_2.setText(cl.getTime());
									lblNewLabel_3.setText(cl.getDMY());

									try {
										String number = String.valueOf(in.next());
										if(T==1) {
											lblNewLabel.setText(number);
											output.print(new SimpleDateFormat("hh:mm:ss a     EE dd MMMMMMM yyyy ").format(new Date()));
											output.flush();

											T=2;
										}
										else if (T==2) {
											TextC.setText(number);
											T=3;
										}
										else if(T==3) {
											TextF.setText(number);
											T=1;
										}

										window.repaint();
									} catch(Exception e) {
										
									}
									
								}
							}
						}
					};
					thread.start();
				} else {
					// disconnect from the serial port
					chosenPort.closePort();
					portList.setEnabled(true);
					connectButton.setText("Connect");
					
				}
			}
		});
		
		// show the window
		window.setVisible(true);
	}
}