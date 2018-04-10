package cn.mister.mjbook.taglist;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import cn.mister.mjbook.R;

public class TagListActivity extends AppCompatActivity implements TagListContract.TagListView{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_tag_list);
        ButterKnife.bind(this);
    }
}
