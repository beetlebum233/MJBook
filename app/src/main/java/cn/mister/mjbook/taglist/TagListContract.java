package cn.mister.mjbook.taglist;

import java.util.List;

import cn.mister.mjbook.BasePresenter;
import cn.mister.mjbook.BaseView;
import cn.mister.mjbook.data.TallyTag;

public interface TagListContract {
    interface TagListView extends BaseView<TagListPresenter>{
        void showTags(List<TallyTag> tags);
    }

    interface TagListPresenter extends BasePresenter{
        void reload();

        void addTag(TallyTag tag);

        void delete(String tagId);
    }
}
