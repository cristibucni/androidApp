package com.exam.AndroidApp.nav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.exam.AndroidApp.adapter.RecyclerViewAdapter;
import com.exam.AndroidApp.data.Data;
import com.exam.AndroidApp.main.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    RecyclerView mRecyclerView;

    private List<Data> dabListItem;
    private RecyclerViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.recyclerview, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dabListItem = new ArrayList<>();

        dabListItem = Data.getData();

        mAdapter = new RecyclerViewAdapter(getActivity(), dabListItem);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.nav_drawer_menu, menu);
        final MenuItem item = menu.findItem(R.id.nav_profile_id);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Filter title");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // On form submit do query
        final List<Data> filteredModelList = filter(dabListItem, query);
        mAdapter.setItems(filteredModelList);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    private List<Data> filter(List<Data> datas, String newText) {
        // Filter function for current data
        // Filters by title
        newText = newText.toLowerCase().trim();

        final List<Data> filteredModelList = new ArrayList<>();
        for (Data data : datas) {
            final String text = data.getTaskName().toLowerCase().trim();
            if (text.contains(newText)) {
                filteredModelList.add(data);
            }
        }
        return filteredModelList;
    }
}
