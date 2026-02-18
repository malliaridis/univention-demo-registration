package com.malliaridis.univention

const val SERVER_PORT = 8080

/**
 * Maximum length of first name and last name.
 *
 * 128 characters should be enough for everyone.
 */
const val MAX_NAME_LENGTH = 128

/**
 * The regex to validate usernames.
 *
 * It contains all alphanumeric characters, dots, dashes, and underscores.
 */
val USERNAME_REGEX = Regex("^[A-Za-z0-9._-]+$")

/**
 * The range the username length can be in.
 */
val USERNAME_RANGE = 3..24

/**
 * A simple regex to validate phone numbers.
 *
 * TODO Use libraries like libphonenumber to validate phone numbers instead
 */
val PHONE_NUMBER_REGEX = Regex("^\\d{10}$")
