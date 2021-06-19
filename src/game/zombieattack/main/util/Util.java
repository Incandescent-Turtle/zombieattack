package game.zombieattack.main.util;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

//Utility Class
public class Util {
	//cant make instances. "Static class"
	private Util() {}
	
	//used to pass methods as parameters
	@FunctionalInterface
	public interface VariableMethod { void method(); }
	
	//used to restrict values to a maximum and minimum
	public static double clamp(double value, int min, int max)
	{
		return value>max ? max : value<min ? min : value;
	}
	//gets a string's width given the current font and such
	public static int getStringWidth(String message, Graphics2D g)
	{
		return (int) g.getFont().getStringBounds(message, g.getFontRenderContext()).getWidth();
	}
	//gets a string's height given the current font and such
	public static int getStringHeight(String message, Graphics2D g)
	{
		return (int) g.getFont().getStringBounds(message, g.getFontRenderContext()).getHeight();
	}
	//gets strings dimensions
	public static Dimension getStringDemensions(String message, Graphics2D g)
	{
		Rectangle rect = g.getFont().getStringBounds(message, g.getFontRenderContext()).getBounds();
		return new Dimension((int)rect.getWidth(), (int)rect.getHeight());
	}
	//centers a string graphically on the x axis (given coord will be center)
	public static void drawXCenteredString(String message, double x, double y, Graphics2D g)
	{
		g.drawString(message, (float) (x-getStringWidth(message, g)/2.0), (float)y);
	} 
	//draw a string centered on the Y axis (given coord will be in the middle of the height of the string)
	public static void drawYCenteredString(String message, double x, double y, Graphics2D g)
	{
		g.drawString(message, (float) x, (float) (y+getStringHeight(message, g)/2.0));
	}
	//the given coords will be the dead center of the string
	public static void drawCenteredString(String message, double x, double y, Graphics2D g)
	{
		g.drawString(message, (float) (x-getStringWidth(message, g)/2.0), (float) (y+getStringHeight(message, g)/2)-getStringHeight(message, g)/4);
	}
	//helper method to easily create a new circle that accepts double values
	public static Shape newCircle(double x, double y, double width, double height)
	{
		return new Ellipse2D.Double(x, y, width, height);
	}
	//helper method to easily create a new rectangle that accepts double values
	public static Shape newRectangle(double x, double y, double width, double height)
	{
		return new Rectangle2D.Double(x, y, width, height);
	}
	
	public static BufferedImage loadImage(String path)
	{
		return new ImageLoader().loadImage(path);
	}
}