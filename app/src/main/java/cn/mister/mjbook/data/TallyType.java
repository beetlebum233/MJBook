package cn.mister.mjbook.data;

public enum TallyType{
    ALL(0),INCOME(1),EXPENSE(2);

    private Integer type;

    TallyType(Integer type) {
        this.type = type;
    }
}
