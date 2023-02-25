package ru.surf.mail.model

enum class Template(
    val html: String
) {
    ACCEPT_APPLICATION("accept_application.html"),
    EVENT_START_NOTIFICATION("event_start_notification.html"),
    ACCOUNT_ACTIVATION("account_activation.html"),
    TEST("test_link.html");
}