package com.roshanadke.inspireme.domain.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        AVAILABLE, LOSING, LOST, UNAVAILABLE
    }

}