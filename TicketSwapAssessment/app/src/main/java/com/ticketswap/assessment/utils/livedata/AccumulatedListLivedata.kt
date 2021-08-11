package com.ticketswap.assessment.utils.livedata

import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.utils.add

class AccumulatedListLivedata<T> : MutableLiveData<List<T>>(listOf()) {

    override fun setValue(value: List<T>?) {
        super.setValue(value)
    }

    fun setValue(value: List<T>?, accumulate: Boolean = true) {
        value ?: return
        if (accumulate) {
            super.setValue(getValue() add value)
        } else super.setValue(value)
    }

    fun clear() = super.setValue(listOf())
    fun trigger() = super.setValue(value)

}