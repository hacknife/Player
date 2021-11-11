package com.hacknife.player.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Size;
import android.view.View;

import com.hacknife.player.SurfaceType;


/**
 * author  : hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : IPlayer
 */
public class SurfaceView extends android.view.TextureView {
    private Size size = new Size(0, 0);
    private SurfaceType type = SurfaceType.SCREEN_TYPE_NORMAL;
    private boolean isTextureUpdated = false;

    public boolean isTextureUpdated() {
        return isTextureUpdated;
    }

    public void setTextureUpdated(boolean textureUpdated) {
        isTextureUpdated = textureUpdated;
    }

    public SurfaceView(Context context) {
        super(context);
    }

    public SurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setType(SurfaceType type) {
        if (this.type != type) {
            this.type = type;
            requestLayout();
        }
    }

    public SurfaceType getType() {
        return type;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        if (this.size.getWidth() != size.getWidth() || this.size.getHeight() != size.getHeight()) {
            this.size = size;
            requestLayout();
        }
    }

    @Override
    public void setRotation(float rotation) {
        if (rotation != getRotation()) {
            super.setRotation(rotation);
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int viewRotation = (int) getRotation();
        int videoWidth = size.getWidth();
        int videoHeight = size.getHeight();

        int parentHeight = ((View) getParent()).getMeasuredHeight();
        int parentWidth = ((View) getParent()).getMeasuredWidth();
        if (parentWidth != 0 && parentHeight != 0 && videoWidth != 0 && videoHeight != 0) {
            if (type == SurfaceType.SCREEN_TYPE_FIT_XY) {
                if (viewRotation == 90 || viewRotation == 270) {
                    int tempSize = parentWidth;
                    parentWidth = parentHeight;
                    parentHeight = tempSize;
                }
                videoHeight = videoWidth * parentHeight / parentWidth;
            }
        }

        if (viewRotation == 90 || viewRotation == 270) {
            int tempMeasureSpec = widthMeasureSpec;
            widthMeasureSpec = heightMeasureSpec;
            heightMeasureSpec = tempMeasureSpec;
        }

        int width = getDefaultSize(videoWidth, widthMeasureSpec);
        int height = getDefaultSize(videoHeight, heightMeasureSpec);
        if (videoWidth > 0 && videoHeight > 0) {

            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);


            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
                // the size is fixed
                width = widthSpecSize;
                height = heightSpecSize;
                // for compatibility, we adjust size based on aspect ratio
                if (videoWidth * height < width * videoHeight) {
                    width = height * videoWidth / videoHeight;
                } else if (videoWidth * height > width * videoHeight) {
                    height = width * videoHeight / videoWidth;
                }
            } else if (widthSpecMode == MeasureSpec.EXACTLY) {
                // only the width is fixed, adjust the height to match aspect ratio if possible
                width = widthSpecSize;
                height = width * videoHeight / videoWidth;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    height = heightSpecSize;
                    width = height * videoWidth / videoHeight;
                }
            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
                // only the height is fixed, adjust the width to match aspect ratio if possible
                height = heightSpecSize;
                width = height * videoWidth / videoHeight;
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    width = widthSpecSize;
                    height = width * videoHeight / videoWidth;
                }
            } else {
                // neither the width nor the height are fixed, try to use actual video size
                width = videoWidth;
                height = videoHeight;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // too tall, decrease both width and height
                    height = heightSpecSize;
                    width = height * videoWidth / videoHeight;
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // too wide, decrease both width and height
                    width = widthSpecSize;
                    height = width * videoHeight / videoWidth;
                }
            }
        } else {
            // no size yet, just adopt the given spec sizes
        }
        if (parentWidth != 0 && parentHeight != 0 && videoWidth != 0 && videoHeight != 0) {
            if (type == SurfaceType.SCREEN_TYPE_ORIGINAL) {
                /**原图**/
                height = videoHeight;
                width = videoWidth;
            } else if (type == SurfaceType.SCREEN_TYPE_CENTER_CROP) {
                if (viewRotation == 90 || viewRotation == 270) {
                    int tempSize = parentWidth;
                    parentWidth = parentHeight;
                    parentHeight = tempSize;
                }
                /**充满剪切**/
                if (((double) videoHeight / videoWidth) > ((double) parentHeight / parentWidth)) {
                    height = (int) (((double) parentWidth / (double) width * (double) height));
                    width = parentWidth;
                } else if (((double) videoHeight / videoWidth) < ((double) parentHeight / parentWidth)) {
                    width = (int) (((double) parentHeight / (double) height * (double) width));
                    height = parentHeight;
                }
            }
        }
        setMeasuredDimension(width, height);
    }
}
