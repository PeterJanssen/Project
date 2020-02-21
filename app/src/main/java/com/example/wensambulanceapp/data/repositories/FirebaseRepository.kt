package com.example.wensambulanceapp.data.repositories

import android.util.Log
import com.example.wensambulanceapp.data.entities.LoginUser
import com.example.wensambulanceapp.data.entities.RegisterUser
import com.example.wensambulanceapp.data.entities.Wish
import com.example.wensambulanceapp.util.DatabaseConstants.Companion.VOLUNTEER_DATABASE_TABLE
import com.example.wensambulanceapp.util.DatabaseConstants.Companion.WISHES_DATABASE_TABLE
import com.example.wensambulanceapp.util.OnGetDataListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import java.util.concurrent.CountDownLatch


class FirebaseRepository {
    private var valueEventListener: ValueEventListener? = null

    private val fireBaseDatabase: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }

    private val fireBaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val storage: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    private fun readData(ref: DatabaseReference, listener: OnGetDataListener) {
        listener.onStart()
        cleanUpValueEventListenerIfExists()
        valueEventListener = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onSuccess(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                listener.onFailure()
            }
        })
    }

    fun login(loginUser: LoginUser): Completable = Completable.create { emitter ->
        fireBaseAuth.signInWithEmailAndPassword(loginUser.email, loginUser.password)
            .addOnCompleteListener { loginUser: Task<AuthResult> ->
                if (!emitter.isDisposed) {
                    if (loginUser.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(loginUser.exception!!)
                    }
                }
            }
    }

    fun register(registerUser: RegisterUser, password: String): Completable =
        Completable.create { emitter ->
            fireBaseAuth.createUserWithEmailAndPassword(
                registerUser.email,
                password
            )
                .addOnCompleteListener { createNewUser: Task<AuthResult> ->
                    if (createNewUser.isSuccessful) {
                        val userId = currentUser()!!.uid

                        fireBaseDatabase.child(VOLUNTEER_DATABASE_TABLE).child(userId)
                            .setValue(registerUser)
                            .addOnCompleteListener { saveNewUser ->
                                if (!emitter.isDisposed) {
                                    if (saveNewUser.isSuccessful)
                                        emitter.onComplete()
                                    else
                                        emitter.onError(saveNewUser.exception!!)
                                }
                            }
                    } else {
                        emitter.onError(createNewUser.exception!!)
                    }
                }
        }

    fun logout() {
        cleanUpValueEventListenerIfExists()
        fireBaseAuth.signOut()
    }

    fun currentUser() = fireBaseAuth.currentUser

    private fun cleanUpValueEventListenerIfExists() {
        if (valueEventListener != null) {
            fireBaseDatabase.removeEventListener(valueEventListener!!)
        }
    }

    fun getWishes(): List<Wish> {
        val wishes: MutableList<Wish> = mutableListOf()
        val keyList: ArrayList<String> = ArrayList()
        val done = CountDownLatch(1)
        readData(fireBaseDatabase.child(WISHES_DATABASE_TABLE), object : OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot?) {
                dataSnapshot!!.children.mapNotNullTo(wishes) { dataSnapshotWish: DataSnapshot ->
                    dataSnapshotWish.getValue<Wish>(Wish::class.java)
                }
                for (child in dataSnapshot!!.children) {

                    keyList.add(child.key!!)
                }
                done.countDown()
                Log.d("ONSUCCES", "Success")
            }

            override fun onStart() {
                Log.d("ONSTART", "Started")
            }

            override fun onFailure() {
                Log.d("ONFAILURE", "Failed")
            }
        })
        done.await()
        for (x in 0 until keyList.size) {
            wishes[x].wishId = keyList[x]
        }
        return wishes
    }

    fun getPersonalDetails(userId: String): RegisterUser? {
        //val accountDisplay = RegisterUser()
        val done = CountDownLatch(1)
        readData(
            fireBaseDatabase.child(VOLUNTEER_DATABASE_TABLE).child(userId),
            object : OnGetDataListener {
                override fun onSuccess(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null) {

                    }
                    done.countDown()
                    Log.d("ONSUCCES", "Success")
                }

                override fun onStart() {
                    Log.d("ONSTART", "Started")
                }

                override fun onFailure() {
                    Log.d("ONFAILURE", "Failed")
                }
            })
        done.await()
        return null
    }

    fun addVolunteerToWish(userId: String, wishId: String): Completable =
        Completable.create { emitter ->
            val wishObject = HashMap<String, Any>()
            wishObject["volunteers"] = userId

            fireBaseDatabase.child(WISHES_DATABASE_TABLE)
                .child(wishId)
                .child(VOLUNTEER_DATABASE_TABLE)
                .setValue(wishId)
                .addOnCompleteListener { addLikeToUser ->
                    if (!emitter.isDisposed) {
                        if (addLikeToUser.isSuccessful)
                            emitter.onComplete()
                        else {
                            emitter.onError(addLikeToUser.exception!!)
                        }
                    }
                }
        }
}