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
package jp.terasoluna.batch.tutorial.sample005;

import jp.terasoluna.batch.tutorial.common.NyusyukkinData;
import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.db.DBCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;
import jp.terasoluna.fw.dao.QueryRowHandleDAO;
import jp.terasoluna.fw.file.dao.FileLineWriter;
import jp.terasoluna.fw.file.dao.FileUpdateDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * ビジネスロジッククラス。(入出金テーブルをcsvファイルに出力するクラス)
 */
@Component
public class SMP005BLogic extends AbstractTransactionBLogic {

	private static final Log log = LogFactory.getLog(SMP005BLogic.class);

	@Autowired
	protected QueryRowHandleDAO queryRowHandleDAO;

	@Autowired
	@Qualifier(value = "csvFileUpdateDAO")
	protected FileUpdateDAO csvFileUpdateDAO;

	public int doMain(BLogicParam param) {

		// ジョブ終了コード（0:正常終了、-1:異常終了）
		int returnCode = 0;

		// コレクタ
		Collector<NyusyukkinData> collector = new DBCollector<NyusyukkinData>(
				this.queryRowHandleDAO, "SMP005.selectNyusyukkin", null);

		// ファイル出力用行ライタの取得(入金用)
		FileLineWriter<NyusyukkinData> fileLineWriterNyukin = csvFileUpdateDAO
				.execute("outputFile/SMP005_output_nyukin.csv",
						NyusyukkinData.class);

		// ファイル出力用行ライタの取得(出金用)
		FileLineWriter<NyusyukkinData> fileLineWriterSyukkin = csvFileUpdateDAO
				.execute("outputFile/SMP005_output_syukkin.csv",
						NyusyukkinData.class);

		try {
			// DBから取得したデータを格納するオブジェクト
			NyusyukkinData inputData = null;

			while (collector.hasNext()) {
				// DBからデータを取得
				inputData = collector.next();

				// ファイルへデータを出力（1行）
				// 入出金区分により出力ファイルを変更
				if (inputData != null && inputData.getNyusyukkinKubun() == 0) {
					fileLineWriterNyukin.printDataLine(inputData);
				}
				if (inputData != null && inputData.getNyusyukkinKubun() == 1) {
					fileLineWriterSyukkin.printDataLine(inputData);
				}
			}
		} catch (DataAccessException e) {
			if (log.isErrorEnabled()) {
				log.error("データアクセスエラーが発生しました", e);
			}

			returnCode = -1;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("エラーが発生しました", e);
			}

			returnCode = -1;
		} finally {
			// コレクタのクローズ
			CollectorUtility.closeQuietly(collector);

			// ファイルのクローズ
			CollectorUtility.closeQuietly(fileLineWriterNyukin);
			CollectorUtility.closeQuietly(fileLineWriterSyukkin);

			// 正常終了時のログ
			if (returnCode == 0 && log.isInfoEnabled()) {
				log.info("ファイル書き込みが正常に終了しました。");
			}
		}

		return returnCode;
	}
}
