package com.aadhk.product.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
/**
 * @author yang
 * 20152015年1月19日上午11:15:21
 *function:开启服务后每隔多久唤醒
 */
public class BackupUtil {
	/*
	 * 开启轮询服务  
	 * seconds: 备份时长(s/秒)
	 * startTime:从格林威治标准时间 1970开始  到   当天 yyyy-MM-dd 设置的时间 HH:mm  之间的毫秒数
	 * cls: BackupService.class 需要的Service
	 * action: 备份动作    
	 */
    public static void startPollingService(Context context, long seconds,long startTime, Class<?> cls,String action) {  
        //获取AlarmManager系统服务  
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);  
        //包装需要执行Service的Intent  
        Intent intent = new Intent(context, cls);  
        intent.setAction(action);  
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);  
         //如果已经有一个相同intent的闹钟被设置过了，那么前一个闹钟将会取消，被新设置的闹钟所代替。
        //触发服务的起始时间  
//        long triggerAtTime = SystemClock.elapsedRealtime();  
        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service  
        //manager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, seconds * 1000, pendingIntent); //重复闹钟 
        //RTC_WAKEUP keep system wakeup, drain the battery
        manager.setRepeating(AlarmManager.RTC, startTime, seconds * 1000, pendingIntent); //重复闹钟
    }  
  
    //停止轮询服务  
    public static void stopPollingService(Context context, Class<?> cls,String action) {  
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);  
        Intent intent = new Intent(context, cls);  
        intent.setAction(action);  
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);  
        //取消正在执行的服务  
        manager.cancel(pendingIntent);  
    }  
}
