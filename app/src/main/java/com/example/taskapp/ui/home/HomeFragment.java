package com.example.taskapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.adapters.AllMenuAdapter;
import com.example.taskapp.adapters.PopularAdapter;
import com.example.taskapp.adapters.RecommendedAdapter;
import com.example.taskapp.model.Allmenu;
import com.example.taskapp.model.FoodData;
import com.example.taskapp.model.Popular;
import com.example.taskapp.model.Recommended;
import com.example.taskapp.retrofit.ApiCallInterface;
import com.example.taskapp.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ApiCallInterface apiCallInterface;

    RecyclerView popularRecyclerView, recommendedRecyclerView, allMenuRecyclerView;

    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    AllMenuAdapter allMenuAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        BottomNavigationView bottomNavigation = (BottomNavigationView) root.findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //TODO
        // This is simple api call just for this  task.
        // later on it should be implemented with viewmodel to prevent data call every time onCreateView is called when fragment is  recreated
        apiCallInterface = RetrofitClient.getRetrofitInstance().create(ApiCallInterface.class);
        Call<List<FoodData>> call = apiCallInterface.getAllData();
        call.enqueue(new Callback<List<FoodData>>() {
            @Override
            public void onResponse(Call<List<FoodData>> call, Response<List<FoodData>> response) {

                List<FoodData> foodDataList = response.body();


                getPopularDataAndSetRecycler(foodDataList.get(0).getPopular());

                getRecommendedDataAndSetRecycler(foodDataList.get(0).getRecommended());

                getAllMenuDataAndSetRecycler(foodDataList.get(0).getAllmenu());

                // we have fetched data from server and it is shown data in app using recycler view.

            }

            @Override
            public void onFailure(Call<List<FoodData>> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong:"+"\n"+"server busy or data is off", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void getPopularDataAndSetRecycler(List<Popular> popularList){

        popularRecyclerView = getActivity().findViewById(R.id.popular_recycler);
        popularAdapter = new PopularAdapter(getContext(), popularList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);

    }

    private void getRecommendedDataAndSetRecycler(List<Recommended> recommendedList){

        recommendedRecyclerView = getActivity().findViewById(R.id.recommended_recycler);
        recommendedAdapter = new RecommendedAdapter(getContext(), recommendedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

    }

    private void getAllMenuDataAndSetRecycler(List<Allmenu> allmenuList){

        allMenuRecyclerView = getActivity().findViewById(R.id.all_menu_recycler);
        allMenuAdapter = new AllMenuAdapter(getContext(), allmenuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(allMenuAdapter);
        allMenuAdapter.notifyDataSetChanged();

    }


    //Change fragments by click nav buttons
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    /*TODO this will be handled with viewModel or local database for cache
                    why this case is blank ?
                     because in current scenario i do not recreate home fragment by clicking bottom menu home
                    since it will again recreaete fragment and retrofitapi call will again consume internet data */
                    //TODO
                    //please check navigation graph under  res/navigation/mobile_navigation.xml

                    break;
                case R.id.nav_service:
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.nav_home_to_home_service);
                    break;

                case R.id.nav_posts:
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.nav_home_to_home_posts);
                    break;
            }

            return true;
        }
    };



}