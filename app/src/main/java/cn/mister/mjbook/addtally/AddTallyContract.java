package cn.mister.mjbook.addtally;

import java.util.List;

import cn.mister.mjbook.BasePresenter;
import cn.mister.mjbook.BaseView;
import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.exception.InputInvalidException;

public interface AddTallyContract {
    interface AddTallyView extends BaseView<AddTallyPresenter>{
        void showTallyList();

        void setTallyInfo(Tally tally);

        Tally getTally() throws InputInvalidException;

        void showMsg(String msg);

        void setTags(List<TallyTag> tags);

        void showTally(Tally tally);

    }

    interface AddTallyPresenter extends BasePresenter{
        void saveTally();

        void populate();

        void getTags();

        void loadTally(String tallyId);
    }
}
