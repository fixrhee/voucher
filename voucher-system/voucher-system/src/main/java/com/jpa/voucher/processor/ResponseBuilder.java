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
			return new ServiceResponse("A00", "PROCESSED", "Transaction completed", payload);
		case UNAUTHORIZED_ACCESS:
			return new ServiceResponse("A90", "UNAUTHORIZED_ACCESS",
					"We cannot authorized you, please check your authorization", payload);
		case SERVICE_NOT_ALLOWED:
			return new ServiceResponse("A16", "SERVICE_NOT_ALLOWED", "Group not allowed to access Webservices",
					payload);
		case INVALID:
			return new ServiceResponse("A14", "INVALID", "Invalid credential", payload);
		case VALID:
			return new ServiceResponse("A01", "VALID", "Credential Valid", payload);
		case BLOCKED:
			return new ServiceResponse("A16", "BLOCKED", "Credential blocked", payload);
		case UNAUTHORIZED_MEMBER_ACCESS:
			return new ServiceResponse("M16", "UNAUTHORIZED_MEMBER_ACCESS", "You don't have access to specified member",
					payload);
		case MEMBER_NOT_FOUND:
			return new ServiceResponse("M14", "MEMBER_NOT_FOUND", "Member not found on system", payload);
		case MEMBER_ALREADY_REGISTERED:
			return new ServiceResponse("M14", "MEMBER_ALREADY_REGISTERED", "Member already registered on system",
					payload);
		case DESTINATION_MEMBER_NOT_FOUND:
			return new ServiceResponse("M15", "DESTINATION_MEMBER_NOT_FOUND", "Destination member not found on system",
					payload);
		case NO_TRANSACTION:
			return new ServiceResponse("S84", "NO_TRANSACTION", "No transaction found for the specified account",
					payload);
		case INVALID_ACCOUNT:
			return new ServiceResponse("S14", "INVALID_ACCOUNT", "Invalid source account/permission not allowed",
					payload);
		case INVALID_DESTINATION_ACCOUNT:
			return new ServiceResponse("S15", "INVALID_DESTINATION_ACCOUNT",
					"Invalid destination account/permission not allowed", payload);
		case INSUFFICIENT_BALANCE:
			return new ServiceResponse("S22", "INSUFFICIENT_BALANCE",
					"You dont have enough balance to process this transaction", payload);
		case CREDIT_LIMIT_REACHED:
			return new ServiceResponse("S40", "CREDIT_LIMIT_REACHED", "Your monthly account limit has reached",
					payload);
		case DESTINATION_CREDIT_LIMIT_REACHED:
			return new ServiceResponse("S41", "DESTINATION_CREDIT_LIMIT_REACHED",
					"The destination monthly limit has reached", payload);
		case INVALID_TRANSFER_TYPE:
			return new ServiceResponse("T14", "INVALID_TRANSFER_TYPE", "Invalid transfer type ID", payload);
		case TRANSACTION_AMOUNT_BELOW_LIMIT:
			return new ServiceResponse("T16", "TRANSACTION_AMOUNT_BELOW_LIMIT",
					"Transaction amount is below the threshold limit", payload);
		case TRANSACTION_AMOUNT_ABOVE_LIMIT:
			return new ServiceResponse("T18", "TRANSACTION_AMOUNT_ABOVE_LIMIT",
					"Transaction amount is above the threshold limit", payload);
		case DUPLICATE_TRANSACTION:
			return new ServiceResponse("P16", "DUPLICATE_TRANSACTION", "Duplicate transaction entry", payload);
		case TRANSACTION_BLOCKED:
			return new ServiceResponse("T40", "TRANSACTION_BLOCKED",
					"Transaction is blocked, please contact administrator", payload);
		case INVALID_PARAMETER:
			return new ServiceResponse("P14", "INVALID_PARAMETER", "Invalid request parameter", payload);
		case SESSION_EXPIRED:
			return new ServiceResponse("L17", "SESSION_EXPIRED", "Session token is already expired", payload);
		case INVALID_SIGNATURE:
			return new ServiceResponse("L21", "INVALID_SIGNATURE", "Invalid message signature", payload);
		case INVALID_URL:
			return new ServiceResponse("B21", "INVALID_URL", "Invalid URL", payload);
		case PAYMENT_CODE_NOT_FOUND:
			return new ServiceResponse("V14", "PAYMENT_CODE_NOT_FOUND",
					"The specified Payment Code already expired or not found", payload);
		case INVALID_OTP:
			return new ServiceResponse("C14", "INVALID_OTP", "OTP already expired or not found", payload);
		default:
			return new ServiceResponse("E99", "UNKNOWN_ERROR", "Unknown Error", payload);
		}
	}
}
