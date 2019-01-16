//package com.flc.coinmarket.admin.jobs;
//
//import com.flc.coinmarket.admin.service.ScheduledTaskService;
//import com.flc.coinmarket.core.constant.Constants;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@EnableScheduling
//@Configuration
//public class ScheduledTasks{
//    private static Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
//
//    private static int EXECUTE_STATUS = 0;
//
////    private String LOCKREPO_EXECUTE_DATE= dateFormat().format(new Date());
//////    private String REFEREE_EXECUTE_DATE = dateFormat().format(new Date());（去掉推荐收益）
////    private String TEAM_EXECUTE_DATE = dateFormat().format(new Date());
////    private String RELEASE_EXECUTE_DATE= dateFormat().format(new Date());
////    private String DESTROY_EXECUTE_DATE = dateFormat().format(new Date());
//////  private String AUTO_REINVEST_EXECUTE_DATE= dateFormat().format(new Date());（与清仓合并）
////    private String TRANS_COLLECT_EXECUTE_DATE= dateFormat().format(new Date());
//
//
////    //测试用
//    private String LOCKREPO_EXECUTE_DATE;
////            = dateFormat().format(new Date());
//    private String TEAM_EXECUTE_DATE ;
////        = dateFormat().format(new Date());
//    private String RELEASE_EXECUTE_DATE;
////        = dateFormat().format(new Date());
//    private String DESTROY_EXECUTE_DATE ;
////        = dateFormat().format(new Date());
//    private String TRANS_COLLECT_EXECUTE_DATE;
////        = dateFormat().format(new Date());
//
//    @Autowired
//    ScheduledTaskService scheduledTaskService;
//
////    @Scheduled(cron = "* * 1-6 * * ? ")
//    @Scheduled(initialDelay = 10000L, fixedDelay = 1000000000000000000L)
//    public void autoRunMethod(){
//        teamProfits();
//        lockrepoProfits();
//        releaseLockrepo();
//        destroyLockrepo();
//        transCollect();
//    }
//
//    /**
//     * 团队收益计算
//     */
//    private void teamProfits(){
//        String executeDate = dateFormat().format(new Date());
//        if((this.TEAM_EXECUTE_DATE != null && this.TEAM_EXECUTE_DATE.equals(executeDate)) || EXECUTE_STATUS != Constants.TaskStatus.NO_EXECUTING.getValue()){
//            return;
//        }
//        TEAM_EXECUTE_DATE = executeDate;
//        logger.info(Constants.TaskStatus.TEAM_PROFITS_START.getName());
//        EXECUTE_STATUS = Constants.TaskStatus.TEAM_PROFITS_START.getValue();
//        scheduledTaskService.teamProfits();
//        EXECUTE_STATUS = Constants.TaskStatus.TEAM_PROFITS_END.getValue();
//        logger.info(Constants.TaskStatus.TEAM_PROFITS_END.getName());
//    }
//
//    /**
//     * 锁仓收益计算
//     */
//    private void lockrepoProfits(){
//        String executeDate = dateFormat().format(new Date());
//        if((this.LOCKREPO_EXECUTE_DATE != null && this.LOCKREPO_EXECUTE_DATE.equals(executeDate)) || EXECUTE_STATUS != Constants.TaskStatus.TEAM_PROFITS_END.getValue()){
//            return;
//        }
//        LOCKREPO_EXECUTE_DATE = executeDate;
//        logger.info(Constants.TaskStatus.LOCKREPO_PROFITS_START.getName());
//        EXECUTE_STATUS = Constants.TaskStatus.LOCKREPO_PROFITS_START.getValue();
//        scheduledTaskService.lockrepoProfits();
//        EXECUTE_STATUS = Constants.TaskStatus.LOCKREPO_PROFITS_END.getValue();
//        logger.info(Constants.TaskStatus.LOCKREPO_PROFITS_END.getName());
//    }
//
//    /**
//     * 推荐收益计算 删除（去掉推荐收益）
//     */
////    @Scheduled(cron = "*/20 * * * * ? ")
////    public void refereeProfits(){
////        String executeDate = dateFormat().format(new Date());
////        if((this.REFEREE_EXECUTE_DATE != null && this.REFEREE_EXECUTE_DATE.equals(executeDate)) || EXECUTE_STATUS != Constants.TaskStatus.LOCKREPO_PROFITS_END.getValue()){
////            return;
////        }
////        REFEREE_EXECUTE_DATE = executeDate;
////        logger.info(Constants.TaskStatus.REFEREE_PROFITS_START.getName());
////        EXECUTE_STATUS = Constants.TaskStatus.REFEREE_PROFITS_START.getValue();
////        scheduledTaskService.refereeProfits();
////        EXECUTE_STATUS = Constants.TaskStatus.REFEREE_PROFITS_END.getValue();
////        logger.info(Constants.TaskStatus.REFEREE_PROFITS_END.getName());
////    }
//
//    /**
//     * 释放锁仓
//     */
//    private void releaseLockrepo(){
//        String executeDate = dateFormat().format(new Date());
//        if((this.RELEASE_EXECUTE_DATE != null && this.RELEASE_EXECUTE_DATE.equals(executeDate)) || EXECUTE_STATUS != Constants.TaskStatus.LOCKREPO_PROFITS_END.getValue()){
//            return;
//        }
//        RELEASE_EXECUTE_DATE = executeDate;
//        logger.info(Constants.TaskStatus.RELEASE_START.getName());
//        EXECUTE_STATUS = Constants.TaskStatus.RELEASE_START.getValue();
//        scheduledTaskService.releaseLockrepo();
//        EXECUTE_STATUS = Constants.TaskStatus.RELEASE_END.getValue();
//        logger.info(Constants.TaskStatus.RELEASE_END.getName());
//    }
//
//    /**
//     * 清仓
//     */
//    private void destroyLockrepo(){
//        String executeDate = dateFormat().format(new Date());
//        if((this.DESTROY_EXECUTE_DATE != null && this.DESTROY_EXECUTE_DATE.equals(executeDate)) || EXECUTE_STATUS != Constants.TaskStatus.RELEASE_END.getValue()){
//            return;
//        }
//        DESTROY_EXECUTE_DATE = executeDate;
//        logger.info(Constants.TaskStatus.DESTROY_START.getName());
//        EXECUTE_STATUS = Constants.TaskStatus.DESTROY_START.getValue();
//        scheduledTaskService.destroyLockrepo();
//        EXECUTE_STATUS = Constants.TaskStatus.DESTROY_END.getValue();
//        logger.info(Constants.TaskStatus.DESTROY_END.getName());
//    }
//
////    /**
////     * 自动复投 删除(与清仓合并)
////     */
////    private void autoReinvest(){
////        String executeDate = dateFormat().format(new Date());
////        if((this.AUTO_REINVEST_EXECUTE_DATE != null && this.AUTO_REINVEST_EXECUTE_DATE.equals(executeDate)) || EXECUTE_STATUS != Constants.TaskStatus.DESTROY_END.getValue()) {
////            return;
////        }
////        AUTO_REINVEST_EXECUTE_DATE = executeDate;
////        logger.info(Constants.TaskStatus.AUTO_REINVEST_START.getName());
////        EXECUTE_STATUS = Constants.TaskStatus.AUTO_REINVEST_START.getValue();
////        scheduledTaskService.autoReinvest();
////        EXECUTE_STATUS = Constants.TaskStatus.AUTO_REINVEST_END.getValue();
////        logger.info(Constants.TaskStatus.AUTO_REINVEST_END.getName());
////    }
//
//
//    /**
//     * 日增量、总量汇总
//     */
//    private void transCollect(){
//        String executeDate = dateFormat().format(new Date());
//        if((this.TRANS_COLLECT_EXECUTE_DATE != null && this.TRANS_COLLECT_EXECUTE_DATE.equals(executeDate)) || EXECUTE_STATUS != Constants.TaskStatus.DESTROY_END.getValue()) {
//            return;
//        }
//        TRANS_COLLECT_EXECUTE_DATE = executeDate;
//        logger.info(Constants.TaskStatus.TRANS_COLLECT_START.getName());
//        EXECUTE_STATUS = Constants.TaskStatus.TRANS_COLLECT_START.getValue();
//        scheduledTaskService.transCollect();
//        EXECUTE_STATUS = Constants.TaskStatus.NO_EXECUTING.getValue();
//        logger.info(Constants.TaskStatus.TRANS_COLLECT_END.getName());
//    }
//
//    private static SimpleDateFormat dateFormat(){
//        return new SimpleDateFormat ("yyyy-MM-dd");
//    }
//
//}