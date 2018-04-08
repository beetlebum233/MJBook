package cn.mister.mjbook.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import cn.mister.mjbook.data.TallyTag;

public interface TallyTagsDataSource {
    interface LoadTallyTagsCallback{
        void onTallyTagsLoaded(List<TallyTag> tags);
    }

    void addTallyTag(@NonNull TallyTag tag);

    void deleteTallyTagById(@NonNull String tagId);

    void getTallyTags(@NonNull LoadTallyTagsCallback callback);
}
