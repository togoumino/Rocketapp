package com.example.sanji.aninterface.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.sanji.aninterface.R;

public class ArrowView extends View {

    Canvas arrow;
    private Region mClipRegion;

    private Paint mArrowPaint;
    private Path mArrowPath;
    private Region mArrowRegion;

    private TextPaint mTextPaint;
    private Rect mBounds;
    private Region mTextRegion;

    private int mWidth;
    private int mHeight;

    private int mMultiplier = 0;

    private float mRotation;
    private float rMat[] = new float[16];
    private float orientation[] = new float[3];
    public ArrowView(Context context) {
        this(context, null, 0);
    }

    public ArrowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArrowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mArrowPaint = new Paint();
        mArrowPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        mArrowPaint.setStyle(Style.FILL);
        mArrowPaint.setAntiAlias(true);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Style.FILL);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Align.CENTER);

        mArrowPath = new Path();
        mBounds = new Rect();

        mClipRegion = new Region();
        mArrowRegion = new Region();
        mTextRegion = new Region();
    }

    public void setMultiplier(int multiplier) {
        mMultiplier = multiplier;

        invalidate();
    }

    public void setArrowRotation(float rotation) {
        SensorManager.getOrientation(rMat, orientation);
        float azimuth = (float)Math.toDegrees(orientation[0]);// get azimuth from the orientation sensor (it's quite simple)
        Location currentLoc = new Location("arrow");// get location from GPS or network
        currentLoc.setLatitude(32.2799304);
        currentLoc.setLongitude(-106.7468314);
        Location target = new Location("target");
        target.setLatitude(46.0035479);
        target.setLongitude(-72.7311097);
        GeomagneticField geoField = new GeomagneticField(
                (float) currentLoc.getLatitude(),
                (float) currentLoc.getLongitude(),
                (float) currentLoc.getAltitude(),
                System.currentTimeMillis());
        azimuth += geoField.getDeclination(); // converts magnetic north to true north
        float bearing = currentLoc.bearingTo(target); // (it's already in degrees)
        rotation  = azimuth - bearing;
        mRotation = rotation;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        mClipRegion.set(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.rotate(mRotation, mWidth / 2, mHeight / 2);

        // Draw an arrow
        mArrowPath.rewind();
        mArrowPath.moveTo(0, mHeight / 2);
        mArrowPath.lineTo(mWidth / 2, 0);
        mArrowPath.lineTo(mWidth, mHeight / 2);
        mArrowPath.lineTo(mWidth * 3 / 4, mHeight / 2);
        mArrowPath.lineTo(mWidth * 3 / 4, mHeight);
        mArrowPath.lineTo(mWidth / 4, mHeight);
        mArrowPath.lineTo(mWidth / 4, mHeight / 2);
        mArrowPath.lineTo(0, mHeight / 2);


        canvas.drawPath(mArrowPath, mArrowPaint);

        canvas.restore();

    }

    private void getTextRegion(String text) {
        mTextPaint.getTextBounds(text, 0, text.length(), mBounds);
        mTextRegion.set((int) (mBounds.left + (mWidth / 2.0f) - mBounds.width() / 2.0f),
                (int) (mBounds.top + (mHeight + mBounds.height()) / 2.0f),
                (int) (mBounds.right + (mWidth / 2.0f) - mBounds.width() / 2.0f),
                (int) (mBounds.bottom + (mHeight + mBounds.height()) / 2.0f));
    }

}