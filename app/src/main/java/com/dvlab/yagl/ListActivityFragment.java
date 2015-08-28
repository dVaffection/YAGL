package com.dvlab.yagl;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivityFragment extends ListFragment {

    public static final String TAG = ListActivityFragment.class.getSimpleName();
    public static final int REQUEST_TEXT = 0;

    private List<String> items = new ArrayList<>();

    public ListActivityFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        setEmptyText(getResources().getString(R.string.empty_list));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);


//        ListView listView = (ListView) view.findViewById(android.R.id.list);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            FloatingActionButton addButton = (FloatingActionButton) getActivity().findViewById(R.id.floating_new_item);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTextFragmentDialog();
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_item:

                showTextFragmentDialog();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showTextFragmentDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        TextFragment dialog = new TextFragment();
        Bundle args = dialog.createArgs("");
        dialog.setArguments(args);
        dialog.setTargetFragment(ListActivityFragment.this, REQUEST_TEXT);
        dialog.show(fm, "text");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_TEXT) {
            String text = data.getStringExtra(TextFragment.EXTRA_TEXT);

            if (!text.isEmpty()) {
                items.add(text);
                ((ArrayAdapter) getListAdapter()).notifyDataSetChanged();
            }
        }
    }

}
