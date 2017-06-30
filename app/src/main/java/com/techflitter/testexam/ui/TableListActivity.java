package com.techflitter.testexam.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.techflitter.testexam.R;
import com.techflitter.testexam.adapter.CustomerListAdapter;
import com.techflitter.testexam.adapter.TableListAdapter;
import com.techflitter.testexam.db.RealmController;
import com.techflitter.testexam.model.CustomerBean;
import com.techflitter.testexam.model.TableBean;
import com.techflitter.testexam.network.RetrofitManager;
import com.techflitter.testexam.services.CustomerServices;
import com.techflitter.testexam.utils.Constant;
import com.techflitter.testexam.utils.RequestDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TableListActivity extends BaseActivity {

    @BindView(R.id.tablelistRecyclerView)
    RecyclerView mtTablelistRecyclerView;

    List<Boolean> mTableList;
    List<TableBean> tableBeanList;
    TableListAdapter mTableListAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tablelist;
    }

    @Override
    protected void initView() {
        setTitle("Tables");
        mtTablelistRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        tableBeanList = new ArrayList<>();
        RequestDialog.show(this);
        getTables();
    }

    private void getTables() {
        if (!RealmController.with(this).getTables().isEmpty()) {
            handlerResult(RealmController.with(this).getTables());
        } else {
            RetrofitManager.getInstance().create(CustomerServices.class)
                    .tableList()
                    .enqueue(new Callback<List<Boolean>>() {
                        @Override
                        public void onResponse(Call<List<Boolean>> call, Response<List<Boolean>> customerBaseBean) {
                            mTableList = customerBaseBean.body();
                            int i = 0;
                            tableBeanList.clear();
                            for (Boolean b : mTableList) {
                                TableBean bean = new TableBean();
                                bean.setId(""+i);
                                bean.setTableAvailable(b);
                                tableBeanList.add(bean);
                                i++;
                            }
                            RealmController.with(TableListActivity.this).addTable(tableBeanList);
                            handlerResult(tableBeanList);
                        }

                        @Override
                        public void onFailure(Call<List<Boolean>> call, Throwable t) {
                            RequestDialog.dismiss(TableListActivity.this);
                            Toast.makeText(TableListActivity.this, R.string.customer_list_failed, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void handlerResult(List<TableBean> mTableList) {
        RequestDialog.dismiss(TableListActivity.this);
        mTableListAdapter = new TableListAdapter(mTableList);
        mtTablelistRecyclerView.setAdapter(mTableListAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(updateBroadcastReceiver, new IntentFilter(Constant.UPDATE_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(updateBroadcastReceiver);
    }

    BroadcastReceiver updateBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getTables();
        }
    };
}
