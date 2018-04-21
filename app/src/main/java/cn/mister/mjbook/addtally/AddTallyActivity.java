package cn.mister.mjbook.addtally;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.mister.mjbook.R;
import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.data.source.TalliesRepository;
import cn.mister.mjbook.data.source.TallyTagsRepository;
import cn.mister.mjbook.event.TalliesChangeEvent;
import cn.mister.mjbook.exception.InputInvalidException;
import io.realm.RealmList;

public class AddTallyActivity extends AppCompatActivity implements AddTallyContract.AddTallyView {

    @BindView(R.id.tf_tally_type)
    TagFlowLayout tallyTypeView;

    @BindView(R.id.et_amount)
    EditText amountView;

    @BindView(R.id.et_time)
    EditText timeView;

    @BindView(R.id.tf_tally_tag)
    TagFlowLayout tallyTagsView;

    @BindView(R.id.et_note)
    EditText noteView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private LayoutInflater mInflater;

    private AddTallyContract.AddTallyPresenter mPresenter;

    private List<Boolean> isIncome;

    private List<TallyTag> tagList;

    private String tallyId = null;

    private Tally tally;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tally);
        ButterKnife.bind(this);


        mPresenter = new AddTallyPresenter(this, TalliesRepository.getInstance(), TallyTagsRepository.getInstance());

        mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        isIncome = new ArrayList<>();
        isIncome.add(Boolean.FALSE);
        isIncome.add(Boolean.TRUE);
        tallyTypeView.setAdapter(new TagAdapter<Boolean>(isIncome)
        {
            @Override
            public View getView(FlowLayout parent, int position, Boolean b)
            {
                TextView tv = (TextView)mInflater.inflate(R.layout.common_tag_btn,
                        tallyTypeView, false);
                if(b == Boolean.FALSE){
                    tv.setText("支出");
                }else{
                    tv.setText("收入");
                }
                return tv;
            }
        });

        mPresenter.getTags();
        Intent intent = getIntent();
        if (intent != null) {
            tallyId = intent.getStringExtra("tallyId");
            if(tallyId != null){
                mPresenter.loadTally(tallyId);
            }
        }

    }

    @Override
    public void showTallyList() {
        EventBus.getDefault().post(new TalliesChangeEvent());
        this.finish();
    }

    @Override
    public void setTallyInfo(Tally tally) {

    }

    @Override
    public Tally getTally() throws InputInvalidException{
        if(tally == null){
            tally = new Tally();
        }

        Set<Integer> set = tallyTypeView.getSelectedList();
        if(set.isEmpty()){
            throw new RuntimeException("请选择类型");
        }
        for(Integer i : set){
            tally.setIncome(isIncome.get(i));
        }
        String amount = amountView.getText().toString();
        if("".equals(amount)){
            throw new RuntimeException("请输入金额");
        }
        try{
            tally.setAmount(Double.parseDouble(amount));
        }catch(NumberFormatException e){
            throw new RuntimeException("金额格式不正确");
        }
        Set<Integer> tagSet = tallyTagsView.getSelectedList();
        if(!tagSet.isEmpty()){
            for(Integer i : tagSet){
                RealmList<TallyTag> list = new RealmList<>();
                list.add(tagList.get(i));
                tally.setTags(list);
            }
        }
        if("".equals(timeView.getText().toString())){
            tally.setCreatedTime(new Date());
        }else{
            SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
            try {
                tally.setCreatedTime(dd.parse(timeView.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        tally.setNote(noteView.getText().toString());
        return tally;
    }

    @Override
    public void showMsg(String msg) {
        Snackbar.make(fab, msg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void setTags(List<TallyTag> tags) {
        tagList = tags;
        tallyTagsView.setAdapter(new TagAdapter<TallyTag>(tags)
        {
            @Override
            public View getView(FlowLayout parent, int position, TallyTag tag)
            {
                TextView tv = (TextView)mInflater.inflate(R.layout.common_tag_btn,
                        tallyTypeView, false);
                tv.setText(tag.getName());
                return tv;
            }
        });
    }

    @Override
    public void showTally(Tally tally) {
        if(tally != null){
            this.tally = tally;
            DecimalFormat df = new DecimalFormat("0.00");
            amountView.setText(df.format(tally.getAmount()));

            Set<Integer> selectedType = new HashSet<>();

            if(tally.getIncome()){
                selectedType.add(1);
                tallyTypeView.getSelectedList();
                tallyTypeView.getAdapter().setSelectedList(selectedType);

            }else{
                selectedType.add(0);
                tallyTypeView.getSelectedList();
                tallyTypeView.getAdapter().setSelectedList(selectedType);
            }

            noteView.setText(tally.getNote());

            SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = dd.format(tally.getCreatedTime());
            timeView.setText(dateStr);

            if(tagList != null && !tally.getTags().isEmpty()){
                Set<Integer> selectedTags = new HashSet<>();
                for(TallyTag myTag : tally.getTags()){
                    for(int i = 0; i < tagList.size(); i ++){
                        TallyTag tag = tagList.get(i);
                        if(myTag.getName().equals(tag.getName())){
                            selectedTags.add(i);
                        }
                    }
                }
                tallyTagsView.getAdapter().setSelectedList(selectedTags);
            }


        }
    }

    @OnClick(R.id.fab)
    public void onFabClick(){
        mPresenter.saveTally();
    }

    @OnClick(R.id.et_time)
    void onTimeClick(){
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(AddTallyActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = dd.format(date);
                timeView.setText(dateStr);
            }
        }).build();
        pvTime.show();
    }


}
