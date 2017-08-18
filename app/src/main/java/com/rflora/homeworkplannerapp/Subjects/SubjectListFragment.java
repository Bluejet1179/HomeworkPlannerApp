package com.rflora.homeworkplannerapp.Subjects;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rflora.homeworkplannerapp.Assignments.Assignment;
import com.rflora.homeworkplannerapp.Assignments.AssignmentDatabaseHandler;
import com.rflora.homeworkplannerapp.Assignments.AssignmentListFragment;
import com.rflora.homeworkplannerapp.Assignments.CustomAnimator;
import com.rflora.homeworkplannerapp.Assignments.RecyclerAdapterAssignment;
import com.rflora.homeworkplannerapp.R;


public class SubjectListFragment extends Fragment {
    private AssignmentDatabaseHandler databaseHandler;
    private RecyclerView mRecyclerView;
    private RecyclerAdapterSubjects mAdapterSubjects;
    public TextView no_Subjects;

    public SubjectListFragment() {
        // Required empty public constructor
    }

    public static SubjectListFragment newInstance() {
        SubjectListFragment fragment = new SubjectListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        databaseHandler = new AssignmentDatabaseHandler(getActivity());
        //Temp
        //databaseHandler.addSubject(new Subject("Math", 750));
        //databaseHandler.addSubject(new Subject("English", 900));
        mAdapterSubjects = new RecyclerAdapterSubjects(databaseHandler, this);
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_subject_list, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_subjects);
        no_Subjects = (TextView) v.findViewById(R.id.no_subjects_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mAdapterSubjects);
        mRecyclerView.setItemAnimator(new CustomAnimator(getContext()));
        //Dividers
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        mAdapterSubjects.updateList();


        if(mAdapterSubjects.getItemCount() == 0){
            no_Subjects.setVisibility(View.VISIBLE);
        }else {
            no_Subjects.setVisibility(View.GONE);
        }



        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                int id =(int) mAdapterSubjects.getItemId(position);
                databaseHandler.deleteSubject(id);
                mAdapterSubjects.updateList();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        return v;
    }

    public void addSubject(Subject subject){
        databaseHandler.addSubject(subject);
        mAdapterSubjects.updateList();
    }



    @Override
    public void onAttach(Context context) {



        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapterSubjects.updateDatabase();

    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapterSubjects.notifyDataSetChanged();
        mRecyclerView.requestLayout();
    }

    public int size(){
        return mAdapterSubjects.getItemCount();
    }


}
