package com.cloudnie.stunclient;

public interface StunConstant {
	/* STUN Message type */
	static final int BINDING_REQUEST                  = 0x0001;
	static final int BINDING_RESPONSE                 = 0x0101;
	static final int BINDING_ERROR_RESPONSE           = 0x0111;
	static final int SHARED_SECRET_REQUEST            = 0x0002;
	static final int SHARED_SECRET_RESPONSE           = 0x0102;
	static final int SHARED_SECRET_ERROR_RESPONSE     = 0x0112;

	/* STUN Attribute type */
	static final int STUN_MAPPED_ADDRESS	         = 0x0001;
	static final int STUN_RESPONSE_ADDRESS	         = 0x0002;
	static final int STUN_CHANGE_REQUEST             = 0x0003;
	static final int STUN_SOURCE_ADDRESS             = 0x0004;
	static final int STUN_CHANGED_ADDRESS		 = 0x0005;
	static final int STUN_USERNAME                   = 0x0006;
	static final int STUN_PASSWORD                   = 0x0007;
	static final int STUN_MESSAGE_INTEGRITY          = 0x0008;
	static final int STUN_ERROR_CODE                 = 0x0009;
	static final int STUN_UNKNOWN_ATTRIBUTES         = 0x000A;
	static final int STUN_REFLECTED_FROM             = 0x000B;
	static final int STUN_REALM			 = 0x0014;
	static final int STUN_NONCE			 = 0x0015;
	static final int STUN_XOR_MAPPED_ADDRESS	 = 0x0020;

	/* 400 (Bad Request): The request was malformed. 
	 * The client should not retry the request without
	 * modification from the previous attempt.
	 */
	static final int STUN_ERROR_BAD_REQUEST			 = 400;


	/* 401 (Unauthorized): The Binding Request did not
	 * contain a MESSAGE- INTEGRITY attribute.
	 */
	static final int STUN_ERROR_UNAUTHORIZED		 = 401;


	/* 420 (Unknown Attribute): The server did not
	 * understand a mandatory attribute in the request.
	 */
	static final int STUN_ERROR_UNKNOWN_ATTRIBUTE	         = 420;


	/* 430 (Stale Credentials): The Binding Request
	 * did contain a MESSAGE- INTEGRITY attribute,
	 * but it used a shared secret that has expired.
	 * The client should obtain a new shared secret and try again.
	 */
	static final int STUN_ERROR_STALE_CREDENTIALS           = 430;


	/* 431 (Integrity Check Failure): The Binding Request
	 * contained a MESSAGE-INTEGRITY attribute, but the HMAC
	 * failed verification.  This could be a sign of
	 * a potential attack, or client implementation error.
	 */
	static final int STUN_ERROR_INTEGRITY_CHECK_FAIL        = 431;

	/* 432 (Missing Username): The Binding Request contained
	 * a MESSAGE- INTEGRITY attribute, but not a USERNAME attribute.
	 * Both must be present for integrity checks.
	 */
	static final int STUN_ERROR_MISSING_USERNAME            = 432;


	/* 433 (Use TLS): The Shared Secret request has to be sent
	 * over TLS, but was not received over TLS.
	 */
	static final int STUN_ERROR_USE_TLS		        = 433;


	/* 500 (Server Error): The server has suffered a temporary error.
	 * The client should try again.
	 */
	static final int STUN_ERROR_SERVER_ERROR		= 500;


	/* 600 (Global Failure:) The server is refusing to fulfill
	 * the request.
	 */
	static final int STUN_ERROR_GLOBAL_FAILURE	        = 600;

};
