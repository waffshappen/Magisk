package com.topjohnwu.magisk.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.topjohnwu.magisk.R;
import com.topjohnwu.magisk.ViewBinder;
import com.topjohnwu.magisk.adapters.SuLogAdapter;
import com.topjohnwu.magisk.components.BaseFragment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class SuLogFragment extends BaseFragment {

    public TextView emptyRv;
    public RecyclerView recyclerView;

    private SuLogAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_log, menu);
        menu.findItem(R.id.menu_save).setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_su_log, container, false);
        ViewBinder.bind(this, v);
        adapter = new SuLogAdapter(mm.mDB);
        recyclerView.setAdapter(adapter);

        updateList();

        return v;
    }

    private void updateList() {
        adapter.notifyDBChanged();

        if (adapter.getSectionCount() == 0) {
            emptyRv.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyRv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                updateList();
                return true;
            case R.id.menu_clear:
                mm.mDB.clearLogs();
                updateList();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewBinder.unbind(this);
    }
}
