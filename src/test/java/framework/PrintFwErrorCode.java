package framework;

import java.util.Arrays;

import com.jy.app.exception.FwErrorCode;

public class PrintFwErrorCode {
	public static void main(String[] args) {

		Arrays	.stream(FwErrorCode.values())
				.forEach(code -> {

					System.out.println(String.format(	"|%-10s|%-50s|",
														code.getCode(),
														code.getMessage()));

				});
	}

}
