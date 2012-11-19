package com.android.maxsec;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Preview extends SurfaceView implements SurfaceHolder.Callback{

	private static final String TAG = null;
	private SurfaceHolder mHolder;
    private Camera camera;

	 public Preview(Context context, Camera camera) {
	        super(context);
	        this.camera = camera;
	        mHolder = getHolder();
	        mHolder.addCallback(this);
	        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    }

	    public void surfaceCreated(SurfaceHolder holder) {
	        try {
	            camera.setPreviewDisplay(holder);
	            camera.startPreview();
	        } catch (IOException e) {
	            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
	        }
	    }

	    public void surfaceDestroyed(SurfaceHolder holder) {
	    	
	    }

	    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	        
	        if (mHolder.getSurface() == null){
	          
	          return;
	        }
            try {
	            camera.stopPreview();
	        } catch (Exception e){
	         
	        }

	        try {
	            camera.setPreviewDisplay(mHolder);
	            camera.startPreview();

	        } catch (Exception e){
	            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
	        }
	    }
}
