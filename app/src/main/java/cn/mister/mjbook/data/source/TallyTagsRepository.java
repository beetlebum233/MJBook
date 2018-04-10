package cn.mister.mjbook.data.source;

import android.support.annotation.NonNull;

import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.data.source.local.TallyTagsLocalDataSource;

public class TallyTagsRepository implements TallyTagsDataSource {
    private static TallyTagsRepository INSTANCE;
    private static TallyTagsLocalDataSource localDataSource;

    private TallyTagsRepository(){
        localDataSource = TallyTagsLocalDataSource.getInstance();
    }

    public static TallyTagsRepository getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TallyTagsRepository();
        }
        return INSTANCE;
    }

    @Override
    public void addTallyTag(@NonNull TallyTag tag) {
        localDataSource.addTallyTag(tag);
    }

    @Override
    public void deleteTallyTagById(@NonNull String tagId) {

    }

    @Override
    public void getTallyTags(@NonNull LoadTallyTagsCallback callback) {
        localDataSource.getTallyTags(callback);
    }
}
