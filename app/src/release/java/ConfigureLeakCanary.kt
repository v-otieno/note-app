import android.util.Log
import com.vivian.mynotes.R

object ConfigureLeakCanary {
    fun enableLeakCanary(isEnable: Boolean = true) {
        Log.i(R.string.app_name.toString(), "LeakCanary Disabled - $isEnable")
    }
}