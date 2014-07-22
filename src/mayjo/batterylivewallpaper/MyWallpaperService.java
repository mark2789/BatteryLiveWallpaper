package mayjo.batterylivewallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.BatteryManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MyWallpaperService extends WallpaperService {
	
	
	@Override
	public Engine onCreateEngine() {
		return new MyWallpaperEngine();
	}

	private class MyWallpaperEngine extends Engine {
		private final Handler handler = new Handler();
		private final Runnable drawRunner = new Runnable() {
			public void run() {
				draw();
			}

		};
		private int width;
		int height;
		private boolean visible = true;
		private boolean touchEnabled;
		double level = 0;
		MySemiCircle section;
		int numberOfSemiCircles = 20;
		MyColor myColor;
		SharedPreferences prefs;
		boolean bool;
		int batteryLife;
		int increments;
		int textHeight;
		int textWidth;
		String textTest;
		int size;
		Paint textPaint;
		Paint paint;
		double rawlevel;
		double scale;
		IntentFilter batteryLevelFilter;
		
		
		public MyWallpaperEngine() {
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(MyWallpaperService.this);

			touchEnabled = prefs.getBoolean("touch", false);
			handler.post(drawRunner);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			if (visible) {
				handler.post(drawRunner);
			} else {
				handler.removeCallbacks(drawRunner);
			}
		}
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			this.visible = false;
			handler.removeCallbacks(drawRunner);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			this.width = width;
			this.height = height;
			super.onSurfaceChanged(holder, format, width, height);
		}

		@Override
		public void onTouchEvent(MotionEvent event) {
			if (touchEnabled) {
				
				SurfaceHolder holder = getSurfaceHolder();
				Canvas canvas = null;
				try {
					canvas = holder.lockCanvas();
					if (canvas != null) {
						drawSquares(canvas);

					}
				} finally {
					if (canvas != null)
						holder.unlockCanvasAndPost(canvas);
				}
				super.onTouchEvent(event);
			}
		}

		private void draw() {
			batteryLevel();

			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = null;
			
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {			
					drawSquares(canvas);
				}
			} finally {
				if (canvas != null)
					holder.unlockCanvasAndPost(canvas);
			}
			handler.removeCallbacks(drawRunner);
			if (visible) {
				//Sets delay
				int size = prefs.getInt("interval", 100);
				size = (size + 5) * 40;
				
				handler.postDelayed(drawRunner, 100);
			}
		}
		
		private void drawSquares(Canvas canvas) {
			prefs = PreferenceManager.getDefaultSharedPreferences(MyWallpaperService.this);			
			
			//Set paint settings
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);			
			paint.setAntiAlias(true);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(10f);   
		    paint.setStyle(Paint.Style.FILL_AND_STROKE);

		    //Set Background Colors based on user selection
		    String backgroundColor = prefs.getString("colors", "black");
		    if (backgroundColor.compareTo("black") == 0) {
		    	paint.setColor(Color.BLACK);
		    	canvas.drawRect(0, 0, width, height, paint);
		    }
		    else if (backgroundColor.compareTo("white") == 0) {
		    	paint.setColor(Color.WHITE);
			    canvas.drawRect(0, 0, width, height, paint);
		    }
			else if (backgroundColor.compareTo("blue") == 0) {
				paint.setColor(Color.BLUE);
			    canvas.drawRect(0, 0, width, height, paint);
			}
			else if (backgroundColor.compareTo("purple") == 0) {
				paint.setColor(Color.rgb(255,0,255));
			    canvas.drawRect(0, 0, width, height, paint);
			}
			else if (backgroundColor.compareTo("grey") == 0) {
				paint.setColor(Color.GRAY);
			    canvas.drawRect(0, 0, width, height, paint);
			}
			else if (backgroundColor.compareTo("pink") == 0) {
				paint.setColor(Color.rgb(255, 105, 180));
			    canvas.drawRect(0, 0, width, height, paint);
			}
			else if (backgroundColor.compareTo("darkGreen") == 0) {
				paint.setColor(Color.rgb(0, 100, 0));
			    canvas.drawRect(0, 0, width, height, paint);
			}
			else {
				paint.setColor(Color.BLACK);
				canvas.drawRect(0, 0, width, height, paint);
			}
		    
		    //Set Color of Battery Levels
		    if (level > 50) {
		    	myColor = new MyColor((int) (255 - (level - 51) * 5.2040816326530612244897959183673), 255, 0);
		    	paint.setColor(Color.rgb(myColor.getRed(), myColor.getGreen(), myColor.getBlue()));
		    }
		    else {
		    	myColor = new MyColor(255, (int) (level * 5.1), 0);
		    	paint.setColor(Color.rgb(myColor.getRed(), myColor.getGreen(), myColor.getBlue()));
		    }
		    
		    /*
		     * Section that draws the semicircless
		     */
		    //Draws 20 semi circles
		    if (prefs.getBoolean("barToggle", false) == false) {
			    batteryLife = (int)((1 - (level / 100.0)) * numberOfSemiCircles);
			    increments = 360 / numberOfSemiCircles;
			    size = increments - 13;
			    //numberOfSemiCircles = 40;
			    for (int i = 0; i < numberOfSemiCircles - batteryLife; i++) {
			        section = new MySemiCircle();
			        section.setDivisor(2);
			        section.setSize(width / 4, height / 4, width / 4 * 3, height / 4 * 3);
			        section.setSemiCircle(increments * i, size);
			        if (prefs.getBoolean("circleOrOval", true)) {
						canvas.drawPath(section.getSemiCircle(), paint);
			        }
			        else {
			        	canvas.drawPath(section.getSemiOval(), paint);
			        }
			    }
		    }
		    //Draws single semicircle
		    else {
		    	size = (int)(360 * level / 100.0);
			    section = new MySemiCircle();
			    section.setDivisor(60);
		        section.setBatteryLife(batteryLife);
		        section.setSize(width / 4, height / 4, width / 4 * 3, height / 4 * 3);
		        section.setSemiCircle(0, (int)size);
		        canvas.drawPath(section.getSemiCircle(), paint);
		    }
			
			bool = prefs.getBoolean("numberToggle", true);
			
			if (bool) {
			    paint.setStrokeWidth(2);
			    paint.setTextSize(50);
			    
			    Rect bounds = new Rect();
			    textPaint = paint;
				textTest = Integer.toString((int)level);
			    textPaint.getTextBounds(textTest,0,textTest.length(),bounds);
			    textHeight = bounds.height();
			    textWidth = bounds.width();
			    
			    canvas.drawText(Integer.toString((int)level), width / 2 - textWidth / 2, height / 2 + textHeight / 2, paint);
			}
			canvas = null;
			paint = null;
		}
		
	    private void batteryLevel() {
	    	BatteryLevelReceiver batteryLevelReceiver = new BatteryLevelReceiver();
	        batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
	        registerReceiver(batteryLevelReceiver, batteryLevelFilter); 
	    }
	    
	    private class BatteryLevelReceiver extends BroadcastReceiver {
	    	
	    	@Override
	    	public void onReceive(Context context, Intent intent) {
	    		if (intent == null)
	    			return;
	    		if (context == null)
	    			return;
	    		
	    		String action = intent.getAction();
	    		if (action == null)
	    			return;
	    		
	    		if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
	    			level = -1;
	    			rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1); 
	                scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1); 
	                        
	                if (rawlevel >= 0 && scale > 0) { 
	                	level = (rawlevel * 100) / scale; 
	    		}
	    	}
	    }
	    }
	}
}
