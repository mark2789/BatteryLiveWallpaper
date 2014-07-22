package mayjo.batterylivewallpaper;

import android.graphics.Path;

public class MySemiCircle extends Path {
	int left;
	int top;
	int right;
	int bottom;
	float startingAngle;
	float sweepAngle;
	int batteryLife;
	int divisor;
	
	public MySemiCircle() {
	    left = 40;
	    top = 40;
	    right = left + 200;
	    bottom = top + 200;
		startingAngle = 270;
		sweepAngle = 45;
	}
	
	//Sets the starting and stopping angle for each arc
	public void setSemiCircle(float startingAngle, float sweepAngle) {
		this.startingAngle = startingAngle;
		this.sweepAngle = sweepAngle;
	}
	
	//Sets the size of the circle/oval
	public void setSize(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	//Gets level of battery life so it knows how much to draw
	public void setBatteryLife(int batteryLife) {
		this.batteryLife = batteryLife;
	}
	
	//Divisor is used for the "roundness" of the edges
	public void setDivisor(int divisor) {
		this.divisor = divisor;
	}
	
	//Draws the semi-circle one piece at a time
	public Path getSemiCircle() {
		
		//Radius of outer circle
		float outerRadius = (float) ((bottom - top) / 2.0);
		
		//Radius of inner circle
		float innerRadius = (float) (outerRadius / 2.0)+ 10;
		
		//Start Angle for first point
		float centerX = (float) ((right - left) / 2.0 + left);
		float centerY = (float) ((bottom - top) / 2.0 + top);
		
		//Starting point in the inner radius
		this.moveTo(centerX + innerRadius * (float)Math.cos(startingAngle * Math.PI / 180), centerY + innerRadius * (float)Math.sin(startingAngle * Math.PI / 180));

		//Draw inner radius of semi-circle
		for (int i = 0; i <= divisor; i++) {
			double radians = ((startingAngle + sweepAngle / divisor * i) * Math.PI / 180);
			this.lineTo((float)(centerX + outerRadius * Math.cos(radians)), (float)(centerY + outerRadius * Math.sin(radians)));
		}
		
		//Draw line to outer radius
		this.lineTo(centerX + innerRadius * (float)Math.cos((startingAngle + sweepAngle) * Math.PI / 180), centerY + innerRadius * (float)Math.sin((startingAngle + sweepAngle) * Math.PI / 180));

		//Draw outer radius of semi-circle
		for (int i = divisor - 1; i > 0; i--) {
			double radians = ((startingAngle + sweepAngle / divisor * i) * Math.PI / 180);
			this.lineTo((float)(centerX + innerRadius * Math.cos(radians)), (float)(centerY + innerRadius * Math.sin(radians)));
		}
		
		//Draw line to inner radius
		this.close();
		this.setFillType(Path.FillType.EVEN_ODD);
		return this;
	}
	
	//Draws the Oval one piece at a time
	public Path getSemiOval() {
		
		//Radius of outer oval
		float outerRadius = (float) ((bottom - top) / 2.0);
		
		//Radius of inner oval
		float innerRadius = (float) (outerRadius / 2.0)+ 10;
		
		//Start Angle for first point
		float centerX = (float) ((right - left) / 2.0 + left);
		float centerY = (float) ((bottom - top) / 2.0 + top);
		
		//Offsets used to make oval instead of circle
		float offsetX = 1.0f;
		float offsetY = 1.5f;
		
		//Starting point in the inner radius
		this.moveTo((float)(centerX + outerRadius * offsetX * Math.cos(startingAngle * Math.PI / 180)), (float)(centerY + outerRadius * offsetY * Math.sin(startingAngle * Math.PI / 180)));

		//Draw inner radius
		for (int i = 0; i <= divisor; i++) {
			double radians = ((startingAngle + sweepAngle / divisor * i) * Math.PI / 180);
			this.lineTo((float)(centerX + outerRadius * offsetX * Math.cos(radians)), (float)(centerY + outerRadius * offsetY * Math.sin(radians)));
		}
		
		//Change offset to make it look better **magic numbers
		offsetX*= 0.6;
		offsetY*= 0.7;
		
		//Draw line to outer radius
		this.lineTo(centerX + outerRadius * offsetX * (float)Math.cos((startingAngle + sweepAngle) * Math.PI / 180), centerY + outerRadius * offsetY * (float)Math.sin((startingAngle + sweepAngle) * Math.PI / 180));

		//Draw outer radius
		for (int i = divisor - 1; i > 0; i--) {
			double radians = ((startingAngle + sweepAngle / divisor * i) * Math.PI / 180);
			this.lineTo((float)(centerX + outerRadius * offsetX * Math.cos(radians)), (float)(centerY + outerRadius * offsetY * Math.sin(radians)));
		}
		
		//Draw line to inner radius
		this.close();
		this.setFillType(Path.FillType.EVEN_ODD);
		return this;
	}
}
