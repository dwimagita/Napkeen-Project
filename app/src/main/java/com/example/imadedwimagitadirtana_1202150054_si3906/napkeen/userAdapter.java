package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by dwima on 2/17/2018.
 */

public class userAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public userAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new bookmarkFragment();
            case 1:
                return new MyPhotoFragment();

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
