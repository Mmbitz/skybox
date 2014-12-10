package cs335;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.GL2;

public class TextureLoader {
	protected GL2 gl;
	
	public TextureLoader( GL2 gl ) {
		this.gl = gl;
	}
	
	public int generateTexture() {
		int textures[] = new int[ 1 ];
		gl.glGenTextures( 1, textures, 0 );
		return textures[ 0 ];
	}
	
	public void deleteTexture( int texture_id ) {
		int textures[] = { texture_id };
		gl.glDeleteTextures( 1, textures, 0 );
	}
	
	public void loadTexture( int texture_id, String filename ) throws IOException, InterruptedException {
		BufferedImage img = ImageIO.read( new File( filename ) );
		
		int[] pixels = new int[ img.getWidth() * img.getHeight() ];
		PixelGrabber grabber = new PixelGrabber( img, 0, 0,
				img.getWidth(), img.getHeight(), pixels, 0, img.getWidth() );
		grabber.grabPixels();

		ByteBuffer pixel_buffer = ByteBuffer.wrap( new byte[ pixels.length * 4 ] );

		for ( int y = img.getHeight() - 1; y >= 0; y-- ) {
			for ( int x = 0; x < img.getWidth(); x++ ) {
				int pixel = pixels[ y * img.getWidth() + x ];
				pixel_buffer.put( (byte) ( ( pixel >> 16 ) & 0xFF ) );
				pixel_buffer.put( (byte) ( ( pixel >> 8 ) & 0xFF ) );
				pixel_buffer.put( (byte) ( ( pixel >> 0 ) & 0xFF ) );
				pixel_buffer.put( (byte) ( ( pixel >> 24 ) & 0xFF ) );
			}
		}

		pixel_buffer.flip();
		
		gl.glBindTexture( GL2.GL_TEXTURE_2D, texture_id );
		setupTextureParameters();
		gl.glTexImage2D( GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, img.getWidth(),
				img.getHeight(), 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, pixel_buffer );
	}
	
	protected void setupTextureParameters() {
		gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR );
		gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR );
		gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT );
		gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT );
		gl.glTexEnvf( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE );
	}
}
