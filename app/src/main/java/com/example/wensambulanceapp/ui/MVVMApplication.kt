package com.example.wensambulanceapp.ui

import android.app.Application
import com.example.wensambulanceapp.data.repositories.AdminRepository
import com.example.wensambulanceapp.data.repositories.FirebaseRepository
import com.example.wensambulanceapp.data.repositories.UserRepository
import com.example.wensambulanceapp.data.repositories.VolunteerRepository
import com.example.wensambulanceapp.ui.auth.AuthViewModelFactory
import com.example.wensambulanceapp.ui.wishes.WishesViewModel
import com.example.wensambulanceapp.ui.wishes.WishesViewModelFactory
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

// MVVMApplication is registered in android manifest as an application
class MVVMApplication : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        val mDatabaseReference: FirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference.setPersistenceEnabled(true)
        val mUserDataBaseReference: DatabaseReference = mDatabaseReference.getReference("Users")
        val mReportDataBaseReference: DatabaseReference = mDatabaseReference.getReference("Reports")
        mUserDataBaseReference.keepSynced(true)
        mReportDataBaseReference.keepSynced(true)
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { FirebaseRepository() }
        bind() from singleton { UserRepository(instance()) }
        bind() from singleton { AdminRepository(instance()) }
        bind() from singleton { VolunteerRepository(instance()) }

        bind() from provider { AuthViewModelFactory(instance()) }

        bind() from provider { WishesViewModelFactory(instance()) }
        bind() from provider { WishesViewModel(instance()) }
    }
}