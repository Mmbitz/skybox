package cs335;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import com.jogamp.opengl.util.Animator;

public class Main extends JFrame {
	static private Animator animator = null;
	
	public Main() {
		super( "Skybox" );
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setSize( 640, 480 );
		setVisible( true );
		
		setupJOGL();
	}
	
	public static void main( String[] args ) {
		Main m = new Main();
		m.setVisible( true );
	}
	
	private void setupJOGL() {
		GLCapabilities caps = new GLCapabilities( null );
		caps.setDoubleBuffered( true );
		caps.setHardwareAccelerated( true );
		
		GLCanvas canvas = new GLCanvas( caps ); 
		add( canvas );
		
		JoglEventListener jgl = new JoglEventListener();
		canvas.addGLEventListener( jgl ); 
		canvas.addKeyListener( jgl ); 
		canvas.addMouseListener( jgl );
		canvas.addMouseMotionListener( jgl );
		
		animator = new Animator( canvas );
		animator.start();
	}
}
