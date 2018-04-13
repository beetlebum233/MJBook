package cn.mister.mjbook.statistics;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.data.source.TalliesDataSource;
import cn.mister.mjbook.data.source.TallyTagsDataSource;
import cn.mister.mjbook.tallylist.TallyType;

public class StatisticsPresenter implements StatisticsContract.StatisticsPresenter, TallyTagsDataSource.LoadTallyTagsCallback, TalliesDataSource.LoadTalliesCallback {

    private StatisticsContract.StatisticsView mView;

    private TalliesDataSource talliesDataSource;

    private TallyTagsDataSource tagsDataSource;

    private Date currentDateFrom = null;

    private List<TallyTag> currentTags = null;

    public StatisticsPresenter(StatisticsContract.StatisticsView mView, TalliesDataSource talliesDataSource, TallyTagsDataSource tagsDataSource) {
        this.mView = mView;
        this.talliesDataSource = talliesDataSource;
        this.tagsDataSource = tagsDataSource;
    }

    @Override
    public void reload() {
        talliesDataSource.getTalliesByCondition(this, TallyType.ALL, currentDateFrom, currentTags);
    }

    @Override
    public void changeTime(Date date) {
        currentDateFrom = date;
        reload();
    }

    @Override
    public void changeTags(List<TallyTag> tags) {
        currentTags = tags;
        reload();
    }

    @Override
    public void getTags() {
        tagsDataSource.getTallyTags(this);
    }

    @Override
    public void start() {
        talliesDataSource.getAllTallies(this);
    }

    @Override
    public void onTallyTagsLoaded(List<TallyTag> tags) {
        mView.setTags(tags);
    }

    @Override
    public void onTalliesLoaded(List<Tally> tallies) {
        StatisticsVO vo = new StatisticsVO();
        Double income = 0.0;
        Double expense = 0.0;
        if(!tallies.isEmpty()){
            for(Tally tally : tallies){
                if(tally.getIncome()){
                    income += tally.getAmount();
                }else{
                    expense += tally.getAmount();
                }
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        vo.setExpense(df.format(expense));
        vo.setIncome(df.format(income));
        mView.showStatistics(vo);
    }
}
