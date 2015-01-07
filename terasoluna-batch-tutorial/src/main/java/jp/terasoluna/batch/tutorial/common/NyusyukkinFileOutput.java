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

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * 入出金情報のファイル出力パラメータクラス。
 */
@FileFormat(overWriteFlg = true, fileEncoding = "MS932")
public class NyusyukkinFileOutput {

    /**
     * 取引日
     */
    @OutputFileColumn(columnIndex = 0, columnFormat = "yyyyMMdd")
    private Date torihikibi;

    /**
     * 支店名
     */
    @OutputFileColumn(columnIndex = 1)
    private String shitenName;

    /**
     * 入金回数
     */
    @OutputFileColumn(columnIndex = 2)
    private int nyukinNum;

    /**
     * 出金回数
     */
    @OutputFileColumn(columnIndex = 3)
    private int syukkinNum;

    /**
     * 入金合計
     */
    @OutputFileColumn(columnIndex = 4)
    private int nyukinSum;

    /**
     * 出金合計
     */
    @OutputFileColumn(columnIndex = 5)
    private int syukkinSum;

    /**
     * 取引日を取得する。
     * 
     * @return torihikibi
     */
    public Date getTorihikibi() {
        return torihikibi;
    }

    /**
     * 取引日を設定する。
     * 
     * @param torihikibi
     */
    public void setTorihikibi(Date torihikibi) {
        this.torihikibi = torihikibi;
    }

    /**
     * 支店名を取得する。
     * 
     * @return shitenName
     */
    public String getShitenName() {
        return shitenName;
    }

    /**
     * 支店名を設定する。
     * 
     * @param shitenName
     */
    public void setShitenName(String shitenName) {
        this.shitenName = shitenName;
    }

    /**
     * 入金回数を取得する。
     * 
     * @return nyukinNum
     */
    public int getNyukinNum() {
        return nyukinNum;
    }

    /**
     * 入金回数を設定する。
     * 
     * @param nyukinNum
     */
    public void setNyukinNum(int nyukinNum) {
        this.nyukinNum = nyukinNum;
    }

    /**
     * 出金回数を取得する。
     * 
     * @return syukkinNum
     */
    public int getSyukkinNum() {
        return syukkinNum;
    }

    /**
     * 出金回数を設定する。
     * 
     * @param syukkinNum
     */
    public void setSyukkinNum(int syukkinNum) {
        this.syukkinNum = syukkinNum;
    }

    /**
     * 入金合計を取得する。
     * 
     * @return nyukinSum
     */
    public int getNyukinSum() {
        return nyukinSum;
    }

    /**
     * 入金合計を設定する。
     * 
     * @param nyukinSum
     */
    public void setNyukinSum(int nyukinSum) {
        this.nyukinSum = nyukinSum;
    }

    /**
     * 出金合計を取得する。
     * 
     * @return syukkinSum
     */
    public int getSyukkinSum() {
        return syukkinSum;
    }

    /**
     * 出金合計を設定する。
     * 
     * @param syukkinSum
     */
    public void setSyukkinSum(int syukkinSum) {
        this.syukkinSum = syukkinSum;
    }

}
