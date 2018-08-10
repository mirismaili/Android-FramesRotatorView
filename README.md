# Android-FramesAnimatorView
Related to this post on stackoverflow.com:
https://stackoverflow.com/a/51794993/5318303

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
