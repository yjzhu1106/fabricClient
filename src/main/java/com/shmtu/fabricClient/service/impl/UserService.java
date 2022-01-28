package com.shmtu.fabricClient.service.impl;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.io.File;
import java.util.Properties;

public class UserService {


    public void createUser() {
        try {
            String caUrl = "http://47.100.224.136:7054";

            CryptoSuite cs = CryptoSuite.Factory.getCryptoSuite();
            Properties prop = new Properties();
            prop.put("verify", false);
            HFCAClient caClient = HFCAClient.createNewInstance(caUrl, prop);
            caClient.setCryptoSuite(cs);
            Enrollment enrollment_admin = caClient.enroll("admin", "adminpw");

            SampleStore sampleStore;
            SampleUser admin;

            File sampleStoreFile = new File(System.getProperty("java.io.tmpdir") + "/HFCSampletest.properties");
            if (sampleStoreFile.exists()) { // For testingstart fresh
                sampleStoreFile.delete();
            }
            sampleStore = new SampleStore(sampleStoreFile);
            sampleStoreFile.deleteOnExit();
            admin = sampleStore.getMember("admin", "adminpw");
            admin.setEnrollment(enrollment_admin);
            RegistrationRequest regreq = new RegistrationRequest("admin_org1", "org1.fabric-iot.edu");
            regreq.setEnrollmentID("8880");
            String str = caClient.register(regreq, admin);
            System.out.println(str);
            String str1 = "bcaHldHlonNC";
            Enrollment enrollment1 = caClient.enroll("8888", str1);
            System.out.println(enrollment1.getCert());
            System.out.println(enrollment1.getKey());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
