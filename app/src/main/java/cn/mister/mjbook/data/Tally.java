package cn.mister.mjbook.data;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tally extends RealmObject {
    @PrimaryKey
    private String id;
    private Integer amount;
    private Boolean isIncome;
    private Date createdTime;
    private String note;
    private RealmList<TallyTag> tags;
}
