package co.edu.udea.compumovil.gr03_20241.lab1.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PersonalViewModel : ViewModel()
{
    private val _name = mutableStateOf<String?>(null)
    val name: State<String?> = _name

    private val _lastname = mutableStateOf<String?>(null)
    val lastname: State<String?> = _lastname

    private val _gender = mutableStateOf<String?>("")
    val gender: State<String?> = _gender

    private val _birthdate = mutableStateOf<String?>("")
    val birthdate: State<String?> = _birthdate

    private val _grade = mutableStateOf<String?>("")
    val grade: State<String?> = _grade

    private val _phone = mutableStateOf<String?>(null)
    val phone: State<String?> = _phone

    private val _address = mutableStateOf<String?>(null)
    val address: State<String?> = _address

    private val _email = mutableStateOf<String?>(null)
    val email: State<String?> = _email

    private val _country = mutableStateOf<Country?>(null)
    val country: State<Country?> = _country

    private val _city = mutableStateOf<City?>(null)
    val city: State<City?> = _city

    private val _complete = mutableStateOf<Boolean>(false)
    val complete: State<Boolean> = _complete

    fun setName(newName: String?) {
        _name.value = newName
    }

    fun setComplete(state: Boolean) {
        _complete.value = state
    }

    fun setLastname(newName: String?) {
        _lastname.value = newName
    }

    fun setGender(newGender: String?) {
        _gender.value = newGender
    }

    fun setGrade(newGrade: String?) {
        _grade.value = newGrade
    }

    fun setBirthdate(newBirthdate: String?) {
        _birthdate.value = newBirthdate
    }

    fun setPhone(newPhone: String?) {
        _phone.value = newPhone
    }

    fun setAddress(newAddress: String?) {
        _address.value = newAddress
    }

    fun setEmail(newEmail: String?) {
        _email.value = newEmail
    }

    fun setCountry(newCountry: Entity?) {
        if(newCountry === null || newCountry is Country) {
            _country.value = newCountry as Country?
        }
    }

    fun setCity(newCity: Entity?) {
        if(newCity === null || newCity is City) {
            _city.value = newCity as City?
        }
    }

    fun clear() = viewModelScope.launch {
        setComplete(false)
        setName(null)
        setLastname(null)
        setBirthdate("")
        setGender("")
        setGrade("")
        setAddress(null)
        setCountry(null)
        setCity(null)
        setPhone(null)
        setEmail(null)
        onCleared()
    }
}
