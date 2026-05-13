package br.com.archsoft.common.error;

import java.util.List;

public record ErrorResponse(String code, String message, List<ErrorDetail> details) {
}
