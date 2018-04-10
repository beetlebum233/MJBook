package cn.mister.mjbook.taglist;

import java.util.List;

import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.data.source.TallyTagsRepository;

public class TagListPresenter implements TagListContract.TagListPresenter, TallyTagsRepository.LoadTallyTagsCallback {

    private TagListContract.TagListView mView;
    private TallyTagsRepository repository;

    public TagListPresenter(TagListContract.TagListView mView, TallyTagsRepository repository) {
        this.mView = mView;
        this.repository = repository;
    }

    @Override
    public void start() {
        repository.getTallyTags(this);
    }

    @Override
    public void reload() {
        repository.getTallyTags(this);
    }

    @Override
    public void addTag(TallyTag tag) {
        repository.addTallyTag(tag);
        reload();
    }

    @Override
    public void delete(String tagId) {
        repository.deleteTallyTagById(tagId);
        reload();
    }

    @Override
    public void onTallyTagsLoaded(List<TallyTag> tags) {
        mView.showTags(tags);
    }
}
