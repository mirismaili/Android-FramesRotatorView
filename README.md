# Android-FramesAnimatorView
Related to this post on stackoverflow.com:
https://stackoverflow.com/a/51794993/5318303

**See [FramesAnimatorView.java file](FramesAnimatorView.java).**

Don't forget to insert these lines in `/res/attrs.xml` file:

```xml
<resources>
    <!-- ... -->
    <declare-styleable name="FramesAnimatorView">
        <attr name="framesCount" format="integer"/>
        <attr name="duration" format="integer" />
    </declare-styleable>
    <!-- ... -->
</resources>
```

Sample usage (like `ImageView` but `android:src` or `app:srcCompat` drawable will rotating):

```xml
<ir.openside.frameanimatorsample.FramesAnimatorView
		android:src="@drawable/ic_spinner"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content" />
```
