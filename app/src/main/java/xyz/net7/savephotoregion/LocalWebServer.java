package xyz.net7.savephotoregion;

import fi.iki.elonen.NanoHTTPD;

public class LocalWebServer extends NanoHTTPD {

    public LocalWebServer() {
        super(8000); // Use your desired port number
    }

    @Override
    public Response serve(IHTTPSession session) {
        String response = "<html><body><h1>Hello from Local Server!</h1></body></html>";
        return newFixedLengthResponse(Response.Status.OK, "text/html", response);
    }
}
