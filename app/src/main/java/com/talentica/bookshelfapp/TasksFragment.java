package com.talentica.bookshelfapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talentica.bookshelfapp.adapter.TaskListAdapter;
import com.talentica.bookshelfapp.model.Task;

import java.util.List;

/**
 * Created by rohanr on 8/29/16.
 */
public class TasksFragment extends Fragment {

    Context ctx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        ctx = getContext();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getAllPendingTasks();
    }

    private void getAllPendingTasks() {
        try{
//            TODO - replace with actual API call once ready; using temp data for now
            List<Task> taskList = ResponseUtil.createTaskListFromResponse();
            setTaskListAdapater(taskList);
        }catch (Exception e){
            Log.d("Rohan", "error in getting tasks:" + e.getMessage());
        }
    }

    private void setTaskListAdapater(List<Task> taskList) {
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.frg_tasks_rv);
        TaskListAdapter adapter = new TaskListAdapter(ctx, taskList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new SimpleItemDividerDecoration(ctx));
    }
}
