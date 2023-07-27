package com.taomish.app.android.farmsanta.farmer.libs.service;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import androidx.multidex.BuildConfig;

import com.google.gson.Gson;
import com.taomish.app.android.farmsanta.farmer.libs.service.constants.ServiceConstants;
import com.taomish.app.android.farmsanta.farmer.libs.service.enums.RequestMethod;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@SuppressWarnings("JavaDoc")
public class ServiceCall {

    private String uRL;
    private String paramToPass;

    private final Map<String, String> headers;
    private final Map<String, String> body;
    private final Map<String, File> multipartBody;

    private boolean isHttps = false;
    private int responseCode = -1;
    private int connectionTimeOutInMillis = 10 * 1000;
    private String charset = ServiceConstants.Encodings.utf8;

    private static final String LINE_FEED = "\r\n";
    private static final String TWO_HYPHENS = "--";
    private static final String BOUNDARY = "----WebKitFormBoundary7MA4YWxkTrZu0gW";

    private boolean isQueryParamAdded = false;

    private RequestMethod requestMethod = RequestMethod.GET;

    public ServiceCall(String url) {
        uRL = url;
        headers = new HashMap<>();
        body = new HashMap<>();
        multipartBody = new HashMap<>();
        paramToPass = "";
        if (url.startsWith("https")) {
            isHttps(true);
        }
        isQueryParamAdded = url.contains("?");
    }

