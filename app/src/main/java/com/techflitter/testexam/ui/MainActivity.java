package com.techflitter.testexam.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.techflitter.testexam.AppManger;
import com.techflitter.testexam.R;
import com.techflitter.testexam.adapter.CustomerListAdapter;
import com.techflitter.testexam.backgroundTask.MyAlarmReceiver;
import com.techflitter.testexam.db.RealmController;
import com.techflitter.testexam.model.CustomerBean;
import com.techflitter.testexam.network.RetrofitManager;
import com.techflitter.testexam.services.CustomerServices;
import com.techflitter.testexam.utils.Constant;
import com.techflitter.testexam.utils.RequestDialog;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements CustomerListAdapter.onItemClickListner {

    @BindView(R.id.customerRecyclerView)
    RecyclerView mCustomerRecyclerView;

    List<CustomerBean> mCustomerBeanList;
    CustomerListAdapter mCustomerListAdapter;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_customlist;
    }

    @Override
    protected void initView() {
        setTitle("Customers");
        mCustomerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestDialog.show(this);
        getCustomers();
        scheduleAlarm();
    }

    private void getCustomers() {
        if (!RealmController.with(this).getCustomers().isEmpty()) {
            handlerResult(RealmController.with(this).getCustomers());
        } else {
            RetrofitManager.getInstance().create(CustomerServices.class)
                    .customerList()
                    .enqueue(new Callback<List<CustomerBean>>() {
                        @Override
                        public void onResponse(Call<List<CustomerBean>> call, Response<List<CustomerBean>> customerBaseBean) {
                            mCustomerBeanList = customerBaseBean.body();
                            RealmController.with(MainActivity.this).addCustomers(mCustomerBeanList);
                            handlerResult(mCustomerBeanList);
                        }

                        @Override
                        public void onFailure(Call<List<CustomerBean>> call, Throwable t) {
                            RequestDialog.dismiss(MainActivity.this);
                            Toast.makeText(MainActivity.this, R.string.customer_list_failed, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                mCustomerListAdapter.filter(searchQuery.toString().trim());
                mCustomerRecyclerView.invalidate();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
        });
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    private void handlerResult(List<CustomerBean> customerBeans) {
        RequestDialog.dismiss(MainActivity.this);
        mCustomerListAdapter = new CustomerListAdapter(customerBeans, this);
        mCustomerRecyclerView.setAdapter(mCustomerListAdapter);
    }

    @Override
    public void onItemClick(int position, CustomerBean bean) {
        Intent intent = new Intent(this, TableListActivity.class);
        startActivity(intent);
    }

    public void scheduleAlarm() {
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                Constant.API_INTERVAL * 1000, pIntent);
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
            getCustomers();
        }
    };
}
