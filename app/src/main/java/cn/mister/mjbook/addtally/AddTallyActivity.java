package cn.mister.mjbook.addtally;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.mister.mjbook.data.Tally;

public class AddTallyActivity extends Activity implements AddTallyConstact.AddTallyView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void showTallyList() {

    }

    @Override
    public void setTallyInfo(Tally tally) {

    }

    @Override
    public void setPresenter(AddTallyConstact.AddTallyPresenter presenter) {

    }
}
