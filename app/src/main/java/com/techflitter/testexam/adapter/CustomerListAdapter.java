package com.techflitter.testexam.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techflitter.testexam.R;
import com.techflitter.testexam.model.CustomerBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder> {

    private List<CustomerBean> customerBeanList;
    private List<CustomerBean> filterCustomerBeanList;
    private onItemClickListner mOnItemClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView firstname, lastname;
        public RelativeLayout list_item_view;

        public MyViewHolder(View view) {
            super(view);
            firstname = (TextView) view.findViewById(R.id.title);
            lastname = (TextView) view.findViewById(R.id.genre);
            list_item_view = (RelativeLayout) view.findViewById(R.id.list_item_view);
        }
    }


    public CustomerListAdapter(List<CustomerBean> customerBeanList, onItemClickListner onItemClickListner) {
        this.customerBeanList = customerBeanList;
        filterCustomerBeanList = new ArrayList<CustomerBean>();
        filterCustomerBeanList.addAll(customerBeanList);
        this.mOnItemClickListner = onItemClickListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CustomerBean customerBean = customerBeanList.get(position);
        holder.firstname.setText(customerBean.getCustomerFirstName());
        holder.lastname.setText(customerBean.getCustomerLastName());

        holder.list_item_view.setTag(position);
        holder.list_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListner.onItemClick(Integer.parseInt(v.getTag().toString()), customerBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerBeanList != null ? customerBeanList.size() : 0;
    }

    public interface onItemClickListner {
        void onItemClick(int position, CustomerBean bean);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        customerBeanList.clear();
        if (charText.length() == 0) {
            customerBeanList.addAll(filterCustomerBeanList);
        } else {
            for (CustomerBean customerBean : filterCustomerBeanList) {
                String firstName = customerBean.getCustomerFirstName();
                String lastName = customerBean.getCustomerLastName();
                if (charText.length() != 0 && (firstName.toLowerCase(Locale.getDefault()).contains(charText) || lastName.toLowerCase(Locale.getDefault()).contains(charText))) {
                    customerBeanList.add(customerBean);
                }
            }
        }
        notifyDataSetChanged();
    }
}
