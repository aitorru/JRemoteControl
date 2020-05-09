package com.JRCon;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import com.cryp.AsymmetricCryptography;
import com.cryp.GenerateKeys;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    public void chechCrypto() {
        try {
            GenerateKeys gk = new GenerateKeys();
            gk.checkLogic();
            AsymmetricCryptography as = new AsymmetricCryptography();
            as.logicTest();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
