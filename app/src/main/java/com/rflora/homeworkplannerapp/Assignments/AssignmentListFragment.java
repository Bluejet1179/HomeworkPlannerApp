package com.rflora.homeworkplannerapp.Assignments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rflora.homeworkplannerapp.R;


public class AssignmentListFragment extends Fragment {
    private AssignmentDatabaseHandler databaseHandler;
    private RecyclerView mRecyclerView;
    private RecyclerAdapterAssignment mAdapterAssignment;
    public TextView no_HW;

    public AssignmentListFragment() {
        // Required empty public constructor
    }

    public static AssignmentListFragment newInstance() {
        AssignmentListFragment fragment = new AssignmentListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        databaseHandler = new AssignmentDatabaseHandler(getActivity());
        mAdapterAssignment = new RecyclerAdapterAssignment(databaseHandler, this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_assignment_list, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_viewf);
        no_HW = (TextView) v.findViewById(R.id.no_hw_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mAdapterAssignment);
        mRecyclerView.setItemAnimator(new CustomAnimator(getContext()));
        //Dividers
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        if(mAdapterAssignment.getItemCount() == 0){
            no_HW.setVisibility(View.VISIBLE);
        }else {
            no_HW.setVisibility(View.GONE);
        }









        return v;
    }

    public void addAssignment(Assignment assignment){
        databaseHandler.addAssignment(assignment);
        mAdapterAssignment.updateList();
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
        mAdapterAssignment.updateDatabase();

    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapterAssignment.notifyDataSetChanged();
        mRecyclerView.requestLayout();
    }

    public int size(){
       return mAdapterAssignment.getItemCount();
    }


}
