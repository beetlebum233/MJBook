package cn.mister.mjbook.tallylist;

public class TallyListPresenter implements TallyListContract.TallyListPresenter {

    private TallyListContract.TallyListView mView;

    public TallyListPresenter(TallyListContract.TallyListView mView) {
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void addTally() {
        mView.showAddTally();
    }
}
