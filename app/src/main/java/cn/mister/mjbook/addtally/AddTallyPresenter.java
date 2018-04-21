package cn.mister.mjbook.addtally;

import java.util.List;

import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.data.source.TalliesDataSource;
import cn.mister.mjbook.data.source.TalliesRepository;
import cn.mister.mjbook.data.source.TallyTagsDataSource;
import cn.mister.mjbook.data.source.TallyTagsRepository;
import cn.mister.mjbook.exception.InputInvalidException;

public class AddTallyPresenter implements AddTallyContract.AddTallyPresenter, TallyTagsDataSource.LoadTallyTagsCallback, TalliesDataSource.GetTallyCallback {
    private AddTallyContract.AddTallyView mView;

    private TalliesRepository talliesRepository;

    private TallyTagsRepository tagsRepository;

    public AddTallyPresenter(AddTallyContract.AddTallyView mView, TalliesRepository talliesRepository, TallyTagsRepository tagsRepository) {
        this.mView = mView;
        this.talliesRepository = talliesRepository;
        this.tagsRepository = tagsRepository;
    }

    @Override
    public void saveTally() {
        Tally tally;
        try {
            tally = mView.getTally();
            if(tally.getId() != null){
                talliesRepository.updateTally(tally.getId(), tally);
            }else{
                talliesRepository.addTally(tally);
            }
            mView.showTallyList();
        }catch (InputInvalidException e){
            mView.showMsg(e.getLocalizedMessage());
        }
    }

    @Override
    public void populate() {

    }

    @Override
    public void getTags() {
        tagsRepository.getTallyTags(this);
    }

    @Override
    public void loadTally(String tallyId) {
        talliesRepository.getTallyById(this, tallyId);
    }

    @Override
    public void start() {

    }

    @Override
    public void onTallyTagsLoaded(List<TallyTag> tags) {
        mView.setTags(tags);
    }

    @Override
    public void onTallyLoaded(Tally tally) {
        mView.showTally(tally);
    }
}
