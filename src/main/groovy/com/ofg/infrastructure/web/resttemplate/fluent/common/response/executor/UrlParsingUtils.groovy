package com.ofg.infrastructure.web.resttemplate.fluent.common.response.executor

import groovy.transform.PackageScope
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j

@TypeChecked
@Slf4j
final class UrlParsingUtils {

    public static final String SLASH = "/"
    public static final String VAR_OPENING_SIGN = '\\{'
    public static final String VAR_CLOSING_SIGN = '}'
    public static final String EMPTY = ''

    private UrlParsingUtils() {
        throw new UnsupportedOperationException("Can't instantiate a utility class")
    }

    @PackageScope static String prependSlashIfPathIsRelative(URI uri) {
        if(!uri.absolute && !uri.toString().startsWith(SLASH)) {
            return "$SLASH$uri"
        }
        return uri.toString()
    }

    @PackageScope static String prependSlashIfPathDoesNotStartWithSlash(String uri) {
        if(!isUriAbsolute(uri) && !uri.startsWith(SLASH)) {
            return "$SLASH$uri"
        }
        return uri
    }
    
    private static boolean isUriAbsolute(String uri) {
        try {
            new URI(removeVariableSignsFromUri(uri)).absolute
        } catch(URISyntaxException uriSytaxException) {
            log.trace("Passed uri [$uri] is not a valid uri. Exception [$uriSytaxException] occurred while trying to parse it")
            return false
        }
    }

    private static String removeVariableSignsFromUri(String uri) {
        return uri.replaceAll(VAR_OPENING_SIGN, EMPTY).replaceAll(VAR_CLOSING_SIGN, EMPTY)
    }

    public static String appendPathToHost(String host, URI path) {
        return "$host${prependSlashIfPathIsRelative(path)}"       
    }
    
    public static String appendPathToHost(String host, String path) {
        return "$host${prependSlashIfPathDoesNotStartWithSlash(path)}"       
    }
}
