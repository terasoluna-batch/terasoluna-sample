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
package jp.terasoluna.batch.tutorial.common;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * 入出金情報のパラメータクラス。
 */
@FileFormat(overWriteFlg = true, fileEncoding = "MS932")
public class NyusyukkinData {

    /**
     * 支店名
     */
    @InputFileColumn(columnIndex = 0)
    @OutputFileColumn(columnIndex = 0)
    private String shitenName;

    /**
     * 顧客ID
     */
    @InputFileColumn(columnIndex = 1)
    @OutputFileColumn(columnIndex = 1)
    @NotEmpty
    private String kokyakuId;

    /**
     * 入出金区分 0:出金 1:入金
     */
    @InputFileColumn(columnIndex = 2)
    @OutputFileColumn(columnIndex = 2)
    private int nyusyukkinKubun;

    /**
     * 取引金額
     */
    @InputFileColumn(columnIndex = 3)
    @OutputFileColumn(columnIndex = 3)
    private int kingaku;

    /**
     * 取引日
     */
    @InputFileColumn(columnIndex = 4, columnFormat = "yyyyMMdd")
    @OutputFileColumn(columnIndex = 4, columnFormat = "yyyyMMdd")
    private Date torihikibi;

    /**
     * 支店名を取得する。
     * @return shitenName
     */
    public String getShitenName() {
        return shitenName;
    }

    /**
     * 支店名を設定する。
     * @param shitenName
     */
    public void setShitenName(String shitenName) {
        this.shitenName = shitenName;
    }

    /**
     * 顧客IDを取得する。
     * @return kokyakuId
     */
    public String getKokyakuId() {
        return kokyakuId;
    }

    /**
     * 顧客IDを設定する。
     * @param kokyakuId
     */
    public void setKokyakuId(String kokyakuId) {
        this.kokyakuId = kokyakuId;
    }

    /**
     * 入出金区分を取得する。
     * @return nyusyukkinKubun
     */
    public int getNyusyukkinKubun() {
        return nyusyukkinKubun;
    }

    /**
     * 入出金区分を設定する。
     * @param nyusyukkinKubun
     */
    public void setNyusyukkinKubun(int nyusyukkinKubun) {
        this.nyusyukkinKubun = nyusyukkinKubun;
    }

    /**
     * 取引金額を取得する。
     * @return kingaku
     */
    public int getKingaku() {
        return kingaku;
    }

    /**
     * 取引金額を設定する。
     * @param kingaku
     */
    public void setKingaku(int kingaku) {
        this.kingaku = kingaku;
    }

    /**
     * 取引日を取得する。
     * @return torihikibi
     */
    public Date getTorihikibi() {
        return torihikibi;
    }

    /**
     * 取引日を設定する。
     * @param torihikibi
     */
    public void setTorihikibi(Date torihikibi) {
        this.torihikibi = torihikibi;
    }
}
