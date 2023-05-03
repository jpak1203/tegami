package com.jpakku.tegami.util

import android.util.Patterns

class ValidatorUtils() {

    companion object {
        private const val MINIMUM_PASSWORD_LENGTH = 8
        private val PASSWORD_CONTENT_REQUIREMENTS = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%&*()_+=|<>?{}\\\\[\\\\]~-]).+$")

        fun validPassword(password: String?): Boolean {
            return if (password.isNullOrBlank() || password.isEmpty()) {
                false
            } else {
                password.length >= MINIMUM_PASSWORD_LENGTH
                        && password.matches(PASSWORD_CONTENT_REQUIREMENTS)
            }
        }

        fun validEmailAddress(emailAddress: String?): Boolean {
            return if (emailAddress.isNullOrBlank() || emailAddress.isEmpty()) {
                false
            } else {
                Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
            }
        }

    }

}
