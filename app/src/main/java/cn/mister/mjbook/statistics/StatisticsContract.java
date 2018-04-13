package cn.mister.mjbook.statistics;

import java.util.Date;
import java.util.List;

import cn.mister.mjbook.BasePresenter;
import cn.mister.mjbook.BaseView;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.data.source.TallyTagsDataSource;
import cn.mister.mjbook.tallylist.TallyType;

public interface StatisticsContract {
    interface StatisticsView extends BaseView<StatisticsPresenter>{
        void showStatistics(StatisticsVO statisticsVO);

        void setTags(List<TallyTag> tags);
    }

    interface StatisticsPresenter extends BasePresenter{
        void reload();

        void changeTime(Date date);

        void changeTags(List<TallyTag> tags);

        void getTags();
    }
}
