package com.android.desafioaudionews.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.activities.NoteDetailActivity;
import com.android.desafioaudionews.adapters.NoteAdapter;
import com.android.desafioaudionews.database.DatabaseHelper;
import com.android.desafioaudionews.interfaces.OnRecyclerItemClick;
import com.android.desafioaudionews.models.Note;
import com.android.desafioaudionews.utils.Const;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by Lucho on 23/09/2015.
 */
public class CategoryFragment extends Fragment implements OnRecyclerItemClick{
    private DatabaseHelper databaseHelper;
    private  int categoryID;
    @InjectView(R.id.contentViews)
    RecyclerView mRecyclerView;
    private NoteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        // get the category Id
        categoryID = args.getInt(Const.CATEGORY_ID, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_note_fragment, container, false);
        ButterKnife.inject(this, view);
        setRecycleView();
        if(savedInstanceState == null) {
            drawNotes();
        }
        return view;
    }

    private void setRecycleView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void drawNotes() {
        List<Note> notesForCategory = getNotesByCategory(categoryID);
        mAdapter = new NoteAdapter(getContext(),notesForCategory, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private List<Note> getNotesByCategory(int categoryID){
        return getHelper().getNotesByCategoryID(categoryID);
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;

    }

    @Override
    public void onListItemClick(Object object) {
        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
        intent.putExtra("noteId", ((Note) object).id);
        startActivity(intent);
    }
}
