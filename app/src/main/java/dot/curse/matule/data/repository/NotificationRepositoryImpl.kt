package dot.curse.matule.data.repository

import dot.curse.matule.domain.model.notification.Notification
import dot.curse.matule.domain.model.notification.toNotification
import dot.curse.matule.domain.repository.ApiRepository
import dot.curse.matule.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: ApiRepository
): NotificationRepository {
    override suspend fun getNotificationByUserId(id: Int): Result<List<Notification>> {
        return api.getNotificationByUserId(id).map { notDot ->
            notDot.map { it.toNotification() }
        }
    }

    override suspend fun sendNotificationToUser(not: Notification): Result<Boolean> {
        return api.sendNotificationToUser(not)
    }
}