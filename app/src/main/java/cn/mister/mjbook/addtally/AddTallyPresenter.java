package cn.mister.mjbook.addtally;

import java.util.List;

import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.data.source.TalliesRepository;
import cn.mister.mjbook.data.source.TallyTagsDataSource;
import cn.mister.mjbook.data.source.TallyTagsRepository;
import cn.mister.mjbook.exception.InputInvalidException;

public class AddTallyPresenter implements AddTallyContract.AddTallyPresenter, TallyTagsDataSource.LoadTallyTagsCallback {
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
    public void getTags() {
        tagsRepository.getTallyTags(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onTallyTagsLoaded(List<TallyTag> tags) {
        mView.setTags(tags);
    }
}
