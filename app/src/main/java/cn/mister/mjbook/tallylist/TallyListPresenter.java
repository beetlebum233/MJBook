package cn.mister.mjbook.tallylist;

import java.util.Date;
import java.util.List;

import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.source.TalliesDataSource;
import cn.mister.mjbook.data.source.TalliesRepository;

public class TallyListPresenter implements TallyListContract.TallyListPresenter, TalliesDataSource.LoadTalliesCallback {

    private TallyListContract.TallyListView mView;

    private TalliesRepository talliesRepository;

    private TallyType currentType = TallyType.ALL;

    private Date currentDateFrom = null;

    public TallyListPresenter(TallyListContract.TallyListView mView, TalliesRepository talliesRepository) {
        this.mView = mView;
        this.talliesRepository = talliesRepository;
    }

    @Override
    public void start() {
        talliesRepository.getAllTallies(this);
    }

    @Override
    public void addTally(String tallyId) {
        mView.showAddTally(tallyId);
    }

    @Override
    public void reload() {
        talliesRepository.getTalliesByCondition(this, currentType, currentDateFrom, null);
    }

    @Override
    public void changeType(TallyType type) {
        currentType = type;
        reload();
    }

    @Override
    public void changeTime(Date date) {
        currentDateFrom = date;
        reload();
    }

    @Override
    public void delete(String tallyId) {
        talliesRepository.deleteTally(tallyId);
        reload();
    }

    @Override
    public void onTalliesLoaded(List<Tally> tallies) {
        mView.showTallies(tallies);
    }
}
