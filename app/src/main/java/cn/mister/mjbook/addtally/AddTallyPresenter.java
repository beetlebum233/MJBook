package cn.mister.mjbook.addtally;

import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.source.TalliesRepository;
import cn.mister.mjbook.exception.InputInvalidException;

public class AddTallyPresenter implements AddTallyContract.AddTallyPresenter {
    private AddTallyContract.AddTallyView mView;

    private TalliesRepository talliesRepository;

    public AddTallyPresenter(AddTallyContract.AddTallyView mView, TalliesRepository talliesRepository) {
        this.mView = mView;
        this.talliesRepository = talliesRepository;
    }

    @Override
    public void saveTally() {
        Tally tally;
        try {
            tally = mView.getTally();
            talliesRepository.addTally(tally);
            mView.showTallyList();
        }catch (InputInvalidException e){
            mView.showMsg(e.getLocalizedMessage());
        }

    }

    @Override
    public void populate() {

    }

    @Override
    public void start() {

    }
}
