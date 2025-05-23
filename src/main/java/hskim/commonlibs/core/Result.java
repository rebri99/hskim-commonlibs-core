/*
 * Copyright 2025 Kim HyeonSu (rebri99@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hskim.commonlibs.core;

/**
 * true/false 결과와 함께 데이터를 전달하는 클래스
 *
 * @author Kim HyeonSu (rebri99@gmail.com)
 * @since 2025. 5. 23.
 */
public class Result<T> {

	/** 결과 데이터 */
	private T data;

	/** true/false 값 */
	private boolean result;

	/** 성공/실패에 관한 정보 */
	private String meassage;

	/**
	 * 데이터가 null이고, 결과가 false인 기본 생성자
	 * 
	 * @see Result#Result(Object, boolean)
	 */
	public Result() {
		this(null, false);
	}

	/**
	 * 데이터를 가지고, 결과가 false인 생성자
	 * 
	 * @param data 결과 데이터
	 * 
	 * @see Result#Result(Object, boolean)
	 */
	public Result(T data) {
		this(data, false);
	}

	/**
	 * 결과 데이터와 true/false 포함한 객체 생성자
	 * 
	 * @param data   결과 데이터
	 * @param result true/false
	 * 
	 * @since 2025. 5. 23.
	 * @author Kim HyeonSu (rebri99@gmail.com)
	 */
	public Result(T data, boolean result) {
		this.data = data;
		this.result = result;
	}

	/**
	 * 결과 데이터를 반환한다.
	 * 
	 * @return the data
	 * 
	 * @since 2025. 5. 23.
	 * @author Kim HyeonSu (rebri99@gmail.com)
	 */
	public T getData() {
		return data;
	}

	/**
	 * @return the meassage
	 * 
	 * @since 2025. 5. 23.
	 * @author Kim HyeonSu (rebri99@gmail.com)
	 */
	public String getMeassage() {
		return meassage;
	}

	/**
	 * 결과를 반환한다.
	 * 
	 * @return the result
	 * 
	 * @since 2025. 5. 23.
	 * @author Kim HyeonSu (rebri99@gmail.com)
	 */
	public boolean getResult() {
		return result;
	}

	/**
	 * 실패 여부를 제공한다.
	 *
	 * @return !{@link Result#getResult()}
	 * 
	 * @since 2025. 5. 23.
	 * @author Kim HyeonSu (rebri99@gmail.com)
	 */
	public boolean isError() {
		return !this.getResult();
	}

	/**
	 * 성공 여부를 제공한다.
	 *
	 * @return {@link Result#getResult()}
	 * 
	 * @since 2025. 5. 23.
	 * @author Kim HyeonSu (rebri99@gmail.com)
	 */
	public boolean isSuccess() {
		return this.getResult();
	}

	/**
	 * 데이터를 설정하고 이전 데이터를 반환한다.
	 * 
	 * @param data the data to set
	 * 
	 * @since 2025. 5. 23.
	 * @author Kim HyeonSu (rebri99@gmail.com)
	 */
	public Result<T> setData(T data) {
		this.data = data;
		return this;
	}

	/**
	 * @param meassage the meassage to set
	 * 
	 * @return this
	 * 
	 * @since 2025. 5. 23.
	 * @author Kim HyeonSu (rebri99@gmail.com)
	 */
	public Result<T> setMeassage(String meassage) {
		this.meassage = meassage;
		return this;
	}

	/**
	 * 결과값을 설정하고 이전 결과값을 반환한다.
	 * 
	 * @param result the result to set
	 * 
	 * @since 2025. 5. 23.
	 * @author Kim HyeonSu (rebri99@gmail.com)
	 */
	public boolean setResult(boolean result) {
		boolean latestResult = this.result;
		this.result = result;

		return latestResult;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Result [data=");
		builder.append(data);
		builder.append(", result=");
		builder.append(result);
		builder.append(", meassage=");
		builder.append(meassage);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * 에러 결과 객체를 제공한다.<br>
	 *
	 * <pre>
	 *  
	 * [개정이력]
	 *      날짜      | 작성자 |       내용 
	 * ------------------------------------------
	 * 2025. 5. 23.    김현수   최초작성
	 * </pre>
	 *
	 * @param <T>
	 * @param errorMessage
	 * @return
	 * 
	 * @since 2025. 5. 23.
	 * @author Kim HyeonSu (rebri99@gmail.com)
	 */
	public static <T> Result<T> error(String errorMessage) {
		return new Result<T>().setMeassage(errorMessage);
	}

	/**
	 * 성공 결과 객체를 제공한다.<br>
	 *
	 * <pre>
	 *  
	 * [개정이력]
	 *      날짜      | 작성자 |       내용 
	 * ------------------------------------------
	 * 2025. 5. 23.    김현수   최초작성
	 * </pre>
	 *
	 * @param <T>
	 * @param data
	 * @return
	 * 
	 * @since 2025. 5. 23.
	 * @author Kim HyeonSu (rebri99@gmail.com)
	 */
	public static <T> Result<T> success(T data) {
		return new Result<T>(data, true);
	}

}
