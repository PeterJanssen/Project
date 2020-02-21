package com.example.wensambulanceapp.ui.auth

import androidx.lifecycle.ViewModel
import com.example.wensambulanceapp.data.entities.Badge
import com.example.wensambulanceapp.data.entities.Location
import com.example.wensambulanceapp.data.enums.Diploma
import com.example.wensambulanceapp.data.enums.Specialization
import com.example.wensambulanceapp.data.enums.TShirtSize
import com.example.wensambulanceapp.data.repositories.UserRepository
import com.example.wensambulanceapp.util.ViewModelListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    var email: String? = null
    var password: String? = null
    var phoneNumber: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var rijksregisternummer: String? = null
    var badgeNumber: String? = null
    var badgeExpirationDate: String? = null
    var street: String? = null
    var houseNr: String? = null
    var township: String? = null
    var postalCode: String? = null
    var tShirtSize: TShirtSize = TShirtSize.L
    var diploma: Diploma = Diploma.BiomedischeLaboratoriumtechnologie
    var specialization: Specialization = Specialization.AnesthesieReanimatie

    var authListener: ViewModelListener? = null

    private val disposables = CompositeDisposable()

    val user by lazy {
        userRepository.currentUser()
    }

    fun onLoginButtonClick() {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Gelieve email en passwoord in te vullen")
            return
        }

        val disposable = userRepository.login(email!!, password!!).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                authListener?.onSuccess()
            }, {
                //sending a failure callback
                authListener?.onFailure("Failed to login please try again or check your internet connection")
            })
        disposables.add(disposable)
    }

    fun onRegisterButtonClick() {
        authListener?.onStarted()

        if (validateAccount()) return

        if (validatePersonnelDetails()) return

        if (validateLocation()) return

        if (validateBadge()) return

        val disposable =
            userRepository.register(
                email = email!!,
                password = password!!,
                firstName = firstName!!,
                lastName = lastName!!,
                phoneNumber = phoneNumber!!,
                socialNumber = rijksregisternummer!!,
                location = Location(
                    houseNr = houseNr!!,
                    townShip = township!!,
                    street = street!!,
                    postalCode = postalCode!!
                ),
                badge = Badge(
                    badgeNumber = badgeNumber!!,
                    badgeExpirationDate = badgeExpirationDate!!
                ),
                sizeTShirt = tShirtSize,
                diploma = diploma,
                specialization = specialization
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //sending a success callback
                    authListener?.onSuccess()
                }, {
                    //sending a failure callback
                    authListener?.onFailure("Failed to register please try again or check your internet connection")
                })
        disposables.add(disposable)
    }

    private fun validateAccount(): Boolean {
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is verplicht")
            return true
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Password is verplicht")
            return true
        }
        return false
    }

    private fun validatePersonnelDetails(): Boolean {
        if (firstName.isNullOrEmpty()) {
            authListener?.onFailure("Voornaam is verplicht")
            return true
        }

        if (lastName.isNullOrEmpty()) {
            authListener?.onFailure("Achternaam is verplicht")
            return true
        }

        if (phoneNumber.isNullOrEmpty()) {
            authListener?.onFailure("GSM-nummer is verplicht")
            return true
        }

        if (rijksregisternummer.isNullOrEmpty()) {
            authListener?.onFailure("Rijksregisternummer is verplicht")
            return true
        }
        return false
    }

    private fun validateLocation(): Boolean {
        if (houseNr.isNullOrEmpty()) {
            authListener?.onFailure("Huis Nr. is verplicht")
            return true
        }

        if (township.isNullOrEmpty()) {
            authListener?.onFailure("Gemeente is verplicht")
            return true
        }

        if (street.isNullOrEmpty()) {
            authListener?.onFailure("Straat is verplicht")
            return true
        }

        if (postalCode.isNullOrEmpty()) {
            authListener?.onFailure("Postcode is verplicht")
            return true
        }
        return false
    }

    private fun validateBadge(): Boolean {
        if (badgeNumber.isNullOrEmpty()) {
            authListener?.onFailure("Badge nummer is verplicht")
            return true
        }

        if (badgeExpirationDate.isNullOrEmpty()) {
            authListener?.onFailure("Badge vervaldatum is verplicht")
            return true
        }
        return false
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
