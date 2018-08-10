# Android-FramesAnimatorView
Related to this post on stackoverflow.com:
https://stackoverflow.com/questions/3760381/rotating-image-animation-list-or-animated-rotate-android/

Don't forget to insert these lines in `/res/attrs.xml` file:

    <resources>
        <!-- ... -->
        <declare-styleable name="FramesAnimatorView">
            <attr name="framesCount" format="integer"/>
            <attr name="duration" format="integer" />
        </declare-styleable>
        <!-- ... -->
    </resources>