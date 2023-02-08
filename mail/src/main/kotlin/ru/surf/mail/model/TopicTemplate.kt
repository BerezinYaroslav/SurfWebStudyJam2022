package ru.surf.mail.model

enum class TopicTemplate(
    val template: String
) {
    SIMPLE_NOTIFICATION("simple_notification.html"),
    GREETING("simple_notification.html"),
    ACCOUNT_ACTIVATION("account_activation.html"),
    TEST("test_link.html"),
    TEST_RESULT("test_result.html"),
    OFFER("offer.html");
    companion object {
        const val SIMPLE_NOTIFICATION_TOPIC: String = "notification-topic"
    }
}