    public ServiceCall addHeader(String propertyName, String value) {
        headers.put(propertyName, value);
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public void isHttps(boolean isHttps) {
        this.isHttps = isHttps;
    }

    @SuppressWarnings("unused")
    public ServiceCall setConnectionTimeOutInMillis(int connectionTimeOutInMillis) {
        this.connectionTimeOutInMillis = connectionTimeOutInMillis;
        return this;
    }

    public ServiceCall setRequestMethod(RequestMethod method) {
        requestMethod = method;
        return this;
    }

    public ServiceCall addParams(String key, String value) {
        body.put(key, value);
        return this;
    }

    public ServiceCall addMultipart(String fieldName, File uploadFile) {
        multipartBody.put(fieldName, uploadFile);
        return this;
    }

    /**
     * Body params for passing into post request
     *
     * @param body
     * @return
     */
    public ServiceCall setBody(String body) {
        paramToPass = body.replace("\\\\", "");
        return this;
    }

    public ServiceCall addQueryParam(String key, String value) {
        if (!isQueryParamAdded) {
            isQueryParamAdded = true;
            uRL += "?";
        } else {
            uRL += "&";
        }

        uRL += key + "=" + value;
        return this;
    }

    public int getResponseCode() {
        return responseCode;
    }

    @SuppressWarnings("unused")
    public ServiceCall setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * Upon completion check for response code by calling getResponseCode on calling object
     *
     * @return
     * @throws IOException
     */
    public String execute() throws IOException {
        String response = null;
        switch (requestMethod) {
            case GET:
                response = isHttps ? executeHttpsGetCall() : executeHttpGetCall();
                break;
            case POST:
                response = isHttps ? executeHttpsPostCall() : executeHttpPostCall();
                break;
            case PUT:
                response = isHttps ? executeHttpsPutCall() : executeHttpPutCall();
                break;
            case DELETE:
                response = isHttps ? executeHttpsDeleteCall() : executeHttpDeleteCall();
                break;
            case MULTI:
                response = isHttps ? executeHttpsMultipartCall() : executeHttpMultipartCall();
                break;
        }

//        if (BuildConfig.DEBUG) {
            Log.e("URL", new Gson().toJson(this));
            Log.e("Response", "----->" + response);
//        }
        return response;
    }

    /**
     * Execute http get call. Https get call can also be made.
     * But for Https use executeHttpsGetCall method
     *
     * @return response from server
     * @throws IOException
     */
    private String executeHttpGetCall() throws IOException {
        String response = "";
        URL url = new URL(uRL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(connectionTimeOutInMillis);
        setHeaderInRequest(httpURLConnection);
        try {
            response = getResponseFromHttpUrlConnection(httpURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return response;
    }

    /**
     * Execute https get call
     *
     * @return response from server
     * @throws IOException
     */
    private String executeHttpsGetCall() throws IOException {
        String response = "";
        trustEveryone();
        URL url = new URL(uRL);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        acceptAllCertificateCustomSSLContext();
        acceptAllCertificateHostNameVerifier();

        httpsURLConnection.setConnectTimeout(connectionTimeOutInMillis);
        setHeaderInRequest(httpsURLConnection);

        try {
            response = getResponseFromHttpsUrlConnection(httpsURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpsURLConnection.disconnect();
        }
        return response;
    }

    /**
     * Executes the put call
     *
     * @return response from server
     * @throws IOException
     */
    private String executeHttpPutCall() throws IOException {
        String response = "";
        URL url = new URL(uRL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setConnectTimeout(connectionTimeOutInMillis);
        httpURLConnection.setRequestMethod(ServiceConstants.RequestMethod.put);
        setHeaderInRequest(httpURLConnection);

        try {
            setBodyInRequest(httpURLConnection);

            response = getResponseFromHttpUrlConnection(httpURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return response;
    }

    /**
     * Executes the put call in https method
     *
     * @return response from server
     * @throws IOException
     */
    private String executeHttpsPutCall() throws IOException {
        String response = "";
        URL url = new URL(uRL);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setConnectTimeout(connectionTimeOutInMillis);
        httpsURLConnection.setRequestMethod(ServiceConstants.RequestMethod.put);
        setHeaderInRequest(httpsURLConnection);

        try {
            setBodyInRequest(httpsURLConnection);

            response = getResponseFromHttpsUrlConnection(httpsURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpsURLConnection.disconnect();
        }
        return response;
    }

    /**
     * Executes the post call
     *
     * @return response from server
     * @throws IOException
     */
    private String executeHttpPostCall() throws IOException {
        String response = "";
        URL url = new URL(uRL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setConnectTimeout(connectionTimeOutInMillis);
        httpURLConnection.setRequestMethod(ServiceConstants.RequestMethod.post);

        setHeaderInRequest(httpURLConnection);
        try {
            setBodyInRequest(httpURLConnection);

            response = getResponseFromHttpUrlConnection(httpURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return response;
    }

    /**
     * Executes the post call in https method
     *
     * @return response from server
     * @throws IOException
     */
    private String executeHttpsPostCall() throws IOException {
        String response = "";
        trustEveryone();
        URL url = new URL(uRL);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        acceptAllCertificateCustomSSLContext();
        acceptAllCertificateHostNameVerifier();

        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setConnectTimeout(connectionTimeOutInMillis);
        httpsURLConnection.setRequestMethod(ServiceConstants.RequestMethod.post);

        setHeaderInRequest(httpsURLConnection);
        try {
            setBodyInRequest(httpsURLConnection);

            response = getResponseFromHttpsUrlConnection(httpsURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpsURLConnection.disconnect();
        }
        return response;
    }

    /**
     * Execute the delete call. Returns a json string with success : true
     * if the call is success. Check response code for actual result
     *
     * @return response from server
     * @throws IOException
     */
    private String executeHttpDeleteCall() throws IOException {
        URL url = new URL(uRL);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

        httpCon.setDoOutput(true);
        httpCon.setConnectTimeout(connectionTimeOutInMillis);
        setHeaderInRequest(httpCon);
        httpCon.setRequestMethod(ServiceConstants.RequestMethod.delete);

        httpCon.connect();

        responseCode = httpCon.getResponseCode();

        httpCon.disconnect();

        return "{success:true}";
    }

    /**
     * Execute the delete call in https. Returns a json string with success : true
     * if the call is success. Check response code for actual result
     *
     * @return response from server
     * @throws IOException
     */
    private String executeHttpsDeleteCall() throws IOException {
        URL url = new URL(uRL);
        HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection();

        httpsCon.setDoOutput(true);
        httpsCon.setConnectTimeout(connectionTimeOutInMillis);
        setHeaderInRequest(httpsCon);
        httpsCon.setRequestMethod(ServiceConstants.RequestMethod.delete);

        httpsCon.connect();

        responseCode = httpsCon.getResponseCode();

        httpsCon.disconnect();

        return "{success:true}";
    }

    /**
     * Executes the post call
     *
     * @return response from server
     * @throws IOException
     */
    private String executeHttpMultipartCall() throws IOException {
        String response = "";
        URL url = new URL(uRL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setConnectTimeout(connectionTimeOutInMillis);
        httpURLConnection.setRequestMethod(ServiceConstants.RequestMethod.post);

        addHeader("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
        setHeaderInRequest(httpURLConnection);

        try {
            writeContentInRequest(httpURLConnection);

            response = getResponseFromHttpUrlConnection(httpURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return response;
    }

    /**
     * Executes the post call in https method
     *
     * @return response from server
     * @throws IOException
     */
    private String executeHttpsMultipartCall() throws IOException {
        String response = "";
        URL url = new URL(uRL);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setConnectTimeout(connectionTimeOutInMillis);
        httpsURLConnection.setRequestMethod(ServiceConstants.RequestMethod.post);

        addHeader("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
        setHeaderInRequest(httpsURLConnection);

        try {
            writeContentInRequest(httpsURLConnection);

            response = getResponseFromHttpsUrlConnection(httpsURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpsURLConnection.disconnect();
        }
        return response;
    }

    /**
     * Sets the headers for the request. Pass by reference. Hence no need to return
     *
     * @param urlConnection http or https url connection
     * @return theUrlConnectionWithHeaders
     */
    private void setHeaderInRequest(URLConnection urlConnection) {
        if (headers.size() > 0) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                urlConnection.addRequestProperty(key, headers.get(key));
            }
        }
        //return urlConnection;
    }

    /**
     * Sets the body of the request. Pass by reference. Hence no need to return anything
     *
     * @param urlConnection http or https url connection
     * @return theUrlConnectionWithBody
     * @throws IOException
     */
    private void setBodyInRequest(URLConnection urlConnection) throws IOException {
        try {
            String contentToPost = getContentToPost();
            if (contentToPost.length() > 0) {
                OutputStreamWriter osw = new OutputStreamWriter(urlConnection.getOutputStream());
                osw.write(contentToPost);
                osw.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //return urlConnection;
    }

    /**
     * Call setBodyInRequest instead of calling this method directly
     *
     * @return contentToPost
     * @throws UnsupportedEncodingException
     */
    private String getContentToPost() throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        if (paramToPass.length() > 0) {
            result.append(paramToPass);
        } else {
            if (body.size() > 0) {
                boolean first = true;

                Set<String> keySet = body.keySet();
                for (String key : keySet) {
                    if (first)
                        first = false;
                    else
                        result.append("&");

                    result.append(URLEncoder.encode(key, ServiceConstants.Encodings.utf8));
                    result.append("=");
                    result.append(URLEncoder.encode(body.get(key), ServiceConstants.Encodings.utf8));
                }
            }
        }

        return result.toString();
    }

    /**
     * @param urlConnection http or https url connection
     * @throws IOException exception thrown when adding data or getting server data
     */
    private void writeContentInRequest(URLConnection urlConnection) throws IOException {

        if (multipartBody.size() > 0) {
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

            /*if (headers.size() > 0) {
                Set<String> keySet      = headers.keySet();
                for (String key : keySet) {
                    writer.append(key).append(": ").append(headers.get(key))
                            .append(LINE_FEED);
                    writer.flush();
                }
            }*/

            Set<String> fieldNameSet = multipartBody.keySet();
            for (String fieldName : fieldNameSet) {
                String fileName = multipartBody.get(fieldName).getName();

                writer.append("multipart/form-data; boundary=" + BOUNDARY);
                writer.append(TWO_HYPHENS).append(BOUNDARY)
                        .append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"").append(fieldName)
                        .append("\"; filename=\"").append(fileName).append("\"")
                        .append(LINE_FEED);
                writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(fileName))
                        .append(LINE_FEED).append(LINE_FEED);
                writer.flush();

                FileInputStream inputStream = new FileInputStream(multipartBody.get(fieldName));
                int bufferSize = Math.min(inputStream.available(), 4096);
                byte[] buffer = new byte[bufferSize];

                int bytesRead;
                while ((bytesRead = inputStream.read(buffer, 0, bufferSize)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.flush();
                inputStream.close();
                writer.append(LINE_FEED);
                writer.flush();
            }

            writer.append(TWO_HYPHENS).append(BOUNDARY).append(LINE_FEED);

            if (paramToPass.length() > 0) {
                writer.append(TWO_HYPHENS).append(BOUNDARY)
                        .append(LINE_FEED);
                writer.append(paramToPass).append(LINE_FEED);
                writer.append(TWO_HYPHENS).append(BOUNDARY);
                writer.flush();
            } else {
                if (body.size() > 0) {
                    Set<String> keySet = body.keySet();
                    for (String key : keySet) {
                        writer.append(TWO_HYPHENS).append(BOUNDARY)
                                .append(LINE_FEED);
                        writer.append("Content-Disposition: form-data; name=\"").append(key).append("\"")
                                .append(LINE_FEED);
                        writer.append(LINE_FEED);
                        writer.append(body.get(key))
                                .append(LINE_FEED);
                        writer.append(TWO_HYPHENS).append(BOUNDARY);
                        writer.flush();
                    }
                }
            }
            writer.append(TWO_HYPHENS);
            writer.close();
        }
    }

    /**
     * Get response from server. Both error and success will be returned;
     *
     * @param httpURLConnection url connection object
     * @return response from server
     * @throws IOException when tries to read input stream
     */
    private String getResponseFromHttpUrlConnection(HttpURLConnection httpURLConnection) throws IOException {
        String responseBody;
        Map<String, List<String>> headerMap = new HashMap<>();
        responseCode = httpURLConnection.getResponseCode();

        if (responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            responseBody = convertStreamToString(inputStream);
            /*Map<String, List<String>> headers       = httpURLConnection.getHeaderFields();
            Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
            for (Map.Entry<String, List<String>> entry : entrySet) {
                String headerName                   = entry.getKey();
                List<String> headerValues           = entry.getValue();
                if (headerName != null && headerValues != null)
                    headers.put(headerName, headerValues);
            }*/
        } else {
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getErrorStream());
            responseBody = convertStreamToString(inputStream);
        }

        return responseBody;
    }

    /**
     * Get response from server. Both error and success will be returned;
     *
     * @param httpsURLConnection url connection object
     * @return response from server
     * @throws IOException when tries to read input stream
     */
    private String getResponseFromHttpsUrlConnection(HttpsURLConnection httpsURLConnection) throws IOException {
        String response;
        responseCode = httpsURLConnection.getResponseCode();

        InputStream inputStream;
        if (responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
            inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
        } else {
            inputStream = new BufferedInputStream(httpsURLConnection.getErrorStream());
        }
        response = convertStreamToString(inputStream);
        return response;
    }


    /**
     * Converts the input stream provided to a readable string
     *
     * @param is the input stream
     * @return input stream converted to string
     */
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void acceptAllCertificateCustomSSLContext() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            }, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        } catch (KeyManagementException kme) {
            kme.printStackTrace();
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
    }

    private void acceptAllCertificateHostNameVerifier() {
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }

    /**
     * Should find a work around
     */

    @SuppressLint({"ObsoleteSdkInt", "TrustAllX509TrustManager"})
    private void trustEveryone() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH)
            return;

        try {
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}
