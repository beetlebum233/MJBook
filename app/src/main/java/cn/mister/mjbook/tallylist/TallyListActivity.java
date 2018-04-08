package cn.mister.mjbook.tallylist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.mister.mjbook.BaseView;
import cn.mister.mjbook.R;
import cn.mister.mjbook.data.Tally;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        List<Tally> list = new ArrayList<>();
        list.add(new Tally());
        tallyListView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TallyItemAdapter(this, list);
        tallyListView.setAdapter(mAdapter);
    }

    @Override
    public void setPresenter(TallyListContract.TallyListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showTallies(List<Tally> tallies) {

    }
}
