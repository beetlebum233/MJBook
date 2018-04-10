package cn.mister.mjbook.taglist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.mister.mjbook.R;
import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.data.source.TallyTagsRepository;
import cn.mister.mjbook.tallylist.TallyItemAdapter;

public class TagListActivity extends AppCompatActivity implements TagListContract.TagListView{

    @BindView(R.id.rv_tag_list)
    RecyclerView tagListView;

    TallyTagAdapter mAdapter;

    TagListContract.TagListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);
        ButterKnife.bind(this);

        mPresenter = new TagListPresenter(this, TallyTagsRepository.getInstance());

        tagListView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TallyTagAdapter(new ArrayList<>(), this, mPresenter);
        tagListView.setAdapter(mAdapter);

        mPresenter.start();
    }

    @Override
    public void showTags(List<TallyTag> tags) {
        mAdapter.setData(tags);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab)
    void onFabClick(){
        final EditText editText = new EditText(this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(this);
        inputDialog.setTitle("请输入标签名称").setView(editText);
        inputDialog.setPositiveButton("确定",
                (dialog, which) -> {
                    TallyTag tag = new TallyTag();
                    tag.setName(editText.getText().toString());
                    mPresenter.addTag(tag);
                }).show();
    }
}
