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
	
	public void setSemiCircle(float startingAngle, float sweepAngle) {
		this.startingAngle = startingAngle;
		this.sweepAngle = sweepAngle;
	}
	public void setSize(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	public void setBatteryLife(int batteryLife) {
		this.batteryLife = batteryLife;
	}
	
	public void setDivisor(int divisor) {
		this.divisor = divisor;
	}
	
	public Path getSemiCircle() {
		
		//Radius of outer circle
		//Radius of inner circle
		
		//Start Angle for first point
		//Sweep Angle for second point
		//Coordinates of center of circle
		
		float outerRadius = (float) ((bottom - top) / 2.0);
		float innerRadius = (float) (outerRadius / 2.0)+ 10;
		
		float centerX = (float) ((right - left) / 2.0 + left);
		float centerY = (float) ((bottom - top) / 2.0 + top);
		
		this.moveTo(centerX + innerRadius * (float)Math.cos(startingAngle * Math.PI / 180), centerY + innerRadius * (float)Math.sin(startingAngle * Math.PI / 180));

		for (int i = 0; i <= divisor; i++) {
			double radians = ((startingAngle + sweepAngle / divisor * i) * Math.PI / 180);
			this.lineTo((float)(centerX + outerRadius * Math.cos(radians)), (float)(centerY + outerRadius * Math.sin(radians)));
		}
		this.lineTo(centerX + innerRadius * (float)Math.cos((startingAngle + sweepAngle) * Math.PI / 180), centerY + innerRadius * (float)Math.sin((startingAngle + sweepAngle) * Math.PI / 180));

		for (int i = divisor - 1; i > 0; i--) {
			double radians = ((startingAngle + sweepAngle / divisor * i) * Math.PI / 180);
			this.lineTo((float)(centerX + innerRadius * Math.cos(radians)), (float)(centerY + innerRadius * Math.sin(radians)));
		}
		this.close();
		
		this.setFillType(Path.FillType.EVEN_ODD);
		return this;
	}
	
	
public Path getSemiOval() {
		
		//Radius of outer circle
		//Radius of inner circle
		
		//Start Angle for first point
		//Sweep Angle for second point
		//Coordinates of center of circle
		
		float outerRadius = (float) ((bottom - top) / 2.0);
		float innerRadius = (float) (outerRadius / 2.0)+ 10;
		
		float centerX = (float) ((right - left) / 2.0 + left);
		float centerY = (float) ((bottom - top) / 2.0 + top);
		float offsetX = 1.0f;
		float offsetY = 1.5f;
		
		//(cos(x) * 5, sin(x) * 10)
		//Equation centered around (h, k)
		//x = centerX + a * cos (t)     0 < t < 2PI
		//y = centerY + b * sin (t)     0 < t < 2PI
		
		

		
		this.moveTo((float)(centerX + outerRadius * offsetX * Math.cos(startingAngle * Math.PI / 180)), (float)(centerY + outerRadius * offsetY * Math.sin(startingAngle * Math.PI / 180)));

		for (int i = 0; i <= divisor; i++) {
			double radians = ((startingAngle + sweepAngle / divisor * i) * Math.PI / 180);
			this.lineTo((float)(centerX + outerRadius * offsetX * Math.cos(radians)), (float)(centerY + outerRadius * offsetY * Math.sin(radians)));
		}
		offsetX*= 0.6;
		offsetY*= 0.7;
		this.lineTo(centerX + outerRadius * offsetX * (float)Math.cos((startingAngle + sweepAngle) * Math.PI / 180), centerY + outerRadius * offsetY * (float)Math.sin((startingAngle + sweepAngle) * Math.PI / 180));

		for (int i = divisor - 1; i > 0; i--) {
			double radians = ((startingAngle + sweepAngle / divisor * i) * Math.PI / 180);
			this.lineTo((float)(centerX + outerRadius * offsetX * Math.cos(radians)), (float)(centerY + outerRadius * offsetY * Math.sin(radians)));
		}
		
		this.close();
		
		this.setFillType(Path.FillType.EVEN_ODD);
		return this;
	}
	
}
