package com.example.a10835.easyweather;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;

/**
 * Created by 10835 on 2017/10/12.
 */

public class SelectCityActivity extends SingleFragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Fragment createFragment() {
        return SelectCityFragment.newInstance();
    }



}
