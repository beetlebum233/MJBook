package cn.mister.mjbook.data.source.local;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.data.source.TallyTagsDataSource;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class TallyTagsLocalDataSource implements TallyTagsDataSource{

    private static TallyTagsLocalDataSource INSTANCE;
    private Realm realm;

    private TallyTagsLocalDataSource(){
        realm = Realm.getDefaultInstance();
    }

    public static TallyTagsLocalDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TallyTagsLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void addTallyTag(@NonNull TallyTag tag) {
        tag.setId(UUID.randomUUID().toString());

        realm.beginTransaction();
        realm.copyToRealm(tag);
        realm.commitTransaction();
    }

    @Override
    public void deleteTallyTagById(@NonNull String tagId) {

    }

    @Override
    public void getTallyTags(@NonNull LoadTallyTagsCallback callback) {
        RealmResults<TallyTag> results = realm.where(TallyTag.class).findAll();
        List<TallyTag> list = realm.copyFromRealm(results);
        Handler handler = new Handler();
        handler.post(() -> callback.onTallyTagsLoaded(list));
    }
}
