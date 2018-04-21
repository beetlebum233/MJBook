package cn.mister.mjbook.data.source;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import cn.mister.mjbook.data.Tally;
import cn.mister.mjbook.data.TallyTag;
import cn.mister.mjbook.tallylist.TallyType;

public interface TalliesDataSource {
    interface LoadTalliesCallback{
        void onTalliesLoaded(List<Tally> tallies);
    }

    interface GetTallyCallback{
        void onTallyLoaded(Tally tally);
    }

    void getAllTallies(@NonNull LoadTalliesCallback callback);

    void getTallyById(@NonNull GetTallyCallback callback, String tallyId);

    void getTalliesByCondition(@NonNull LoadTalliesCallback callback, @NonNull TallyType tallyType, Date date, List<TallyTag> tags);

    void addTally(@NonNull Tally tally);

    void updateTally(@NonNull String tallyId, @NonNull Tally tally);

    void deleteTally(@NonNull String tallyId);

    void deleteTallies(@NonNull List<String> tallyIds);
}
