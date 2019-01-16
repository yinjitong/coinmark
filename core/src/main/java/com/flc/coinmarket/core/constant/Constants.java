package com.flc.coinmarket.core.constant;

import java.math.BigDecimal;

public interface Constants {
    interface  INCOME{
        String NAME = "收入";
        String VALUE = "1";

        enum  SourceType{
            TRANS_IN("交易转入", "1"),
            LOCK_REPO_PROFITS("锁仓收益", "2"),
            TEAM_PROFITS("团队收益", "3"),
            REFEREE_PROFITS("推荐收益", "4"),
            RELEASE_LOCK_REPO("锁仓资产释放", "5"),
            LOCK_FLOAT("流动转锁仓","16");
            SourceType(String name, String value){
                this.name = name;
                this.value = value;
            }
            String name;
            String value;

            public String getValue() {
                return value;
            }

            public String getName() {
                return name;
            }
        }
    }

    interface EXPENSE{
        String NAME = "支出";
        String VALUE = "2";

        enum  SourceType{
            TRANS_OUT("交易转出", "6"),
            REINVEST("复投锁仓", "7"),
            DESTROY("锁仓资金销毁", "8"),
            TRANS_FEE("交易手续费", "9"),
            FLOAT_LOCK("流动转锁仓","10"),
            LOCKREPO_RELESE("锁仓资产释放","11");
            SourceType(String name, String value){
                this.name = name;
                this.value = value;
            }
            String name;
            String value;

            public String getValue() {
                return value;
            }

            public String getName() {
                return name;
            }
        }
    }

    interface SYSTEM_PARAMETER{
        interface REFEREE_PROFITS_PARAM{
            String REFEREE_PROFITS_RATIO = "referee_profits_ratio";
        }

        interface RELEASE_RATIO_PARAM{
            String RELEASE_RATIO = "release_lockrepo_ratio";
        }

        interface DESTROY_LOCKREPO_PARAM{
            String DESTROY_LOCKREPO_TIMES = "destroy_lockrepo_times";
            String DESTROY_LOCKREPO_LIMIT = "destroy_lockrepo_limit";
        }

        interface REINVEST_PARAM{
            String REINVEST_LIMIT = "reinvest_lockrepo_limit";
        }
        interface DAILY_PROFITS_PARAM{
            String DAILY_PROFITS_LIMIT = "daily_profits_limit";
        }
        interface DEFAULT_LIMIT{
            BigDecimal DEFAULT_LIMIT = new BigDecimal(1000);
        }
    }

    enum  TaskStatus{
        NO_EXECUTING("没有执行的任务", 0),
        TEAM_PROFITS_START("团队收益计算开始", 1),
        TEAM_PROFITS_END("团队收益计算结束", 11),
        LOCKREPO_PROFITS_START("锁仓收益计算开始", 2),
        LOCKREPO_PROFITS_END("锁仓收益计算结束", 12),
        REFEREE_PROFITS_START("推荐人收益计算开始", 4),
        REFEREE_PROFITS_END("推荐人收益计算结束", 16),
        RELEASE_START("锁仓资产释放开始", 5),
        RELEASE_END("锁仓资产释放结束", 17),
        DESTROY_START("清仓开始", 6),
        DESTROY_END("清仓结束", 18),
        AUTO_REINVEST_START("自动复投开始", 7),
        AUTO_REINVEST_END("自动复投结束", 19),
        TRANS_COLLECT_START("交易汇总开始", 8),
        TRANS_COLLECT_END("交易汇总结束", 20);
        private TaskStatus(String name, int value){
            this.name = name;
            this.value = value;
        }
        String name;
        int value;

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    enum  DimensionCode{
        LEFT("左码", "L"),
        RIGHT("右码", "R");
        private DimensionCode(String name, String value){
            this.name = name;
            this.value = value;
        }
        String name;
        String value;

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    enum  Transferor{
        SYSTEM("系统", "0"),
        PERSONAL("个人", "1");
        private Transferor(String name, String value){
            this.name = name;
            this.value = value;
        }
        String name;
        String value;

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    enum  AddressFlag{
        FLOATINGADDRESS("消费地址", "0"),
        LOCKREPOADDRESS("锁仓地址", "1");
        private AddressFlag(String name, String value){
            this.name = name;
            this.value = value;
        }
        String name;
        String value;

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }


}
