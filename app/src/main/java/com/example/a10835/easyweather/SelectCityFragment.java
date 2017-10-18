package com.example.a10835.easyweather;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.a10835.easyweather.moudle.CityPath;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 10835 on 2017/10/12.
 */

public class SelectCityFragment extends Fragment {
    private RecyclerView mCityListRecycleView;
    private RadioButton mRadioButton;
    private List<CityPath> mCitys;

    public static Fragment newInstance(){
        return new SelectCityFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCitys= DataSupport.findAll(CityPath.class);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_select_city,container,false);
        mCityListRecycleView=view.findViewById(R.id.select_recycleView);
        mCityListRecycleView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        CityListAdapter adapter=new CityListAdapter(mCitys);
        mCityListRecycleView.setAdapter(adapter);
        return view;
    }

    public class CityListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mCityNameText;

        private CityPath city;
        public CityListViewHolder(View itemView) {
            super(itemView);

            mCityNameText=itemView.findViewById(R.id.textView21);
            mRadioButton=itemView.findViewById(R.id.radioButton);
            itemView.setOnClickListener(this);
        }
        public void Bind(CityPath cityPath){
            city=cityPath;
            mCityNameText.setText(cityPath.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent=EasyWeatherViewPager.newIntent(v.getContext(),city.getName());
            startActivity(intent );
        }
    }
    public class CityListAdapter extends RecyclerView.Adapter<CityListViewHolder> {
        private List<CityPath> mCitys;

        public CityListAdapter(List<CityPath> mCitys) {
            this.mCitys = mCitys;
        }

        @Override
        public CityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_select_city_item,parent,false);
            return new CityListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CityListViewHolder holder, int position) {
            CityPath city=mCitys.get(position);
            holder.Bind(city);
        }

        @Override
        public int getItemCount() {
            return mCitys.size();
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_select,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_city:
                Intent intent=AddCityActivity.newIntent(getContext());
                startActivity(intent);
                break;
            case R.id.delete_city:
                mRadioButton.setVisibility(View.VISIBLE);
                break;
        }
        return false;
    }

}
