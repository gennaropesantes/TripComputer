import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class TripComputer extends JApplet implements ActionListener 
{
    
	private double totalTime;
	private boolean restStopTaken; 
	private double stopTaken;
	private double legTaken;
	
	
	public double distance;
	public double speed;
	public double time;
	
	private JButton stopButton = new JButton("ADD STOP");
	private JButton legButton = new JButton("ADD LEG");
	
	private JLabel stopTimeLabel = new JLabel("STOP TIME: ");
	public JTextField stopTimeTextField = new JTextField(8);
	
	private JLabel distanceLabel = new JLabel("DISTANCE: ");
	public JTextField distanceTextField = new JTextField(8);
	
	private JLabel timeSoFarLabel = new JLabel("YOUR TRIP TIME SO FAR:");
	public JTextField timeSoFarTextField = new JTextField(8);
	
	private JLabel speedLabel = new JLabel("SPEED: ");
	public JTextField speedTextField = new JTextField(8);
	
	//private boolean emptySpace1 = distanceTextField !=null && !="";
	
	public void init()
	{	
		this.setSize(600, 300);
		
		stopTaken = 0;
		legTaken = 0;
		totalTime = 0;
		restStopTaken = (stopTaken > 1);
		distance = 0;
		speed = 0;
		time = 0;
		
		Container computerTripPane = getContentPane();
		computerTripPane.setLayout(new  FlowLayout());
		
		//stop
		stopButton.addActionListener(this); 
		computerTripPane.add(stopButton, BorderLayout.PAGE_START);
		computerTripPane.add(stopTimeLabel);
		computerTripPane.add(stopTimeTextField);
		computerTripPane.add(timeSoFarLabel);
		computerTripPane.add(timeSoFarTextField);
		
		//leg
		legButton.addActionListener(this); 
		computerTripPane.add(legButton, BorderLayout.NORTH);
		computerTripPane.add(distanceLabel);
		computerTripPane.add(distanceTextField);
		computerTripPane.add(speedLabel);
		computerTripPane.add(speedTextField);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub	
		if(event.getActionCommand().equals("ADD LEG"))
		{
			try
			{
				legTaken += 1;
				double distance = Double.parseDouble(distanceTextField.getText());
				double speed = Double.parseDouble(speedTextField.getText());
				computeLegTime(distance, speed);
				timeSoFarTextField.setText(getTripTime() + " Hours");
				
			
				
				if (distanceTextField.getText().equalsIgnoreCase(""))
				{
					distanceTextField.setText("0");
					throw new noBlanksException();
				}
				else if (speedTextField.getText().equalsIgnoreCase(""))
				{
					speedTextField.setText("0");
					throw new noBlanksException();
				}
				else if(distance < 0 || speed < 0)
				{
					throw new NegativeNumberException();
				}
				
			}
			
			catch(NegativeNumberException e)
			{
				timeSoFarTextField.setText("");
				distanceTextField.setText("");
				stopTimeTextField.setText("");
				speedTextField.setText("");
				JOptionPane.showMessageDialog(null, e.getMessage());
				
			}
			
			catch(noBlanksException e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		
		else if(event.getActionCommand().equals("ADD STOP"))
		{
			
			try
			{
				double time = Double.parseDouble(stopTimeTextField.getText());
				takeRestStop(time);
				timeSoFarTextField.setText(getTripTime() + " Hours");
				stopTaken += 1;
			
				if (time < 0)
				{
					throw new NegativeNumberException();
				}
				else if (stopTaken > 1 && legTaken < 2)
				{
					throw new TwiceInOneRowException();
				}
				
			}
			catch(TwiceInOneRowException e)
			{
				timeSoFarTextField.setText("");
				distanceTextField.setText("");
				stopTimeTextField.setText("");
				speedTextField.setText("");
				JOptionPane.showMessageDialog(null, e.getMessage());

			}
			
			catch(NegativeNumberException e)
			{
				timeSoFarTextField.setText("");
				distanceTextField.setText("");
				stopTimeTextField.setText("");
				speedTextField.setText("");
				JOptionPane.showMessageDialog(null, e.getMessage());
				
			} 
			
		}
		
		else
		{
			System.out.println("Error");
			System.exit(0);
		}
	}
	
	public class NegativeNumberException extends Exception
	{
		public NegativeNumberException()
		{
			super("The number you entered is negative.");
		}
	}
	
	public class TwiceInOneRowException extends Exception
	{
		public TwiceInOneRowException()
		{
			super("You cannot take two rest stops in a row.");
		}
	}
	
	public class noBlanksException extends Exception
	{
		public noBlanksException()
		{
			super("You must enter a number!");
		}
	}
	
	public void computeLegTime(double distance, double speed)
	{
		totalTime += distance / speed;
	}
	
	public void takeRestStop(double time)
	{
		totalTime += time;
	}
	
	public double getTripTime()
	{
		return totalTime;
	}
}
