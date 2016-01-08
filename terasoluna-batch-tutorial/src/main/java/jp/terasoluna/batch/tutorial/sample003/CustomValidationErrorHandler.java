/*
 * Copyright (c) 2011-2016 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.terasoluna.batch.tutorial.sample003;

import java.util.List;

import jp.terasoluna.fw.collector.validate.ValidateErrorStatus;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;
import jp.terasoluna.fw.collector.vo.DataValueObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class CustomValidationErrorHandler implements ValidationErrorHandler {

    /**
     * Log.
     */
    private static final Logger log = LoggerFactory
            .getLogger(CustomValidationErrorHandler.class);

    public ValidateErrorStatus handleValidationError(
            DataValueObject dataValueObject, Errors errors) {

        if (log.isWarnEnabled()) {
            List<FieldError> fieldErrorList = errors.getFieldErrors();
            for (FieldError fieldError : fieldErrorList) {
                log.warn("{} フィールドにおいて必須入力チェックエラー発生", fieldError.getField());
            }
        }

        // エラーデータをとばして、そのまま処理を続行する
        return ValidateErrorStatus.SKIP;
    }
}
