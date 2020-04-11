package com.jpa.voucher.processor;

import com.jpa.voucher.data.ServiceResponse;
import com.jpa.voucher.data.Status;

public abstract class ResponseBuilder {

	public static ServiceResponse getStatus(String arg, Object payload) {
		return ResponseBuilder.getStatus(Status.valueOf(arg), payload);
	}

	public static ServiceResponse getStatus(Status arg, Object payload) {
		switch (arg) {
		case PROCESSED:
			return new ServiceResponse(arg.toString(), "200", "Transaction Succesfull", payload);
		case INVALID_PARAMETER:
			return new ServiceResponse(arg.toString(), "404", "Incomplete Request Parameter", payload);
		case PRODUCT_NOT_FOUND:
			return new ServiceResponse(arg.toString(), "404", "The Specified Product Not Found", payload);
		case VOUCHER_NOT_FOUND:
			return new ServiceResponse(arg.toString(), "404", "The Specified Voucher Not Found", payload);
		case MEMBER_NOT_FOUND:
			return new ServiceResponse(arg.toString(), "404", "The Specified Member Not Found On System", payload);
		case UNAUTHORIZED_ACCESS:
			return new ServiceResponse(arg.toString(), "403", "Invalid Token Signature", payload);
		case ACCESS_DENIED:
			return new ServiceResponse(arg.toString(), "403", "You don't have Permission to Access this WebService",
					payload);
		case INVALID_REDEMPTION_POINT:
			return new ServiceResponse(arg.toString(), "404", "Invalid Redemption Point", payload);
		case VOUCHER_ALREADY_USED:
			return new ServiceResponse(arg.toString(), "409", "Voucher has Already Being Used", payload);
		case DUPLICATE_TRACENO:
			return new ServiceResponse(arg.toString(), "409", "The specified Trace Number has Already Being Used",
					payload);
		case REGISTRATION_TIME_EXPIRED:
			return new ServiceResponse(arg.toString(), "419", "Voucher Registration Time has Expired", payload);
		case QUOTA_EXCEEDED:
			return new ServiceResponse(arg.toString(), "419", "Voucher Registration Quota Exceeded", payload);
		case VOUCHER_INACTIVE:
			return new ServiceResponse(arg.toString(), "419", "Voucher is Inactive", payload);
		case INVALID_DATE:
			return new ServiceResponse(arg.toString(), "403", "Invalid Date Format", payload);
		default:
			return new ServiceResponse("UNKNOWN_ERROR", "500", "Transaction Failed, Please Contact Administrator",
					payload);
		}
	}
}
