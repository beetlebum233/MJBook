package cn.mister.mjbook.data;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tally extends RealmObject {
    @PrimaryKey
    private String id;
    private Double amount;
    private Boolean isIncome;
    private Date createdTime;
    private String note;
    private RealmList<TallyTag> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getIncome() {
        return isIncome;
    }

    public void setIncome(Boolean income) {
        isIncome = income;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public RealmList<TallyTag> getTags() {
        return tags;
    }

    public void setTags(RealmList<TallyTag> tags) {
        this.tags = tags;
    }
}
