package cn.mister.mjbook.addtally;

import cn.mister.mjbook.BasePresenter;
import cn.mister.mjbook.BaseView;
import cn.mister.mjbook.data.Tally;

public interface AddTallyConstact {
    interface AddTallyView extends BaseView<AddTallyPresenter>{
        void showTallyList();

        void setTallyInfo(Tally tally);

    }

    interface AddTallyPresenter extends BasePresenter{
        void saveTally(Tally tally);

        void populate();
    }
}
