package com.taomish.app.android.farmsanta.farmer.libs.service.constants;

/**
 * Class holding Service constants
 *
 * @author UST Global
 **/
public final class ServiceConstants {
    public static final class HeaderKeys {
        public static final String authToken            = "Auth-Token";
        public static final String contentType          = "Content-Type";
        public static final String contentLength        = "Content-Length";
        public static final String acceptLang           = "Accept-Language";
        public static final String charset              = "charset";
        public static final String accept               = "Accept";
        public static final String csrfToken            = "X-Csrf-Token";
        public static final String authorization        = "Authorization";
        public static final String userAgent            = "User-Agent";
        public static final String awControl            = "awcontrol";
        public static final String cacheControl         = "cache-control";//multipart/form-data;
    }
    public static final class HeaderValues {

        public static final String textXml              = "text/xml";
        public static final String textPlain            = "text/plain";
        public static final String textJavascript       = "text/javascript";
        public static final String usLang               = "en-US";
        public static final String formUrlEncoded       = "application/x-www-form-urlencoded";
        public static final String applicationOctet     = "application/octet-stream";
        public static final String applicationJson      = "application/json";
        public static final String applicationJsonUtf8  = "application/json; charset=utf-8";
        public static final String multiPartFormData    = "multipart/form-data";
        public static final String acceptLangVal        = "en;q=1";
        public static final String userAgentVal         = " AirWatch Browser";
        public static final String trueVal              = "true";
        public static final String bearer               = "Bearer";
        public static final String noCache              = "no-cache";
    }

    public static final class RequestMethod {
        public static final String put                  = "PUT";
        public static final String post                 = "POST";
        public static final String get                  = "GET";
        public static final String delete               = "DELETE";
    }

    public static final class Encodings {
        public static final String utf8                 = "UTF-8";
        public static final String usAscii              = "us-ascii";
    }

    public static final class ResponseKeys {
        public static final String contentLang          = "Content-Language";
        public static final String contentEncoding      = "Content-Encoding";
    }
}
