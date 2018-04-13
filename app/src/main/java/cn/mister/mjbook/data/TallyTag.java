package cn.mister.mjbook.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TallyTag extends RealmObject{
    @PrimaryKey
    private String id;
    private String name;
    private Boolean isIncome;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIncome() {
        return isIncome;
    }

    public void setIncome(Boolean income) {
        isIncome = income;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
