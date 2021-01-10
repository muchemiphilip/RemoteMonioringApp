package com.okotheffie.remotemonioringapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.okotheffie.remotemonioringapp.R;
import com.okotheffie.remotemonioringapp.models.Task;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TasksViewHolder> {

    private static final String TAG = "TasksAdapter";
    private Context context;
    private final List<Task> listTasks;

    public TaskAdapter(Context context, List<Task> listTasks) {
        this.context = context;
        this.listTasks = listTasks;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_assigned_tasks_item,parent,false);
      TasksViewHolder tasksViewHolder = new TasksViewHolder(view);
      return tasksViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TasksViewHolder holder, int position) {
        Task task = listTasks.get(position);
        holder.txvTaskName.setText(task.getName());
        holder.txvTaskAssigned.setText(task.getDescription());
        holder.txvAssignedBy.setText(task.getAssignedBy());
        holder.txvAssignedDate.setText(task.getDateAssigned());
        holder.txvDueDate.setText(task.getDueDate());
        holder.txvTaskId.setText(task.getTaskId());

    }

    @Override
    public int getItemCount() {
        Log.v(TAG,"All Assigned Tasks" + listTasks.size());
        return listTasks.size();    }

    public class TasksViewHolder extends RecyclerView.ViewHolder {

        public Context mContext;
        public CardView crdAssignedTasks;
        public TextView txvTaskName;
        public TextView txvTaskAssigned;
        public TextView txvAssignedBy;
        public TextView txvAssignedDate;
        public TextView txvDueDate;
        public TextView txvTaskId;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);

            crdAssignedTasks = itemView.findViewById(R.id.item_crd);
            txvTaskName = itemView.findViewById(R.id.task_name_txv);
            txvTaskAssigned = itemView.findViewById(R.id.task_txv);
            txvAssignedBy = itemView.findViewById(R.id.assigned_by_txv);
            txvAssignedDate = itemView.findViewById(R.id.assigned_date_txv);
            txvDueDate = itemView.findViewById(R.id.due_date_txv);
            txvTaskId = itemView.findViewById(R.id.task_id_txv);
        }
    }
}
