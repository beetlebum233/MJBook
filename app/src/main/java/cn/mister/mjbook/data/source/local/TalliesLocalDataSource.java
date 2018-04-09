package cn.mister.mjbook.data.source.local;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.tallylist.TallyType;
import cn.mister.mjbook.data.source.TalliesDataSource;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class TalliesLocalDataSource implements TalliesDataSource{

    private static TalliesLocalDataSource INSTANCE = null;
    private Realm realm;

    private TalliesLocalDataSource(){
        realm = Realm.getDefaultInstance();
    }

    public static TalliesLocalDataSource getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new TalliesLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getAllTallies(@NonNull LoadTalliesCallback callback) {
        RealmResults<Tally> results = realm.where(Tally.class).findAllSorted("createdTime", Sort.DESCENDING);
        List<Tally> list = realm.copyFromRealm(results);
        Handler handler = new Handler();
        handler.post(() -> callback.onTalliesLoaded(list));
    }

    @Override
    public void getTalliesByCondition(@NonNull LoadTalliesCallback callback, @NonNull TallyType tallyType, Date date, List<TallyTag> tags) {

    }

    @Override
    public void addTally(@NonNull Tally tally) {
        tally.setId(UUID.randomUUID().toString());

        realm.beginTransaction();
        realm.copyToRealm(tally);
        realm.commitTransaction();
    }

    @Override
    public void updateTally(@NonNull String tallyId, @NonNull Tally tally) {

    }

    @Override
    public void deleteTally(@NonNull String tallyId) {

    }

    @Override
    public void deleteTallies(@NonNull List<String> tallyIds) {

    }
}
