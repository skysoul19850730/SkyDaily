package com.skysoul.skydaily.manages.biometricprompt.api23

import android.app.Activity
import android.hardware.fingerprint.FingerprintManager
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.security.Key
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator

/**
 *@author skysoul
 *@date 2020/9/1
 *@description
 */
@RequiresApi(23)
class CryptoObjectHelper {

   private var KEY_NAME:String=""
   private val KEYSTORE_NAME="AndroidKeyStore"
    private val KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    private val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
    private val ENCRYPTION_PADDING=KeyProperties.ENCRYPTION_PADDING_PKCS7
    private val TRANSFORMATION="$KEY_ALGORITHM/$BLOCK_MODE/$ENCRYPTION_PADDING"

   private lateinit var keyStore: KeyStore
    constructor(activity: Activity){
        KEY_NAME = activity.application.packageName
        keyStore = KeyStore.getInstance(KEYSTORE_NAME)
        keyStore.load(null)
    }
    @Throws
    fun buildCryptoObject():FingerprintManager.CryptoObject{
        var cipher = createCipher(true)
        return FingerprintManager.CryptoObject(cipher)
    }

    @Throws(Exception::class)
    fun createCipher(retry:Boolean):Cipher{
        var key = getKey()
        var cipher = Cipher.getInstance(TRANSFORMATION)
        try{
            cipher.init(Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE,key)
        }catch (e:KeyPermanentlyInvalidatedException){
            keyStore.deleteEntry(KEY_NAME)
            if(retry){
               return createCipher(false)
            }else{
                throw Exception("could not create the cipher for fingerprint authentication.",e)
            }
        }
        return cipher
    }

    @Throws
    private fun getKey(): Key {
        if(!keyStore.isKeyEntry(KEY_NAME)){
            createKey()
        }
        return keyStore.getKey(KEY_NAME,null)
    }


    @Throws
    private fun createKey(){
        var keyGen = KeyGenerator.getInstance(KEY_ALGORITHM,KEYSTORE_NAME)
        var keyGenSpec = KeyGenParameterSpec.Builder(KEY_NAME,KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(BLOCK_MODE)
                .setEncryptionPaddings(ENCRYPTION_PADDING)
                .setUserAuthenticationRequired(true)
                .build()
        keyGen.run {
            init(keyGenSpec)
            generateKey()
        }

    }
}