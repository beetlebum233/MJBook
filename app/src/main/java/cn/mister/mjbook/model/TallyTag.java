package cn.mister.mjbook.model;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class TallyTag extends RealmObject{
    @PrimaryKey
    private String id;
    private String name;
    @LinkingObjects("tags")
    private final RealmResults<Tally> tallies;

    public TallyTag(RealmResults<Tally> tallies) {
        this.tallies = tallies;
    }
}
