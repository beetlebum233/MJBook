package cn.mister.mjbook.tallylist;

import java.util.List;

import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.source.TalliesDataSource;
import cn.mister.mjbook.data.source.TalliesRepository;

public class TallyListPresenter implements TallyListContract.TallyListPresenter, TalliesDataSource.LoadTalliesCallback {

    private TallyListContract.TallyListView mView;

    private TalliesRepository talliesRepository;

    public TallyListPresenter(TallyListContract.TallyListView mView, TalliesRepository talliesRepository) {
        this.mView = mView;
        this.talliesRepository = talliesRepository;
    }

    @Override
    public void start() {
        talliesRepository.getAllTallies(this);
    }

    @Override
    public void addTally() {
        mView.showAddTally();
    }

    @Override
    public void reload() {
        talliesRepository.getAllTallies(this);
    }

    @Override
    public void onTalliesLoaded(List<Tally> tallies) {
        mView.showTallies(tallies);
    }
}
