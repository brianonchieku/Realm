package com.example.realm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realm.models.Address
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val realm = MyApp.realm

    fun createSampleEntries(){
        viewModelScope.launch {
            realm.write {
                val address1 = Address().apply {
                    fullName = "Brian Onchieku"
                    street = "Onchieku street"
                    houseNumber = 24
                    zip = 1327
                    city = "oncity"

                }
                val address2 = Address().apply {
                    fullName = "Sharon Nyokabi"
                    street = "Shaz street"
                    houseNumber = 27
                    zip = 1738
                    city = "shacty"

                }
            }
        }
    }
}