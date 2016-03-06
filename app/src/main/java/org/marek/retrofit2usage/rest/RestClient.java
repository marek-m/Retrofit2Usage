package org.marek.retrofit2usage.rest;

import android.util.Log;

import org.marek.retrofit2usage.App;
import org.marek.retrofit2usage.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.CertificatePinner;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by M on 2016-03-06.
 */
public class RestClient {
    private static final String TAG = RestClient.class.getSimpleName();

    public static final String BASE_URL = "https://URL:443";
    public static ClientService getService() {

        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .client(getOkClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL + "/back/")
                    //TODO ERROR HANDLING.addCallAdapterFactory()
                    .build();

        } catch (GeneralSecurityException | IOException e) {
            Log.d(TAG, "Exception:"+e.getMessage());
        }
        return retrofit.create(ClientService.class);
    }


    private static OkHttpClient getOkClient() throws GeneralSecurityException, IOException {
        //LOGGING INTERCEPTOR
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //CONNECTION SPECIFICATION
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256)
                .build();

        //CERTIFICATE PINNING
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                //AFTER FIRST RUN IT PRINTS CERTIFICATE PINS
                .add("some.domain.com", "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAA=")
                .build();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.certificatePinner(certificatePinner);
        builder.sslSocketFactory(sslContext().getSocketFactory());
        builder.addInterceptor(interceptor);
        //builder.connectionSpecs(Collections.singletonList(spec));

        return builder.build();
    }



    //SSL CONTEXT WITH KEYSTORE (CERT)
    private static SSLContext sslContext()
            throws GeneralSecurityException, IOException {

        final InputStream in = App.getInstance().getResources().openRawResource(R.raw.geotrustrootca);

        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // From https://www.washington.edu/itconnect/security/ca/load-der.crt
        Certificate ca;
        try {
            ca = cf.generateCertificate(in);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } finally {
            Util.closeQuietly(in);
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        return context;
    }
}
