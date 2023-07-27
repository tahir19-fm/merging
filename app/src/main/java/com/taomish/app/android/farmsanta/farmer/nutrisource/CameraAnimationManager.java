package com.taomish.app.android.farmsanta.farmer.nutrisource;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

public class CameraAnimationManager implements GoogleMap.CancelableCallback {

    private GoogleMap mGoogleMap;
    private List<CameraAnimation> mAnimations = new ArrayList<>();   // list of "animations"
    private int mCurrentAnimationIndex = -1;                         // index of current animation


    public CameraAnimationManager(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }


    @Override
    public void onFinish() {
        // if next animation exists
        if (mCurrentAnimationIndex < mAnimations.size() - 1) {
            // animate it
            mCurrentAnimationIndex++;
            CameraAnimation currentAnimation = mAnimations.get(mCurrentAnimationIndex);
            animate(currentAnimation);
        } else {
            mCurrentAnimationIndex = -1;
        }
    }


    @Override
    public void onCancel() {
        stopAnimation();
        mCurrentAnimationIndex = -1;
    }


    private void animate(CameraAnimation currentAnimation) {
        mGoogleMap.animateCamera(currentAnimation.cameraUpdate, currentAnimation.duration, this);
    }


    public void clear() {
        stopAnimation();
        mAnimations.clear();
        mCurrentAnimationIndex = -1;
    }


    public void addAnimation(CameraAnimation animation) {
        mAnimations.add(animation);
    }

    public void startAnimation() {
        // start sequence of animations from first
        if (mAnimations.size() > 0) {
            mCurrentAnimationIndex = 0;
            CameraAnimation currentAnimation = mAnimations.get(mCurrentAnimationIndex);
            animate(currentAnimation);
        }
    }


    public void stopAnimation() {
        mGoogleMap.stopAnimation();
    }


    // class for animation parameters store
    public static class CameraAnimation {
        public CameraUpdate cameraUpdate;
        public int duration;


        public CameraAnimation(CameraUpdate cameraUpdate, int duration) {
            this.cameraUpdate = cameraUpdate;
            this.duration = duration;
        }
    }

}