package org.codeforiraq.doctor

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class DoctorsOfSalahAldin  : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}