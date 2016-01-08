package jp.terasoluna.batch.functionsample.b009;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


import jp.terasoluna.fw.batch.message.MessageAccessor;
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
 * 入力チェックエラーがあった場合は、指定されたログレベルでログにエラーメッセージを出力し、指定されたValidateErrorStatusを返却する。<br>
 * (指定がない場合はINFOレベルで出力し、ValidateErrorStatus.SKIPを返す)
 * </p>
 */
public class CustomValidationErrorHandler implements ValidationErrorHandler {

    /**
     * Log.
     */
    private static Logger logger = LoggerFactory
            .getLogger(CustomValidationErrorHandler.class);

    /**
     * 入力チェックエラー時に返すステータス.<br>
     * <p>
     * デフォルトではValidateErrorStatus.SKIP
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
    protected LogLevel logLevel = LogLevel.INFO;

    /**
     * 入力チェックエラーメッセージ取得のためのメッセージ管理機能.
     */
    MessageAccessor messageAccessor;

    /**
     * コンストラクタ.<br>
     */
    public CustomValidationErrorHandler(MessageAccessor messageAccessor) {
        // メッセージ管理機能をビジネスロジックから設定する(本クラスがDIコンテナの管理下ではないことに注意する)
        this.messageAccessor = messageAccessor;
    }

    /**
     * コンストラクタ.<br>
     * @param messageAccessor メッセージ管理機能
     * @param logLevel LogLevel ログレベル
     */
    public CustomValidationErrorHandler(MessageAccessor messageAccessor,
            LogLevel logLevel) {
        this(messageAccessor);
        this.logLevel = logLevel;
    }

    /**
     * コンストラクタ.<br>
     * @param messageAccessor メッセージ管理機能
     * @param validateStatus ValidateStatus 入力チェックエラー時に返すステータス
     */
    public CustomValidationErrorHandler(MessageAccessor messageAccessor,
            ValidateErrorStatus validateStatus) {
        this(messageAccessor);
        this.validateStatus = validateStatus;
    }

    /**
     * コンストラクタ.<br>
     * @param messageAccessor メッセージ管理機能
     * @param validateStatus ValidateStatus 入力チェックエラー時に返すステータス
     * @param logLevel LogLevel ログレベル
     */
    public CustomValidationErrorHandler(MessageAccessor messageAccessor,
            ValidateErrorStatus validateStatus, LogLevel logLevel) {
        this(messageAccessor);
        this.validateStatus = validateStatus;
        this.logLevel = logLevel;
    }

    /**
     * 指定されたログレベルでログにエラーメッセージを出力し、指定されたValidateErrorStatusを返却
     * @param dataValueObject DataValueObject
     * @param errors Errors
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
        return validateStatus;
    }

    /**
     * ログ出力
     * @param dataValueObject DataValueObject
     * @param errors Errors
     */
    private void outputLog(DataValueObject dataValueObject, Errors errors) {

        List<FieldError> fieldErrorsList = errors.getFieldErrors();

        for (FieldError fe : fieldErrorsList) {
            String errorMessage = messageAccessor.getMessage(fe);
            outputLogWithProperLevel(errorMessage);
        }
    }

    /**
     * 適切なレベルで指定されたメッセージを出力
     * @param errorMessage メッセージ
     */
    private void outputLogWithProperLevel(String errorMessage) {
        switch (this.logLevel) {
        case TRACE:
            if (logger.isTraceEnabled()) {
                logger.trace(errorMessage);
            }
            break;
        case DEBUG:
            if (logger.isDebugEnabled()) {
                logger.debug(errorMessage);
            }
            break;
        case INFO:
            if (logger.isInfoEnabled()) {
                logger.info(errorMessage);
            }
            break;
        case WARN:
            if (logger.isWarnEnabled()) {
                logger.warn(errorMessage);
            }
            break;
        case ERROR:
            if (logger.isErrorEnabled()) {
                logger.error(errorMessage);
            }
            break;
        default:
            if (logger.isInfoEnabled()) {
                logger.info(errorMessage);
            }
            break;
        }
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

}
