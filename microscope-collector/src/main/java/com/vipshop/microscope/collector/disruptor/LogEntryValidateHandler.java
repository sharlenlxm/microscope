package com.vipshop.microscope.collector.disruptor;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.vipshop.microscope.collector.validater.MessageValidater;
import com.vipshop.microscope.common.logentry.Codec;
import com.vipshop.microscope.common.logentry.Constants;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.metrics.Metric;
import com.vipshop.microscope.common.trace.Span;

/**
 * LogEntry validate handler.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class LogEntryValidateHandler implements EventHandler<LogEntryEvent> {

	public final Logger logger = LoggerFactory.getLogger(LogEntryValidateHandler.class);
	
	private final MessageValidater messageValidater = new MessageValidater();

	private RingBuffer<TraceEvent> traceRingBuffer;
	private RingBuffer<MetricsEvent> metricsRingBuffer;
	private RingBuffer<ExceptionEvent> exceptionRingBuffer;

	public LogEntryValidateHandler(RingBuffer<TraceEvent> traceBuffer, 
								   RingBuffer<MetricsEvent> metricsBuffer,
								   RingBuffer<ExceptionEvent> exceptionRingBuffer) {
		this.traceRingBuffer = traceBuffer;
		this.metricsRingBuffer = metricsBuffer;
		this.exceptionRingBuffer = exceptionRingBuffer;
	}

	@Override
	public void onEvent(LogEntryEvent event, long sequence, boolean endOfBatch) throws Exception {
		LogEntry logEntry = event.getResult();
		String category = logEntry.getCategory();

		// handle trace message
		if (category.equals(Constants.TRACE)) {
			Span span = null;
			try {
				span = Codec.decodeToSpan(logEntry.getMessage());
			} catch (Exception e) {
				logger.error("decode to Span error, ignore this message ", e);
				return;
			}
			span = messageValidater.validateMessage(span);
			publish(span);
		}
		
		// handle metrics message
		if (category.equals(Constants.METRICS)) {
			Metric metrics = null;
			try {
				metrics = Codec.decodeToMetric(logEntry.getMessage());
			} catch (Exception e) {
				logger.error("decode to Set error, ignore this message ", e);
				return;
			}
			// TODO validate
			publish(metrics);
		}

		// handle exception message
		if (category.equals(Constants.EXCEPTION)) {
			HashMap<String, Object> metrics = null;
			try {
				metrics = Codec.decodeToMap(logEntry.getMessage());
			} catch (Exception e) {
				logger.error("decode to Metrics error, ignore this message ", e);
				// TODO: handle exception
				return;
			}
			metrics = messageValidater.validateMessage(metrics);
			publish(metrics);
		}
	}

	/**
	 * Publish trace to {@code TraceRingBuffer}.
	 * 
	 * @param msg
	 */
	private void publish(Span span) {
		if (span != null) {
			long sequence = this.traceRingBuffer.next();
			this.traceRingBuffer.get(sequence).setSpan(span);
			this.traceRingBuffer.publish(sequence);
		}
	}
	
	/**
	 * Publish metrics to {@code MetricsRingBuffer}.
	 * 
	 * @param metrics
	 */
	private void publish(Metric metrics) {
		if (metrics != null) {
			long sequence = this.metricsRingBuffer.next();
			this.metricsRingBuffer.get(sequence).setResult(metrics);
			this.metricsRingBuffer.publish(sequence); 
		}
	}

	/**
	 * Publish exception to {@code ExceptionRingBuffer}.
	 * 
	 * @param msg
	 */
	private void publish(HashMap<String, Object> exception) {
		if (exception != null) {
			long sequence = this.exceptionRingBuffer.next();
			this.exceptionRingBuffer.get(sequence).setResult(exception);
			this.exceptionRingBuffer.publish(sequence);
		}
	}

}