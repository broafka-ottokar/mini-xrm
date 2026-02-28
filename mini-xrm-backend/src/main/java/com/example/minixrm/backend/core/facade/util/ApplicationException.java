package com.example.minixrm.backend.core.facade.util;

public class ApplicationException extends RuntimeException {
	
	private final ErrorCode errorCode;
	private final String publicMessage;
	
	public ApplicationException(ErrorCode errorCode, String publicMessage, Throwable cause) {
		super(publicMessage, cause);
		this.errorCode = errorCode;
		this.publicMessage = publicMessage;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	
	public String getPublicMessage() {
		return publicMessage;
	}
	
	public static ApplicationException partnerNotFound(long id) {
		return new ApplicationException(ErrorCode.PARTNER_NOT_FOUND, id + " is not a valid partner id", null);
	}

	public static ApplicationException entityNotFound(Long id) {
		return new ApplicationException(ErrorCode.ENTITY_NOT_FOUND, id + " is not a valid entity id", null);
	}

	public static ApplicationException activityNotFound(Long activityId) {
		return new ApplicationException(ErrorCode.ACTIVITY_NOT_FOUND, activityId + " is not a valid activity id", null);
	}

	public static ApplicationException partnerTagNotFound(Long tagId) {
		return new ApplicationException(ErrorCode.PARTNER_TAG_NOT_FOUND, tagId + " is not a valid partner tag id", null);
	}

	public static ApplicationException partnerInactive(long partnerId) {
		return new ApplicationException(ErrorCode.PARTNER_INACTIVE, "Partner with id " + partnerId + " is not active", null);
	}

	public static ApplicationException concurrentModification(Long id, Throwable t) {
		return new ApplicationException(ErrorCode.CONCURRENT_MODIFICATION, "Concurrent modification detected for entity with id " + id, t);
	}

}