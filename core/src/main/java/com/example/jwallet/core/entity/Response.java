package com.example.jwallet.core.entity;

public abstract class Response<T> {
	private BaseResponse response = new BaseResponse();
	private T data;

	public BaseResponse getResponse() {
		return response;
	}

	public void setResponse(BaseResponse response) {
		this.response = response;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static class BaseResponse {

		private String responseCode = "0";
		private String responseMessage = "OK";

		public String getResponseCode() {
			return responseCode;
		}

		public void setResponseCode(String responseCode) {
			this.responseCode = responseCode;
		}

		public String getResponseMessage() {
			return responseMessage;
		}

		public void setResponseMessage(String responseMessage) {
			this.responseMessage = responseMessage;
		}
	}
}
