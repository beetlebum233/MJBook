package cn.mister.mjbook.data.source.local;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.tallylist.TallyType;
import cn.mister.mjbook.data.source.TalliesDataSource;
import io.realm.Realm;
import io.realm.RealmQuery;
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
        RealmResults<Tally> results = realm.where(Tally.class).sort("createdTime", Sort.DESCENDING).findAll();
        List<Tally> list = realm.copyFromRealm(results);
        Handler handler = new Handler();
        handler.post(() -> callback.onTalliesLoaded(list));
    }

    @Override
    public void getTallyById(@NonNull GetTallyCallback callback, String tallyId) {
        Tally result = realm.where(Tally.class).equalTo("id", tallyId).findFirst();
        Tally tally = realm.copyFromRealm(result);
        Handler handler = new Handler();
        handler.post(() -> callback.onTallyLoaded(tally));
    }

    @Override
    public void getTalliesByCondition(@NonNull LoadTalliesCallback callback, @NonNull TallyType tallyType, Date dateFrom, List<TallyTag> tags) {
        RealmQuery<Tally> query = realm.where(Tally.class);
        switch (tallyType){
            case ALL:
                break;
            case INCOME:
                query.equalTo("isIncome", Boolean.TRUE);
                break;
            case EXPENSE:
                query.equalTo("isIncome", Boolean.FALSE);
                break;
        }
        if(dateFrom != null){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dateFrom);
            calendar.set(Calendar.DATE, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            dateFrom = calendar.getTime();
            calendar.add(calendar.MONTH,1);
            Date dateTo = calendar.getTime();
            query.between("createdTime", dateFrom, dateTo);
        }
        if(tags != null && !tags.isEmpty()){
            for(int i = 0; i < tags.size(); i++){
                TallyTag tag = tags.get(i);
                if(tag.getId() == null){
                    continue;
                }
                if(i != 0){
                    query.or();
                }
                query.equalTo("tags.id", tag.getId());
            }
        }
        RealmResults<Tally> results = query.sort("createdTime", Sort.DESCENDING).findAll();
        List<Tally> list = realm.copyFromRealm(results);
        Handler handler = new Handler();
        handler.post(() -> callback.onTalliesLoaded(list));
    }

    @Override
    public void addTally(@NonNull Tally tally) {
        tally.setId(UUID.randomUUID().toString());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(tally);
        realm.commitTransaction();
    }

    @Override
    public void updateTally(@NonNull String tallyId, @NonNull Tally tally) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(tally);
        realm.commitTransaction();
    }

    @Override
    public void deleteTally(@NonNull String tallyId) {
        realm.beginTransaction();
        Tally tally = realm.where(Tally.class).equalTo("id", tallyId).findFirst();
        if(tally != null){
            tally.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    @Override
    public void deleteTallies(@NonNull List<String> tallyIds) {

    }
}
