package cn.mister.mjbook.data.source;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.data.source.local.TalliesLocalDataSource;
import cn.mister.mjbook.tallylist.TallyType;

public class TalliesRepository implements TalliesDataSource{
    private static TalliesRepository INSTANCE = null;

    private TalliesLocalDataSource localDataSource;

    private TalliesRepository(){
        localDataSource = TalliesLocalDataSource.getInstance();
    }

    public static TalliesRepository getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TalliesRepository();
        }
        return INSTANCE;
    }

    @Override
    public void getAllTallies(@NonNull LoadTalliesCallback callback) {
        localDataSource.getAllTallies(callback);
    }

    @Override
    public void getTalliesByCondition(@NonNull LoadTalliesCallback callback, @NonNull TallyType tallyType, Date date, List<TallyTag> tags) {

    }

    @Override
    public void addTally(@NonNull Tally tally) {
        localDataSource.addTally(tally);
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
