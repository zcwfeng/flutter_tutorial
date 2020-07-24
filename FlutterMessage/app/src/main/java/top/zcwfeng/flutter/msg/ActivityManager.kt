package top.zcwfeng.flutter.msg

import android.app.Activity

object ActivityManager {

    val activities:MutableList<Activity> = mutableListOf()
    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>?) {
        for (activity in activities) {
            if (activity::class.java == cls) {
                activity.finish()
            }
        }
    }


    /**
     * 获得当前的Activity
     *
     * @return 当前Activity
     */
    fun currentActivity(): Activity? {
        return if (activities.size > 0) {
            activities[activities.size - 1]
        } else null
    }
}