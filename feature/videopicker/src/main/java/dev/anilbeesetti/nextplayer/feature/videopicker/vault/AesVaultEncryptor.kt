package dev.anilbeesetti.nextplayer.feature.videopicker.vault

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Military-Grade AES-256 GCM Media Vault Encryption Engine for MEDIOVYN.
 */
object AesVaultEncryptor {

    private const val ALGORITHM = "AES/GCM/NoPadding"
    private const val TAG_LENGTH_BIT = 128
    private const val IV_LENGTH_BYTE = 12

    fun generateKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        return keyGen.generateKey()
    }

    fun encryptFile(inputFile: File, outputFile: File, secretKey: SecretKey) {
        val iv = ByteArray(IV_LENGTH_BYTE)
        SecureRandom().nextBytes(iv)

        val cipher = Cipher.getInstance(ALGORITHM)
        val parameterSpec = GCMParameterSpec(TAG_LENGTH_BIT, iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec)

        FileOutputStream(outputFile).use { fos ->
            fos.write(iv)
            FileInputStream(inputFile).use { fis ->
                val buffer = ByteArray(8192)
                var bytesRead: Int
                while (fis.read(buffer).also { bytesRead = it } != -1) {
                    val output = cipher.update(buffer, 0, bytesRead)
                    if (output != null) fos.write(output)
                }
                val finalBytes = cipher.doFinal()
                if (finalBytes != null) fos.write(finalBytes)
            }
        }
    }

    fun decryptFile(inputFile: File, outputFile: File, secretKey: SecretKey) {
        FileInputStream(inputFile).use { fis ->
            val iv = ByteArray(IV_LENGTH_BYTE)
            fis.read(iv)

            val cipher = Cipher.getInstance(ALGORITHM)
            val parameterSpec = GCMParameterSpec(TAG_LENGTH_BIT, iv)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec)

            FileOutputStream(outputFile).use { fos ->
                val buffer = ByteArray(8192)
                var bytesRead: Int
                while (fis.read(buffer).also { bytesRead = it } != -1) {
                    val output = cipher.update(buffer, 0, bytesRead)
                    if (output != null) fos.write(output)
                }
                val finalBytes = cipher.doFinal()
                if (finalBytes != null) fos.write(finalBytes)
            }
        }
    }
}