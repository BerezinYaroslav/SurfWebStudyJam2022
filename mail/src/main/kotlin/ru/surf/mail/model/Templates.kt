package ru.surf.mail.model

enum class Templates(
    val template: String
) {
    GREETING("greeting_email.html"),
    ACCOUNT_ACTIVATION("account_activation.html"),
    TEST("test_link.html"),
    TEST_RESULT("test_result.html"),
    OFFER("offer.html")
}