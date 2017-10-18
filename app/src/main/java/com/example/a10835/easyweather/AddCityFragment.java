package com.example.a10835.easyweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a10835.easyweather.moudle.CityPath;
import com.example.a10835.easyweather.util.ApiUrl;
import com.example.a10835.easyweather.util.HttpConnect;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by 10835 on 2017/10/12.
 */

public class AddCityFragment extends Fragment {
    private static final String TAG = "AddCityFragment";
    private RecyclerView mCityList;
    public static AddCityFragment newInstance() {

        return new AddCityFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_add_city,container,false);
        mCityList=view.findViewById(R.id.city_list_reCcycleView);
        mCityList.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_add,menu);
        MenuItem searchItem=menu.findItem(R.id.search_city);
        SearchView searchView= (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                HttpConnect.SendOkHttp(ApiUrl.getApiUrL(6, query), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "onFailure: ", e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                         final List<CityPath> citys=ApiUrl.parseCityItem(response.body().string());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CityListAdapter adapter=new CityListAdapter(citys);
                                mCityList.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                        });

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
    public class CityListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mCityName;
        private TextView mPath;
        private CityPath mCityPath;
        public CityListViewHolder(View itemView) {
            super(itemView);
            mCityName=itemView.findViewById(R.id.textView34);
            mPath=itemView.findViewById(R.id.textView35);
            itemView.setOnClickListener(this);
        }
        public void Bind(CityPath cityPath){
            mCityPath=cityPath;
            mCityName.setText(cityPath.getName());
            mPath.setText(cityPath.getPath());
        }

        @Override
        public void onClick(View v) {
             CityPath path=new CityPath();
             path=mCityPath;
             path.save();
             Intent intent=EasyWeatherViewPager.newIntent(getActivity(),mCityPath.getName());
             startActivity(intent);
        }
    }
    public class CityListAdapter extends RecyclerView.Adapter<CityListViewHolder>{
        private List<CityPath> mCitys;

        public CityListAdapter(List<CityPath> mCitys) {
            this.mCitys = mCitys;
        }

        @Override
        public CityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.city_select_recycleview,parent,false);
            return new CityListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CityListViewHolder holder, int position) {
            CityPath cityPath=mCitys.get(position);
            holder.Bind(cityPath);
        }

        @Override
        public int getItemCount() {
            return mCitys.size();
        }
    }
}
