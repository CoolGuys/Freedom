import java.awt.*;
import javax.swing.*;

public class FrameTest
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{

				// TODO Auto-generated method stub
				Frame frame = new Frame(1000, 600);
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}


