package cn.mister.mjbook.tallylist;

public enum TallyType{
    ALL("全部"),INCOME("收入"),EXPENSE("支出");
    private String name;

    TallyType(String name) {
        this.name = name;
    }

    public static TallyType getTallyType(String name){
        switch(name){
            case "全部":
                return TallyType.ALL;
            case "收入":
                return TallyType.INCOME;
            case "支出":
                return TallyType.EXPENSE;
            default:
                return TallyType.ALL;
        }
    }
}
