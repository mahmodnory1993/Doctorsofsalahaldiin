package org.codeforiraq.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_about_app.*

class About_app : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)

        textaboutapp.text = "تطبيق يتيح للمستخدم الاستدلال و معرفة عناوين الاطباء في المحافظات و يمكن اضافة الاطباء عن طريق المستخدم العادي"
    }
}
