package dot.curse.matule.ui.utils

import android.content.Context
import android.widget.Toast

fun Context.myToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}