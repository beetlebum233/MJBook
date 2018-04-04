package cn.mister.mjbook.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TallyTag extends RealmObject{
    @PrimaryKey
    private String id;
    private String name;
}
