package cn.mister.mjbook.tallylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.EventLog;
import android.view.View;
import android.widget.Spinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.mister.mjbook.BaseView;
import cn.mister.mjbook.R;
import cn.mister.mjbook.addtally.AddTallyActivity;
import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.event.TalliesChangeEvent;

public class TallyListActivity extends AppCompatActivity implements TallyListContract.TallyListView {

    @BindView(R.id.sp_type_selector)
    public Spinner typeSpinner;

    @BindView(R.id.sp_time_selector)
    public Spinner timeSpinner;

    @BindView(R.id.rv_tally_list)
    public RecyclerView tallyListView;

    private TallyListContract.TallyListPresenter mPresenter;

    private TallyItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tally_list);
        ButterKnife.bind(this);

        mPresenter = new TallyListPresenter(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Tally> list = new ArrayList<>();
        list.add(new Tally());
        list.add(new Tally());
        tallyListView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TallyItemAdapter(this, list);
        tallyListView.setAdapter(mAdapter);
    }

    @Override
    public void showTallies(List<Tally> tallies) {
        mAdapter.setData(tallies);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAddTally() {
        Intent intent = new Intent();
        intent.setClass(this, AddTallyActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.fab)
    public void onFabClick(){
        mPresenter.addTally();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TalliesChangeEvent event) {

    }
}
