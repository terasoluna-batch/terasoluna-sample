/*
 * Copyright (c) 2011 NTT DATA Corporation
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

package jp.terasoluna.batch.functionsample.b009;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jp.terasoluna.fw.collector.validate.ValidateErrorStatus;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;
import jp.terasoluna.fw.collector.vo.DataValueObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

/**
 * 入力チェックエラーハンドラの実装クラス.<br>
 * <p>
 * 入力チェックエラーがあった場合は、INFOログにエラーコードを出力する。<br>
 * 戻り値は必ずValidateStatus.SKIPを返す。
 * </p>
 */
public class SkipValidationErrorHandler implements ValidationErrorHandler {
    /**
     * Log.
     */
    private static Logger logger = LoggerFactory
            .getLogger(SkipValidationErrorHandler.class);

    /** ログレベル(TRACE) */
    public static final String LOG_LEVEL_TRACE = "trace";

    /** ログレベル(DEBUG) */
    public static final String LOG_LEVEL_DEBUG = "debug";

    /** ログレベル(INFO) */
    public static final String LOG_LEVEL_INFO = "info";

    /** ログレベル(WARN) */
    public static final String LOG_LEVEL_WARN = "warn";

    /** ログレベル(ERROR) */
    public static final String LOG_LEVEL_ERROR = "error";

    /** ログレベル(FATAL) */
    public static final String LOG_LEVEL_FATAL = "fatal";

    /**
     * 入力チェックエラー時に返すステータス.<br>
     * <p>
     * デフォルトではERROR_SKIP
     * </p>
     */
    protected ValidateErrorStatus validateStatus = ValidateErrorStatus.SKIP;

    /**
     * 入力チェックエラー件数.<br>
     */
    protected int errorFieldCount = 0;

    /**
     * エラーキュー.<br>
     */
    protected BlockingQueue<Errors> errorsQueue = new LinkedBlockingQueue<Errors>();

    /**
     * ログレベル.<br>
     */
    protected String logLevel = LOG_LEVEL_INFO;

    /**
     * コンストラクタ.<br>
     */
    public SkipValidationErrorHandler() {
        super();
    }

    /**
     * コンストラクタ.<br>
     * @param logLevel String ログレベル
     */
    public SkipValidationErrorHandler(String logLevel) {
        this();
        this.logLevel = logLevel;
    }

    /**
     * コンストラクタ.<br>
     * @param validateStatus ValidateStatus 入力チェックエラー時に返すステータス
     */
    public SkipValidationErrorHandler(ValidateErrorStatus validateStatus) {
        this();
        this.validateStatus = validateStatus;
    }

    /**
     * コンストラクタ.<br>
     * @param validateStatus ValidateStatus 入力チェックエラー時に返すステータス
     * @param logLevel String ログレベル
     */
    public SkipValidationErrorHandler(ValidateErrorStatus validateStatus,
            String logLevel) {
        this();
        this.validateStatus = validateStatus;
        this.logLevel = logLevel;
    }

    /*
     * (non-Javadoc)
     * @seejp.terasoluna.fw.ex.iterator.validate.ValidationErrorHandler#handleValidationError(jp.terasoluna.fw.ex.iterator.vo.
     * DataValueObject, org.springframework.validation.Errors)
     */
    public ValidateErrorStatus handleValidationError(
            DataValueObject dataValueObject, Errors errors) {
        errorFieldCount++;

        try {
            if (errors != null) {
                errorsQueue.put(errors);
            }
        } catch (InterruptedException e) {
            // 無視する
        }

        // ログ出力
        outputLog(dataValueObject, errors);

        // ValidateStatusを返す
        return getValidateStatus(dataValueObject, errors);
    }

    /**
     * ログ出力
     * @param dataValueObject DataValueObject
     * @param errors Errors
     */
    protected void outputLog(DataValueObject dataValueObject, Errors errors) {
        if (LOG_LEVEL_TRACE.equalsIgnoreCase(this.logLevel)
                && logger.isTraceEnabled()) {
            logger.trace(logEdit(dataValueObject, errors));
        } else if (LOG_LEVEL_DEBUG.equalsIgnoreCase(this.logLevel)
                && logger.isDebugEnabled()) {
            logger.debug(logEdit(dataValueObject, errors));
        } else if (LOG_LEVEL_INFO.equalsIgnoreCase(this.logLevel)
                && logger.isInfoEnabled()) {
            logger.info(logEdit(dataValueObject, errors));
        } else if (LOG_LEVEL_WARN.equalsIgnoreCase(this.logLevel)
                && logger.isWarnEnabled()) {
            logger.warn(logEdit(dataValueObject, errors));
        } else if (LOG_LEVEL_ERROR.equalsIgnoreCase(this.logLevel)
                && logger.isErrorEnabled()) {
            logger.error(logEdit(dataValueObject, errors));
        } else if (logger.isTraceEnabled()) {
            logger.trace(logEdit(dataValueObject, errors));
        }
    }

    /**
     * ログ編集.<br>
     * @param dataValueObject DataValueObject
     * @param errors Errors
     * @return ログ
     */
    protected String logEdit(DataValueObject dataValueObject, Errors errors) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> fel = getFieldErrorList(errors);

        for (FieldError fe : fel) {
            sb.setLength(0);
            sb.append("ValidationError");
            sb.append(" dataCount:[");
            if (dataValueObject != null) {
                sb.append(dataValueObject.getDataCount());
            }
            sb.append("]");
            sb.append(" code:[");
            sb.append(fe.getCode());
            sb.append("]");
            sb.append(" objectName:[");
            sb.append(fe.getObjectName());
            sb.append("]");
            sb.append(" field:[");
            sb.append(fe.getField());
            sb.append("]");
            sb.append(" rejectedValue:[");
            sb.append(fe.getRejectedValue());
            sb.append("]");
        }
        return sb.toString();
    }

    /**
     * ValidateStatusを返す。
     * @param dataValueObject DataValueObject
     * @param errors Errors
     * @return ValidateStatus
     */
    protected ValidateErrorStatus getValidateStatus(
            DataValueObject dataValueObject, Errors errors) {
        return validateStatus;
    }

    /**
     * ErrorsからFieldErrorのリストを取得する
     * @param errors Errors
     * @return List<FieldError>
     */
    public static List<FieldError> getFieldErrorList(Errors errors) {
        List<FieldError> resultList = new ArrayList<FieldError>();

        if (errors != null) {
            List<?> errs = errors.getAllErrors();
            for (Object errObj : errs) {
                if (errObj instanceof FieldError) {
                    FieldError fe = (FieldError) errObj;
                    resultList.add(fe);
                }
            }
        }

        return resultList;
    }

    /**
     * 入力チェックエラー件数を取得する
     * @return int 入力チェックエラー件数
     */
    public int getErrorFieldCount() {
        return errorFieldCount;
    }

    /**
     * 入力チェックエラーの配列を取得する
     * @return Errors[] 入力チェックエラーの配列
     */
    public Errors[] getErrors() {
        return errorsQueue.toArray(new Errors[0]);
    }

    /**
     * ログレベルを設定する.<br>
     * <p>
     * <li>trace</li>
     * <li>debug</li>
     * <li>info</li>
     * <li>warn</li>
     * <li>error</li>
     * <li>fatal</li>
     * </p>
     * @param logLevel ログレベル
     */
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

}
