package dot.curse.matule.domain.repository

import dot.curse.matule.domain.model.notification.Notification

interface NotificationRepository {
    suspend fun getNotificationByUserId(id: Int): Result<List<Notification>>
    suspend fun sendNotificationToUser(not: Notification): Result<Boolean>
}