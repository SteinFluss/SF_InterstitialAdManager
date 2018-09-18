package com.steinfluss.library;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * @Autor SteinFluss
 * @USE: Helper class for Interstitial Ads*/

public class InterstitialAdManager {

    private static InterstitialAd mInterstitialAd;
    private static OnFinishedListener mOnFinishedListener;
    private static boolean showAdsThisSession = true;

    public static void render(Activity mainActivity, OnFinishedListener onFinishedListener, String interstitial_ad_unit_id){
        //showAds = PreferenceUtils.showAds();
        if (PreferenceUtils.showAds(mainActivity.getApplicationContext()) && showAdsThisSession) {
            mOnFinishedListener = onFinishedListener;
            showInterstitial(mainActivity, interstitial_ad_unit_id);
        }else {
            onFinishedListener.onFinish();
        }
    }

    private static void showInterstitial(Activity mainActivity, String interstitial_ad_unit_id) {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        try {
            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                showAdsThisSession = false;
            } else {
                goToNextLevel(mainActivity, interstitial_ad_unit_id);
                mOnFinishedListener.onFinish();
            }
            mOnFinishedListener.onFinish();
        }catch (Exception ae){
            mOnFinishedListener.onFinish();
        }
    }

    private static InterstitialAd newInterstitialAd(Activity mainActivity, String interstitial_ad_unit_id) {
        InterstitialAd interstitialAd = new InterstitialAd(mainActivity);
        interstitialAd.setAdUnitId(interstitial_ad_unit_id);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //nothing
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mOnFinishedListener.onFinish();
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                showAdsThisSession = false;
                mOnFinishedListener.onFinish();
            }
        });
        return interstitialAd;
    }



    private static void loadInterstitial() {
        // Disable the next level button and load the ad.
        /*AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();*/
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private static void goToNextLevel(Activity mainActivity, String interstitial_ad_unit_id) {
        mInterstitialAd = newInterstitialAd(mainActivity,interstitial_ad_unit_id);
        loadInterstitial();
    }

    public interface OnFinishedListener{
        void onFinish();
    }

    public static void disableAdsPermanent(Context context){
        PreferenceUtils.disableAds(context);
    }
}