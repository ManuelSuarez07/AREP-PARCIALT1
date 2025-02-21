package com.eci.Calculator.Rest;

import com.eci.Calculator.Rest.Response;
import com.eci.Calculator.Rest.Request;

/**
 *
 * @author Manuel S
 */
public interface Service {
    String getValue(Request req, Response resp);
}


