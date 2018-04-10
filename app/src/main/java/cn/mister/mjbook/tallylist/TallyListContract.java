package cn.mister.mjbook.tallylist;

import java.util.Date;
import java.util.List;

import cn.mister.mjbook.BasePresenter;
import cn.mister.mjbook.BaseView;
import cn.mister.mjbook.data.Tally;

public interface TallyListContract {
    interface TallyListView extends BaseView<TallyListPresenter>{
        void showTallies(List<Tally> tallies);

        void showAddTally();
    }

    interface TallyListPresenter extends BasePresenter{
        void addTally();

        void reload();

        void changeType(TallyType type);

        void changeTime(Date date);
    }
}
