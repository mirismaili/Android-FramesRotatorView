package ir.openside.frameanimatorsample;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by S. Mahdi Mir-Ismaili on 1397/5/18 (09/08/2018).
 * <a href="mailto:s.m.mirismaili@gmail.com">s.m.mirismaili@gmail.com</a>
 */
public class FramesAnimatorView extends AppCompatImageView {
	private int framesCount;
	private int duration;
	private Bitmap frameBitmap;
	
	public FramesAnimatorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}
	
	public FramesAnimatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	public FramesAnimatorView(Context context) { super(context); }
	
	private void init(Context context, AttributeSet attrs) {
		final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FramesAnimatorView);
		framesCount = typedArray.getInt(R.styleable.FramesAnimatorView_framesCount, 12);
		duration = typedArray.getInt(R.styleable.FramesAnimatorView_duration, 1200);
		typedArray.recycle();
		
		// Method 1: Use <rotate> as Animation (RotateAnimation) and startAnimation() (Rotate view itself).
		//method1(framesCount, duration);
		
		// Method 2: Use <rotate> as Drawable (RotateDrawable) and ObjectAnimator. Usable for API 21+ (because of using RotateDrawable.setDrawable).
		//method2();
		
		// Method 3 (Recommended): Use <animation-list> (AnimationDrawable) dynamically.
		final int frameDuration = this.duration / framesCount;
		final AnimationDrawable animationDrawable = (AnimationDrawable) getDrawable();
		
		for (int i = 0; i < framesCount; i++)
			animationDrawable.addFrame(
					new RotatedDrawable(frameBitmap, i * 360f / framesCount, getResources()),
					frameDuration);
		
		animationDrawable.start();
	}
	
	@Override public void setImageResource(int resId) { //info();
		frameBitmap = BitmapFactory.decodeResource(getResources(), resId);
		super.setImageDrawable(new AnimationDrawable());
	}
	
	@Override public void setImageDrawable(@Nullable Drawable drawable) { //info();
		frameBitmap = drawableToBitmap(drawable);
		super.setImageDrawable(new AnimationDrawable());
	}
	
	@Override public void setImageBitmap(Bitmap bitmap) { //info();
		frameBitmap = bitmap;
		super.setImageDrawable(new AnimationDrawable());
	}
	
	/**
	 * See <a href="https://stackoverflow.com/a/21376008/5318303">@android-developer's answer on stackoverflow.com</a>.
	 */
	private static class RotatedDrawable extends BitmapDrawable {
		private final float degrees;
		private int pivotX;
		private int pivotY;
		
		RotatedDrawable(Bitmap bitmap, float degrees, Resources res) {
			super(res, bitmap);
			pivotX = bitmap.getWidth() / 2;
			pivotY = bitmap.getHeight() / 2;
			this.degrees = degrees;
		}
		
		@Override public void draw(final Canvas canvas) {
			canvas.save();
			canvas.rotate(degrees, pivotX, pivotY);
			super.draw(canvas);
			canvas.restore();
		}
	}
	
	/**
	 * See <a href="https://stackoverflow.com/a/10600736/5318303">@André's answer on stackoverflow.com</a>.
	 */
	@NonNull private static Bitmap drawableToBitmap(Drawable drawable) {
		final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}
	
	//region Method1:
	
	// /**
	//  * Based on <a href="https://stackoverflow.com/a/14996762/5318303">@vokilam's answer on stackoverflow.com</a>.
	//  */
	// private void method1(final int frameCount, final int duration) {
	// 	// final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.progress_anim);
	// 	final Animation animation = new RotateAnimation(
	// 			0f, 360f, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
	// 	animation.setRepeatCount(INFINITE);
	// 	animation.setDuration(duration);
	// 	animation.setInterpolator(t -> (float) Math.floor(t * frameCount) / frameCount);
	// 	startAnimation(animation);
	// }
	//endregion
	
	//region Method2:
	
	// private RotateDrawable rotateDrawable;
	// private static final int MAX_ROTATE_DRAWABLE_LEVEL = 10000;
	//
	// private void method2() {
	// 	final ObjectAnimator animator = ObjectAnimator.ofInt(rotateDrawable,
	// 			new Property<RotateDrawable, Integer>(Integer.class, null) {
	// 				@Override public void set(RotateDrawable rotateDrawable, Integer value) {
	// 					rotateDrawable.setLevel(MAX_ROTATE_DRAWABLE_LEVEL * value / framesCount);
	// 				}
	// 				@Override public Integer get(RotateDrawable rotateDrawable) {
	// 					return rotateDrawable.getLevel() * framesCount / MAX_ROTATE_DRAWABLE_LEVEL;
	// 				}
	// 			}, 0, framesCount);
	//
	// 	animator.setDuration(duration).setRepeatCount(ValueAnimator.INFINITE);
	// 	animator.setInterpolator(new LinearInterpolator());
	// 	animator.start();
	// }
	//
	// @Override public void setImageResource(int resId) {
	// 	Log.i("xxx", "setImageResource: ");
	// 	rotateDrawable = new RotateDrawable();
	// 	rotateDrawable.setDrawable(getResources().getDrawable(resId));
	// 	super.setImageDrawable(rotateDrawable);
	// }
	//
	// @Override public void setImageDrawable(@Nullable Drawable drawable) {
	// 	Log.i("xxx", "setImageDrawable: ");
	// 	rotateDrawable = new RotateDrawable();
	// 	rotateDrawable.setDrawable(drawable);
	// 	super.setImageDrawable(rotateDrawable);
	// }
	//
	// @Override public void setImageBitmap(Bitmap bm) {
	// 	Log.i("xxx", "setImageBitmap: ");
	// 	rotateDrawable = new RotateDrawable();
	// 	rotateDrawable.setDrawable(new BitmapDrawable(getResources(), bm));
	// 	super.setImageDrawable(rotateDrawable);
	// }
	//endregion
}
