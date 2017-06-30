package com.techflitter.testexam.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techflitter.testexam.AppManger;
import com.techflitter.testexam.R;
import com.techflitter.testexam.db.RealmController;
import com.techflitter.testexam.model.CustomerBean;
import com.techflitter.testexam.model.TableBean;

import java.util.List;


public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.MyViewHolder> {

    private List<TableBean> tableList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tableNo;
        public RelativeLayout list_item_view;

        public MyViewHolder(View view) {
            super(view);
            tableNo = (TextView) view.findViewById(R.id.title);
            list_item_view = (RelativeLayout) view.findViewById(R.id.list_item_view);
        }
    }


    public TableListAdapter(List<TableBean> tableList) {
        this.tableList = tableList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tablelist_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tableNo.setText("" + (position + 1));
        holder.list_item_view.setBackgroundColor(tableList.get(position).isTableAvailable() ? holder.tableNo.getResources().getColor(R.color.green) : holder.tableNo.getResources().getColor(R.color.red));
        holder.list_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tableList.get(position).isTableAvailable()) {
                    holder.list_item_view.setBackgroundColor(holder.tableNo.getResources().getColor(R.color.red));
                    RealmController.with(new AppManger()).updateTableForUser(tableList.get(position));
                } else {
                    Toast.makeText(AppManger.getAppManger(), "Table is not avilable", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tableList != null ? tableList.size() : 0;
    }
}
