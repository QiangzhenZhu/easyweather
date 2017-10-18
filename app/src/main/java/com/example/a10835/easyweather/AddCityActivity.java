package com.example.a10835.easyweather;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by 10835 on 2017/10/12.
 */

public class AddCityActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,AddCityActivity.class);
        return intent;
    }
    @Override
    public Fragment createFragment() {
        return AddCityFragment.newInstance();
    }



}
