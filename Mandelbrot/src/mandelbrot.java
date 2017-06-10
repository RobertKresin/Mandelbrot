import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class mandelbrot extends JComponent implements ActionListener{

	/*
	 FROM java:8
COPY . /usr/src/cloudcomputing
WORKDIR /usr/src/cloudcomputing

CMD ["java", "-jar", "cloudmatrixServer.jar"]
	 * 
	 * 
	 */
	
	
	
	public static void main(String args[]) {
		
		new mandelbrot();
	}
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int ITERATIONS = 100;
	public static final float SCALE = 250;
	
	private float hueOffset = 0;
	private Timer timer;
	
	private BufferedImage buffer;
	
	
	public mandelbrot() {
		
		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		timer = new Timer(1, this);

		
		JFrame frame = new JFrame("Mandelbrot Set");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);	
		
		
	}
	
	@Override
	public void addNotify() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		timer.start();
	}
	
	public void renderMandelbrotSet() {
		
		for(int x = 0; x < WIDTH; x++)
			for(int y = 0; y < HEIGHT; y++) {
				int color = calculatePoint((x - WIDTH/2f)/SCALE , (y - HEIGHT/2f)/SCALE);
				
				buffer.setRGB(x, y, color);
			}
	}
	
	public int calculatePoint(float x, float y) {
		
		float cx = x;
		float cy = y;
		
		int i = 0;
		
		for(; i < ITERATIONS; i++) {
			
			float nx = x*x - y*y + cx;
			float ny = 2*x *  y  + cy;
			x = nx;
			y = ny;
			
			if(x*x +y*y > 4) break;
		}
		
		if(i == ITERATIONS) return 0x00000000;
		return Color.HSBtoRGB(((float)i / ITERATIONS + hueOffset)%1, 0.5f, 1);
	}
	
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(buffer, 0, 0, null);
	}

	
	public void actionPerformed(ActionEvent e) {
		
		hueOffset += 0.01f;
		renderMandelbrotSet();
		repaint();
	}
	
}