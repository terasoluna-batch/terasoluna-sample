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

package jp.terasoluna.batch.tutorial.sample000;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import jp.terasoluna.batch.tutorial.common.NyusyukkinData;
import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ビジネスロジッククラス。(入出金テーブルにランダムデータを出力するクラス)
 */
@Component
public class SMP000BLogic extends AbstractTransactionBLogic {

	/**
	 * ログクラス。
	 */
	private static final Log log = LogFactory.getLog(SMP000BLogic.class);

	@Autowired
	protected UpdateDAO updateDAO;

	@Override
	public int doMain(BLogicParam param) {

		int resultNum = 0;

		// DBの作成行数。デフォルトとして100を設定
		int maxNumber = 100;

		// 引数が存在した場合は入金テーブルの作成行数を変更
		if (null != param.getJobArgNm1()) {
			maxNumber = Integer.parseInt(param.getJobArgNm1());
		}

		// データ生成用ランダム関数
		Random random = new Random();

		NyusyukkinData nyusyukkin = new NyusyukkinData();

		try {
			// 入出金テーブルのデータをクリア
			updateDAO.execute("SMP000.deleteNyusyukkin", null);

			for (int count = 1; count <= maxNumber; count++) {

				// ランダムに支店名を決定
				String shitenName = "";
				int shitenNum = random.nextInt(3) + 1;
				if (shitenNum == 1) {
					shitenName = "東京";
				} else if (shitenNum == 2) {
					shitenName = "埼玉";
				} else if (shitenNum == 3) {
					shitenName = "千葉";
				}
				String kokyakuId = String.valueOf(random.nextInt(1000) + 1);

				// パディング(0埋め)
				while (kokyakuId.length() < 4) {
					kokyakuId = "0" + kokyakuId;
				}
				int nyusyukkinKubun = random.nextInt(2);
				int kingaku = random.nextInt(1000000) + 1;

				// 日付を(2010/01/01～2011/12/31の間で)ランダムで作成。
				StringBuilder hiduke = new StringBuilder();
				hiduke.append(String.valueOf(2010 + random.nextInt(2)) + "/");
				int month = random.nextInt(12) + 1;
				hiduke.append(String.valueOf(month) + "/");
				if (month == 4 || month == 6 || month == 9 || month == 11) {
					hiduke.append(String.valueOf(random.nextInt(30) + 1));
				} else if (month == 2) {
					hiduke.append(String.valueOf(random.nextInt(28) + 1));
				} else {
					hiduke.append(String.valueOf(random.nextInt(31) + 1));
				}
				Date date = DateFormat.getDateInstance().parse(
						hiduke.toString());

				// １行分のオブジェクト作成
				nyusyukkin.setShitenName(shitenName);
				nyusyukkin.setKokyakuId(kokyakuId);
				nyusyukkin.setNyusyukkinKubun(nyusyukkinKubun);
				nyusyukkin.setKingaku(kingaku);
				nyusyukkin.setTorihikibi(date);

				// DBへのデータ登録
				updateDAO.execute("SMP000.insertNyusyukkin", nyusyukkin);
			}

			if (log.isDebugEnabled()) {
				log.debug("end:NyusyukkinReset");
			}

		} catch (Exception e) {
			// 例外処理(エラーコードの設定)
			resultNum = -1;
		}
		return resultNum;
	}
}
