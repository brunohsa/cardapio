package br.com.unip.cardapio.security.filter

import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CorsFilterCustom : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest,
                          res: ServletResponse,
                          chain: FilterChain) {
        val response = res as HttpServletResponse
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE")
        response.addHeader("Access-Control-Max-Age", "3600")
        response.addHeader("Access-Control-Allow-Credentials", "true")
        response.addHeader("Access-Control-Allow-Headers", "Accept, Accept-CH, Accept-Charset, " +
                "Accept-Datetime, Accept-Encoding, Accept-Ext, Accept-Features, Accept-Language, " +
                "Accept-Params, Accept-Ranges, Access-Control-Allow-Credentials, " +
                "Access-Control-Allow-Headers, Access-Control-Allow-Methods, " +
                "Access-Control-Allow-Origin, Access-Control-Expose-Headers, " +
                "Access-Control-Max-Age, Access-Control-Request-Headers, " +
                "Access-Control-Request-Method, Age, Allow, Alternates, Authentication-Info, " +
                "Authorization, C-Ext, C-Man, C-Opt, C-PEP, C-PEP-Info, CONNECT, " +
                "Cache-Control, Compliance, Connection, Content-Base, Content-Disposition, " +
                "Content-Encoding, Content-ID, Content-Language, Content-Length, " +
                "Content-Location, Content-MD5, Content-Range, Content-Script-Type, " +
                "Content-Security-Policy, Content-Style-Type, Content-Transfer-Encoding, " +
                "Content-Type, Content-Version, Cookie, Cost, DAV, DELETE, DNT, " +
                "DPR, Date, Default-Style, Delta-Base, Depth, Derived-From, Destination, " +
                "Differential-ID, Digest, ETag, Expect, Expires, Ext, From, GET, " +
                "GetProfile, HEAD, HTTP-date, Host, IM, If, If-Match, If-Modified-Since, " +
                "If-None-Match, If-Range, If-Unmodified-Since, Keep-Alive, Label, " +
                "Last-Event-ID, Last-Modified, Link, Location, Lock-Token, " +
                "MIME-Version, Man, Max-Forwards, Media-Range, Message-ID, Meter, Negotiate, " +
                "Non-Compliance, OPTION, OPTIONS, OWS, Opt, Optional, Ordering-Type, Origin, " +
                "Overwrite, P3P, PEP, PICS-Label, POST, PUT, Pep-Info, Permanent, Position, " +
                "Pragma, ProfileObject, Protocol, Protocol-Query, Protocol-Request, " +
                "Proxy-Authenticate, Proxy-Authentication-Info, Proxy-Authorization, " +
                "Proxy-Features, Proxy-Instruction, Public, RWS, Range, Referer, Refresh, " +
                "Resolution-Hint, Resolver-Location, Retry-After, Safe, Sec-Websocket-Extensions, " +
                "Sec-Websocket-Key, Sec-Websocket-Origin, Sec-Websocket-Protocol, " +
                "Sec-Websocket-Version, Security-Scheme, Server, Set-Cookie, Set-Cookie2, " +
                "SetProfile, SoapAction, Status, Status-URI, Strict-Transport-Security, " +
                "SubOK, Subst, Surrogate-Capability, Surrogate-Control, TCN, TE, TRACE, " +
                "Timeout, Title, Trailer, Transfer-Encoding, UA-Color, UA-Media, " +
                "UA-Pixels, UA-Resolution, UA-Windowpixels, URI, Upgrade, User-Agent, " +
                "Variant-Vary, Vary, Version, Via, Viewport-Width, WWW-Authenticate, " +
                "Want-Digest, Warning, Width, X-Content-Duration, X-Content-Security-Policy, " +
                "X-CustomHeader, X-DNSPrefetch-Control, X-Forwarded-For, X-Forwarded-Port, " +
                "X-Forwarded-Proto, X-Modified, X-OTHER, X-PING, X-PINGOTHER, X-Powered-By, X-Requested-With, token")
        response.addHeader("Access-Control-Expose-Headers", "token")

        val request = req as HttpServletRequest
        if (request.method == "OPTIONS") {
            response.status = HttpServletResponse.SC_OK
            return
        }

        chain.doFilter(req, res)
    }
}