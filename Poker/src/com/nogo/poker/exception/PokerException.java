package com.nogo.poker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
public class PokerException extends RuntimeException {
  private static final long serialVersionUID = -5566936335905513713L;
}
