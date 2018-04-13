package cn.mister.mjbook.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.mister.mjbook.R;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.data.source.TalliesRepository;
import cn.mister.mjbook.data.source.TallyTagsRepository;
import cn.mister.mjbook.tallylist.TallyType;

public class StatisticsActivity extends AppCompatActivity implements StatisticsContract.StatisticsView {

    @BindView(R.id.tv_income)
    TextView incomeView;

    @BindView(R.id.tv_expense)
    TextView expenseView;

    @BindView(R.id.sp_tag_selector)
    public Spinner tagSpinner;

    @BindView(R.id.tv_time_selector)
    public TextView timeSelector;

    private ArrayAdapter<TallyTag> tagAdapter;

    private StatisticsContract.StatisticsPresenter mPresenter;

    private LayoutInflater mInflater;

    private List<TallyTag> tagList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);

        mPresenter = new StatisticsPresenter(this, TalliesRepository.getInstance(), TallyTagsRepository.getInstance());

        String types[] = {"全部","收入","支出"};

        List<TallyTag> emptyList = new ArrayList<>();
        tagAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, emptyList);
        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(tagAdapter);
        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                TallyTag tag = tagAdapter.getItem(position);
                List<TallyTag> tagList = new ArrayList<>();
                tagList.add(tag);
                mPresenter.changeTags(tagList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        timeSelector.setOnClickListener((v) -> {
            TimePickerView pvTime = new TimePickerBuilder(this,
                    (date, v1) -> mPresenter.changeTime(date))
                    .setType(new boolean[]{true, true, false, false, false, false})
                    .build();
            pvTime.show();
        });

        TallyTag tag = new TallyTag();
        tag.setName("全部");
        tagAdapter.add(tag);

        mPresenter.start();
        mPresenter.getTags();
    }

    @Override
    public void showStatistics(StatisticsVO statisticsVO) {
        incomeView.setText(statisticsVO.getIncome());
        expenseView.setText(statisticsVO.getExpense());
    }

    @Override
    public void setTags(List<TallyTag> tags) {
        tagList = tags;
        tagAdapter.addAll(tags);
    }
}
