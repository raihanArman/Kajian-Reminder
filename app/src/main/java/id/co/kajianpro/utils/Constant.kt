package id.co.kajianpro.utils

import android.graphics.Rect
import android.os.Handler
import android.text.format.DateFormat
import android.view.View
import android.widget.ScrollView
import java.util.*

class Constant {
    companion object{

        const val ALARM_TYPE_INTENT = "type_alarm"
        const val LOGIN_KEY = "login_key"
        const val ID_USER_KEY = "id_user"
        const val LOGIN_STATUS = "status_login"
        fun scrollToView(scrollView: View, view: View) {
            view.requestFocus()
            val scrollBounds = Rect()
            scrollView.getHitRect(scrollBounds)
            if (!view.getLocalVisibleRect(scrollBounds)) {
                Handler().post {
                    val toScroll: Int = getRelativeTop(view) - getRelativeTop(scrollView)
                    (scrollView as ScrollView).smoothScrollTo(0, toScroll - 120)
                }
            }
        }


        fun getRelativeTop(myView: View): Int {
            return if (myView.parent === myView.rootView) myView.top else myView.top + getRelativeTop(myView.parent as View)
        }


        fun getdateToday(): String{
            val cFirst = Calendar.getInstance()

            cFirst.set(Calendar.HOUR_OF_DAY, 0)
            cFirst.set(Calendar.MINUTE, 0)
            cFirst.set(Calendar.SECOND, 0)
            val firstDate = DateFormat.format("yyyy-MM-dd HH:mm:ss", cFirst).toString()

            cFirst.set(Calendar.HOUR_OF_DAY, 23)
            cFirst.set(Calendar.MINUTE, 59)
            cFirst.set(Calendar.SECOND, 59)
            val endDate = DateFormat.format("yyyy-MM-dd HH:mm:ss", cFirst).toString()

            return firstDate+"_"+endDate

        }

    }
}