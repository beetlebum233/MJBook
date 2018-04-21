package cn.mister.mjbook.tallylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.mister.mjbook.R;
import cn.mister.mjbook.about.AboutActivity;
import cn.mister.mjbook.addtally.AddTallyActivity;
import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.source.TalliesRepository;
import cn.mister.mjbook.event.TalliesChangeEvent;
import cn.mister.mjbook.statistics.StatisticsActivity;
import cn.mister.mjbook.taglist.TagListActivity;

public class TallyListActivity extends AppCompatActivity implements TallyListContract.TallyListView {

    @BindView(R.id.sp_type_selector)
    public Spinner typeSpinner;

    @BindView(R.id.tv_time_selector)
    public TextView timeSelector;

    @BindView(R.id.rv_tally_list)
    public RecyclerView tallyListView;

    private TallyListContract.TallyListPresenter mPresenter;

    private TallyItemAdapter tallyItemAdapter;

    private ArrayAdapter<String> typeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tally_list);
        ButterKnife.bind(this);

        mPresenter = new TallyListPresenter(this, TalliesRepository.getInstance());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Tally> list = new ArrayList<>();
        tallyListView.setLayoutManager(new LinearLayoutManager(this));
        tallyItemAdapter = new TallyItemAdapter(this, list, mPresenter);
        tallyListView.setAdapter(tallyItemAdapter);
        String types[] = {"全部","收入","支出"};

        typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String typeStr = typeAdapter.getItem(position);
                mPresenter.changeType(TallyType.getTallyType(typeStr));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mPresenter.start();

        timeSelector.setOnClickListener((v) -> {
            TimePickerView pvTime = new TimePickerBuilder(this,
                    (date, v1) -> mPresenter.changeTime(date))
                    .setType(new boolean[]{true, true, false, false, false, false})
                    .build();
            pvTime.show();
        });
    }

    @Override
    public void showTallies(List<Tally> tallies) {
        tallyItemAdapter.setData(tallies);
        tallyItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAddTally(String tallyId) {
        Intent intent = new Intent();
        intent.setClass(this, AddTallyActivity.class);
        intent.putExtra("tallyId", tallyId);
        startActivity(intent);
    }

    @OnClick(R.id.fab)
    public void onFabClick(){
        mPresenter.addTally(null);
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
        mPresenter.reload();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.menu_statistics:
                intent.setClass(this, StatisticsActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_tags:
                intent.setClass(this, TagListActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_about:
                intent.setClass(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tally_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
