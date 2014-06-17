package mayjo.batterylivewallpaper;

public class MyColor {
	private int red;
	private int green;
	private int blue;
	
	//Red getter/setter
	public int getRed() {
		return red;
	}
	public void setRed(int red) {
		this.red = red;
	}
	//Green getter/setter
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	//Blue getter/setter
	public int getBlue() {
		return blue;
	}	
	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	public void setRGB(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public MyColor(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
}
