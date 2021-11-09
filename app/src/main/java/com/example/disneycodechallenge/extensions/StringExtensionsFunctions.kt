package com.example.disneycodechallenge.extensions

import java.math.BigInteger
import java.security.MessageDigest

private const val MESSAGE_DIGEST_MD5_INSTANCE = "MD5"

fun String.md5(): String {
    val messageDigest = MessageDigest.getInstance(MESSAGE_DIGEST_MD5_INSTANCE)
    return BigInteger(1, messageDigest.digest(toByteArray())).toString(16).padStart(32, '0')
}