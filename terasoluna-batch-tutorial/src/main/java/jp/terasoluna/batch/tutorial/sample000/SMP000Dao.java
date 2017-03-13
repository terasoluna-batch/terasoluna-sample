/*
 * Copyright (c) 2011-2017 NTT DATA Corporation
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

import jp.terasoluna.batch.tutorial.common.NyusyukkinData;

public interface SMP000Dao {

    /**
     * 入出金情報を削除する。
     * @return 削除件数
     */
    public int deleteNyusyukkinData();

    /**
     * 入出金情報を1件挿入する。
     * @param data 入出金情報
     * @return 挿入件数
     */
    public int insertNyusyukkinData(NyusyukkinData data);
}
