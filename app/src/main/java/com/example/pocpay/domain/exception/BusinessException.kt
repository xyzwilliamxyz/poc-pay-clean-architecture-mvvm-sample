package com.example.pocpay.domain.exception

import java.lang.Exception

class BusinessException(
    message: String
): Exception(message)