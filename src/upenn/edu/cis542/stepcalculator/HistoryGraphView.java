package upenn.edu.cis542.stepcalculator;

import java.util.ArrayList;

import android.view.View;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class HistoryGraphView extends View {
	
	  public class point{
		  
		  float x,y;
	  }
	  
	  private float mX, mY;
      private static final float TOUCH_TOLERANCE = 4;

	  public static Canvas  mCanvas;
	  private Path   mPath;
	  private Paint  mPaint,newPaint;  
	  Bitmap bitmapSpeed=null;  
	  Bitmap bitmapDis=null;  
	  static int strokeWidth = 6;
	  int start;
	  float k = 8f;//parameter that project speed into view
		 
	  public static ArrayList<Pair<Path, Paint>> paths = new ArrayList<Pair<Path, Paint>>();
	  
	  static boolean resetCanvas = false;
	  //control points parameters
	  int numPoint = 5;
	  point[] speedPoints;
	  point[] disPoints;
	  float speed = 100f;
	  
	
	
	
	  public HistoryGraphView (Context c) {
	      super(c);
	      init();
	      setFocusable(true);
	      setFocusableInTouchMode(true);

	      mPaint = new Paint();
	      mPaint.setAntiAlias(true);
	      mPaint.setDither(true);
	      mPaint.setStyle(Paint.Style.STROKE);
	      mPaint.setStrokeJoin(Paint.Join.ROUND);
	      mPaint.setStrokeCap(Paint.Cap.ROUND);
	      mCanvas = new Canvas();
	      mPaint.setColor(Color.GREEN);
          mPaint.setStrokeWidth(10);
          mPaint.setTextSize(30);
         
          mPath = new Path();
	      newPaint = new Paint(mPaint);
          paths.add(new Pair<Path, Paint>(mPath, newPaint));
	     	       
	  }    
		  public HistoryGraphView (Context c, AttributeSet a) {
		      super(c, a);
		      init();

		      setFocusable(true);
		      setFocusableInTouchMode(true);

		      mPaint = new Paint();
		      mPaint.setAntiAlias(true);
		      mPaint.setDither(true);
		      mPaint.setStyle(Paint.Style.STROKE);
		      mPaint.setStrokeJoin(Paint.Join.ROUND);
		      mPaint.setStrokeCap(Paint.Cap.ROUND);
		      mCanvas = new Canvas();
		      mPath = new Path();
		      mPaint.setColor(Color.YELLOW);
			  newPaint = new Paint(mPaint);
		      paths.add(new Pair<Path, Paint>(mPath, newPaint));
      
	    }

		    protected ShapeDrawable square;
		    protected ShapeDrawable circle;

		     protected int squareColor = Color.BLUE;
		    
		     protected void init() {

		       speedPoints = new point[numPoint];
		       disPoints = new point[numPoint];
		       for(int i = 0; i<numPoint; i++){
		    	   speedPoints[i] = new point();
		    	   disPoints[i] = new point();
//		    	   calPoints[i].x = 100+i*i*20;
//		    	   calPoints[i].y = 120+i*i*10;
		    	   disPoints[i].x = 120+i*70;
		    	   disPoints[i].y = 150-i*10;
		       }
		       speedPoints[0].x = 40; speedPoints[0].y = 100;
		       speedPoints[1].x = 200; speedPoints[1].y = 110;
		       speedPoints[2].x = 250; speedPoints[2].y = 40;
		       speedPoints[3].x = 300; speedPoints[3].y = 157;
		       speedPoints[4].x = 360; speedPoints[4].y = 70;
		    
		     } 
		     
		     @SuppressLint("DrawAllocation")
			@Override
		     protected void onDraw(Canvas canvas) {

		    
			 // mPaint.setColor(0xff79cd40);
			  mPaint.setColor(0xffe1db9c);
		      drawLine(mPath,speedPoints);
	
//		      mPaint.setColor(Color.YELLOW);
//		      drawLine(mPath,disPoints);
		      
		      //if want to draw lines
//		      for (Pair<Path, Paint> p : paths)
//	              canvas.drawPath(p.first,p.second); 
		      
		      //if want to draw dots
		      newPaint = new Paint(mPaint);
		      newPaint.setStyle(Paint.Style.FILL);
		      newPaint.setColor(0xffe1db9c);
		      for(int i =0; i<numPoint; i++)
	    		  canvas.drawCircle(speedPoints[i].x, speedPoints[i].y, 10, newPaint);

		      mPaint.setColor(0xffcf6568);
		      mPaint.setStyle(Paint.Style.FILL);
		      mPaint.setStrokeWidth(0.5f);
		      mPaint.setTextSize(20);
		      bitmapSpeed = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
		      canvas.drawBitmap(bitmapSpeed, 400, speedPoints[numPoint-1].y, null);  
		      canvas.drawText("Speed "+speed, 360, 170, mPaint);  

	
		    
		    }

	
	   
	      private void drawLine(Path mPath, point []myPoints){
	    	  
	    	  mPath = new Path();
		      mPaint.setStrokeWidth(10);
			  newPaint = new Paint(mPaint);
		      paths.add(new Pair<Path, Paint>(mPath, newPaint));
		      mPath.reset();
		      
	    	  mPath.moveTo(myPoints[0].x, myPoints[0].y);
	    	  for(int i =1; i<numPoint; i++){

	    		  mPath.quadTo(myPoints[i-1].x, myPoints[i-1].y, 
	    				  (myPoints[i-1].x + myPoints[i].x)/2, 
	    				  (myPoints[i-1].y + myPoints[i].y)/2);
	    		
	    	  }

		      mPath.lineTo(myPoints[numPoint-1].x, myPoints[numPoint-1].y);
	      }


}
