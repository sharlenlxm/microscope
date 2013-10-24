package com.vipshop.microscope.trace;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.HttpUriRequest;

import com.vipshop.microscope.trace.span.Category;

public class Tracer {
	
	/**
	 * trace start api
	 * 
	 * @param spanName
	 * @param category
	 */
	public static void clientSend(String spanName, Category category){
		TraceFactory.getTrace().clientSend(spanName, category);
	}
	
	public static void clientSend(HttpUriRequest request, Category category){
		TraceFactory.getTrace().clientSend(request.getMethod() + category, category);
		TraceFactory.setHttpRequestHead(request);
	}
	
	public static void clientSend(HttpServletRequest request, Category category){
		System.out.println("request" + request);
		TraceFactory.getHttpRequestHead(request);
		TraceFactory.getTrace().clientSend(request.getMethod() + category, category);
	}
	
	/**
	 * trace end api
	 */
	public static void clientReceive() {
		TraceFactory.getTrace().clientReceive();
	}
	
	/**
	 * app programmer api
	 * 
	 * @param key
	 * @param value
	 */
	public static void record(String key, String value) {
		TraceFactory.getTrace().record(key, value);
	}
	
	/**
	 * asyn thread invoke.
	 * 
	 * @return
	 */
	public static Trace getContext() {
		return TraceFactory.getContext();
	}
	
	public static void setContext(Trace trace) {
		TraceFactory.setContext(trace);
	}
	
	/**
	 * integrate with other system.
	 * 
	 * @return
	 */
	public static long getTraceId() {
		return TraceFactory.getTraceId();
	}
	
}