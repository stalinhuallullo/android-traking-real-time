package gob.pe.msi.trakingrealtime.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import gob.pe.msi.trakingrealtime.presentation.model.dto.GPSExpresoResponseDto
import gob.pe.msi.trakingrealtime.presentation.model.dto.LocationDto
import gob.pe.msi.trakingrealtime.presentation.repository.TrakingRepository


class LocationViewModel : ViewModel() {
    private val trakingRepository: TrakingRepository = TrakingRepository()

    //val users: LiveData<List<User>> = userRepository.getUsers()

    fun saveLocation(location: LocationDto): LiveData<GPSExpresoResponseDto> {
        //val headers: Map<String, String> = HashMap()
        val headers = HashMap<String, String>()
        headers["uuid"] = ""

        return trakingRepository.saveLocation(headers, location)
    }

    /*fun updateUser(id: Int, user: User): LiveData<User> {
        return userRepository.updateUser(id, user)
    }*/

    /*fun deleteUser(id: Int): LiveData<Boolean> {
        return userRepository.deleteUser(id)
    }*/
}